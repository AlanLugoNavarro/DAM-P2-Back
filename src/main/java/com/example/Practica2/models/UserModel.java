package com.example.Practica2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, name = "Nombre")
    private String nombre;

    @Column(nullable = false, length = 50, name = "Apellido")
    private String apellido;

    @Column(nullable = false, unique = true, length = 50, name = "Correo")
    private String correo;

    @Column(nullable = false, length = 255, name = "Contraseña")
    private String contraseña;

    @Column(columnDefinition = "TINYINT UNSIGNED DEFAULT 0", name = "Rol")
    private Integer rol;

    @Column(nullable = true, name = "Imagen")
    private byte[] imagen;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getContraseña(){
        return contraseña;
    }

    public void setContraseña(String contraseña){
        this.contraseña = contraseña;
    }

    public Integer getRol(){
        return rol;
    }

    public void setRol(Integer rol){
        this.rol = rol;
    }

    public byte[] getImagen(){
        return imagen;
    }

    public void setImagen(byte[] imagen){
        this.imagen = imagen;
    }
}
