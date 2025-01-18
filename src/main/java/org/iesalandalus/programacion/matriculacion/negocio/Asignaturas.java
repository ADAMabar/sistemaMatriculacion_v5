package org.iesalandalus.programacion.matriculacion.negocio;
import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

public class Asignaturas {

    private Asignatura[] coleccionAsignaturas;
    private int capacidad;
    private int tamano;


    public Asignaturas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionAsignaturas = new Asignatura[capacidad];
    }


    public Asignatura[] get() {
        return copiaProfundaAsignaturas();
    }


    private Asignatura[] copiaProfundaAsignaturas() {
        Asignatura[] copia = new Asignatura[tamano]; // Crear un nuevo arreglo con la misma capacidad

        // Iterar sobre el arreglo original y crear una nueva instancia de Alumno para cada uno
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Asignatura(coleccionAsignaturas[i]); // Suponiendo que Alumno tiene un constructor de copia
        }

        return copia;
    }


    public int getTamano() {
        return tamano;
    }


    public int getCapacidad() {
        return capacidad;
    }


    public void insertar(Asignatura asignatura)throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        if (buscar(asignatura) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }

        if (tamano >= capacidad) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más asignaturas.");
        }

        coleccionAsignaturas[tamano++] = new Asignatura(asignatura);
    }


    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERRROR: La asignatura no puede ser nula.");
        }

        for (int i = 0; i < tamano; i++) {
            if (coleccionAsignaturas[i].equals(asignatura)) {
                return coleccionAsignaturas[i];
            }
        }
        return null;
    }


    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        int indice = buscarIndice(asignatura);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        } else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
    }


    private int buscarIndice(Asignatura asignatura) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionAsignaturas[i].equals(asignatura)) {
                return i;
            }
        }
        return -1;
    }


    private boolean tamanoSuperado(int indice) {
        return indice >= tamano;
    }


    private boolean capacidadSuperada(int indice) {
        return indice >= capacidad;
    }


    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionAsignaturas[i] = coleccionAsignaturas[i + 1];
        }
    coleccionAsignaturas[tamano - 1] = null;
    }
}
