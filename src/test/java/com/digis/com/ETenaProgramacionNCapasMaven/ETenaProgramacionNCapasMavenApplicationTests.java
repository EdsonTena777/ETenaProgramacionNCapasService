//package com.digis.com.ETenaProgramacionNCapasMaven;
//
//import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
//import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
//import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
//import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
//import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Rol;
//import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class ETenaProgramacionNCapasMavenApplicationTests {
//        @PersistenceContext
//        private EntityManager entityManager;
//        @Autowired
//        private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
//	@Test
//	void contextLoads() {
//	}
//        
//        @Test
//        void AddTest() {
//            Usuario usuario = new Usuario();
//            usuario.setUsername("testUser");
//            usuario.setNombre("Edson");
//            usuario.setApellidoPaterno("Tena");
//            usuario.setApellidoMaterno("Rodriguez");
//            usuario.setEmail("test@gmail.com");
//            usuario.setPassword("123456");
//            usuario.setSexo("M");
//            usuario.setTelefono("5512345678");
//            usuario.setCelular("5512345678");
//            usuario.setCURP("TEST123456HDFABC01");
//            usuario.setFechaNacimiento(new Date());
//
//            Rol rol = new Rol();
//            rol.setIdRol(1);
//            usuario.setRoles(rol);
//
//            Direccion direccion = new Direccion();
//            direccion.setCalle("Abasolo");
//            direccion.setNumeroExterior("10");
//            direccion.setNumeroInterior("2");
//
//            Colonia colonia = new Colonia();
//            colonia.setIdColonia(41);
//            direccion.setColonia(colonia);
//
//            usuario.setDirecciones(new ArrayList<>());
//            usuario.getDirecciones().add(direccion);
//
//            Result result = usuarioDAOJPAImplementation.Add(usuario);
//
//            Assertions.assertTrue(result.correct);
//            Assertions.assertNotEquals(0, usuario.getIdUsuario());
//
//            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
//            Assertions.assertNotNull(usuarioBD);
//        }
//        
//        @Test
//        void GetByIdTest(){
//            Result result = usuarioDAOJPAImplementation.GetById(2);
//            
//            Assertions.assertTrue(result.correct);
//            Assertions.assertNotNull(result.object);
//        }
//        
//        @Test
//        void GetAllTest(){
//            Result result = usuarioDAOJPAImplementation.GetAll();
//            
//            Assertions.assertTrue(result.correct);
//            Assertions.assertNotNull(result.objects);
//            
//            List<Usuario> usuarios = (List<Usuario>) result.objects;
//            
//            
//        }
//        @Test
//        void GetAllDinamicoTest(){
//            String nombreDinamico = "ana";
//            
//            Result result = usuarioDAOJPAImplementation.GetAllDinamico(nombreDinamico, null, null, null);
//            Assertions.assertTrue(result.correct);
//            Assertions.assertNotNull(result.objects);
//            
//            List<Usuario> usuarios = (List<Usuario>) result.objects;
//            
//            Assertions.assertFalse(usuarios.isEmpty());
//            
//            System.out.println(usuarios);
//        }
//        
//        @Test
//        void UpdateUsuarioTest(){
//            Usuario usuario = entityManager.find(Usuario.class, 421);
//            
//            usuario.setUsername("r10");
//            usuario.setNombre("Fabregas");
//            usuario.setApellidoPaterno("Alonso");
//            usuario.setApellidoMaterno("Ronaldinho");
//            usuario.setSexo("M");
//            usuario.setTelefono("5599345678");
//            usuario.setCelular("5598345677");
//            usuario.setFechaNacimiento(new Date());
//            
//            Result result = usuarioDAOJPAImplementation.UpdateUsuairo(usuario);
//            
//            Assertions.assertTrue(result.correct);
//            Assertions.assertNotEquals(0, usuario.getIdUsuario());
//            
//            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
//            Assertions.assertNotNull(usuarioBD);
//        }
//        
//        @Test
//        void UpdateStatusTest() {
//            Usuario usuario = entityManager.find(Usuario.class, 2);
//            Assertions.assertNotNull(usuario);
//            int nuevoStatus = (usuario.getStatus() == 1) ? 0 : 1;
//            usuario.setStatus(nuevoStatus);
//
//            Result result = usuarioDAOJPAImplementation.UpdateStatus(usuario);
//            Assertions.assertTrue(result.correct);
//
//            entityManager.clear(); 
//
//            Usuario usuarioBD = entityManager.find(Usuario.class, 2);
//            Assertions.assertEquals(nuevoStatus, usuarioBD.getStatus());
//        }
//        
//        @Test
//        void DeleteTest(){
//            Usuario usuario = entityManager.find(Usuario.class, 426);
//            Assertions.assertNotNull(usuario);
//            
//            Result result = usuarioDAOJPAImplementation.Delete(usuario);
//            Assertions.assertTrue(result.correct);
//            
//            entityManager.clear();
//            
//            Usuario usuarioBD = entityManager.find(Usuario.class, 426);
//            Assertions.assertNull(usuarioBD);
//            
//        }
//}
