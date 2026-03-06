
package com.digis.com.ETenaProgramacionNCapasMaven.JPA;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Rol;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;
    @Column(name = "username")
    private String Username;
    @Lob
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "nombre")
    private String Nombre;
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;
    @Column(name = "email")
    private String Email;
    @Column(name = "password")
    private String Password;
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date FechaNacimiento;
    @Column(name = "sexo")
    private String Sexo;
    @Column(name = "telefono")
    private String Telefono;
    @Column(name = "celular")
    private String Celular;
    @Column(name = "curp")
    private String CURP;
    @Column(name = "status")
    private int status;
    @ManyToOne
    @JoinColumn(name = "idrol")
    public Rol Roles;
    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones;
    public Usuario() {
        this.Direcciones = new ArrayList<>();
}

    public Usuario(int idUsuario, String Username, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Email, String Password, Date FechaNacimiento, String Sexo, String Telefono, String Celular, String imagen, String CURP, Rol Roles, List<Direccion> Direcciones) {
        this.idUsuario = idUsuario;
        this.Username = Username;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Email = Email;
        this.Password = Password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.imagen = imagen;
        this.CURP = CURP;
        this.Roles = Roles;
        this.Direcciones = Direcciones;
    }
    
    public String getUsername(){
        return Username;
    }
    
    public void setUsername (String Username){
        this.Username = Username;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre (){
        return Nombre;
    }
    public void setNombre (String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno ){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    public void setApellidoMaterno (String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    public void setEmail (String Email){
        this.Email = Email;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }    
    
    public String getSexo(){
        return Sexo;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public String getTelefono (){
        return Telefono;
    }
    
    public void setTelefono (String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getCelular (){
        return Celular;
    }
    
    public void setCelular (String Celular){
        this.Celular = Celular;
    }
    
    public String getCURP (){
        return CURP;
    }
    
    public void setCURP(String CURP){
        this.CURP = CURP;
    }

    public Rol getRoles() {
        return Roles;
    }

    public void setRoles(Rol Roles) {
        this.Roles = Roles;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}