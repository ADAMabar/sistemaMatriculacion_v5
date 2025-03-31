package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoD extends Grado {

    private Modalidad modalidad;


    public GradoD(String nombre, int numAnios, Modalidad modalidad) {
        super(nombre);
        this.setNumAnios(numAnios);
        this.setModalidad(modalidad);
    }


    public Modalidad getModalidad() {
        return modalidad;
    }


    protected void setModalidad(Modalidad modalidad) {
        if (modalidad == null) {
            throw new NullPointerException("ERROR: La modalidad no puede ser nula.");
        }
        this.modalidad = modalidad;
    }

    @Override
    protected void setNumAnios(int numAnios) throws IllegalArgumentException {
        if (numAnios != 2 && numAnios != 3) {
            throw new IllegalArgumentException("El número de años para el Grado Dual debe ser 2 o 3.");
        }

    }


    @Override
    public String toString() {
        return super.toString() + " - Modalidad: " + modalidad;
    }
}


