package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum TiposGrado {

        GRADOD("Grado D"),
        GRADOE("Grado E");
        private final String cadenaMostrar;


        private TiposGrado(String cadenaMostrar) {
            this.cadenaMostrar = cadenaMostrar;
        }


        public String imprimir() {
            return this.ordinal() + ".-" + this.cadenaMostrar;
        }


        @Override
        public String toString() {
            return ordinal() + " .- " + cadenaMostrar;
        }
}


