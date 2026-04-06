package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.Service.JwtService;
import com.digis.com.ETenaProgramacionNCapasMaven.Service.UserDetailServiceImplementation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired 
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImplementation userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.get("username"),
                loginRequest.get("password")
            )
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.get("username"));

        Result<Usuario> resultUsuario = usuarioDAOJPAImplementation.getByUsername(loginRequest.get("username"));

        int idUsuario = 0;
        if (resultUsuario.correct && resultUsuario.object != null) {
            idUsuario = resultUsuario.object.getIdUsuario();
        }

        String token = jwtService.generateToken(user);

        String rol = user.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("");

        String destino;
        if ("ROLE_Admin".equals(rol)) {
            destino = "/usuario";
        } else {
            destino = "/UsuarioDetail?idUsuario=" + idUsuario;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Token", token);
        map.put("Rol", rol);
        map.put("Destino", destino);
        map.put("IdUsuario", idUsuario);

        return ResponseEntity.ok(map);
    }
}