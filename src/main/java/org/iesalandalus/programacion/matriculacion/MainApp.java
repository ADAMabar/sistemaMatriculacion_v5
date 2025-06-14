package org.iesalandalus.programacion.matriculacion;
import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;

import org.iesalandalus.programacion.matriculacion.vista.*;


import javax.naming.OperationNotSupportedException;



public class MainApp {
    private static Modelo procesarArgumentosFuenteDatos(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Debe indicarse un argumento: -fdmemoria o -fdmysql");
        }

        switch (args[0]) {
            case "-fdmemoria":
                return new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
            case "-fdmysql":
                return new Modelo(FactoriaFuenteDatos.MYSQL.crear());
            case "-fdfichero":
                return new Modelo(FactoriaFuenteDatos.FICHERO.crear());
            default:
                throw new IllegalArgumentException("Fuente de datos no válida. Use -fdmemoria o -fdmysql");
        }
    }

    private static Vista procesarArgumentosVista(String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "-vgrafica":
                    System.out.println("Vista: GRAFICA");
                    return FactoriaVista.GRAFICA.crear();
                case "-vtexto":
                    System.out.println("Vista: TEXTO");
                    return FactoriaVista.TEXTO.crear();
            }
        }
        throw new IllegalArgumentException("El argumento de vista no valido, elija: -vtexto o -vgrafica");
    }

        public static void main (String[]args) throws OperationNotSupportedException {
            System.out.println("Iniciando la apliación...");

            Modelo modelo = procesarArgumentosFuenteDatos(args);
            Vista vista = procesarArgumentosVista(args);

            Controlador controlador = new Controlador(modelo, vista);
            controlador.comenzar();
            controlador.terminar();

            System.out.println("Aplicación cerrada.");
        }

    }







