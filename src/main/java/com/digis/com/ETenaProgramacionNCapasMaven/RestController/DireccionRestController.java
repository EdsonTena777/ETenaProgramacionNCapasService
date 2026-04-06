
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.DireccionDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.DireccionAddDTO;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.DireccionUpdateDTO;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
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
    public ResponseEntity<?> update(@PathVariable int idDireccion,
                                    @RequestBody DireccionUpdateDTO dto,
                                    Authentication authentication) {

        System.out.println("Entró al update dirección con idDireccion: " + idDireccion);

        try {
            String username = authentication.getName();

            boolean esAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_Admin"));

            Result<Direccion> resultDireccion = direccionDAOJPAImplentation.GetById(idDireccion);

            if (!resultDireccion.correct || resultDireccion.object == null) {
                return ResponseEntity.status(404).body("La dirección no existe");
            }

            Direccion direccionExistente = resultDireccion.object;

            if (!esAdmin) {
                Result<Usuario> resultUsuarioAuth = usuarioDAOJPAImplementation.getByUsername(username);

                if (!resultUsuarioAuth.correct || resultUsuarioAuth.object == null) {
                    return ResponseEntity.status(401).body("Usuario autenticado no encontrado");
                }

                Usuario usuarioAuth = resultUsuarioAuth.object;

                if (direccionExistente.getUsuario() == null ||
                    direccionExistente.getUsuario().getIdUsuario() != usuarioAuth.getIdUsuario()) {

                    return ResponseEntity.status(403)
                            .body("Forbidden: no puedes actualizar la dirección de otro usuario");
                }
            }

            Direccion direccion = new Direccion();
            direccion.setIdDireccion(idDireccion);

            direccion.setCalle(dto.getCalle());
            direccion.setNumeroExterior(dto.getNumeroExterior());
            direccion.setNumeroInterior(dto.getNumeroInterior());

            if (dto.getIdColonia() != null) {
                Colonia colonia = new Colonia();
                colonia.setIdColonia(dto.getIdColonia());
                direccion.setColonia(colonia);
            }

            Result<Direccion> result = direccionDAOJPAImplentation.UpdateDireccion(direccion);

            if (result.correct) {
                if (result.object != null) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
    @PostMapping("/{idUsuario}/direccion")
    public ResponseEntity<?> add(@PathVariable int idUsuario, @RequestBody DireccionAddDTO dto) {
        
        try {
            Direccion direccion = new Direccion();
            direccion.setCalle(dto.getCalle());
            direccion.setNumeroExterior(dto.getNumeroExterior());
            direccion.setNumeroInterior(dto.getNumeroInterior());

            Colonia colonia = new Colonia();
            colonia.setIdColonia(dto.getColonia().getIdColonia());
            direccion.setColonia(colonia);

            Result<Direccion> result = direccionDAOJPAImplentation.Add(idUsuario, direccion);

            if (result.correct) {
                return ResponseEntity.status(201).body(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error: " + ex.getMessage());
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
