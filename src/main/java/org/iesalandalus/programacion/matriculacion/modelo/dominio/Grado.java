package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public abstract class Grado {

    protected String nombre;
    protected String iniciales;
    protected int numAnios;


    public Grado(String nombre) {
        this.setNombre(nombre);
    }


    public String getNombre() {
        return nombre;
    }

    protected void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre;
        setIniciales();
    }


    protected String getIniciales() {
        return iniciales;
    }

    private void setIniciales() {
        String[] palabras = nombre.split(" ");
        StringBuilder inicialesBuilder = new StringBuilder();

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                inicialesBuilder.append(palabra.charAt(0));
            }
        }

        this.iniciales = inicialesBuilder.toString().toUpperCase();
    }


    @Override
    public String toString() {
        return "(" + iniciales + ") - " + nombre;
    }


    protected abstract void setNumAnios(int numAnios);


    public int getNumAnios() {
        return numAnios;
    }
}



