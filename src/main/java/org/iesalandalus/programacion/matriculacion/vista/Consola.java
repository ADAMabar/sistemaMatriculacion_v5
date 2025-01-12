package org.iesalandalus.programacion.matriculacion.vista;

import java.time.LocalDate;
import java.util.Scanner;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;

public class Consola {

        // Constructor privado para evitar la instancia de la clase Consola
        private Consola() {
        }

        // Mostrar el menú de opciones disponibles
        public static void mostrarMenu() {
            System.out.println("Meunú de opciones, digite su opción");


            for (Opcion opcion : Opcion.values()) {
                System.out.println(opcion);
            }
        }
        public Opcion elegirOpcion (){
            mostrarMenu();
            int opcion=-1;
           while (true){

               opcion=Entrada.entero();
               if (opcion >= 0 || opcion>Opcion.values().length){
                   return Opcion.values()[opcion];
               }else{
                   System.out.println("ERROR: No puedes elegir una opción inexistente");
               }
           }

        }

        public Alumno leerAlumno(){
            String Nombre, dni, correo, telefono, fecha;

            System.out.println("Introduce los datos del alumno");
            System.out.println("==============================");
            System.out.print("Nombre: ");
            Nombre=Entrada.cadena();
            System.out.print("DNI: ");
            dni=Entrada.cadena();
            System.out.print("Correo: ");
            correo=Entrada.cadena();
            System.out.println("Telefono: ");
            telefono=Entrada.cadena();
            System.out.println("Fecha de nacimiento: ");
            fecha=Entrada.cadena();


            LocalDate localDate;
            return new Alumno(Nombre,dni,correo,telefono,LocalDate.of(2000,12,1));



        }/*
        public Alumno leerAlumnoPorDni(){

                Scanner scanner = new Scanner(System.in);

                System.out.println("Introduce el DNI del alumno (8 dígitos seguidos de una letra):");
                String dni = scanner.nextLine();

                // Verificar el formato del DNI y comprobar la letra
                if (!comprobarLetraDni(dni)) {
                    throw new IllegalArgumentException("ERROR: El DNI introducido no es válido.");
                }

                // Crear datos ficticios para el resto de los campos
                String nombreFicticio = "Alumno Ficticio";
                String apellidoFicticio = "Apellido Ficticio";
                LocalDate fechaNacimientoFicticia = LocalDate.of(2000, 1, 1); // Fecha ficticia válida

                // Crear y devolver el objeto Alumno
                return new Alumno(nombreFicticio, apellidoFicticio, dni, fechaNacimientoFicticia);
            }

        }*/




    }



