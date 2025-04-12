package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public interface IAlumnos {
    void comenzar();
    void terminar();
    ArrayList<Alumno> get();


    int getTamano();
    void insertar(Alumno alumno) throws OperationNotSupportedException;
    Alumno buscar(Alumno alumno);
    Alumno borrar(Alumno alumno) throws OperationNotSupportedException;

}
