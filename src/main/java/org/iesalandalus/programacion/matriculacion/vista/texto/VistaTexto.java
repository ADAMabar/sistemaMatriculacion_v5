package org.iesalandalus.programacion.matriculacion.vista.texto;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class VistaTexto extends Vista {

    public VistaTexto() {
        Opcion.setVista(this);
    }

    public void comenzar() throws OperationNotSupportedException {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        getControlador().terminar();
    }


    public void insertarAlumno() {
        try {
            Alumno alumno = Consola.leerAlumno();
            getControlador().insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar un Alumno nulo.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }


    public void buscarAlumno() {
        try {

            Alumno alumnoBuscado = getControlador().buscar(Consola.leerAlumnoPorDni());
            if (alumnoBuscado != null) {
                System.out.printf("Los datos del alumno solicitado son: %s", alumnoBuscado);
            } else {
                System.out.println("No existe ningun alumno con tales datos.");
            }
        } catch (NullPointerException| IllegalArgumentException e) {
            System.out.println("ERROR: 3.No se puede buscar un Alumno nulo.");
        }
    }

    public void borrarAlumno() {
        try {

            getControlador().borrar(Consola.leerAlumnoPorDni());
            System.out.println("Alumno borrado correctamente.");
        } catch (NullPointerException | OperationNotSupportedException e) {
            System.out.println("ERROR: No se puede borrar un Alumno nulo.");
        }

    }

    public void mostrarAlumnos() {
        List<Alumno> arrayAlumnos = getControlador().getAlumnos();
        if (arrayAlumnos.size() == 0) {
            System.out.println("No existen alumnos.");
        } else {
            arrayAlumnos.sort(Comparator.comparing(Alumno::getNombre));

            for (Alumno a : arrayAlumnos) {
                System.out.println(a);
            }
        }
    }

    public void insertarAsignatura() {

        try {
            mostrarCicloFormativos();
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo ciclo = getControlador().buscar(cicloFormativo);
            if (ciclo == null) {
                System.out.println("No existe el ciclo formativo indicado.");
                return;
            }
            Asignatura asignatura = Consola.leerAsigantura(ciclo);
            getControlador().insertar(asignatura);
            System.out.println("Asignatura insertada correctamente.");
        } catch (NullPointerException | OperationNotSupportedException e) {
            System.out.println("ERROR: No se puede insertar una Asignatura nula.");
        }
    }


    public void buscarAsignatura() {
        try {

            Asignatura asignaturaBuscar = getControlador().buscar(Consola.getAsignaturaPorCodigo());
            Asignatura encontrada = getControlador().buscar(asignaturaBuscar);
            if (encontrada != null) {
                System.out.printf("Los datos de la asignatura solicitada son: %s", asignaturaBuscar);
            } else {
                System.out.println("No existe ninguna asignatura con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar una asignatura nula.");
        }
    }


    public void borrarAsignatura() {
        try {
            Asignatura asignaturaBorrar = Consola.getAsignaturaPorCodigo();

            getControlador().borrar(asignaturaBorrar);
            System.out.println("Asignatura borrada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede borrar una asignatura nula.");
        } catch (IllegalArgumentException |OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }


    public void mostrarAsignaturas() {

        List<Asignatura> arrayAsignatura = getControlador().getAsignaturas();
        if (arrayAsignatura.size() == 0) {
            System.out.println("No existen asignaturas.");
        } else {
            arrayAsignatura.sort(Comparator.comparing(Asignatura::getNombre));
            for (Asignatura asignatura : arrayAsignatura) {
                System.out.println(asignatura);
            }
        }


    }

    public void insertarCicloFormativo() {
        try {
            CicloFormativo ciclosFormativo = Consola.leerCicloformativo();
            getControlador().insertar(ciclosFormativo);
            //ciclosFormativos.insertar(ciclosFormativo);
            System.out.println("Ciclo formativo insertada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar Ciclo Formativo nulo.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //buscar CicloFormativo
    public void buscarCicloFormativo() {
        try {

            CicloFormativo cicloFormativoBuscar = getControlador().buscar(Consola.getCicloFormativoPorCodigo());
            CicloFormativo encontrada = getControlador().buscar(cicloFormativoBuscar);
            if (encontrada != null) {
                System.out.printf("Los datos del ciclo formativo solicitado son: %s", cicloFormativoBuscar);
            } else {
                System.out.println("No existe ningun ciclo formativo con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar un ciclo formativo nulo.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void borrarCicloFormativo() {
        try {
            CicloFormativo cicloFormativoBorrar = Consola.getCicloFormativoPorCodigo();
            getControlador().borrar(cicloFormativoBorrar);
            System.out.println("Ciclo formativo borrada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede borrar un ciclo formativo nulo.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarCicloFormativos() {

        List<CicloFormativo> arrayCicloFormativo = getControlador().getCiclosFormativos();
        if (arrayCicloFormativo.size() == 0) {
            System.out.println("No existen ciclos formativos.");
        } else {
            arrayCicloFormativo.sort(Comparator.comparing(CicloFormativo::getNombre));
            for (CicloFormativo cicloFormativo : arrayCicloFormativo) {
                System.out.println(cicloFormativo);
            }
        }
    }

    public void insertarMatricula() {
        try {
            System.out.println("Datos del alumno:");
            Alumno alumno = Consola.leerAlumnoPorDni();
            Alumno a = getControlador().buscar(alumno);
            if (a == null) {
                System.out.println("No existe el alumno indicado.");
                return;
            }
            System.out.println("Asignaturas de la matricula:");
            ArrayList<Asignatura> matriculaAsignaturas = Consola.elegirAsignaturasMatricula(getControlador().getAsignaturas());
            System.out.println("Datos de la matricula:");
            Matricula matricula = Consola.leerMatricula(a, matriculaAsignaturas);
            getControlador().insertar(matricula);
            System.out.println("Matricula insertada correctamente.");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede insertar una Matricula nula.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //buscar Matricula
    public void buscarMatricula() {
        try {

            Matricula matriculaBuscar = getControlador().buscar(Consola.getMatriculaPorIdentificador());
            Matricula encontrada = getControlador().buscar(matriculaBuscar);
            if (encontrada != null) {
                System.out.printf("Los datos de la matricula solicitada son: %s", matriculaBuscar);
            } else {
                System.out.println("No existe ninguna matricula con tales datos.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede buscar una Matricula nula.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    //Anular Matricula
    public void anularMatricula() {
        try {
            Alumno alumno = Consola.leerAlumnoPorDni();
            //Matricula matriculaAnular = matriculas.buscar(Consola.getMatriculaPorIdentificador());
            Matricula matriculaAnular = getControlador().buscar(Consola.getMatriculaPorIdentificador());
            if (matriculaAnular != null && matriculaAnular.getAlumno().equals(alumno)) {
                //controlador.borrar(matriculaAnular);
                System.out.println("indique la fecha de anulación:");
                String fechaAnulacion = (Entrada.cadena());
                LocalDate fechaAnular;
                fechaAnular = LocalDate.parse(fechaAnulacion, DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA));
                matriculaAnular.setFechaAnulacion(fechaAnular);
                getControlador().borrar(matriculaAnular);
                System.out.println("Matricula anulada correctamente.");
            } else {
                System.out.println("No se ha encontrado la matricula o no corresponde al alumno indicado.");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: No se puede anular una matricula nula.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarMatriculas() {
        try {

            List<Matricula> arrayMatriculas = getControlador().getMatriculas();
            if (arrayMatriculas.size() == 0) {
                System.out.println("No existen Matriculas.");
            }else {
                arrayMatriculas.sort(Comparator.comparing(Matricula::getFechaMatriculacion).reversed().thenComparing(matricula -> matricula.getAlumno().getNombre()));
            }
            for (Matricula m : arrayMatriculas) {
                System.out.println(m);
            }

        } catch (OperationNotSupportedException e) {
            System.out.println("ERROR: No se pudo mostrar matriculas.");
        }
    }

    public void mostrarMatriculasPorAlumno() {
        try {

            Alumno alumno = Consola.leerAlumnoPorDni();
            List<Matricula> arrayMatricula = getControlador().getMatriculas(alumno);
            if (arrayMatricula.size() == 0) {
                System.out.println("No existen matriculas para el alumno indicado.");
            } else {
                arrayMatricula.sort(
                        Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                                .thenComparing(m -> m.getAlumno().getNombre())
                );
                for (Matricula matricula : arrayMatricula) {
                    System.out.println(matricula);
                }
            }

        } catch (OperationNotSupportedException e) {
            System.out.println("ERROR: No se pueden mostrar matriculas por alumno");
        } catch (NullPointerException e) {
            System.out.println("ERROR: No existe ningun alumno con tales datos.");
        }
    }

    public void mostrarMatriculasPorCicloFormativo() {

        try {
            System.out.println("Indique uno de los siguientes ciclos formativos:");
            mostrarCicloFormativos();
            CicloFormativo cicloFormativo = Consola.getCicloFormativoPorCodigo();
            cicloFormativo = getControlador().buscar(cicloFormativo);
            if (cicloFormativo == null) {
                System.out.println("No existe ningun ciclo formativo con tales datos.");
            }
            List<Matricula> matriculaCiclo;
            matriculaCiclo = getControlador().getMatriculas(cicloFormativo);
            if (matriculaCiclo.size() == 0) {
                System.out.println("No existen matriculas para el ciclo formativo indicado.");
            }

            System.out.println("Matrículas del ciclo formativo " + cicloFormativo.getCodigo() + ":");
            matriculaCiclo.sort(
                    Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                            .thenComparing(m -> m.getAlumno().getNombre())
            );
            for (Matricula matricula : matriculaCiclo) {
                System.out.println(matricula);
            }
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("ERROR: No existe ningun ciclo como el indicado.");
        }


    }

    public void mostrarMatriculasPorCursoAcademico() {
        try {
            System.out.println("indique el curso academico:");
            System.out.println("El formato del curso es YY-YY");
            String cursoAcademico = Entrada.cadena();
           List<Matricula> arrayMatriculas = getControlador().getMatriculas(cursoAcademico);

            if (arrayMatriculas.size() == 0) {
                System.out.println("No existen matrículas para el curso académico indicado.");
                return;
            }
            System.out.println("Matrículas del curso académico " + cursoAcademico + ":");
            arrayMatriculas.sort(
                    Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                            .thenComparing(m -> m.getAlumno().getNombre())
            );
            for (Matricula matricula : arrayMatriculas) {
                System.out.println(matricula);
            }

        } catch (OperationNotSupportedException e) {
            System.out.println("ERROR: No se pudo mostrar matriculas por curso academico.");
        }

    }
}