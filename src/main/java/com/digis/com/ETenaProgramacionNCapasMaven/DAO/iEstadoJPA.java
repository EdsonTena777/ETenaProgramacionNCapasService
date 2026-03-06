
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;


public interface iEstadoJPA {
//    Result GetByIdPais(int idPais);
    Result GetAll();
    Result GetByIdPais(int idPais);
}
