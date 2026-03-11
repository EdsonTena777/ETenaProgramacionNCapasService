
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Rol;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementation implements iUsuarioJPA {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetAll(){
        Result result = new Result();
        try{
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();
            result.objects = (List) usuarios;
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    public Result<Usuario> GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol) {
        Result<Usuario> result = new Result<>();
        try {
            StringBuilder jpql = new StringBuilder("SELECT u FROM Usuario u LEFT JOIN FETCH u.Roles r WHERE 1=1 ");

            if (nombre != null && !nombre.trim().isEmpty()) 
                jpql.append("AND LOWER(u.Nombre) LIKE :nombre ");
            if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty()) 
                jpql.append("AND LOWER(u.ApellidoPaterno) LIKE :apellidoPaterno ");
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) 
                jpql.append("AND LOWER(u.ApellidoMaterno) LIKE :apellidoMaterno ");
            if (rol != null && !rol.trim().isEmpty()) 
                jpql.append("AND LOWER(r.NombreRol) LIKE :rol ");

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery(jpql.toString(), Usuario.class);

            if (nombre != null && !nombre.trim().isEmpty())queryUsuario.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty())queryUsuario.setParameter("apellidoPaterno", "%" + apellidoPaterno.toLowerCase() + "%");
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty())queryUsuario.setParameter("apellidoMaterno", "%" + apellidoMaterno.toLowerCase() + "%");
            if (rol != null && !rol.trim().isEmpty())queryUsuario.setParameter("rol", "%" + rol.toLowerCase() + "%");

            List<Usuario> usuarios = queryUsuario.getResultList();

            result.objects = usuarios;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }


    /*
    @Override
    public Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol) {
        Result result = new Result();
        try {
            StringBuilder jpql = new StringBuilder("SELECT u FROM Usuario u LEFT JOIN u.Roles r WHERE 1=1 ");

            if (nombre != null && !nombre.isEmpty()) jpql.append("AND LOWER(u.Nombre) LIKE :nombre ");
            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) jpql.append("AND LOWER(u.ApellidoPaterno) LIKE :apellidoPaterno ");
            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) jpql.append("AND LOWER(u.ApellidoMaterno) LIKE :apellidoMaterno ");
            if (rol != null && !rol.isEmpty()) jpql.append("AND LOWER(r.NombreRol) LIKE :rol ");

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery(jpql.toString(), Usuario.class);

            if (nombre != null && !nombre.isEmpty()) queryUsuario.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) queryUsuario.setParameter("apellidoPaterno", "%" + apellidoPaterno.toLowerCase() + "%");
            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) queryUsuario.setParameter("apellidoMaterno", "%" + apellidoMaterno.toLowerCase() + "%");
            if (rol != null && !rol.isEmpty()) queryUsuario.setParameter("rol", "%" + rol.toLowerCase() + "%");

            List<Usuario> usuarios = queryUsuario.getResultList();

            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuarioML = usuarios.stream().map(usuarioMapper -> modelMapper.map(usuarioMapper, com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario.class)).toList();

            result.objects = (List) usuarioML;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    */
    @Override
    public Result GetById(int idUsuario){
        Result result = new Result();
        try{
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if(usuario == null){
                result.correct = false;
                result.errorMessage = "usuario no encontrado";
                return result;
            }
            result.object = usuario;
            result.correct = true;
        }catch(Exception ex){
            result.correct=false;
            result.errorMessage=ex.getLocalizedMessage();
            result.ex=ex;
        }
    return result;    
    }
    @Override
    @Transactional
    public Result<Usuario> Add(Usuario usuario) {
        Result<Usuario> result = new Result<>();
        try {
            if (usuario.getDirecciones() != null) {
                for (Direccion direccion : usuario.getDirecciones()) {
                    direccion.setUsuario(usuario); 
                }
            }

            entityManager.persist(usuario); // Ahora persistirá usuario y direcciones en cascada

            result.correct = true;
            result.object = usuario;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    /*
    @Override
    @Transactional
    public Result Add(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = modelMapper.map(usuarioML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class);
            usuarioJPA.setRoles(entityManager.find(Rol.class, usuarioML.getRoles().getIdRol()));
            if(usuarioML.getDirecciones() != null && !usuarioML.getDirecciones().isEmpty()){
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML = usuarioML.getDirecciones().get(0);
                com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = modelMapper.map(direccionML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class);
                direccionJPA.setColonia(entityManager.find(Colonia.class, direccionML.getColonia().getIdColonia()));
                direccionJPA.setUsuario(usuarioJPA);
                if(usuarioJPA.getDirecciones() == null){
                    usuarioJPA.setDirecciones(new ArrayList<>());
                }
                usuarioJPA.getDirecciones().add(direccionJPA);
            }
            entityManager.persist(usuarioJPA);
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    @Transactional
    public Result AddAll(List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuariosML){
        Result result = new Result();
        try{
            for(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML : usuariosML){
                com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = modelMapper.map(usuarioML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class);
                usuarioJPA.setRoles(entityManager.find(Rol.class, usuarioML.getRoles().getIdRol()));
                if(usuarioML.getDirecciones() != null && !usuarioML.getDirecciones().isEmpty()){
                    com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML = usuarioML.getDirecciones().get(0);
                    com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = modelMapper.map(direccionML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class);
                    direccionJPA.setColonia(entityManager.find(Colonia.class, direccionML.getColonia().getIdColonia()));
                    direccionJPA.setUsuario(usuarioJPA);
                    if(usuarioJPA.getDirecciones() == null){
                        usuarioJPA.setDirecciones(new ArrayList<>());
                    }
                    usuarioJPA.getDirecciones().add(direccionJPA);
                }
                entityManager.persist(usuarioJPA);
                result.correct = true;
            }
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
     return result;    
    }
    @Override
    @Transactional
    public Result UpdateImagen(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, usuarioML.getIdUsuario());
            if (usuarioJPA != null) {
                usuarioJPA.setImagen(usuarioML.getImagen());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe en la base de datos.";
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    @Transactional
    public Result UpdateUsuario(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, usuarioML.getIdUsuario());
            usuarioJPA.setUsername(usuarioML.getUsername());
            usuarioJPA.setNombre(usuarioML.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioML.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioML.getApellidoMaterno());
            usuarioJPA.setEmail(usuarioML.getEmail());
            usuarioJPA.setSexo(usuarioML.getSexo());
            usuarioJPA.setTelefono(usuarioML.getTelefono());
            usuarioJPA.setCelular(usuarioML.getCelular());
            usuarioJPA.setFechaNacimiento(usuarioML.getFechaNacimiento());
            Rol rolJPA = entityManager.find(Rol.class, usuarioML.getRoles().getIdRol());
            usuarioJPA.setRoles(rolJPA);
            result.correct=true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    */
    @Override 
    @Transactional
    public Result<Usuario> UpdateUsuairo(Usuario usuario){
        Result<Usuario> result = new Result<>();
        try{
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if(usuarioJPA == null){
                result.correct = false;
                result.errorMessage = "El usuario no se encuentra";
                return result;
            }
            usuarioJPA.setUsername(usuario.getUsername());
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setEmail(usuario.getEmail());
            usuarioJPA.setSexo(usuario.getSexo());
            usuarioJPA.setTelefono(usuario.getTelefono());
            usuarioJPA.setCelular(usuario.getCelular());
            usuarioJPA.setCURP(usuario.getCURP());
            usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
            Rol rolJPA = entityManager.find(Rol.class, usuario.getRoles().getIdRol());
            usuarioJPA.setRoles(rolJPA);
            result.correct = true;
            result.object = usuarioJPA;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    @Override
    @Transactional
    public Result<Usuario> UpdateStatus(Usuario usuario) {
        Result result = new Result();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if (usuarioJPA != null) {
                usuarioJPA.setStatus(usuario.getStatus());
                result.correct = true;
                result.object = usuarioJPA;
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe en la base de datos.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override 
    @Transactional
    public Result<Usuario> UpdateImagen(Usuario usuario){
        Result<Usuario> result = new Result<>();
        try{
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if(usuarioJPA != null){
                usuarioJPA.setImagen(usuario.getImagen());
                entityManager.merge(usuarioJPA);
                result.correct = true;
                result.object = usuarioJPA; 
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;   
    }
    
    @Override
    @Transactional
    public Result<Usuario> Delete(Usuario usuario) {
        Result<Usuario> result = new Result();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdUsuario());

            if (usuarioJPA != null) {
                entityManager.remove(usuarioJPA);
                entityManager.flush();
                result.correct = true;
                result.object = null; 
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.ex = null;
        }
        return result;
    }

}
