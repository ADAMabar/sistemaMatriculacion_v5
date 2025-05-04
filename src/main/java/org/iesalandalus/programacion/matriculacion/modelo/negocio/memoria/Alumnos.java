package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;


import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Alumnos implements IAlumnos {
    private ArrayList<Alumno> coleccionAlumnos;

    public Alumnos() {
        coleccionAlumnos = new ArrayList<>();
    }

    public ArrayList<Alumno> get() {
        return copiaProfundaAlumnos();
    }

    private ArrayList<Alumno> copiaProfundaAlumnos() {
        ArrayList<Alumno> copia = new ArrayList<>();
        for (Alumno alumno : coleccionAlumnos) {
            copia.add(new Alumno(alumno));
        }
        return copia;
    }

    public int getTamano() {
        return coleccionAlumnos.size();
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if (buscar(alumno) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }

        coleccionAlumnos.add(new Alumno(alumno));
    }

    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
        }


        for (int i = 0; i < getTamano(); i++) {
            if (coleccionAlumnos.get(i).equals(alumno)) {
                return coleccionAlumnos.get(i);
            }
        }
        return null;
    }

    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }

        for (int i = 0; i < getTamano(); i++) {
            if (coleccionAlumnos.get(i).equals(alumno)) {
                coleccionAlumnos.remove(i);
                return;
            }
        }

        throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
    }


    @Override
    public void comenzar() {
        System.out.println("Comenzar alumnos");
    }

    @Override
    public void terminar() {
        System.out.println("Terminar alumno");
    }
}
