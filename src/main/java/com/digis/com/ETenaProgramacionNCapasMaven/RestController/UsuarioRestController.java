
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.PaisDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.RolDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Autowired
    private RolDAOJPAImplementation rolDAOJPAImplementation;
    
    @Autowired
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    @GetMapping
    public ResponseEntity<?> GetAll(){
        try{
            Result result = usuarioDAOJPAImplementation.GetAll();
            if(result.correct){
                if(result.objects != null && !result.objects.isEmpty()){
                    return ResponseEntity.ok(result);

                } else{
                    return ResponseEntity.noContent().build();
                }
            } else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> GetById(@PathVariable("idUsuario") int idUsuario) {
        try {
            Result result = usuarioDAOJPAImplementation.GetById(idUsuario);
            if (result.correct) {
                if (result.object != null) { 
                    return ResponseEntity.ok(result.object);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex);
        }
    }
    @GetMapping("/rolGetAll")
    public ResponseEntity<?> rolGetAll(){
        try{
            Result result = rolDAOJPAImplementation.rolGetAll();
            if(result.correct){
                if(result.objects != null && !result.objects.isEmpty()){
                    return ResponseEntity.ok(result);

                } else{
                    return ResponseEntity.noContent().build();
                }
            } else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @GetMapping("/paisGetAll")
    public ResponseEntity<?> paisGetAll(){
        try{
            Result result = paisDAOJPAImplementation.GetAll();
            if(result.correct){
                if(result.objects != null && !result.objects.isEmpty()){
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @GetMapping("/estadoGetAll")
    public ResponseEntity<?> estadoGetAll(){
        try{
            Result result = estadoDAOJPAImplementation.GetAll();
            if(result.correct){
                if(result.objects != null && !result.objects.isEmpty()){
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @PutMapping("/updateStatus/{idUsuario}")
    public ResponseEntity<?> updateStatus(@PathVariable int idUsuario,
                                          @RequestParam int status){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setStatus(status);

            Result<Usuario> result = usuarioDAOJPAImplementation.UpdateStatus(usuario);

            if(result.correct){
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        }catch(Exception ex){
            Result<Usuario> result = new Result<>();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            return ResponseEntity.status(500).body(result);
        }
    }
    @PutMapping("/updateImagen/{idUsuario}")
    public ResponseEntity<?> updateImagen(@PathVariable int idUsuario, @RequestBody Usuario usuario){
        try{
            usuario.setIdUsuario(idUsuario);       
            Result<Usuario> result = usuarioDAOJPAImplementation.UpdateImagen(usuario);
            if(result.correct){
                if(result.object != null){
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(
            @RequestPart("datos") String datos,
            @RequestPart(name = "imagen", required = false) MultipartFile imagen) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Usuario usuario = objectMapper.readValue(datos, Usuario.class);

            if (imagen != null && !imagen.isEmpty()) {
                String nombreArchivo = imagen.getOriginalFilename();
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();

                if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                    byte[] bytes = imagen.getBytes();
                    String imagenBase64 = Base64.getEncoder().encodeToString(bytes);
                    usuario.setImagen(imagenBase64);
                } else {
                    Result<Usuario> result = new Result<>();
                    result.correct = false;
                    result.errorMessage = "Formato de imagen no permitido";
                    return ResponseEntity.badRequest().body(result);
                }
            }

            Result<Usuario> result = usuarioDAOJPAImplementation.Add(usuario);

            if (result.correct) {
                return ResponseEntity.status(201).body(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getLocalizedMessage());
        }
    }
    
    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> update(@PathVariable int idUsuario, @RequestBody Usuario usuario){
        try{
            usuario.setIdUsuario(idUsuario);
            Result<Usuario> result = usuarioDAOJPAImplementation.UpdateUsuairo(usuario);
            if(result.correct){
                if(result.object != null){
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch (Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> delete(@PathVariable int idUsuario){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);

            Result<Usuario> result = usuarioDAOJPAImplementation.Delete(usuario);

            if(result.correct){
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        }catch(Exception ex){
            ex.printStackTrace();

            Result<Usuario> result = new Result<>();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = null;

            return ResponseEntity.status(500).body(result);
        }
    }
    @GetMapping("/getAllDinamico")
    public ResponseEntity<?> getAllDinamico(@RequestParam(required = false) String nombre, 
                                            @RequestParam(required = false) String apellidoPaterno, 
                                            @RequestParam(required = false) String apellidoMaterno, 
                                            @RequestParam(required = false) String rol){
        try{
            Result<Usuario> result = usuarioDAOJPAImplementation.GetAllDinamico(nombre, apellidoPaterno, apellidoMaterno, rol);

            if(result.correct){
                if(result.objects != null){ 
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.ok(result); 
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }

}
