
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.DireccionDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
