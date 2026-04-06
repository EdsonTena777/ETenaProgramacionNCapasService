/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis.com.ETenaProgramacionNCapasMaven.Service;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImplementation implements  UserDetailsService{
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result result = usuarioDAOJPAImplementation.getByUsername(username);
        
        if(result.correct){
            Usuario usuario = (Usuario) result.object;
            return User.withUsername(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.getRoles().getNombreRol())
                    .build();
        } else {
            throw new UsernameNotFoundException("No se encontró el usuario con el usuario: " + username);
        }
    }
}
