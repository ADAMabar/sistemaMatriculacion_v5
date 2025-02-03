package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;

import javax.naming.OperationNotSupportedException;

public class Modelo {
    public static final int CAPACIDAD = 3;
    private Alumnos alumnos;
    private CiclosFormativos ciclosFormativos;
    private Asignaturas asignaturas;
    private Matriculas matriculas;

    public void comenzar(){
        alumnos = new Alumnos(CAPACIDAD);
        matriculas = new Matriculas(CAPACIDAD);
        asignaturas = new Asignaturas(CAPACIDAD);
        ciclosFormativos = new CiclosFormativos(CAPACIDAD);
    }
    public void terminar(){
        System.out.println("Modelo terminado.");
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        alumnos.insertar(alumno);
    }
    public Alumno buscar(Alumno alumno){
        return alumnos.buscar(alumno);
    }
    public void borrar(Alumno alumno) throws OperationNotSupportedException{
        alumnos.borrar(alumno);
    }
    public Alumno[] getAlumnos(){
        return alumnos.get();
    }
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        asignaturas.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) {
        return asignaturas.buscar(asignatura);
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        asignaturas.borrar(asignatura);
    }

    public Asignatura[] getAsignaturas() {
        return asignaturas.get();
    }

    public void insertar(CicloFormativo ciclo) throws OperationNotSupportedException {
        ciclosFormativos.insertar(ciclo);
    }

    public CicloFormativo buscar(CicloFormativo ciclo) {
        return ciclosFormativos.buscar(ciclo);
    }

    public void borrar(CicloFormativo ciclo) throws OperationNotSupportedException {
        ciclosFormativos.borrar(ciclo);
    }

    public CicloFormativo[] getCiclosFormativos() {
        return ciclosFormativos.get();
    }


    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        matriculas.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula) {
        return matriculas.buscar(matricula);
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        matriculas.borrar(matricula);
    }

    public Matricula[] getMatriculas() throws OperationNotSupportedException {
        return matriculas.get();
    }


}
