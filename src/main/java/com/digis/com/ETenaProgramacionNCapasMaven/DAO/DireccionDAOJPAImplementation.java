
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOJPAImplementation implements iDireccionJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetById(int IdDireccion){
        Result result = new Result();
        try{
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            result.object = direccionJPA;
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
    public Result<Direccion> UpdateDireccion(Direccion direccion){
        Result<Direccion> result = new Result<>();
        try {
            Direccion direccionJPA = entityManager.find(Direccion.class, direccion.getIdDireccion());
            if(direccionJPA == null){
                result.correct = false;
                result.errorMessage = "La direccion no existe";
                return result;
            }
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            
            if(direccion.getColonia() == null || direccion.getColonia().getIdColonia() == 0){
                result.correct = false;
                result.errorMessage = "La colonia no es valida";
                return result;
            }
            Colonia coloniaJPA = entityManager.find(Colonia.class, direccion.getColonia().getIdColonia());
            
            if(coloniaJPA == null){
                result.correct = false;
                result.errorMessage = "La colonia no existe";
                return result;
            }
            
            direccionJPA.setColonia(coloniaJPA);
            
            result.correct = true;
            result.object = direccionJPA;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    @Override
    @Transactional
    public Result<Direccion> Add(int idUsuario, Direccion direccion){
        Result<Direccion> result = new Result<>();
        try{
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            if(usuarioJPA == null){
                result.correct = false;
                result.errorMessage = "No se encontro usuario";
                return result;
            }
            if(direccion.getColonia() == null || direccion.getColonia().getIdColonia() == 0){
                result.correct = false;
                result.errorMessage = "debe seleccionar colonia valida";
                return result;
            }
            Colonia coloniaJPA = entityManager.find(Colonia.class, direccion.getColonia().getIdColonia());
            if(coloniaJPA == null){
                result.correct = false;
                result.errorMessage = "No se encontro colonia";
                return result;
            }
            direccion.setUsuario(usuarioJPA);
            direccion.setColonia(coloniaJPA);
            
            entityManager.persist(direccion);
            entityManager.flush();
            result.correct = true;
            result.object = direccion;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    @Override
    @Transactional
    public Result<Direccion> Delete(Direccion direccion){
        Result<Direccion> result = new Result<>(); 
        try{
            Direccion direccionJPA = entityManager.find(Direccion.class, direccion.getIdDireccion());
            if(direccionJPA != null){
                entityManager.remove(direccionJPA);
                entityManager.flush();
                result.correct = true;
                result.object = null;
            } else {
                result.correct = false;
                result.errorMessage = "La Direccion no existe.";
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.ex = null;
        }
    return result;
    }
    /*
    @Override
    @Transactional
    public Result UpdateDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class, direccionML.getIdDireccion());
            direccionJPA.setCalle(direccionML.getCalle());
            direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
            Colonia coloniaJPA = entityManager.find(Colonia.class, direccionML.getColonia().getIdColonia());
            direccionJPA.setColonia(coloniaJPA);
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
    public Result DeleteDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class, direccionML.getIdDireccion());
            if(direccionJPA != null){
                entityManager.remove(direccionJPA);
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
    public Result AddDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = modelMapper.map(direccionML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class);
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia coloniaJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia.class, direccionML.Colonia.getIdColonia());
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, direccionML.Usuario.getIdUsuario());
            direccionJPA.setColonia(coloniaJPA);
            direccionJPA.setUsuario(usuarioJPA);
            entityManager.persist(direccionJPA);
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    */
}
