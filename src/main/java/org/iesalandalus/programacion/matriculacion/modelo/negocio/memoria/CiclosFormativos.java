package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;
import java.util.List;



public class CiclosFormativos implements ICiclosFormativos {

    private List<CicloFormativo> coleccionCiclosFormativos;

    public CiclosFormativos() {
        this.coleccionCiclosFormativos = new ArrayList<>();
    }

    public List<CicloFormativo> get() {
        return copiaProfundaCiclosFormativos();
    }

    private List<CicloFormativo> copiaProfundaCiclosFormativos() {
        List<CicloFormativo> copia = new ArrayList<>();
        for (CicloFormativo ciclo : coleccionCiclosFormativos) {
            copia.add(new CicloFormativo(ciclo));
        }
        return copia;
    }

    public int getTamano() {
        return coleccionCiclosFormativos.size();
    }

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        if (buscar(cicloFormativo) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
        coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("El ciclo formativo no puede ser nulo.");
        }


        for (int i = 0; i < getTamano(); i++) {
            if (coleccionCiclosFormativos.get(i).equals(cicloFormativo)) {
                return new CicloFormativo(coleccionCiclosFormativos.get(i));
            }
        }
        return null;
    }

    public CicloFormativo borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }


        for (int i = 0; i < getTamano(); i++) {
            if (coleccionCiclosFormativos.get(i).equals(cicloFormativo)) {
                coleccionCiclosFormativos.remove(i);
                return cicloFormativo;
            }
        }

        throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
    }
    @Override
    public void comenzar() {
        System.out.println("Comenzando ciclo formativo");
    }

    @Override
    public void terminar() {
        System.out.println("Terminar ciclo formativo");
    }
}






