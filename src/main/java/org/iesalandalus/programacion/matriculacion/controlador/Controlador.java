package org.iesalandalus.programacion.matriculacion.controlador;

import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;

public class Controlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista){

        if(modelo== null){
            throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
        }
        if (vista==null){
            throw new NullPointerException("ERROR: La vista no puede ser nula.");
        }
        this.modelo=modelo;
        this.vista=vista;
        vista.setControlador(this);
    }

    public void comenzar() throws OperationNotSupportedException{
        modelo.comenzar();
        vista.comenzar();
    }
    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        modelo.insertar(alumno);
    }
    public Alumno buscar(Alumno alumno){
        modelo.buscar(alumno);
        return alumno;
    }
    public void borrar(Alumno alumno)throws OperationNotSupportedException{
        modelo.borrar(alumno);
    }
    public Alumno[] getAlumnos(){
        return modelo.getAlumnos();
    }

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        modelo.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) {
        modelo.buscar(asignatura);
        return asignatura;
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, NullPointerException {
        modelo.borrar(asignatura);
    }

    public Asignatura[] getAsignaturas() {
        return modelo.getAsignaturas();
    }


    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        modelo.insertar(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo ciclo) throws NullPointerException {
        modelo.buscar(ciclo);
        return ciclo;
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        modelo.borrar(cicloFormativo);
    }

    public CicloFormativo[] getCiclosFormativos() {
        return modelo.getCiclosFormativos();
    }


    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        modelo.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula) {
        modelo.buscar(matricula);
        return matricula;
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        modelo.borrar(matricula);
    }

    public Matricula[] getMatriculas() throws OperationNotSupportedException {
        return modelo.getMatriculas();
    }

    public Matricula[] getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        return modelo.getMatriculas(cicloFormativo);
    }
    public Matricula[] getMatriculas(Alumno alumno) throws OperationNotSupportedException {
        return modelo.getMatriculas(alumno);
    }
    public Matricula[] getMatriculas(String cursoAcademico) throws OperationNotSupportedException {
        return modelo.getMatriculas(cursoAcademico);
    }






}
