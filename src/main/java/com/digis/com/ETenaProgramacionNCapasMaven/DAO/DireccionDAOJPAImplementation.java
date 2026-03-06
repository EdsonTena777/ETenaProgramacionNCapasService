
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
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
