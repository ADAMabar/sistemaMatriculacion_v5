package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;
import java.util.List;


public class Matriculas {
    private List<Matricula> coleccionMatriculas;
    private int capacidad;

    public Matriculas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.coleccionMatriculas = new ArrayList<>(capacidad);
    }

    public List<Matricula> get() throws OperationNotSupportedException{
        List<Matricula> copia = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            copia.add(new Matricula(matricula));
        }
        return copia;
    }

    public int getTamano() {
        return coleccionMatriculas.size();
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }
        if (coleccionMatriculas.size() >= capacidad) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más matrículas.");
        }
        coleccionMatriculas.add(new Matricula(matricula));
    }

    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new NullPointerException("La matricula no puede ser nula.");
        }
        int indice = coleccionMatriculas.indexOf(matricula);
        return (indice != -1) ? coleccionMatriculas.get(indice) : null;
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        if (!coleccionMatriculas.remove(matricula)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
    }

    public List<Matricula> get(Alumno alumno) {
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getAlumno().equals(alumno)) {
                resultado.add(matricula);
            }
        }
        return resultado;
    }

    public List<Matricula> get(String cursoAcademico) {
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getCursoAcademico().equals(cursoAcademico)) {
                resultado.add(matricula);
            }
        }
        return resultado;
    }

    public List<Matricula> get(CicloFormativo cicloFormativo) {
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
                if (asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    resultado.add(matricula);
                    break;
                }
            }
        }
        return resultado;
    }
}
