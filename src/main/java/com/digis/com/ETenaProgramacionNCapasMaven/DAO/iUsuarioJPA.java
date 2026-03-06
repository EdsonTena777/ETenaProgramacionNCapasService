
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import java.util.List;


public interface iUsuarioJPA {
    Result GetAll();
    Result GetById(int idUsuario);
    /*Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol);
    Result Add(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result AddAll(List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuariosML);
    Result UpdateUsuario(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result UpdateImagen(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);*/
    Result UpdateStatus(Usuario usuario);
    /*Result Delete(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);*/
}
