
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.ColoniaDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.MunicipioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogo")
public class CatalogoRestController {
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;

    @GetMapping("/estado/getbyidpais")
    public ResponseEntity<Result> estadoGetByIdPais(@RequestParam int idPais) {
        try {
            Result result = estadoDAOJPAImplementation.GetByIdPais(idPais);
            
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result); 
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null); 
        }
    }
    
    @GetMapping("/municipio/getbyidestado")
    public ResponseEntity<Result> municipioGetByIdEstado(@RequestParam int idEstado) {
        try {
            Result result = municipioDAOJPAImplementation.GetByIdEstado(idEstado);
            
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result); 
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null); 
        }
    }
    @GetMapping("/colonia/getbyidmunicipio")
    public ResponseEntity<Result> coloniaGetByIdMunicipio(@RequestParam int idMunicipio) {
        try {
            Result result = coloniaDAOJPAImplementation.GetByIdMunicipio(idMunicipio);
            
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result); 
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null); 
        }
    }
}


