package org.iesalandalus.programacion.matriculacion.dominio;

public enum EspecialidadProfesorado {
    INFORMATICA("informática"), SISTEMAS("sistemas"), FOL("fol");


    private String cadenaAMostrar;

    private EspecialidadProfesorado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }


    public String imprimir() {
        return (this.ordinal()+1) + ".-" + this.cadenaAMostrar;
    }

    @Override
    public String toString() {
        return this.cadenaAMostrar;
    }
}