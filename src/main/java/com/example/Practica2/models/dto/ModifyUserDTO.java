package com.example.Practica2.models.dto;

public class ModifyUserDTO {
    private String correoActual;
    private String nuevoCorreo;
    private String nombre;
    private String apellido;
    private String contraseña;
    private byte[] imagen;
    private Integer rol;

    public String getCorreoActual() {
        return correoActual;
    }
    public void setCorreoActual(String correoActual) {
        this.correoActual = correoActual;
    }
    public String getNuevoCorreo() {
        return nuevoCorreo;
    }
    public void setNuevoCorreo(String nuevoCorreo) {
        this.nuevoCorreo = nuevoCorreo;
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
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public byte[] getImagen() {
        return imagen;
    }
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    public Integer getRol() {
        return rol;
    }
    public void setRol(Integer rol) {
        this.rol = rol;
    }
}

