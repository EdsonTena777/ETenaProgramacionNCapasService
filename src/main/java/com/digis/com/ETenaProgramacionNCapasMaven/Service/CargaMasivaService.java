
package com.digis.com.ETenaProgramacionNCapasMaven.Service;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.Enums.EstatusCargaMasiva;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.ErroresArchivo;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Rol;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CargaMasivaService implements iCargaMasivaService{
    
    @Autowired
    private LogCargaMasivaService logCargaMasivaService;
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Override
    public Result validarArchivo(MultipartFile archivo){
        Result result = new Result();
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(archivo.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String key = hexString.toString();
            String rutaBase = System.getProperty("user.dir");
            String rutaCarpeta = "src/main/resources/archivosCM";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));  
            String nombreArchivo = fecha + archivo.getOriginalFilename();
            String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
            File fileDestino = new File(rutaArchivo);
            archivo.transferTo(fileDestino);
            logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Recibido, "Archivo validacion");
            String extension = archivo.getOriginalFilename().split("\\.")[1];
            List<Usuario> usuarios;
            if(extension.equals("txt")){
                    usuarios = LecturaArchivoTxt(fileDestino); 
                }else if(extension.equals("xlsx")){
                    usuarios = LecturaArchivoXLSX(fileDestino);
                }else{
                    logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Error, "Extension no soportada");
                    result.correct = false;
                    result.errorMessage = "Extension no soportada";
                    return result;
                }
            List<ErroresArchivo> errores = ValidarDatos(usuarios);
                if (errores.isEmpty()) {
                    logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Validado, "Archivo validado");
                    result.correct = true;
                    result.object = key;
                    result.objects = new ArrayList<>();
                    result.errorMessage = rutaArchivo;
                } else {
                    logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Invalidado, "Archivo con " +errores.size()+ " errores de validadcion");
                    result.correct = false;
                    result.object = key;
                    result.objects = errores;
                    result.errorMessage = rutaArchivo;
                }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;
    }
    
    @Override 
    public Result procesarArchivo(String rutaArchivo, String key){
        Result result = new Result();
        try{
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Error, "El archivo no existe");
                result.correct = false;
                result.errorMessage = "El archivo no existe";
                return result;
            }
            String extension = rutaArchivo.substring(rutaArchivo.lastIndexOf('.') + 1).toLowerCase();
            
            List<Usuario> usuarios;
            if (extension.equals("txt")) {
                usuarios = LecturaArchivoTxt(archivo);
            } else if (extension.equals("xlsx")) {
                usuarios = LecturaArchivoXLSX(archivo);
            } else {
                logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Error, "Extension no soportada");
                result.correct = false;
                result.errorMessage = "Extension no soportada";
                return result;
            }
            logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Procesando, "Iniciando procesamiento");
            Result resultadoAddAll = usuarioDAOJPAImplementation.AddAll(usuarios);
            
            if(resultadoAddAll.correct){
                logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Procesado, "Archivo procesado");
                result.correct = true;
            } else {
                logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Error, resultadoAddAll.errorMessage);
                result.correct = false;
                result.errorMessage = resultadoAddAll.errorMessage;
            }
        }catch(Exception ex){
            logCargaMasivaService.escribirLogs(key, rutaArchivo, EstatusCargaMasiva.Error, ex.getLocalizedMessage());
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }
    return result;
    }
    
    public List<Usuario> LecturaArchivoXLSX(File archivo){
        List<Usuario> usuarios = new ArrayList<>();
        Result result = new Result();
        try(InputStream inputStream = new FileInputStream(archivo);
               XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            usuarios = new ArrayList<>();
            for (Row row : sheet){
                Usuario usuario = new Usuario();
                
                usuario.setUsername(row.getCell(0).toString());
                usuario.setNombre(row.getCell(1).toString());
                usuario.setApellidoPaterno(row.getCell(2).toString());
                usuario.setApellidoMaterno(row.getCell(3).toString());
                usuario.setTelefono(formatter.formatCellValue(row.getCell(4)));
                usuario.setEmail(row.getCell(5).toString());
                usuario.setPassword(row.getCell(6).toString());
                Cell celdaFecha = row.getCell(7);
                usuario.setFechaNacimiento(celdaFecha.getDateCellValue());
                usuario.setSexo(row.getCell(8).toString());
                usuario.setCelular(formatter.formatCellValue(row.getCell(9)));
                usuario.setCURP(row.getCell(10).toString());
                usuario.Roles = new Rol();
                usuario.Roles.setIdRol(Integer.parseInt(formatter.formatCellValue(row.getCell(11))));
                Direccion direccion = new Direccion();
                direccion.setCalle(row.getCell(12).toString());
                direccion.setNumeroInterior(formatter.formatCellValue(row.getCell(13)));
                direccion.setNumeroExterior(formatter.formatCellValue(row.getCell(14)));
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(Integer.parseInt(formatter.formatCellValue(row.getCell(15))));
                usuario.getDirecciones().add(direccion);
                usuarios.add(usuario);
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return usuarios;
    }
    public List<Usuario> LecturaArchivoTxt(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        Result result = new Result();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = bufferedReader.readLine()) != null){
                String[] datos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setDirecciones(new ArrayList<>());
                usuario.setUsername(datos[0]);
                usuario.setNombre(datos[1]);
                usuario.setApellidoPaterno(datos[2]);
                usuario.setApellidoMaterno(datos[3]);
                usuario.setTelefono(datos[4]);
                usuario.setEmail(datos[5]);
                usuario.setPassword(datos[6]);
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                usuario.setFechaNacimiento(formato.parse(datos[7]));
                usuario.setSexo(datos[8]);
                usuario.setCelular(datos[9]);
                usuario.setCURP(datos[10]);
                usuario.Roles = new Rol();
                usuario.Roles.setIdRol(Integer.parseInt(datos[11]));
                Direccion direccion = new Direccion();
                direccion.setCalle(datos[12]);
                direccion.setNumeroInterior(datos[13]);
                direccion.setNumeroExterior(datos[14]);
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(Integer.parseInt(datos[15]));
                usuario.getDirecciones().add(direccion);
                usuarios.add(usuario);
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return usuarios;
    }
    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios){
        List<ErroresArchivo> errores = new ArrayList<>();
        int fila = 0;
        for (Usuario usuario : usuarios){
            BindingResult bindingResult = validationService.ValidateObject(usuario);
            fila++;
            if(bindingResult.hasErrors()){
                for(ObjectError objectError : bindingResult.getFieldErrors()){
                    ErroresArchivo erroresArchivo = new ErroresArchivo();
                    FieldError fieldError = (FieldError) objectError;
                    erroresArchivo.setFila(fila);
                    erroresArchivo.setDato(fieldError.getField()); 
                    erroresArchivo.setDescripcion(fieldError.getDefaultMessage());
                    errores.add(erroresArchivo);
                }
            }
        }
        return errores;
    }
}
