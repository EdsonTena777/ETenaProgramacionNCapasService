
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;


public interface iDireccionJPA {
    Result GetById(int idDireccion);
    Result<Direccion> UpdateDireccion(Direccion direccion);
    Result<Direccion> Add(int idUsuario, Direccion direccion);
    Result<Direccion> Delete(Direccion direccion);
//    Result UpdateDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML);
//    Result DeleteDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML);
//    Result AddDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML);
    
}
