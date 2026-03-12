
package com.digis.com.ETenaProgramacionNCapasMaven.Service;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import org.springframework.web.multipart.MultipartFile;


public interface iCargaMasivaService {
    Result validarArchivo(MultipartFile archivo);
    Result procesarArchivo(String rutaArchivo, String key);
}
