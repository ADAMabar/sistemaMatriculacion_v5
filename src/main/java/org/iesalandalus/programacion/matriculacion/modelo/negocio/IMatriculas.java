package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IMatriculas {

    // Métodos de la clase Matriculas
    void insertar(Matricula matricula) throws OperationNotSupportedException;

    Matricula buscar(Matricula matricula);

    void borrar(Matricula matricula) throws OperationNotSupportedException;

    List<Matricula> get() throws OperationNotSupportedException;

    int getTamano();

    // Métodos adicionales de búsqueda
    List<Matricula> get(Alumno alumno) throws OperationNotSupportedException;

    List<Matricula> get(String cursoAcademico) throws OperationNotSupportedException;

    List<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException;

    // Métodos comenzando y terminando
    void comenzar();

    void terminar();
}

