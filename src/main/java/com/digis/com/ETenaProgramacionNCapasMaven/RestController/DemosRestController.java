
package com.digis.com.ETenaProgramacionNCapasMaven.RestController;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemosRestController {
    
    @GetMapping
    public String HolaMundo(){
        return "HolaMundo";
    }
    
    @GetMapping("/saludo/{nombre}")
    public String HolaPersonalizado(@PathVariable("nombre") String nombre){
        return "Hola " +nombre;
    }
    
    @GetMapping("/saludo")
    public String HolaPersonalizado2(@RequestParam("nombre") String nombre){
        return "Hola " +nombre;
    }
    
    @GetMapping("/suma")
    public String Suma(@RequestParam(name="a") int num1, @RequestParam(name="b") int num2){
        int resultado = num1 + num2;
        return "la suma de "+num1+ " y " +num2+ " es: " +resultado;
    }
    @PostMapping("/suman")
    public String suma(@RequestBody int[] numeros){

        int resultado = 0;

        for(int numero : numeros){
            resultado += numero;
        }

        return "La suma total es: " + resultado;
    }
    @GetMapping("/usuarios")
    public List<Usuario> getUsuario(){
        List<Usuario> usuarios = new ArrayList<>();
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Edson");
        
        usuarios.add(usuario);
        return usuarios;
    }
}
