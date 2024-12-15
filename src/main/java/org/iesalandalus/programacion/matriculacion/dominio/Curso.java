package org.iesalandalus.programacion.matriculacion.dominio;

public enum Curso {

PRIMERO("primero"), SEGUNDO("segundo");

private String cadenaAMostrar;

private Curso(String cadenaAMostrar){
    this.cadenaAMostrar=cadenaAMostrar;
}


public String imprimir() {
    return this.ordinal() + ".-" + this.cadenaAMostrar;
}

    @Override
    public String toString() {
        return this.cadenaAMostrar;
    }

}

