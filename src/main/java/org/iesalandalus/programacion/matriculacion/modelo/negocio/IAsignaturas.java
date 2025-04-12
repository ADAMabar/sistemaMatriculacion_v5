package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;


import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IAsignaturas {
    void comenzar();
    void terminar();

        // Métodos que debe tener la clase Asignaturas
        void insertar(Asignatura asignatura) throws OperationNotSupportedException;

        Asignatura buscar(Asignatura asignatura);

        Asignatura borrar(Asignatura asignatura) throws OperationNotSupportedException;

        List<Asignatura> get();

        int getTamano();
}
