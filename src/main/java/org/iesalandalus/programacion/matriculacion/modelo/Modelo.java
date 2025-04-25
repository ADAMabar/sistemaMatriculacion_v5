package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;



import javax.naming.OperationNotSupportedException;
import java.util.List;


public class Modelo {
    private IFuenteDatos fuenteDatos;
    private IAlumnos alumnos;
    private ICiclosFormativos ciclosFormativos;
    private IAsignaturas asignaturas;
    private IMatriculas matriculas;

    public Modelo(IFuenteDatos fuenteDatos) {
        this.fuenteDatos = fuenteDatos;
    }

    public void setFuenteDatos(IFuenteDatos fuenteDatos) {
        this.fuenteDatos = fuenteDatos;
    }

    public void comenzar() {
        alumnos = fuenteDatos.crearAlumnos();
        ciclosFormativos = fuenteDatos.crearCiclosFormativos();
        asignaturas = fuenteDatos.crearAsignaturas();
        matriculas = fuenteDatos.crearMatriculas();
    }

    public void terminar() {
        if (alumnos != null) {
            alumnos.terminar();
        }
        if (ciclosFormativos != null) {
            ciclosFormativos.terminar();
        }
        if (asignaturas != null) {
            asignaturas.terminar();
        }
        if (matriculas != null) {
            matriculas.terminar();
        }
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
    public List<Matricula> getMatriculas(Alumno alumno) throws IllegalArgumentException,NullPointerException,OperationNotSupportedException  {
        return matriculas.get(alumno);
    }
    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws IllegalArgumentException,NullPointerException,OperationNotSupportedException {
        return matriculas.get(cicloFormativo);
    }
    public List<Matricula> getMatriculas(String cursoAcademico) throws IllegalArgumentException,NullPointerException,OperationNotSupportedException  {
        return matriculas.get(cursoAcademico);
    }
}
