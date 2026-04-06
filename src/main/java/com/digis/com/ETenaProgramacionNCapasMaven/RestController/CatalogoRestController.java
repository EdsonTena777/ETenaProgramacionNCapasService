
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.ColoniaDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.MunicipioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.PaisDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    @Tag(name = "Pais", description = "Endpoints para el procesamiento de Pais")
    @Operation(summary = "Obtener los paises por all", 
               description = "Busca todos los paises y sirve para ddl.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paises encontrado"),
        @ApiResponse(responseCode = "204", description = "El Pais no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/pais/getall")
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
                return ResponseEntity.badRequest().body(result);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(null);
        }
    }
    @Tag(name = "Estado", description = "Endpoints para el procesamiento de estado")
    @Operation(summary = "Obtener el estado por id", 
               description = "Busca el estado y sirve para ddl.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paises encontrado"),
        @ApiResponse(responseCode = "204", description = "El Pais no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Tag(name = "Municipio", description = "Endpoints para el procesamiento de municipio")
    @Operation(summary = "Obtener el municipio por id", 
               description = "Busca el estado y sirve para ddl.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "municipio encontrado"),
        @ApiResponse(responseCode = "204", description = "El municipio no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/municipio/getbyidestado")
    public ResponseEntity<Result> municipioGetByIdEstado(@Parameter(name = "idEstado", description = "Ingersa un idEstado para mostrar ejemplo 3")@RequestParam int idEstado) {
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
    @Tag(name = "Colonia", description = "Endpoints para el procesamiento de colonia")
    @Operation(summary = "Obtener la colonia por id", 
               description = "Busca el estado y sirve para ddl.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "colonia encontrado"),
        @ApiResponse(responseCode = "204", description = "El colonia no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/colonia/getbyidmunicipio")
    public ResponseEntity<Result> coloniaGetByIdMunicipio(@Parameter(name = "idMunicipio", description = "Ingersa un idMunicipio para mostrar ejemplo 121")@RequestParam int idMunicipio) {
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


