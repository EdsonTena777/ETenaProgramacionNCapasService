
package com.digis.com.ETenaProgramacionNCapasMaven.Service;

import com.digis.com.ETenaProgramacionNCapasMaven.Enums.EstatusCargaMasiva;


public interface iLogCargaMasivaService {
    void escribirLogs(String key, String ruta, EstatusCargaMasiva status, String detalle);
}
