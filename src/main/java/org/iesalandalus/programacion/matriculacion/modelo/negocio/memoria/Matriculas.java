package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;


import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;
import java.util.List;


public class Matriculas implements IMatriculas {

    private List<Matricula> coleccionMatriculas;

    public Matriculas() {
        coleccionMatriculas = new ArrayList<>();
    }

    public List<Matricula> get() throws OperationNotSupportedException {
        List<Matricula> copia = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            copia.add(new Matricula(matricula)); // Suponiendo que Matricula tiene un constructor de copia
        }
        return copia;
    }

    public int getTamano() {
        return coleccionMatriculas.size();
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (buscar(matricula) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }

        coleccionMatriculas.add(new Matricula(matricula));
    }

    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }

        // Usamos getTamano() para buscar la matrícula
        for (int i = 0; i < getTamano(); i++) {
            if (coleccionMatriculas.get(i).equals(matricula)) {
                return coleccionMatriculas.get(i);
            }
        }
        return null;
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        // Usamos getTamano() para buscar y eliminar la matrícula
        for (int i = 0; i < getTamano(); i++) {
            if (coleccionMatriculas.get(i).equals(matricula)) {
                coleccionMatriculas.remove(i);
                return;
            }
        }

        throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
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

    @Override
    public void comenzar() {
        System.out.println("Comenzando matriculación");
    }

    @Override
    public void terminar() {
        System.out.println("Terminando matriculación");
    }
}


