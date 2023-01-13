
package com.miportfolio.lp.Dto;

import javax.validation.constraints.NotBlank;


public class DtoProyectos {
    
    @NotBlank
    private String nombre;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String img;
    
    //Constructores

    public DtoProyectos() {
    }

    public DtoProyectos(String nombre, String descripcion, String img) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img = img;
    }
    
    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
}
