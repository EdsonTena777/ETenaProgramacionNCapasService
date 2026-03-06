
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOJPAImplementation implements iRolJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result rolGetAll() {
        Result result = new Result();
        try {
            TypedQuery<Rol> queryRol = entityManager.createQuery("FROM Rol", Rol.class);
            List<Rol> roles = queryRol.getResultList();

            result.objects = (List) roles;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
