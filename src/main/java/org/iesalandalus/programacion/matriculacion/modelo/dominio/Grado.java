package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public abstract class Grado {

    protected String nombre;       // Atributo protegido
    protected String iniciales;    // Atributo protegido
    protected int numAnios;        // Atributo protegido

    // Constructor
    public Grado(String nombre) {
        this.setNombre(nombre);  // Usamos el setter para validar el nombre y establecer iniciales
        // No asignamos directamente numAnios, ya que se establece por separado
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

    // Método setIniciales que genera las iniciales del grado en mayúsculas
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

    // Método getNumAnios
    protected int getNumAnios() {
        return numAnios;
    }
}



