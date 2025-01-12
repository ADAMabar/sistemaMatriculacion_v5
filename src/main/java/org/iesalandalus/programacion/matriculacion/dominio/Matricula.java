package org.iesalandalus.programacion.matriculacion.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Objects;



public class Matricula {

    public static final int MAXIMO_MESES_ANTERIOR_ANULACION = 6;
    public static final int MAXIMO_DIAS_ANTERIOR_MATRICULA = 15;
    public static final int MAXIMO_NUMERO_HORAS_MATRICULA = 300;
    public static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA = 6;
    public static final String ER_CURSO_ACADEMICO = "\\d{2}-\\d{2}"; // Ejemplo: "2023-2024"
    public static final String FORMATO_FECHA = "dd/MM/yyyy";

    private int idMatricula;
    private String cursoAcademico;
    private LocalDate fechaMatriculacion;
    private LocalDate fechaAnulacion;
    private Alumno alumno;
    private Asignatura[] coleccionAsignaturas;

    public Matricula(int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno, Asignatura[] coleccionAsignaturas) throws OperationNotSupportedException{
        setIdMatricula(idMatricula);
        setCursoAcademico(cursoAcademico);
        setFechaMatriculacion(fechaMatriculacion);
        setAlumno(alumno);
        setColeccionAsignaturas(coleccionAsignaturas);




    }

    public Matricula(Matricula matricula)  {
        if (matricula == null){
            throw new NullPointerException("ERROR: No es posible copiar una matrícula nula.");
        }

        this.idMatricula = matricula.idMatricula;
        this.cursoAcademico = matricula.cursoAcademico;
        this.fechaMatriculacion = matricula.fechaMatriculacion;
        this.fechaAnulacion = matricula.fechaAnulacion;
        this.alumno = matricula.alumno;
        this.coleccionAsignaturas = matricula.coleccionAsignaturas.clone(); // Clon profundo
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) throws OperationNotSupportedException{
        if (idMatricula <= 0) {
            throw new OperationNotSupportedException("El ID de matrícula debe ser mayor que cero.");
        }
        this.idMatricula = idMatricula;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) throws IllegalArgumentException {
        if (cursoAcademico.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
        } else if ( !cursoAcademico.matches(ER_CURSO_ACADEMICO)) {
            throw new IllegalArgumentException("ERROR: El formato del curso académico no es correcto.");
        }
        this.cursoAcademico = cursoAcademico;
    }

    public LocalDate getFechaMatriculacion() {
        return fechaMatriculacion;
    }

    public void setFechaMatriculacion(LocalDate fechaMatriculacion) throws OperationNotSupportedException{
        if (fechaMatriculacion == null) {
            throw new NullPointerException("ERROR: La fecha de matriculación de una matrícula no puede ser nula.");
        } else if (fechaMatriculacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException ("ERROR: La fecha de matriculación no puede ser posterior a la fecha actual.");
        } else if (fechaMatriculacion.isBefore(LocalDate.now().minusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA))) {
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser anterior a " + MAXIMO_DIAS_ANTERIOR_MATRICULA + " días.");
        }

        this.fechaMatriculacion = fechaMatriculacion;

    }

    public LocalDate getFechaAnulacion() {
        return fechaAnulacion;
    }
    public void setFechaAnulacion(LocalDate fechaAnulacion) throws IllegalArgumentException {

        if (fechaAnulacion == null) {
            throw new NullPointerException("ERROR: La fecha de anulación no puede ser nula.");
        }

        if (fechaMatriculacion == null) {
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no está establecida.");
        }

        if (fechaAnulacion.equals(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
        }

        if (fechaAnulacion.isBefore(fechaMatriculacion)) {
            throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern(FORMATO_FECHA);
        try {
            this.fechaAnulacion = LocalDate.parse(fechaAnulacion.format(formato), formato);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("ERROR: La fecha no tiene el formato correcto. Debe ser " + FORMATO_FECHA);
        }
    }


    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) throws NullPointerException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }
        this.alumno = alumno;
    }

    public Asignatura[] getColeccionAsignaturas() {
        return coleccionAsignaturas;
    }

    public void setColeccionAsignaturas(Asignatura[] coleccionAsignaturas) {
        if (coleccionAsignaturas == null ) {
            throw new NullPointerException("ERROR: La lista de asignaturas de una matrícula no puede ser nula.");
        }
        this.coleccionAsignaturas = new Asignatura[0];

    }


    public boolean superaMaximoNumeroHorasMatricula(Asignatura[] asignaturasMatricula) throws OperationNotSupportedException {
        if (fechaAnulacion != null) {
            throw new OperationNotSupportedException("La operación no está soportada cuando la matrícula ha sido anulada.");
        }

        int totalHoras = 0;
        for (Asignatura asignatura : asignaturasMatricula) {
            totalHoras += asignatura.getHorasAnuales();
            if (totalHoras > MAXIMO_NUMERO_HORAS_MATRICULA) {
                return true;
            }
        }

        return false;
    }


    private String asignaturasMatricula() {

        if (fechaAnulacion != null) {
            throw new NullPointerException("No se pueden listar las asignaturas de una matrícula anulada.");
        }

        StringBuilder sb = new StringBuilder("Asignaturas Matriculadas:\n");
        for (Asignatura asignatura : coleccionAsignaturas) {
            sb.append(asignatura.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return idMatricula == matricula.idMatricula &&
                Objects.equals(cursoAcademico, matricula.cursoAcademico) &&
                Objects.equals(fechaMatriculacion, matricula.fechaMatriculacion) &&
                Objects.equals(alumno, matricula.alumno) &&
                Arrays.equals(coleccionAsignaturas, matricula.coleccionAsignaturas);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idMatricula, cursoAcademico, fechaMatriculacion, alumno);
        result = 31 * result + Arrays.hashCode(coleccionAsignaturas);
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder resultado= new StringBuilder();

        for(Asignatura asignatura : coleccionAsignaturas)
        {
            if (asignatura!=null)
                resultado.append(asignatura.imprimir());
        }

        if(fechaAnulacion==null) {
            return "idMatricula=" + idMatricula +
                    ", curso académico=" + cursoAcademico +
                    ", fecha matriculación=" + fechaMatriculacion.format(formato) +
                    ", alumno=Nombre: " + alumno.getNombre() + "\n" + "DNI: " + alumno.getDni() + "\n" + "Correo: " + alumno.getCorreo() + "\n" + "Teléfono: " + alumno.getTelefono() + "\n" + "Fecha de nacimiento: " + alumno.getFechaNacimiento().format(formato) + ", Asignaturas=" + "{" + resultado + " }"
                    ;
        }else {
            return  "idMatricula=" + idMatricula +
                    ", curso académico=" + cursoAcademico +
                    ", fecha matriculación=" + fechaMatriculacion.format(formato) + ", fecha anulación= " +fechaAnulacion.format(formato) +
                    ", alumno=Nombre: " + alumno.getNombre() + "\n" + "DNI: " + alumno.getDni() + "\n" + "Correo: " + alumno.getCorreo() + "\n" + "Teléfono: " + alumno.getTelefono() + "\n" + "Fecha de nacimiento: " + alumno.getFechaNacimiento().format(formato) + ", Asignaturas=" + "{" + resultado+ " }"
                    ;
        }
    }

    public String imprimir() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "idMatricula=" + idMatricula +
                ", curso académico=" + cursoAcademico +
                ", fecha matriculación=" + fechaMatriculacion.format(formato) +
                ", alumno="+"{Nombre: " +alumno.getNombre() + "\n"+"DNI: "+ alumno.getDni()+ "\n"+"Correo: "+ alumno.getCorreo()+ "\n"+"Teléfono: "+alumno.getTelefono()+ "\n"+"Fecha de nacimiento: "+ alumno.getFechaNacimiento().format(formato)+"}";
    }

}




