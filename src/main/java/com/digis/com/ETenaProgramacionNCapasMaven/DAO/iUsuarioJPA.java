
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import java.util.List;


public interface iUsuarioJPA {
    Result GetAll();
    Result GetById(int idUsuario);
    Result Add(Usuario usuario);
    Result<Usuario> UpdateUsuairo(Usuario usuario);
    Result<Usuario> UpdateImagen(Usuario usuario);
    Result<Usuario> GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol);
    Result<Usuario> Delete(Usuario usuario);
    Result AddAll(List<Usuario> usuarios);
    Result getByUsername(String Username);
    Result UpdateStatus(Usuario usuario);

}
