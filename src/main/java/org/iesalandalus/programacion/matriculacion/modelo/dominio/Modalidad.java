package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Modalidad {


        PRESENCIAL("Presencial"),
        SEMIPRESENCIAL("Semipresencial");

        private final String cadenaMostrar;


        private Modalidad(String cadenaAMostrar) {
            this.cadenaMostrar = cadenaAMostrar;
        }

    public String imprimir() {
        return this.ordinal() + ".-" + this.cadenaMostrar;
    }

        @Override
        public String toString() {
            return ordinal() + " .- " + cadenaMostrar;
        }
    }



