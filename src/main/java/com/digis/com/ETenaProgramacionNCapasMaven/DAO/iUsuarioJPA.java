
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;


public interface iUsuarioJPA {
    Result GetAll();
    Result GetById(int idUsuario);
    Result Add(Usuario usuario);
    Result<Usuario> UpdateUsuairo(Usuario usuario);
    Result<Usuario> UpdateImagen(Usuario usuario);
    Result<Usuario> GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol);
    Result<Usuario> Delete(Usuario usuario);
    Result AddAll(List<Usuario> usuarios);
    /*Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol);
    Result AddAll(List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuariosML);
    Result UpdateUsuario(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result UpdateImagen(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);*/
    Result UpdateStatus(Usuario usuario);
    /*Result Delete(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);*/
}
