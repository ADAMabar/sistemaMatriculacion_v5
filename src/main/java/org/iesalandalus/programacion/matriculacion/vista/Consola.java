package org.iesalandalus.programacion.matriculacion.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.utilidades.Entrada;

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

    public static Opcion elegirOpcion() {
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

    public static Alumno leerAlumno() {
        String Nombre, dni, correo, telefono;
        LocalDate fechaNacimiento;
        System.out.println("===============================");
        System.out.println("Introduce los datos del alumno");
        System.out.println("==============================");


        System.out.print("Nombre: ");
        Nombre = Entrada.cadena();


        System.out.print("DNI: ");
        dni = Entrada.cadena();


        System.out.print("Correo: ");
        correo = Entrada.cadena();


        System.out.print("Telefono: ");
        telefono = Entrada.cadena();

        System.out.println("Fecha de nacimiento: ");

        fechaNacimiento = leerFecha("Introduce una fecha válida en formato dd/MM/YYYY : ");

        String fechaNacimientoString = fechaNacimiento.toString();




        return new Alumno(Nombre, dni, correo, telefono, fechaNacimiento);

    }

    public static Alumno leerAlumnoPorDni() {

        String dni;

        System.out.print("Introduce el DNI del alumno (8 dígitos seguidos de una letra): ");
        dni = Entrada.cadena();


        LocalDate fechaNacimientoFicticia = LocalDate.of(2000, 1, 1);

        return new Alumno("fictico", dni, "juan.perez@email.com", "123456789", fechaNacimientoFicticia);
    }

    public static LocalDate leerFecha(String mensaje) {
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

    public static Grado leerGrado() {
        int entrada;
        Grado gradoSel = null;
        System.out.println("===========================");
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

    public static CicloFormativo leerCicloformativo() {
        String familiaProfesional, nombre;
        int codigo, horas;
        Grado grado;
        try {
            System.out.println("========================================");
            System.out.println("Introduzca los datos del ciclo formativo.");
            System.out.println("========================================");

            System.out.print("Introduce el codigo: ");
            codigo = Entrada.entero();
            System.out.println("Introduce la familia profesional del Ciclo formativo: ");
            familiaProfesional = Entrada.cadena();

            System.out.print("Introduce el nombre del ciclo formativo: ");
            nombre = Entrada.cadena();
            System.out.print("Introduce horas del ciclo formativo: ");
            horas = Entrada.entero();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: Debes introducir bien los datos.");
        }
        System.out.print("Introduce el grado: ");
        grado=leerGrado();

        return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
    }

    public static void mostrarCiclosFormativos(CiclosFormativos ciclosFormativos) {
        System.out.println("======================================");
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

    public static CicloFormativo getCicloFormativoPorCodigo() {
        System.out.println("Introduce codigo del ciclo formatico: ");
        int codigo;
        try {
            codigo = Entrada.entero();
            return new CicloFormativo(codigo, "FICTICIO", Grado.GDCFGB, "FICTICO", 12);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ERROR: Debes introducir numeros.");
        }

    }

    public static Curso leerCurso() {
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
    public static EspecialidadProfesorado leerEspecialidadProfesorado(){
        System.out.println("==================================");
        System.out.println("Lista de especialiudad profesorado");
        System.out.println("==================================");
        for (EspecialidadProfesorado profesorado: EspecialidadProfesorado.values()){
            System.out.println(profesorado.imprimir());
        }

        int Numero;
        while (true){
            System.out.print("Introduce el número correspondiente al curso: ");
            try {
                Numero=Entrada.entero();

                if (Numero==1){
                    return EspecialidadProfesorado.INFORMATICA;
                }else if(Numero==2){
                    return EspecialidadProfesorado.SISTEMAS;
                }else if (Numero==3){
                    return EspecialidadProfesorado.FOL;
                }else {
                    System.out.print("Solo puedes introducir 1 para 'Informatica' , 2 para 'Sistemas' o 3 para 'Fol' . Intenta de nuevo.");
                }
            }catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
        }

    }
    public static Asignatura leerAsigantura (CiclosFormativos ciclosFormativos){
        String codigo,nombre;
        int horasAnuales,horasDesdoble;
        Curso curso;
        EspecialidadProfesorado especialidadProfesorado;
        CicloFormativo ciclo;
        System.out.println("=====================================");
        System.out.println("Introduce Los datos de la asigantuta.");
        System.out.println("=====================================");


        System.out.print("Introduce codigo del la asigantura (4 digitos): ");
        codigo=Entrada.cadena();

        System.out.print("Introduce el nombre de la asignatura: ");
        nombre=Entrada.cadena();



        System.out.print("Introduce las horas anuales de la asigantura (300 horas maximas): ");
        horasAnuales = Entrada.entero();

        System.out.println("Ahora introduce el curso al que te quieres matricular.");

        curso = leerCurso();

        System.out.println("Horas desdoble.");

        while (true) {
            try {
                System.out.print("Introduce las horas de desdoble de la asignatura (Entre 1 y 3): ");
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

        ciclo=getCicloFormativoPorCodigo();
        ciclo=ciclosFormativos.buscar(ciclo);



        return new Asignatura(codigo,nombre,horasAnuales,curso,horasDesdoble,especialidadProfesorado,ciclo);
    }
    public static Asignatura getAsignaturaPorCodigo(){
        CicloFormativo cicloFormativo =new CicloFormativo(4321,"Informatica",Grado.GDCFGB,"adads",1200);
        String codigo;
        try {
            System.out.println("Introduce el codigo de la asigantura: ");
            codigo = Entrada.cadena();
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
    private static boolean asignaturaYaMatriculada(Asignatura[] asignaturasMatriculadas, Asignatura asignatura) {
        if (asignaturasMatriculadas == null || asignatura == null) {
            return false;
        }

        for (Asignatura asignatura1 : asignaturasMatriculadas) {
            if (asignatura.equals(asignatura1)) {
                return true;
            }
        }
        return false;
    }

    public static Matricula leerMatricula(Alumnos alumnos, Asignaturas asignaturas)throws OperationNotSupportedException {
        int idMatricula;
        String cursoAdemico;
        LocalDate fecha;
        Alumno alumno;
        int numAsignaturas =0;
        int contador;

        System.out.println("Introduce los datos de la matricula.");
        System.out.println("====================================");

        try {
            System.out.print("Introduce el ID de la matricula: ");
            idMatricula=Entrada.entero();
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Has introducido mal el ID, intentelo de nuevo.");
        }

        do {
            System.out.print("Introduce el curso academico en este formato 'AA-AA': ");
            cursoAdemico=Entrada.cadena();
        }while (cursoAdemico.isBlank());

        System.out.println("Selecciona algún grado.");
        leerGrado();

        fecha=leerFecha("Intrduce la fecha de matricualción.");

        alumno=leerAlumnoPorDni();
        alumno=alumnos.buscar(alumno);

        Asignatura [] coleccionAsignaturas;
        System.out.print("introduce el numero de asigaturas en las que te quieres matricular: ");
        contador=Entrada.entero();
        numAsignaturas=contador+numAsignaturas;

        coleccionAsignaturas=new Asignatura[numAsignaturas];
        Asignatura asignatura;
        for (int i = 0; i < numAsignaturas; i++) {
            System.out.println("Introduce la asignatura " + (i + 1) + " por favor: ");


            asignatura= getAsignaturaPorCodigo();

            asignatura = asignaturas.buscar(asignatura);


            if (!asignaturaYaMatriculada(coleccionAsignaturas, asignatura)) {

                coleccionAsignaturas[i] = asignatura;
                System.out.println("Asignatura insertada.");
            }else {
                System.out.println("Esta asignatura no se insertó, ya está matriculada.");
            }

        }


        return new Matricula(idMatricula, cursoAdemico, fecha, alumno, coleccionAsignaturas);

    }




    public static Matricula getMatriculaPorIdentificador() throws OperationNotSupportedException {
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
        Alumno alumno=new Alumno("fictico","12345678Z", "ficticio@GMAIL.COM", "123456789",  fechaFicticia);
        return new Matricula(id,"24-25",LocalDate.now(),alumno,asignaturas);

    }
}