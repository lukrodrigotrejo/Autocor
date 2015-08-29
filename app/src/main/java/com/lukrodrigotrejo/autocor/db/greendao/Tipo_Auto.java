package com.lukrodrigotrejo.autocor.db.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TIPO__AUTO".
 */
public class Tipo_Auto {

    private Long id;
    private int Codigo;
    private String Marca;
    private String Descripcion;

    public Tipo_Auto() {
    }

    public Tipo_Auto(Long id) {
        this.id = id;
    }

    public Tipo_Auto(Long id, int Codigo, String Marca, String Descripcion) {
        this.id = id;
        this.Codigo = Codigo;
        this.Marca = Marca;
        this.Descripcion = Descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

}