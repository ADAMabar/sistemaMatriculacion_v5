package org.iesalandalus.programacion.matriculacion;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.matriculacion.vista.*;


import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;


public class MainApp {

    public static final int CAPACIDAD = 3;
    private Alumnos alumnos;
    private CiclosFormativos ciclosFormativos;
    private Asignaturas asignaturas;
    private Matriculas matriculas;

    public MainApp() {
        alumnos = new Alumnos(CAPACIDAD);
        matriculas = new Matriculas(CAPACIDAD);
        asignaturas = new Asignaturas(CAPACIDAD);
        ciclosFormativos = new CiclosFormativos(CAPACIDAD);
    }

    private void insertarAlumno(){
        System.out.println("===============");
        System.out.println("Insertar Alumno.");
        System.out.println("===============");
        Alumno alumno;
        try {
            alumno = Consola.leerAlumno();
            alumnos.insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        } catch ( IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println( e.getMessage());
            System.out.println("No se ha podido insertar el alumno, porfavor intentelo de nuevo.");
        }
    }


    private void buscarAlumno(){
        System.out.println("=============");
        System.out.println("Buscar alumno.");
        System.out.println("=============");

        try {

            Alumno alumno = Consola.leerAlumnoPorDni();


            Alumno encontrado = alumnos.buscar(alumno);


            if (encontrado != null) {
                System.out.println("Alumno encontrado: " + encontrado);
            } else {
                System.out.println("Alumno no exsitente.");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println( e.getMessage());
        }



    }

    private void borrarAlumno(){
        System.out.println("=============");
        System.out.println("Borrar alumno.");
        System.out.println("=============");
        try {

            Alumno alumno = Consola.leerAlumnoPorDni();
            Alumno encontrado = alumnos.borrar(alumno);

            if (encontrado != null) {
                System.out.println("Alumno borrado correctamente: " + encontrado);
            } else {
                System.out.println("El alumno no estaba registrado en el sistema.");
            }
        }catch (IllegalArgumentException | OperationNotSupportedException| NullPointerException e){
            System.out.println(e.getMessage()+" Porfavor intentelo de nuevo.");
        }

    }

    private void mostrarAlumnos (){
        System.out.println("==================");
        System.out.println("Listado de Alumnos");
        System.out.println("==================");

        Alumno[] coleccionAlumnos = alumnos.get();

        if(coleccionAlumnos==null){
            System.out.println("No existen alumnos en el sistema actualmente");
        }
        if (coleccionAlumnos != null) {
            for (Alumno alumno: coleccionAlumnos){
                System.out.println(alumno);
            }
        }

    }
    private void insertarAsignatura() {
        System.out.println("======================");
        System.out.println("Listado de Asignaturas");
        System.out.println("======================");


        Asignatura asignatura;
        try {
            asignatura = Consola.leerAsigantura(ciclosFormativos);  // Método en Consola que pide los datos de la asignatura
            asignaturas.insertar(asignatura);
            System.out.println("Asignatura insertada correctamente.");
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No se ha podido insertar la asignatura, por favor intente de nuevo.");
        }
    }
    private void buscarAsigantura(){
        System.out.println("==================");
        System.out.println("Buscar Asignatura.");
        System.out.println("==================");

        try {

            Asignatura asignatura = Consola.getAsignaturaPorCodigo();


            Asignatura asignaturaEncontrada = asignaturas.buscar(asignatura);


            if (asignaturaEncontrada != null) {
                System.out.println("Asignatura registrada.");
                System.out.println(asignaturaEncontrada);
            } else {
                System.out.println("No se encontró ninguna asignatura con el código que has proporcionado.");
            }
        } catch (Exception e) {
            System.out.println( e.getMessage()+" Porfavor intentelo de nuevo.");
        }

    }
    private void borrarAsignatura() {
        System.out.println("==================");
        System.out.println("Borrar Asignatura.");
        System.out.println("==================");
        try {
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            Asignatura asignaturaBorrada = asignaturas.borrar(asignatura);

            if (asignaturaBorrada != null) {
                System.out.println("Asignatura borrada correctamente." );
            } else {
                System.out.println("La asignatura no estaba registrada en el sistema.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage() + " Por favor, inténtelo de nuevo.");
        }
    }

    private void mostrarAsignaturas() {
        System.out.println("====================");
        System.out.println("Listado de Asignaturas");
        System.out.println("====================");

        Asignatura[] coleccionAsignaturas = asignaturas.get();

        if (coleccionAsignaturas == null || coleccionAsignaturas.length == 0) {
            System.out.println("No existen asignaturas en el sistema actualmente.");
        } else {
            for (Asignatura asignatura : coleccionAsignaturas) {
                System.out.println(asignatura);
            }
        }
    }
    private void insertarCicloFormativo() {
        System.out.println("=========================");
        System.out.println("Insertar Ciclo Formativo.");
        System.out.println("=========================");

        try {
            CicloFormativo cicloFormativo = Consola.leerCicloformativo();
            ciclosFormativos.insertar(cicloFormativo);
            System.out.println("Ciclo formativo insertado correctamente.");
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No se ha podido insertar el ciclo formativo. Por favor, inténtelo de nuevo.");
        }
    }

    private void buscarCicloFormativo() {
        System.out.println("=========================");
        System.out.println("Buscar Ciclo Formativo.");
        System.out.println("=========================");

        try {
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo encontrado = ciclosFormativos.buscar(cicloFormativo);

            if (encontrado != null) {
                System.out.println("Ciclo formativo encontrado: " + encontrado);
            } else {
                System.out.println("No se encontró ningún ciclo formativo con el código proporcionado.");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No se ha podido buscar el ciclo formativo. Por favor, inténtelo de nuevo.");
        }
    }

    private void borrarCicloFormativo() {
        System.out.println("=========================");
        System.out.println("Borrar Ciclo Formativo.");
        System.out.println("=========================");

        try {
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo eliminado = ciclosFormativos.borrar(cicloFormativo);

            if (eliminado != null) {
                System.out.println("Ciclo formativo borrado correctamente: " + eliminado);
            } else {
                System.out.println("El ciclo formativo no estaba registrado en el sistema.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No se ha podido borrar el ciclo formativo. Por favor, inténtelo de nuevo.");
        }
    }

    private void mostrarCiclosFormativos() {
        System.out.println("==============================");
        System.out.println("Listado de Ciclos Formativos.");
        System.out.println("==============================");


        if (ciclosFormativos.get() == null || ciclosFormativos.get().length == 0) {
            System.out.println("No existen ciclos formativos en el sistema actualmente.");
        } else {
            for (CicloFormativo cicloFormativo : ciclosFormativos.get()) {
                System.out.println(cicloFormativo);
            }
        }
    }
    private void insertarMatricula() {
        System.out.println("ALUMNOS");
        mostrarAlumnos();
        System.out.println("ASIGNATURAS");
        mostrarAsignaturas();
        System.out.println("CICLOS FORMATIVOS");
        mostrarCiclosFormativos();
        System.out.println("=====================");
        System.out.println("Insertar Matrícula.");
        System.out.println("=====================");

        try {
            Matricula matricula = Consola.leerMatricula(alumnos ,asignaturas);
            matriculas.insertar(matricula);
            System.out.println("Matrícula insertada correctamente.");
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage()+" Por favor, inténtelo de nuevo.");
        }
    }

    private void buscarMatricula() {
        System.out.println("=====================");
        System.out.println("Buscar Matrícula.");
        System.out.println("=====================");

        try {
            Matricula matricula = Consola.getMatriculaPorIdentificador();
            matricula = matriculas.buscar(matricula);

            if (matricula != null) {
                System.out.println("Matrícula encontrada: " + matricula);
            } else {
                System.out.println("No se encontró ninguna matrícula con el identificador proporcionado.");
            }
        } catch (IllegalArgumentException | NullPointerException| OperationNotSupportedException e) {
            System.out.println(e.getMessage()+" Por favor, inténtelo de nuevo.");

        }
    }


    private void mostrarMatriculas()  {
        System.out.println("=====================");
        System.out.println("Listado de Matrículas");
        System.out.println("=====================");


        try {
            System.out.println("Matrículas registradas:");
            for (Matricula matricula : matriculas.get()) {
                System.out.println(matricula);
            }


        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private void mostrarMatriculasPorAlumno()  {
        System.out.println("=====================");
        System.out.println("Mostrar Matricula de alumno");
        System.out.println("=====================");


        Alumno alumno = Consola.leerAlumnoPorDni();
        alumno=alumnos.buscar(alumno);



        System.out.println("Matrículas registradas:");
        for (Matricula matricula : matriculas.get(alumno)) {
            System.out.println(matricula);
        }

    }


    private void mostrarMatriculasPorCicloFormativo() {
        System.out.println("========================================");
        System.out.println("Mostrar Matrículas por Ciclo Formativo");
        System.out.println("========================================");

        try {

            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            cicloFormativo=ciclosFormativos.buscar(cicloFormativo);

            System.out.println("Matrículas registradas para el ciclo formativo: " );
            for (Matricula matricula : matriculas.get(cicloFormativo)) {
                System.out.println(matricula);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println( e.getMessage());
        }
    }
    private void mostrarMatriculasPorCursoAcademico(){
        System.out.println("====================================");
        System.out.println("Mostrar Matrículas por Curso Académico");
        System.out.println("====================================");

        String cursoAcademico;
        cursoAcademico=Entrada.cadena();
        try {

            System.out.println();
            System.out.println("Matrículas registradas para el curso académico: " );
            for (Matricula matricula : matriculas.get(cursoAcademico)) {
                System.out.println(matricula);
            }
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println( e.getMessage());
        }
    }

    private void anularMatricula(){
        mostrarMatriculas();
        System.out.println("Elige la matricla que quiere anular");
        try {

            Matricula matricula = Consola.getMatriculaPorIdentificador();
            Matricula matricula1=matriculas.buscar(matricula);

            LocalDate fechaAnulacion=Consola.leerFecha("Introduzca fecha anulacion:");
            matricula1.setFechaAnulacion(fechaAnulacion);

            System.out.println("Fecha de anulación insertada.");
        } catch (OperationNotSupportedException| IllegalArgumentException |NullPointerException e) {
            System.out.println(e.getMessage());
        }


    }




    private void ejecutarOpcion(Opcion opcion) throws OperationNotSupportedException {
        switch (opcion) {

            case INSERTAR_ALUMNO -> insertarAlumno();
            case BUSCAR_ALUMNO -> buscarAlumno();
            case BORRAR_ALUMNO -> borrarAlumno();
            case MOSTRAR_ALUMNOS -> mostrarAlumnos();
            case INSERTAR_CICLO_FORMATIVO -> insertarCicloFormativo();
            case BUSCAR_CICLO_FORMATIVO -> buscarCicloFormativo();
            case BORRAR_CICLO_FORMATIVO -> borrarCicloFormativo();
            case MOSTRAR_CICLOS_FORMATIVOS -> mostrarCiclosFormativos();
            case INSERTAR_ASIGNATURA -> insertarAsignatura();
            case BUSCAR_ASIGNATURA -> buscarAsigantura();
            case BORRAR_ASIGNATURA -> borrarAsignatura();
            case MOSTRAR_ASIGNATURAS -> mostrarAsignaturas();
            case INSERTAR_MATRICULA -> insertarMatricula();
            case BUSCAR_MATRICULA -> buscarMatricula();
            case ANULAR_MATRICULA -> anularMatricula();
            case MOSTRAR_MATRICULAS -> mostrarMatriculas();
            case MOSTRAR_MATRICULAS_ALUMNO -> mostrarMatriculasPorAlumno();
            case MOSTRAR_MATRICULAS_CICLO_FORMATIVO -> mostrarMatriculasPorCicloFormativo();
            case MOSTRAR_MATRICULAS_CURSO_ACADEMICO -> mostrarMatriculasPorCursoAcademico();
            case SALIR -> System.out.println("Saliendo del sistema.");
            default -> System.out.println("Opción no reconocida. Inténtelo de nuevo.");
        }
    }





















    public static void main(String[] args) throws OperationNotSupportedException {

        MainApp app = new MainApp();

        Opcion opcion;
        do {

            opcion = Consola.elegirOpcion();
            app.ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
        System.out.println("Hasta luego!!!!");
    }

}






