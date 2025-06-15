package com.example.Practica2.models.dto;

public class ObtenerUserDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private int rol;
    private byte[] imagen;

    public ObtenerUserDTO(String nombre, String apellido, String correo, Integer rol, byte[] imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
        this.imagen = imagen;

    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public int getRol() {
        return rol;
    }
    public void setRol(int rol) {
        this.rol = rol;
    }
    public byte[] getImagen() {
        return imagen;
    }
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    } 
}