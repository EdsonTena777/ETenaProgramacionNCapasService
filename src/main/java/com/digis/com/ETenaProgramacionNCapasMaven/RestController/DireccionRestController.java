
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.DireccionDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "Direccion", description = "Endpoints para el procesamiento de Direccion")
    @Operation(summary = "Eliminar Direccion", 
               description = "Eliminar una Direccion mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "direccion eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la Direccion"),
        @ApiResponse(responseCode = "400", description = "Error en la direccion"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<?> delete (@Parameter(name = "idDireccion", description = "Ingersa un idDireccion para mostrar ejemplo 385")@PathVariable int idDireccion){
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
