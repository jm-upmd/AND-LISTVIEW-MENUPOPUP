package com.example.jose.ejerciciolistview;

public class Queso {
    private String nombre;
    private String origen;
    private boolean favorito;



    public Queso(String nombre, String origen){
        this.nombre = nombre;
        this.origen = origen;
        favorito = false;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
