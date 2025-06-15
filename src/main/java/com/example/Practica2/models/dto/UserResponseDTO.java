package com.example.Practica2.models.dto;

public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private Integer rol;
    
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

    public Integer getRol(){
        return rol;
    }

    public void setRol(Integer rol){
        this.rol = rol;
    }
}
