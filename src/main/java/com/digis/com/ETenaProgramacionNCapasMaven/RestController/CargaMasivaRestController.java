
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.Service.CargaMasivaService;
import com.digis.com.ETenaProgramacionNCapasMaven.Service.LogCargaMasivaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cargamasiva")
public class CargaMasivaRestController {
    
    @Autowired
    private CargaMasivaService cargaMasivaService;

    @Tag(name = "Carga Masiva", description = "Endpoints para la carga y proceso de archivos TXT y Excel")
    @PostMapping(value = "/validar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Validar y cargar archivo masivo",
        description = "Sube un archivo .txt o .xlsx para generar y validar los datos de los usuarios. Debe tener un orden en especifico Username | Nombre | Apellido Paterno | Apellido Materno | Teléfono | Email | Password | Fecha Nacimiento (dd/MM/yyyy) | Sexo | Celular | CURP | ID Rol | Calle | Número Interior | Número Exterior | ID Colonia",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Archivo procesado con éxito",
                content = @Content(schema = @Schema(implementation = Result.class))
            ),
            @ApiResponse(responseCode = "400", description = "Extensión de archivo no permitida o error en datos")
        }
    )
    public ResponseEntity<Result> validarArchivo(
        @Parameter(description = "Archivo de usuarios (.txt o .xlsx)", required = true)
        @RequestPart("archivo") MultipartFile archivo) { 

        Result result = cargaMasivaService.validarArchivo(archivo);

        if (result.errorMessage != null) {
            result.errorMessage = result.errorMessage.replace('\\', '/');
        }

        return ResponseEntity.ok(result);
    }

    @Tag(name = "Carga Masiva", description = "Endpoints para el procesamiento de archivos TXT y Excel")
    @PostMapping("/procesar")
    @Operation(
        summary = "Procesar archivo masivo",
        description = "Realiza la inserción de los datos validados previamente en la base de datos.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Archivo insertado con éxito",
                content = @Content(schema = @Schema(implementation = Result.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error en el procesamiento o parámetros inválidos")
        }
    )
    public ResponseEntity<Result> procesarArchivo(
        @Parameter(description = "Hash generado al validar el archivo") 
        @RequestParam("key") String key, 
        @Parameter(description = "Ruta absoluta del archivo en el servidor (esta en errorMessage despues de validar)") 
        @RequestParam("rutaArchivo") String rutaArchivo
    ) {

        Result result = cargaMasivaService.procesarArchivo(rutaArchivo, key);
        return ResponseEntity.ok(result);
    }

}
