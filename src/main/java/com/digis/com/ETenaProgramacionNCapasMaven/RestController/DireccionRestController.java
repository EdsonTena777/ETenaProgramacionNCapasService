
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.DireccionDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/direccion")
public class DireccionRestController {
    
    @Autowired
    private DireccionDAOJPAImplementation direccionDAOJPAImplentation;
    
    @GetMapping("/{idDireccion}")
    public ResponseEntity<?> GetById(@PathVariable("idDireccion") int idDireccion){
        try{
            Result result = direccionDAOJPAImplentation.GetById(idDireccion);
            if(result.correct){
                if(result.object != null){
                    return ResponseEntity.ok(result.object);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @GetMapping("/getbyid")
    public ResponseEntity<?> getById(@RequestParam int idDireccion) {

        Result result = direccionDAOJPAImplentation.GetById(idDireccion);

        if (result.correct) {
            if (result.object != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    @PutMapping("/{idDireccion}")
    public ResponseEntity<?> update(@PathVariable int idDireccion, @RequestBody Direccion direccion){
        try{
            direccion.setIdDireccion(idDireccion);
            Result<Direccion> result = direccionDAOJPAImplentation.UpdateDireccion(direccion);
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
    @PostMapping("/{idUsuario}/direccion")
    public ResponseEntity<?> add(@PathVariable int idUsuario, @RequestBody Direccion direccion){
        try{
            Result<Direccion> result = direccionDAOJPAImplentation.Add(idUsuario, direccion);
            if(result.correct){
                if(result.object != null){
                    return ResponseEntity.ok(result);
                }else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<?> delete (@PathVariable int idDireccion){
        try{
            Direccion direccion = new Direccion();
            direccion.setIdDireccion(idDireccion);
            Result<Direccion> result = direccionDAOJPAImplentation.Delete(direccion);
            if(result.correct){
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        }catch(Exception ex){
            ex.printStackTrace();

            Result<Direccion> result = new Result<>();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = null;

            return ResponseEntity.status(500).body(result);
        }
    }
}
