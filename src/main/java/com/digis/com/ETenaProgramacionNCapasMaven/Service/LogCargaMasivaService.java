
package com.digis.com.ETenaProgramacionNCapasMaven.Service;

import com.digis.com.ETenaProgramacionNCapasMaven.Enums.EstatusCargaMasiva;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class LogCargaMasivaService implements iLogCargaMasivaService{
    
    private static String CARPETA_LOG = "src/main/resources/logs";
    
    private String nombreArchivoLog;
    
    public LogCargaMasivaService(){
        String fechaArchivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        nombreArchivoLog = "logCargaMasiva" + fechaArchivo + ".txt"; 
    }   
    
    @Override
    public void escribirLogs(String key, String ruta, EstatusCargaMasiva status, String detalle){
        try{
            String rutaNormalizada = ruta.replace('\\', '/');
            String rutaBase = System.getProperty("user.dir");
            File carpeta = new File(rutaBase + "/" + CARPETA_LOG);
            if(!carpeta.exists()){
                carpeta.mkdirs();
            }
            File archivo = new File(carpeta, nombreArchivoLog);
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivo, true))){
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                bufferedWriter.write(key + "|" + rutaNormalizada + "|" + status + "|" + fechaHora + "|" + detalle);
                bufferedWriter.newLine();
            }
        }catch(Exception ex){
            ex.getLocalizedMessage();
        }
    }
}
