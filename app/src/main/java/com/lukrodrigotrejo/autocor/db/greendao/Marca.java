package com.lukrodrigotrejo.autocor.db.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MARCA".
 */
public class Marca {

    private String Codigo;
    private String Descripcion;

    public Marca() {
    }

    public Marca(String Codigo) {
        this.Codigo = Codigo;
    }

    public Marca(String Codigo, String Descripcion) {
        this.Codigo = Codigo;
        this.Descripcion = Descripcion;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

}
