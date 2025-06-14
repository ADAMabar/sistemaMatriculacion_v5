package org.iesalandalus.programacion.matriculacion.vista.grafica;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

    public class VistaGrafica extends Vista {
        private static VistaGrafica instancia;

        public VistaGrafica() {
            super();
        }

        public static VistaGrafica getInstancia() {
            if (instancia == null) {
                instancia = new VistaGrafica();
            }
            return instancia;
        }
        @Override
        public void comenzar() {
            LanzadoraVentanaPrincipal.comenzar();
        }

        @Override
        public void terminar() {
            controlador.terminar();
        }
    }

