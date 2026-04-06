
package com.digis.com.ETenaProgramacionNCapasMaven.Configuration;


import com.digis.com.ETenaProgramacionNCapasMaven.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFileChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/usuario").hasRole("Admin")
                .requestMatchers(HttpMethod.GET, "/catalogo/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuario/me").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
                .requestMatchers(HttpMethod.GET, "/api/usuario/getAllDinamico").hasRole("Admin")
                .requestMatchers(HttpMethod.PUT, "/api/usuario/updateStatus/**").hasRole("Admin")
                .requestMatchers(HttpMethod.DELETE, "/api/direccion/*").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
                .requestMatchers(HttpMethod.DELETE, "/api/usuario/*").hasRole("Admin")
                .requestMatchers(HttpMethod.POST, "/api/usuario").hasRole("Admin")
                .requestMatchers(HttpMethod.POST, "/api/direccion/*/direccion").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
                .requestMatchers(HttpMethod.PUT, "/api/usuario/updateImagen/**").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
                .requestMatchers(HttpMethod.PUT, "/api/usuario/**").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
                .requestMatchers(HttpMethod.PUT, "/api/direccion/**").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
                .requestMatchers(HttpMethod.GET, "/api/usuario").hasRole("Admin")
                .requestMatchers("/api/usuario/getall").hasRole("Admin")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}