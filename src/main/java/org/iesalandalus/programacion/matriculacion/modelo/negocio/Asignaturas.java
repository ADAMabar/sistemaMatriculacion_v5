package org.iesalandalus.programacion.matriculacion.modelo.negocio;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Asignaturas {
    private List<Asignatura> coleccionAsignaturas;

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
    }

    public List<Asignatura> get() {
        return copiaProfundaAsignaturas();
    }

    private List<Asignatura> copiaProfundaAsignaturas() {
        List<Asignatura> copia = new ArrayList<>();
        for (Asignatura asignatura : coleccionAsignaturas) {
            copia.add(new Asignatura(asignatura));
        }
        return copia;
    }

    public int getTamano() {
        return coleccionAsignaturas.size();
    }

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        if (buscar(asignatura) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        coleccionAsignaturas.add(new Asignatura(asignatura));
    }

    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: La asignatura no puede ser nula.");
        }

        for (int i = 0; i < getTamano(); i++) {
            if (coleccionAsignaturas.get(i).equals(asignatura)) {
                return coleccionAsignaturas.get(i);
            }
        }
        return null;
    }

    public Asignatura borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }


        for (int i = 0; i < getTamano(); i++) {
            if (coleccionAsignaturas.get(i).equals(asignatura)) {
                coleccionAsignaturas.remove(i);
                return asignatura;
            }
        }

        throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
    }
}
