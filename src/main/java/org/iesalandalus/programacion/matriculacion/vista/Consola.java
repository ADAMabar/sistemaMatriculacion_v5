package org.iesalandalus.programacion.matriculacion.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;

import javax.naming.OperationNotSupportedException;


public class Consola {


    private Consola() {
    }


    public static void mostrarMenu() {
        System.out.println("Meunú de opciones, digite su opción.");


        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.toString());
        }
    }

    public Opcion elegirOpcion() {
        mostrarMenu();
        int opcion ;
        while (true) {

            opcion = Entrada.entero();
            if (opcion >= 0 || opcion > Opcion.values().length) {
                return Opcion.values()[opcion];
            } else {
                System.out.println("ERROR: No puedes elegir una opción inexistente.");
            }
        }

    }

    public Alumno leerAlumno() {
        String Nombre, dni, correo, telefono;
        LocalDate fechaNacimiento;

        System.out.println("Introduce los datos del alumno");
        System.out.println("==============================");
        do {
            System.out.print("Nombre: ");
            Nombre = Entrada.cadena();
        }while (Nombre.isBlank());
        do {
            System.out.print("DNI: ");
            dni = Entrada.cadena();
        }while (dni.isBlank());
        do {
            System.out.print("Correo: ");
            correo = Entrada.cadena();
        }while (correo.isBlank());
        do {
            System.out.print("Telefono: ");
            telefono = Entrada.cadena();
        }while (telefono.isBlank());
        System.out.println("Fecha de nacimiento: ");

        fechaNacimiento = leerFecha("Introduce una fecha válida en formato yyyy-MM-dd: ");

        String fechaNacimientoString = fechaNacimiento.toString();


        return new Alumno(Nombre, dni, correo, telefono, fechaNacimiento);


    }

    public Alumno leerAlumnoPorDni() {

        String dni;

        System.out.print("Introduce el DNI del alumno (8 dígitos seguidos de una letra): ");
        dni = Entrada.cadena();


        LocalDate fechaNacimientoFicticia = LocalDate.of(2000, 1, 1);

        return new Alumno("fictico", dni, "ficticio", "sadasd@adada.com", fechaNacimientoFicticia);
    }

    public LocalDate leerFecha(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA);
        LocalDate fecha = null;
        boolean fechaValida = false;

        while (!fechaValida) {
            System.out.println(mensaje);
            String entrada;
            entrada = Entrada.cadena();

            try {

                fecha = LocalDate.parse(entrada, formatter);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: La fecha introducida no tiene un formato válido. Usa el formato dd/MM/YYYY.");
            }
        }

        return fecha;
    }

    public Grado leerGrado() {
        int entrada;
        Grado gradoSel = null;
        System.out.println("Lista de grados disponibles.");
        System.out.println("===========================");
        System.out.println("Seleccione un grado de la lista:");
        for (Grado grado : Grado.values()) {
            System.out.println(grado.imprimir());
        }
        entrada = Entrada.entero();
        try {
            if (entrada < 0 || entrada > Grado.values().length) {
                System.out.println("ERROR: La opción escogida no es correcta.");
            } else {
                gradoSel = Grado.values()[entrada];
            }
        } catch (NumberFormatException e) {
            throw new NullPointerException("ERROR: no has introducido un numero, porfavor intentelo de nuevo.");
        }
        return gradoSel;
    }

    public CicloFormativo leerCicloformativo() {
        String familiaProfesional, nombre;
        int codigo, horas;
        Grado grado;
        try {

            System.out.println("Introduzca los datos del ciclo formativo.");
            System.out.println("========================================");

            System.out.print("Introduce el codigo: ");
            codigo = Entrada.entero();
            System.out.println("Introduce la familia profesional del Ciclo formativo (Informatica, Sistemas, Fol ): ");
            familiaProfesional = Entrada.cadena();
            System.out.print("Introduce el grado: ");
            leerGrado();
            System.out.print("Introduce el nombre del ciclo formativo: ");
            nombre = Entrada.cadena();
            System.out.print("Introduce horas del ciclo formativo: ");
            horas = Entrada.entero();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: Debes introducir bien los datos.");
        }
        grado = leerGrado();
        return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
    }

    public static void mostrarCiclosFormativos(CiclosFormativos ciclosFormativos) {
        System.out.println("Lista de ciclos formativos registrados");
        System.out.println("======================================");
        if (ciclosFormativos == null || ciclosFormativos.get().length == 0) {
            System.out.println("No hay ciclos formativos registrados en este momento.");
            return;
        }

        System.out.println("Lista de ciclos formativos registrados:");
        System.out.println("========================================");

        for (CicloFormativo ciclo : ciclosFormativos.get()) {
            System.out.println(ciclo);
        }

    }

    public CicloFormativo getCicloFormativoPorCodigo() {
        System.out.println("Introduce codigo del ciclo formatico: ");
        int codigo;
        try {
            codigo = Entrada.entero();
            CicloFormativo cicloFormativo = new CicloFormativo(codigo, "FICTICIO", Grado.GDCFGB, "FICTICO", 12);
            return cicloFormativo;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ERROR: Debes introducir numeros.");
        }

    }

    public Curso leerCurso() {
        System.out.println("Lista de cursos existentes");
        for (Curso curso : Curso.values()) {
            System.out.println(curso.imprimir());
        }
        int Numero;
        while (true) {
            System.out.print("Introduce el número correspondiente al curso: ");
            try {
                Numero = Entrada.entero(); 
                if (Numero == 1) {
                    return Curso.PRIMERO;
                } else if (Numero == 2) {
                    return Curso.SEGUNDO;
                } else {
                    System.out.println("Solo puedes introducir 1 para 'primero' o 2 para 'segundo'. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
            }
        }
    }
    public EspecialidadProfesorado leerEspecialidadProfesorado(){
        System.out.println("Lista de especialiudad profesorado");
        System.out.println("==================================");
        for (EspecialidadProfesorado profesorado: EspecialidadProfesorado.values()){
            System.out.println(profesorado);
        }

        int Numero;
        while (true){
            System.out.println("Introduce el número correspondiente al curso: ");
            try {
                Numero=Entrada.entero();

                if (Numero==1){
                    return EspecialidadProfesorado.INFORMATICA;
                }else if(Numero==2){
                    return EspecialidadProfesorado.SISTEMAS;
                }else if (Numero==3){
                    return EspecialidadProfesorado.FOL;
                }else {
                    System.out.println("Solo puedes introducir 1 para 'Informatica' , 2 para 'Sistemas' o 3 para 'Fol' . Intenta de nuevo.");
                }
            }catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
        }

    }
    public Asignatura leerAsigantura (CicloFormativo cicloFormativo){
        String codigo,nombre;
        int horasAnuales,horasDesdoble;
        Curso curso;
        EspecialidadProfesorado especialidadProfesorado;
        CicloFormativo ciclo;
        System.out.print("Introduce Los datos de la asigantuta: ");
        System.out.println("=====================================");

        do {
            System.out.print("Introduce codigo del la asigantur: ");
            codigo=Entrada.cadena();
        }while (codigo.isBlank());
        do {
            System.out.print("Introduce el nombre de la asignatura: ");
            nombre=Entrada.cadena();
        }while (nombre.isBlank());
        try {

            System.out.println("Introduce las horas anuales de la asigantura: ");
            horasAnuales = Entrada.entero();
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: Introduce bien el numero.");
        }
        System.out.println("Introduce el curso");

        curso = leerCurso();


        while (true) {
            try {
                System.out.print("Introduce las horas de desdoble de la asignatura: ");
                horasDesdoble = Entrada.entero();
                if (horasDesdoble < 0) {
                    throw new IllegalArgumentException("ERROR: Las horas de desdoble no pueden ser negativas.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }


        especialidadProfesorado = leerEspecialidadProfesorado();

        cicloFormativo=leerCicloformativo();

        return new Asignatura(codigo,nombre,horasAnuales,curso,horasDesdoble,especialidadProfesorado,cicloFormativo);
    }
    public Asignatura getAsignaturaPorCodigo(){
        CicloFormativo cicloFormativo =null;
        String codigo;
        try {
            do {
                System.out.println("Introduce el codigo de la asigantura: ");
                codigo = Entrada.cadena();
            } while (codigo.isBlank());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Porfavor introduzca bien el codigo");
        }
        return new Asignatura(codigo,"ficticio",111,Curso.PRIMERO,2,EspecialidadProfesorado.INFORMATICA,cicloFormativo);
    }

    private static void mostrarAsignaturas(Asignaturas asignaturas){
        System.out.println("Lista de Asiganturas");
        System.out.println("====================");
        if(asignaturas==null||asignaturas.get().length==0){
            System.out.println("No hay asiganturas registradas en este momento.");
            return;
        }
        System.out.println("Lista de asiganturas.");
        System.out.println("====================");
        for (Asignatura asignatura: asignaturas.get()){
            System.out.println(asignatura);
        }

    }
    private boolean asignaturaYaMatriculada(Asignaturas[] asignaturasMatricula, Asignatura asignatura){

        for (Asignaturas asignatura1:asignaturasMatricula) {
            if (asignatura.equals(asignatura1)) {
                return true;
            }
        }
        return false;


    }
    public Matricula leerMatricula(Alumno alumno, Asignaturas asignaturas)throws OperationNotSupportedException {
        int idMatricula;
        String cursoAdemico;
        LocalDate fecha;
        CicloFormativo cicloFormativo1;
        System.out.println("Introduce los datos de la asignatura.");
        System.out.println("====================================");

        try {
            System.out.print("Introduce el ID de la matricula: ");
            idMatricula=Entrada.entero();
        }catch (IllegalArgumentException e){
           throw new IllegalArgumentException("Has introducido mal el ID, intentelo de nuevo.");
        }

        do {
            System.out.print("Introduce el curso academico en este formato 'AA-AA");
            cursoAdemico=Entrada.cadena();
        }while (cursoAdemico.isBlank());

        System.out.println("Selecciona algún grado.");
        leerGrado();

        fecha=leerFecha("Intrduce la fecha de matricualción.");


        return new Matricula(idMatricula,cursoAdemico,fecha,alumno,asignaturas.get());
    }
    public Matricula getMatriculaPorIdentificador() throws OperationNotSupportedException{
        int id;
        LocalDate fechaFicticia;

        Asignaturas asignaturas1 = new Asignaturas(3);
        System.out.println("Introduce el identificador numerico de la matricula: ");
        try {
            id=Entrada.entero();
        } catch (IllegalArgumentException e){
         throw new IllegalArgumentException("ERROR: porfavor vuele a introducir bien el id numerico ");
        }
        Asignatura[] asignaturas = asignaturas1.get();
        fechaFicticia= LocalDate.of(2000,1,1);
        Alumno alumno=new Alumno("fictico","12345678Z", "ficticio", "sadasd@adada.com",  fechaFicticia);
        return new Matricula(id,"fictcio",LocalDate.of(2000,1,1),alumno,asignaturas);

    }



}






