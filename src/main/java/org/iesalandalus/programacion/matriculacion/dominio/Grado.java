package org.iesalandalus.programacion.matriculacion.dominio;

public enum Grado {
    GDCFGB("Grado.GDCFGB"), GDCFGM("Grado.GDCFGM"), GDCFGS("Grado.GDCFGS");

private String cadenaAMostrar;

private Grado(String cadenaAMostrar){
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

