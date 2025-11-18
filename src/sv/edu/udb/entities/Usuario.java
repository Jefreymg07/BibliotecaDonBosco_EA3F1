/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.udb.entities;

/**
 *
 * @author jehud
 */
public class Usuario {
    
    //Atrinutos.
    private int id;
    private String usuario;
    private String contrasenia;
    private String rol;
    private String estado;
    private String fechaCreacion;
  
    //Constructor vacio
    public Usuario(){
    }
    
    //Constructor con parametros.
    public Usuario(int id, String usuario, String contrasenia, String rol, String estado, String fechaCreacion){
        this.id = id;
        this.usuario = usuario;
        this.contrasenia = contrasenia; 
        this.rol = rol;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }
    
    //Getter id
    public int getId(){
        return id;
    }
    
    //Setter para id. Modifica el valor del id.
    public void setId(int id){
        this.id = id;
    }
    
    //Getter usuario.
    public String getUsuario(){
        return usuario;
    }
    
    //Setter usuario
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    //Getter contra
    public String getContrasenia(){
        return contrasenia;
    }
    
    //Setter contra
    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }
    
    //Getter rol
    public String getRol(){
        return rol;
    }
    
    //Setter rol
    public void setRol(String rol){
        this.rol = rol;
    }
    
    //Getter estado
    public String getEstado(){
        return estado;
    }
    
    //Setter
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    //Getter fechaCreacion
    public String getFechaCreacion(){
        return fechaCreacion;
    }
    
    //Setter fechaCreacion
    public void setFechaCreacion(String fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }
    
    //toString
    @Override
    public String toString(){
        return "Usuario{" +
                "id" + id +
                ", usuario = '" + usuario + '\'' + 
                ", contrasenia = '" + contrasenia + '\'' +
                ", rol = '" + rol + '\'' +
                ", estado = '" + rol + '\'' + 
                ". fechaCreacion = '" + fechaCreacion + '\'' + '}';     
    }
}
