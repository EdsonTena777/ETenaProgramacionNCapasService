
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementation implements iColoniaJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetByIdMunicipio(int idMunicipio) {
        Result result = new Result();
        try {
            TypedQuery<Colonia> queryColonia = entityManager.createQuery("SELECT c FROM Colonia c WHERE c.Municipio.idMunicipio = :idMunicipio", Colonia.class);
            queryColonia.setParameter("idMunicipio", idMunicipio);
            List<Colonia> colonias = queryColonia.getResultList();

            result.objects = (List) colonias;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}