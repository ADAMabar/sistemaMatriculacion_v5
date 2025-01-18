package org.iesalandalus.programacion.matriculacion.negocio;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

    public class CiclosFormativos {


        private CicloFormativo[] coleccionCiclosFormativos;
        private int capacidad;
        private int tamano;


        public CiclosFormativos(int capacidad) {
            if (capacidad <= 0) {
                throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
            }
            this.capacidad = capacidad;
            this.tamano = 0;
            this.coleccionCiclosFormativos = new CicloFormativo[capacidad];
        }


        public CicloFormativo[] get() {
            return copiaProfundaCiclosFormativos();
        }


        private CicloFormativo[] copiaProfundaCiclosFormativos() {
            CicloFormativo[] copia = new CicloFormativo[tamano];


            for (int i = 0; i < tamano; i++) {
                copia[i] = new CicloFormativo(coleccionCiclosFormativos[i]);
            }

            return copia;
        }


        public int getTamano() {
            return tamano;
        }

        public int getCapacidad() {
            return capacidad;
        }


        public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
            if (cicloFormativo == null) {
                throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
            }
            if (buscar(cicloFormativo) != null) {
                throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
            }
            if (tamano >= capacidad) {
                throw new OperationNotSupportedException("ERROR: No se aceptan más ciclos formativos.");
            }

            coleccionCiclosFormativos[tamano++] = new CicloFormativo(cicloFormativo);
        }


        public CicloFormativo buscar(CicloFormativo cicloFormativo) {
            if (cicloFormativo == null) {
                throw new NullPointerException("El ciclo formativo no puede ser nulo.");
            }

            int indice = buscarIndice(cicloFormativo);
            if (indice != -1) {
                return coleccionCiclosFormativos[indice];
            }
            return null;
        }


        public void borrar(CicloFormativo cicloFormativo)throws OperationNotSupportedException{
            if (cicloFormativo == null) {
                throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
            }

            boolean encontrado = false;

            for (int i = 0; i < tamano; i++) {
                if (copiaProfundaCiclosFormativos()[i].equals(cicloFormativo)) {
                    // Desplazar todos los elementos a la izquierda
                    desplazarUnaPosicionHaciaIzquierda(i);
                    tamano--;
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
            }
        }


        private int buscarIndice(CicloFormativo cicloFormativo) {
            for (int i = 0; i < tamano; i++) {
                if (coleccionCiclosFormativos[i].equals(cicloFormativo)) {
                    return i;
                }
            }
            return -1;
        }


        private boolean tamanoSuperado(int indice) {
            return indice >= tamano;
        }


        private boolean capacidadSuperada(int indice) {
            return indice >= capacidad;
        }


        private void desplazarUnaPosicionHaciaIzquierda(int indice) {
            for (int i = indice; i < tamano - 1; i++) {
                coleccionCiclosFormativos[i] = coleccionCiclosFormativos[i + 1];
            }
            coleccionCiclosFormativos[tamano - 1] = null;
        }
    }


