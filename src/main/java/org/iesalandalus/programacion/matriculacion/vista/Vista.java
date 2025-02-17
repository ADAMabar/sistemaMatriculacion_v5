package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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


    private void buscarAlumno()   {
        System.out.println("=============");
        System.out.println("Buscar alumno.");
        System.out.println("=============");
        try {
            Alumno alumno1 = Consola.leerAlumnoPorDni();
            Alumno[] coleccionAlumnos = controlador.getAlumnos();
            for (Alumno alumno: coleccionAlumnos){
                controlador.buscar(alumno1);
                if (alumno.equals(alumno1)){
                    System.out.println(alumno);
                }else{
                    System.out.println("Alumno no exsitente.");
                }
            }
        if(coleccionAlumnos.length==0){
            System.out.println("Alumno no exsitente.");
        }
        }catch (IllegalArgumentException| NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    private void borrarAlumno() throws IllegalArgumentException, OperationNotSupportedException, NullPointerException{
        System.out.println("=============");
        System.out.println("Borrar alumno.");
        System.out.println("=============");
        mostrarAlumnos();


            Alumno alumno = Consola.leerAlumnoPorDni();
            Alumno encontrado=controlador.buscar(alumno);
            controlador.borrar(encontrado);

            System.out.println("Alumno borrado correctamente" );

    }

    private void mostrarAlumnos (){
        System.out.println("==================");
        System.out.println("Listado de Alumnos");
        System.out.println("==================");

        Alumno[] coleccionAlumnos = controlador.getAlumnos();

        if(coleccionAlumnos==null){
            System.out.println("No existen alumnos en el sistema actualmente");
        }
        if (coleccionAlumnos != null) {
            for (Alumno alumno: coleccionAlumnos){
                System.out.println(alumno);
            }
        }else {
            System.out.println("No existe ningún alumno en el sistema.");
        }

    }

    private void insertarAsignatura() {
        System.out.println("======================");
        System.out.println("Listado de Asignaturas");
        System.out.println("======================");

        try {

            CicloFormativo cicloFicticio1 = Consola.getCicloFormativoPorCodigo();
            CicloFormativo[] coleccionCiclos = controlador.getCiclosFormativos();

            for (CicloFormativo cicloFormativo: coleccionCiclos) {
                  controlador.buscar(cicloFicticio1);
                if (cicloFormativo.equals(cicloFicticio1)){
                    Asignatura asignatura = Consola.leerAsigantura(cicloFormativo);
                    controlador.insertar(asignatura);
                }else {
                    System.out.println("Ciclo Formativo no exsitente.");
                }
            }

        }catch ( IllegalArgumentException| OperationNotSupportedException| NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
    private void buscarAsigantura(){
        System.out.println("==================");
        System.out.println("Buscar Asignatura.");
        System.out.println("==================");

        try {
            Asignatura asignaturaBuscada = Consola.getAsignaturaPorCodigo();
            Asignatura[] coleccionAsignaturas = controlador.getAsignaturas();

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

            if (coleccionAsignaturas.length == 0) {
                System.out.println("No hay asignaturas registradas.");
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

        Asignatura[] coleccionAsignaturas = controlador.getAsignaturas();

        if (coleccionAsignaturas == null || coleccionAsignaturas.length == 0) {
            System.out.println("No existen asignaturas en el sistema actualmente.");
        } else {
            for (Asignatura asignatura : coleccionAsignaturas) {
                System.out.println(asignatura);
            }
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
            CicloFormativo[] coleccionCiclos = controlador.getCiclosFormativos();
            for (CicloFormativo cicloFormativo: coleccionCiclos){
                controlador.buscar(cicloFormativo1);
                if (cicloFormativo.equals(cicloFormativo1)){
                    System.out.println(cicloFormativo);
                }else {
                    System.out.println("Ciclo Formativo no exsitente.");
                }
            }
            if(coleccionCiclos.length==0){
                System.out.println("Ciclo Formativo no exsitente.");
            }

        }catch (NullPointerException e){
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

        CicloFormativo[] listaCiclos = controlador.getCiclosFormativos();

        if (listaCiclos == null || listaCiclos.length == 0) {
            System.out.println("No existen ciclos formativos en el sistema actualmente.");
        } else {
            for (CicloFormativo cicloFormativo : controlador.getCiclosFormativos()) {
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



            Alumno[] coleccionAlumno = controlador.getAlumnos();

           Asignatura [] asignaturas=controlador.getAsignaturas();
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

            Asignatura[] asignaturasSeleccionadas = Consola.elegirAsignaturasMatricula(asignaturas);
           if(asignaturasSeleccionadas!=null) {
               Matricula matricula = Consola.leerMatricula(alumno, asignaturasSeleccionadas);
               controlador.insertar(matricula);
               System.out.println(matricula);
               System.out.println("Matrícula insertada correctamente.");
           }else{
               System.out.println("No se puede rellenar matricula si no hay asignaturas en el sistema.");
           }

        }catch ( IllegalArgumentException| OperationNotSupportedException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void buscarMatricula()  {
        System.out.println("=====================");
        System.out.println("Buscar Matrícula.");
        System.out.println("=====================");

        try {
            Matricula matriculaBuscada = Consola.getMatriculaPorIdentificador();
            Matricula[] coleccionMatriculas = controlador.getMatriculas();

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

            if (coleccionMatriculas.length == 0) {
                System.out.println("No hay matrículas registradas.");
            }

        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void mostrarMatriculas()  throws OperationNotSupportedException  {
        System.out.println("=====================");
        System.out.println("Listado de Matrículas");
        System.out.println("=====================");


        Matricula[] matriculasRegistradas = controlador.getMatriculas();

        if (matriculasRegistradas != null && matriculasRegistradas.length > 0) {
            System.out.println("Matrículas registradas:");

            for (Matricula matricula : matriculasRegistradas) {
                Matricula buscar = controlador.buscar(matricula);
                System.out.println(buscar);
            }
        } else {
            System.out.println("No existen matrículas registradas en el sistema actualmente.");
        }



    }
    private void mostrarMatriculasPorAlumno() throws OperationNotSupportedException  {
        System.out.println("=====================");
        System.out.println("Mostrar Matricula de alumno");
        System.out.println("=====================");


        Alumno alumno = Consola.leerAlumnoPorDni();
        Alumno alumno1=controlador.buscar(alumno);

       Matricula [] matriculas= controlador.getMatriculas(alumno1);

        System.out.println("Matrículas registradas:");
        for (Matricula matricula : matriculas) {

            System.out.println(matricula);
        }

    }


    private void mostrarMatriculasPorCicloFormativo()  throws IllegalArgumentException,OperationNotSupportedException,NullPointerException {
        System.out.println("========================================");
        System.out.println("Mostrar Matrículas por Ciclo Formativo");
        System.out.println("========================================");



            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
           CicloFormativo cicloFormativo1=controlador.buscar(cicloFormativo);
            System.out.println("Matrículas registradas para el ciclo formativo: " );
            for (Matricula matricula : controlador.getMatriculas(cicloFormativo1)) {
                System.out.println(matricula);
            }

    }
    private void mostrarMatriculasPorCursoAcademico() throws IllegalArgumentException, OperationNotSupportedException, NullPointerException{
        System.out.println("====================================");
        System.out.println("Mostrar Matrículas por Curso Académico");
        System.out.println("====================================");

        String cursoAcademico;
        cursoAcademico= Entrada.cadena();
        Matricula[] coleccion=controlador.getMatriculas(cursoAcademico);
            System.out.println();
            System.out.println("Matrículas registradas para el curso académico: " );
            for (Matricula matricula : coleccion) {
                System.out.println(matricula);
            }

    }

    /*private void anularMatricula() throws IllegalArgumentException, OperationNotSupportedException, NullPointerException{
        mostrarMatriculas();
        System.out.println("Elige la matricla que quiere anular");


            Matricula matricula = Consola.getMatriculaPorIdentificador();
            Matricula matricula1=controlador.buscar(matricula);

            LocalDate fechaAnulacion=Consola.leerFecha("Introduzca fecha anulacion:");
            matricula1.setFechaAnulacion(fechaAnulacion);

            System.out.println("Fecha de anulación insertada.");
        System.out.println(matricula1);


    }*/

    private void anularMatricula() throws IllegalArgumentException, OperationNotSupportedException, NullPointerException {
        mostrarMatriculas();
        System.out.println("Elige la matrícula que quieres anular:");

        Matricula matriculaFicticia = Consola.getMatriculaPorIdentificador();
        Matricula[] coleccionMatriculas = controlador.getMatriculas();

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
        matriculaReal.setFechaAnulacion(fechaAnulacion);

        System.out.println("Fecha de anulación insertada correctamente.");
        System.out.println(matriculaReal);


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




    public void setControlador(Controlador controlador){
        if(controlador==null ){
            throw new NullPointerException("ERROR: el controlador es nulo.");
        }
        this.controlador=controlador;
    }



    public void comenzar() {
       try {

        Opcion opcion;
        do {

            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
        System.out.println("Hasta luego!!!!");
    }catch (OperationNotSupportedException e) {
           System.out.println(e.getMessage());
       }
    }
    public void terminar(){
        System.out.println("Cerrando.");
    }


}





