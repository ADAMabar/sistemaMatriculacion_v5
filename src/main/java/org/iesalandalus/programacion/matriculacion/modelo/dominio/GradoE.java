package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoE extends Grado {
    private int numEdiciones;


    public GradoE(String nombre, int numAnios, int numEdiciones) {
        super(nombre);
        this.setNumAnios(1);
        this.setNumEdiciones(numEdiciones);
    }


    public int getNumEdiciones() {
        return numEdiciones;
    }


    public void setNumEdiciones(int numEdiciones) {
        if (numEdiciones <= 0) {
            throw new IllegalArgumentException("ERROR: El número de ediciones debe ser mayor que 0.");
        }
        this.numEdiciones = numEdiciones;
    }


    @Override
    public void setNumAnios(int numAnios) throws IllegalArgumentException {
        if (numAnios != 1) {
            throw new IllegalArgumentException("El número de años debe ser 1 para los grados de tipo GradoE.");
        }

    }

    @Override
    public String toString() {
        return super.toString() + " (Grado de 1 año, Ediciones: " + numEdiciones + ")";
    }

}

