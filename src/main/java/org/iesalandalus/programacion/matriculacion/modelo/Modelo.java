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
import java.util.List;


public class Modelo {
    public static final int CAPACIDAD = 3;
    private Alumnos alumnos;
    private CiclosFormativos ciclosFormativos;
    private Asignaturas asignaturas;
    private Matriculas matriculas;

    public void comenzar(){
        alumnos = new Alumnos();
        matriculas = new Matriculas();
        asignaturas = new Asignaturas();
        ciclosFormativos = new CiclosFormativos();
    }
    public void terminar(){
        System.out.println("Modelo terminado.");
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException,IllegalArgumentException,NullPointerException {
        alumnos.insertar(alumno);
    }
    public Alumno buscar(Alumno alumno){
        return alumnos.buscar(alumno);
    }
    public void borrar(Alumno alumno) throws OperationNotSupportedException,IllegalArgumentException,NullPointerException {
        alumnos.borrar(alumno);
    }

    public List<Alumno>getAlumnos(){
        return alumnos.get();
    }

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException,IllegalArgumentException,NullPointerException  {
        asignaturas.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) {
        return asignaturas.buscar(asignatura);
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException ,IllegalArgumentException,NullPointerException {
        asignaturas.borrar(asignatura);
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas.get();
    }

    public void insertar(CicloFormativo ciclo) throws OperationNotSupportedException ,IllegalArgumentException,NullPointerException {
        ciclosFormativos.insertar(ciclo);
    }

    public CicloFormativo buscar(CicloFormativo ciclo) throws NullPointerException{
        return ciclosFormativos.buscar(ciclo);
    }

    public void borrar(CicloFormativo ciclo) throws OperationNotSupportedException ,IllegalArgumentException,NullPointerException {
        ciclosFormativos.borrar(ciclo);
    }

    public List<CicloFormativo> getCiclosFormativos() {
        return ciclosFormativos.get();
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException ,IllegalArgumentException,NullPointerException {
        matriculas.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula) {
        return matriculas.buscar(matricula);
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException ,IllegalArgumentException,NullPointerException {
        matriculas.borrar(matricula);
    }

    public List<Matricula> getMatriculas() throws OperationNotSupportedException ,IllegalArgumentException,NullPointerException {
        return matriculas.get();
    }
    public List<Matricula> getMatriculas(Alumno alumno) throws IllegalArgumentException,NullPointerException  {
        return matriculas.get(alumno);
    }
    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws IllegalArgumentException,NullPointerException  {
        return matriculas.get(cicloFormativo);
    }
    public List<Matricula> getMatriculas(String cursoAcademico) throws IllegalArgumentException,NullPointerException  {
        return matriculas.get(cursoAcademico);
    }
}
