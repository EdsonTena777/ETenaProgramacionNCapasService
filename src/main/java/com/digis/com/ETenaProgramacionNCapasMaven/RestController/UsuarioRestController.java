
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.PaisDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.RolDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> updateStatus(@PathVariable("idUsuario") int idUsuario, @RequestBody Usuario usaurio){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            Result result = usuarioDAOJPAImplementation.UpdateStatus(usuario);
            if(result.correct){
                if(result.object != null){
                    return ResponseEntity.ok(result.object);
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
}
