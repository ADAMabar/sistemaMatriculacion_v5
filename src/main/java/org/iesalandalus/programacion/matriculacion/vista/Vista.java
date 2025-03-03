package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Vista {
    private Controlador controlador;



    private void insertarAlumno() {
        System.out.println("===============");
        System.out.println("Insertar Alumno.");
        System.out.println("===============");
        Alumno alumno;
        try {

            alumno = Consola.leerAlumno();
            controlador.insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        }catch (IllegalArgumentException|OperationNotSupportedException|NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void buscarAlumno() {
        System.out.println("=============");
        System.out.println("Buscar alumno.");
        System.out.println("=============");
        try {
            Alumno alumno1 = Consola.leerAlumnoPorDni();
            List<Alumno> coleccionAlumnos = controlador.getAlumnos();

            if (coleccionAlumnos.isEmpty()) {
                System.out.println("Alumno no existente.");
                return;
            }

            boolean encontrado = false;
            for (Alumno alumno : coleccionAlumnos) {
                if (alumno.equals(alumno1)) {
                    System.out.println(alumno);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Alumno no existente.");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void borrarAlumno() {
        System.out.println("=============");
        System.out.println("Borrar alumno.");
        System.out.println("=============");
        mostrarAlumnos();
        try {

            Alumno alumno = Consola.leerAlumnoPorDni();
            Alumno encontrado = controlador.buscar(alumno);
            controlador.borrar(encontrado);

            System.out.println("Alumno borrado correctamente");
        }catch (IllegalArgumentException| OperationNotSupportedException| NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void mostrarAlumnos() {
        System.out.println("==================");
        System.out.println("Listado de Alumnos");
        System.out.println("==================");

       List<Alumno> coleccionAlumnos = controlador.getAlumnos();

        if (coleccionAlumnos == null || coleccionAlumnos.isEmpty()) {
            System.out.println("No existen alumnos en el sistema actualmente.");
            return;
        }
        coleccionAlumnos.sort(Comparator.comparing(Alumno::getNombre));
      coleccionAlumnos.forEach(System.out::println);
    }

    private void insertarAsignatura() {
        System.out.println("======================");
        System.out.println("Listado de Asignaturas");
        System.out.println("======================");

        try {
            CicloFormativo cicloFicticio1 = Consola.getCicloFormativoPorCodigo();
            List<CicloFormativo> coleccionCiclos = controlador.getCiclosFormativos();

            boolean cicloEncontrado = false;
            for (CicloFormativo cicloFormativo : coleccionCiclos) {
                if (cicloFormativo.equals(cicloFicticio1)) {
                    Asignatura asignatura = Consola.leerAsigantura(cicloFormativo);
                    controlador.insertar(asignatura);
                    cicloEncontrado = true;
                    break;
                }
            }

            if (!cicloEncontrado) {
                System.out.println("Ciclo Formativo no existente.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarAsignatura() {
        System.out.println("==================");
        System.out.println("Buscar Asignatura.");
        System.out.println("==================");

        try {
            Asignatura asignaturaBuscada = Consola.getAsignaturaPorCodigo();
            List<Asignatura> coleccionAsignaturas = controlador.getAsignaturas();

            if (coleccionAsignaturas.isEmpty()) {
                System.out.println("No hay asignaturas registradas.");
                return;
            }

            boolean encontrada = false;
            for (Asignatura asignatura : coleccionAsignaturas) {
                if (asignatura.equals(asignaturaBuscada)) {
                    System.out.println("Asignatura encontrada:");
                    System.out.println(asignatura);
                    encontrada = true;
                    break;
                }
            }

            if (!encontrada) {
                System.out.println("Asignatura no existente.");
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void borrarAsignatura()  {
        System.out.println("==================");
        System.out.println("Borrar Asignatura.");
        System.out.println("==================");
        try {

            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            controlador.buscar(asignatura);
            controlador.borrar(asignatura);

            System.out.println("Asignatura borrada correctamente.");
        }catch (OperationNotSupportedException| NullPointerException|IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void mostrarAsignaturas() {
        System.out.println("====================");
        System.out.println("Listado de Asignaturas");
        System.out.println("====================");
        List<Asignatura> coleccionAsignaturas = controlador.getAsignaturas();
        coleccionAsignaturas.sort(Comparator.comparing(Asignatura::getNombre));
        if (coleccionAsignaturas.isEmpty()) {
            System.out.println("No existen asignaturas en el sistema actualmente.");
        } else {
          coleccionAsignaturas.forEach(System.out::println);
        }
    }
    private void insertarCicloFormativo()  {
        System.out.println("=========================");
        System.out.println("Insertar Ciclo Formativo.");
        System.out.println("=========================");
        try {

            CicloFormativo cicloFormativo = Consola.leerCicloformativo();
            controlador.insertar(cicloFormativo);
            System.out.println(cicloFormativo);
            System.out.println("Ciclo formativo insertado correctamente.");
        }catch (IllegalArgumentException| OperationNotSupportedException|NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void buscarCicloFormativo() {
        System.out.println("=========================");
        System.out.println("Buscar Ciclo Formativo.");
        System.out.println("=========================");

        try {

            CicloFormativo cicloFormativo1 = Consola.getCicloFormativoPorCodigo();
            List<CicloFormativo> coleccionCiclos = controlador.getCiclosFormativos();
            boolean cicloEncontrado = false;

            for (CicloFormativo cicloFormativo : coleccionCiclos) {
                controlador.buscar(cicloFormativo1);
                if (cicloFormativo.equals(cicloFormativo1)) {
                    System.out.println(cicloFormativo);
                    cicloEncontrado = true;
                    break; // Salir del bucle si se encuentra el ciclo
                }
            }

            if (!cicloEncontrado) {
                System.out.println("Ciclo Formativo no existente.");
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void borrarCicloFormativo(){
        System.out.println("=========================");
        System.out.println("Borrar Ciclo Formativo.");
        System.out.println("=========================");

    try {
        CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
        controlador.buscar(cicloFormativo);
        controlador.borrar(cicloFormativo);

        System.out.println("Ciclo formativo borrado correctamente: " + cicloFormativo);
    } catch (IllegalArgumentException| OperationNotSupportedException| NullPointerException e){
        System.out.println(e.getMessage());
    }
    }

    private void mostrarCiclosFormativos() {
        System.out.println("==============================");
        System.out.println("Listado de Ciclos Formativos.");
        System.out.println("==============================");

        List<CicloFormativo> listaCiclos = controlador.getCiclosFormativos(); // Cambiado a List
          listaCiclos.sort(Comparator.comparing(CicloFormativo::getNombre));
        if (listaCiclos.isEmpty()) {
            System.out.println("No existen ciclos formativos en el sistema actualmente.");
        } else {
          listaCiclos.forEach(System.out::println);
        }
    }

    private void insertarMatricula() {
        System.out.println("ALUMNOS");
        mostrarAlumnos();
        System.out.println("ASIGNATURAS");
        mostrarAsignaturas();
        System.out.println("=====================");
        System.out.println("Insertar Matrícula.");
        System.out.println("=====================");
        try {

            List<Alumno> coleccionAlumno = controlador.getAlumnos();
            List<Asignatura> asignaturas = controlador.getAsignaturas();

            Alumno alumno = null;

            System.out.print("Introduce el DNI del alumno: ");
            Alumno posibleAlumno = Consola.leerAlumnoPorDni();

            // Comprobar si el alumno realmente existe en la colección
            for (Alumno real : coleccionAlumno) {
                if (real.equals(posibleAlumno)) {
                    alumno = real;
                    break;
                }
            }

            if (alumno == null) {
                System.out.println("Error: No se encontró un alumno con ese DNI. Inténtalo de nuevo.");
                return;
            }

            List<Asignatura> asignaturasSeleccionadas = Consola.elegirAsignaturasMatricula(asignaturas);

            if (asignaturasSeleccionadas != null && !asignaturasSeleccionadas.isEmpty()) {
                Matricula matricula = Consola.leerMatricula(alumno, asignaturasSeleccionadas);
                controlador.insertar(matricula);
                System.out.println(matricula);
                System.out.println("Matrícula insertada correctamente.");
            } else {
                System.out.println("No se puede rellenar matrícula si no hay asignaturas seleccionadas.");
            }

        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void buscarMatricula() {
        System.out.println("=====================");
        System.out.println("Buscar Matrícula.");
        System.out.println("=====================");

        try {
            Matricula matriculaBuscada = Consola.getMatriculaPorIdentificador();
            List<Matricula> coleccionMatriculas = controlador.getMatriculas();

            boolean encontrada = false;
            for (Matricula matricula : coleccionMatriculas) {
                if (matricula.equals(matriculaBuscada)) {
                    System.out.println("Matrícula encontrada:");
                    System.out.println(matricula);
                    encontrada = true;
                    break;
                }
            }

            if (!encontrada) {
                System.out.println("Matrícula no existente.");
            }

            if (coleccionMatriculas.isEmpty()) {
                System.out.println("No hay matrículas registradas.");
            }

        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void mostrarMatriculas() throws OperationNotSupportedException {
        System.out.println("=====================");
        System.out.println("Listado de Matrículas");
        System.out.println("=====================");

        List<Matricula> matriculasRegistradas = controlador.getMatriculas().stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                .thenComparing(matricula -> matricula.getAlumno().getNombre())).toList();

        if ( !matriculasRegistradas.isEmpty()) {
            System.out.println("Matrículas registradas:");

     matriculasRegistradas.forEach(System.out::println);
        } else {
            System.out.println("No existen matrículas registradas en el sistema actualmente.");
        }
    }

private void mostrarMatriculasPorAlumno() throws OperationNotSupportedException {
    System.out.println("=====================");
    System.out.println("Mostrar Matrícula de alumno");
    System.out.println("=====================");

    Alumno alumno = Consola.leerAlumnoPorDni();
    Alumno alumno1 = controlador.buscar(alumno);

    List<Matricula> matriculas = controlador.getMatriculas(alumno1).stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
            .thenComparing(matricula -> matricula.getAlumno().getNombre())).toList();


    if ( !matriculas.isEmpty()) {
        System.out.println("Matrículas registradas:");
        matriculas.forEach(System.out::println);
    } else {
        System.out.println("No hay matrículas registradas para este alumno.");
    }
}



    private void mostrarMatriculasPorCicloFormativo()  throws IllegalArgumentException,OperationNotSupportedException,NullPointerException {
        System.out.println("========================================");
        System.out.println("Mostrar Matrículas por Ciclo Formativo");
        System.out.println("========================================");



            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
           CicloFormativo cicloFormativo1=controlador.buscar(cicloFormativo);
        List<Matricula> matriculas = controlador.getMatriculas(cicloFormativo1).stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                .thenComparing(matricula -> matricula.getAlumno().getNombre())).toList();


        if ( !matriculas.isEmpty()) {
            System.out.println("Matrículas registradas:");
            matriculas.forEach(System.out::println);
        } else {
            System.out.println("No hay matrículas registradas para este cilo formativo.");
        }

    }

    private void mostrarMatriculasPorCursoAcademico() throws IllegalArgumentException, OperationNotSupportedException, NullPointerException {
        System.out.println("====================================");
        System.out.println("Mostrar Matrículas por Curso Académico");
        System.out.println("====================================");

        String cursoAcademico;
        cursoAcademico = Entrada.cadena();


        List<Matricula> matriculas = controlador.getMatriculas(cursoAcademico).stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                .thenComparing(matricula -> matricula.getAlumno().getNombre())).toList();


        if (!matriculas.isEmpty()) {
            System.out.println("Matrículas registradas:");
            matriculas.forEach(System.out::println);
        } else {
            System.out.println("No hay matrículas registradas para este alumno.");
        }

    }

    private void anularMatricula() throws IllegalArgumentException, OperationNotSupportedException, NullPointerException {
        mostrarMatriculas();
        System.out.println("Elige la matrícula que quieres anular:");

        Matricula matriculaFicticia = Consola.getMatriculaPorIdentificador();
        Matricula buscar=controlador.buscar(matriculaFicticia);
        List<Matricula> coleccionMatriculas = controlador.getMatriculas();

        Matricula matriculaReal = null;

        for (Matricula m : coleccionMatriculas) {
            if (m.equals(matriculaFicticia)) {
                matriculaReal = m;
                break;
            }
        }

        if (matriculaReal == null) {
            System.out.println("Error: No se encontró ninguna matrícula con ese identificador.");
            return;
        }

        LocalDate fechaAnulacion = Consola.leerFecha("Introduzca fecha de anulación:");
        buscar.setFechaAnulacion(fechaAnulacion);

        System.out.println("Fecha de anulación insertada correctamente.");
        System.out.println(buscar);
    }



    private void ejecutarOpcion(Opcion opcion)throws IllegalArgumentException, OperationNotSupportedException, NullPointerException{

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
            case BUSCAR_ASIGNATURA -> buscarAsignatura();
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




    public void setControlador(Controlador controlador){
        if(controlador==null ){
            throw new NullPointerException("ERROR: el controlador es nulo.");
        }
        this.controlador=controlador;
    }



    public void comenzar() {
       try {

        Opcion opcion;//
        do {
            System.out.println("Elige una opción:");
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR );
        System.out.println("Hasta luego!!!!");
    }catch (OperationNotSupportedException e) {
           System.out.println(e.getMessage());
       }
    }
    public void terminar(){
        System.out.println("Cerrando.");
    }


}





