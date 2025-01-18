package org.iesalandalus.programacion.matriculacion;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.negocio.*;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.negocio.Matriculas;
import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.matriculacion.vista.*;


import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;



public class MainApp {

    public static final int CAPACIDAD = 3;

    public static void main(String[] args) throws OperationNotSupportedException {




















/*

                try {
                    // Crear la colección de alumnos
                    Alumnos coleccionAlumnos = new Alumnos(CAPACIDAD);

                    // Crear instancias de Alumno
                    Alumno alumno1 = new Alumno("Juan Pérez", "12345678Z", "juan.perez@email.com", "123456781", LocalDate.of(2000, 1, 12));


                    // Insertar alumnos en la colección
                    coleccionAlumnos.insertar(alumno1);


                    // Probar el método buscar
                    System.out.println("Buscando a María García:");
                    Alumno encontrado = coleccionAlumnos.buscar(alumno1);

                    if (encontrado != null) {
                        System.out.println("Alumno encontrado: " + encontrado);
                    } else {
                        System.out.println("Alumno no encontrado.");
                    }

                    // Probar buscar un alumno que no existe
                    Alumno alumnoNoExiste = new Alumno("Ana López", "55667788B", "ana.lopez@email.com", "556677889", LocalDate.of(2003, 8, 25));
                    System.out.println("\nBuscando a Ana López:");
                    encontrado = coleccionAlumnos.buscar(alumnoNoExiste);

                    if (encontrado != null) {
                        System.out.println("Alumno encontrado: " + encontrado);
                    } else {
                        System.out.println("Alumno no encontrado.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

*/




        try {

            // Crear instancias de Alumno y CicloFormativo
            Alumno alumno1 = new Alumno("Juan Pérez", "12345678Z","adadafsdas@afsdasds.asd" , "123456781", LocalDate.of(2000, 1, 12));

            CicloFormativo ciclo1 = new CicloFormativo(1231,"informatica",Grado.GDCFGB,"jose",123 );
            CicloFormativo ciclo2 = new CicloFormativo(1231,"Electronica",Grado.GDCFGB,"javi",121 );
            Asignatura asignatura1=new Asignatura("1222","DAW",100,Curso.SEGUNDO,3,EspecialidadProfesorado.SISTEMAS,ciclo1);

            Asignaturas asignaturas = new Asignaturas(5);

            asignaturas.insertar(asignatura1);

            CiclosFormativos ciclosFormativos =new CiclosFormativos(4);

            ciclosFormativos.insertar(ciclo1);

            System.out.println("Ciiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiic");
            Consola.mostrarCiclosFormativos(ciclosFormativos);
            // Crear instancias de Matricula
            Matricula matricula1 = new Matricula(1234,"23-24",LocalDate.of(2025,1,10),alumno1,asignaturas.get());



            // Crear la colección de matrículas con capacidad 5
            Matriculas coleccion = new Matriculas(5);

            // Insertar matrículas
            coleccion.insertar(matricula1);


/*
            // Probar método get (sin parámetros)
            System.out.println("Todas las matrículas:");
            System.out.println(Arrays.toString(coleccion.get()));

            // Probar método get por alumno
            System.out.println("\nMatrículas de Juan Pérez:");
            System.out.println(Arrays.toString(coleccion.get(alumno1)));

            // Probar método get por curso académico
            System.out.println("\nMatrículas del curso 2023-2024:");
            System.out.println(Arrays.toString(coleccion.get("23-23")));

            // Probar método get por ciclo formativo
            System.out.println("\nMatrículas del ciclo Informática:");
            System.out.println(Arrays.toString(coleccion.get(ciclo1)));

            // Probar eliminación
            coleccion.borrar(matricula1);
            System.out.println("\nDespués de borrar una matrícula:");
            System.out.println(Arrays.toString(coleccion.get()));
*/
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }








/*





                try {
                    // Crear asignaturas de prueba
                    Asignatura asignatura1 = new Asignatura("1234","ADA",12,Curso.PRIMERO,1,EspecialidadProfesorado.INFORMATICA,new CicloFormativo(1234,"iNFORMATICA",Grado.GDCFGB,"ASENSIO",12));
                    Asignatura asignatura2 = new Asignatura("1244","ADA",12,Curso.PRIMERO,2,EspecialidadProfesorado.INFORMATICA,new CicloFormativo(1234,"iNFORMATICA",Grado.GDCFGB,"JUAN",12));
                    Asignatura asignatura3 = new Asignatura("1534","ADA",12,Curso.PRIMERO,3,EspecialidadProfesorado.INFORMATICA,new CicloFormativo(1234,"iNFORMATICA",Grado.GDCFGB,"PEREZ",12));
                    Asignatura[] asignaturas = {asignatura1, asignatura2, asignatura3};

                    // Crear alumno de prueba
                    Alumno alumno = new Alumno("Juan Pérez", "12345678Z", "juan.perez@gmail.com", "666555444", LocalDate.of(2005, 1, 15));

                    // Crear matrícula
                    Matricula matricula = new Matricula(1, "23-24",LocalDate.of(2005,12,1) , alumno, asignaturas);

                    // Obtener y mostrar asignaturas
                    System.out.println("Asignaturas iniciales:");
                    for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
                        System.out.println(asignatura);
                    }

                    // Comprobar el método superaMaximoNumeroHorasMatricula
                    boolean superaHoras = matricula.superaMaximoNumeroHorasMatricula(asignaturas);
                    System.out.println("¿Supera el máximo de horas? " + superaHoras);

                    // Probar setColeccionAsignaturas con valor nulo
                    try {
                        matricula.setColeccionAsignaturas(null);
                    } catch (NullPointerException e) {
                        System.out.println("Excepción esperada: " + e.getMessage());
                    }

                    // Probar superaMaximoNumeroHorasMatricula con matrícula anulada
                    matricula.setFechaAnulacion(LocalDate.now());
                    try {
                        superaHoras = matricula.superaMaximoNumeroHorasMatricula(asignaturas);
                    } catch (OperationNotSupportedException e) {
                        System.out.println("Excepción esperada: " + e.getMessage());
                    }

                } catch (Exception e) {
                    System.out.println("Error en las pruebas: " + e.getMessage());
                }*/
        System.out.println("Hasta luego!!!!");
            }

        }




