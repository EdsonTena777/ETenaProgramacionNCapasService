
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.PaisDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.RolDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "Usuario", description = "Endpoints para el procesamiento de Usuario")
    @Operation(summary = "Obtener todos los usuarios", 
           description = "Retorna una lista de todos los usuarios registrados con sus direcciones y roles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class)) }),
        @ApiResponse(responseCode = "204", description = "No hay usuarios en la base de datos", 
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud (Business Logic)", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content)
    })
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
    @Tag(name = "Usuario", description = "Endpoints para el procesamiento de Usuario")
    @Operation(summary = "Obtener un usuario por ID", 
               description = "Busca un usuario específico en la base de datos mediante su ID y retorna sus direcciones y roles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "204", description = "El usuario no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> GetById(@Parameter(name = "idUsuario", description = "Ingersa un idUsuario para mostrar ejemplo 2") @PathVariable("idUsuario") int idUsuario) {
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
    @Tag(name = "Rol", description = "Endpoints para el procesamiento de Rol")
    @GetMapping("/rolGetAll")
    @Operation(summary = "Obtener todos los roles", 
           description = "Esto me sirve para mi ddl de rol en Editar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de rol obtenida con éxito",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class)) }),
        @ApiResponse(responseCode = "204", description = "No hay roles en la base de datos", 
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud (Business Logic)", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content)
    })
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
    @Tag(name = "Pais", description = "Endpoints para el procesamiento de Pais")
    @GetMapping("/paisGetAll")
    @Operation(summary = "obtener todos los paises", 
               description = "Esto me sirve para mi ddl en pais.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pais encontrado"),
        @ApiResponse(responseCode = "204", description = "los pais no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Tag(name = "Estado", description = "Endpoints para el procesamiento de Estado")
    @Operation(summary = "obtener todos los estados", 
               description = "Esto me sirve para mi ddl de estados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado encontrado"),
        @ApiResponse(responseCode = "204", description = "los estados no existe"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta de negocio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Tag(name = "Usuario", description = "Endpoints para el procesamiento de Usuario")
    @Operation(summary = "Actualizar estatus de usuario", 
               description = "Cambia el estado (activo/inactivo) de un usuario mediante su ID (0/1).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estatus actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/updateStatus/{idUsuario}")
    public ResponseEntity<?> updateStatus(
            @Parameter(description = "ID del usuario a modificar") 
            @PathVariable int idUsuario,

            @Parameter(description = "Nuevo valor de estatus (ej. 1 para Activo, 0 para Inactivo)", example = "1") 
            @RequestParam int status) {

        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setStatus(status);

            Result result = usuarioDAOJPAImplementation.UpdateStatus(usuario);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception ex) {
            Result result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(500).body(result);
        }
    }

    @Tag(name = "Usuario", description = "Endpoints para el procesamiento de Usuario")
    @Operation(summary = "Actualizar imagen de perfil", 
               description = "Actualiza la imagen de un usuario específico enviando la cadena Base64 en el cuerpo de la petición.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró el usuario para actualizar"),
        @ApiResponse(responseCode = "400", description = "Error en los datos de la imagen"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/updateImagen/{idUsuario}")
    public ResponseEntity<?> updateImagen(
            @Parameter(description = "ID del usuario") 
            @PathVariable int idUsuario, 

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto usuario que contiene la nueva cadena de imagen (Base64)",
                required = true,
                content = @Content(schema = @Schema(implementation = Usuario.class),
                examples = @ExampleObject(value = "{ \"imagen\": \"data:image/png;base64,iVBOR...\" }")))
            @RequestBody Usuario usuario) {

        try {
            usuario.setIdUsuario(idUsuario);       
            Result result = usuarioDAOJPAImplementation.UpdateImagen(usuario);

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
    @Tag(name = "Usuario", description = "Endpoints para el procesamiento de Usuario")
    @Operation(summary = "Eliminar usuario", 
               description = "Eliminar un usuario mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "busqueda realizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la busqueda"),
        @ApiResponse(responseCode = "400", description = "Error en la busqueda"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> delete(@Parameter(name = "idUsuario", description = "Ingersa un idUsuario para mostrar ejemplo 385")@PathVariable int idUsuario){
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
    @Tag(name = "Usuario", description = "Endpoints para el procesamiento de Usuario")
    @Operation(summary = "Busqueda dinamica", 
               description = "Hace una busqueda dinamica con combre, apellido paterno, apellido materno, roles")
    @GetMapping("/getAllDinamico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "busqueda realizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la busqueda"),
        @ApiResponse(responseCode = "400", description = "Error en la busqueda"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
