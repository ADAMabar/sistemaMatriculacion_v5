package org.iesalandalus.programacion.matriculacion.dominio;

import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;

import javax.naming.OperationNotSupportedException;

public class Asignatura {
    public static final int MAX_NUM_HORAS_ANUALES = 300;
    public static final int MAX_NUM_HORAS_DESDOBLES = 6;
    private static final String   ER_CODIGO="\\d{4}";
    private String codigo;
    private String nombre;
    private Curso curso;
    private int horasDesdoble;
    private int horasAnuales;
    private EspecialidadProfesorado especialidadProfesorado;
    private CicloFormativo cicloFormativo;

    public Asignatura(String codigo, String nombre, int horasAnuales, Curso curso, int horasDesdoble, EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {
        if(codigo==null){
            throw new NullPointerException("ERROR: El código de una asignatura no puede ser nulo.");

        }else if (codigo.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");

    }
        setCodigo(codigo);
        setNombre(nombre);
        setHorasAnuales(horasAnuales);
        setCurso(curso);
        setHorasDesdoble(horasDesdoble);
        setEspecialidadProfesorado(especialidadProfesorado);
        setCicloFormativo(cicloFormativo);

    }

    public Asignatura(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No es posible copiar una asignatura nula.");
        }

     this.codigo=asignatura.codigo;
     this.nombre=asignatura.nombre;
     this.curso=asignatura.curso;
     this.horasDesdoble=asignatura.horasDesdoble;
     this.horasAnuales=asignatura.horasAnuales;
     this.especialidadProfesorado=asignatura.especialidadProfesorado;
     this.cicloFormativo=asignatura.cicloFormativo;
    }

    public CicloFormativo getCicloFormativo() {
        return cicloFormativo;
    }

    public void setCicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo de una asignatura no puede ser nulo.");
        }
        this.cicloFormativo = cicloFormativo;
    }

    public String getCodigo() {
        return codigo;
    }

    private void setCodigo(String codigo) {

            if (codigo == null) {
                throw new NullPointerException("ERROR: El código de una asignatura no puede estar vacío.");
            } else if (codigo.trim().isEmpty()) {
                throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");

            } else if (!codigo.matches(ER_CODIGO)) {
              throw new IllegalArgumentException("ERROR: El código de la asignatura no tiene un formato válido.");

            } else {
                this.codigo = codigo;
            }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null ) {
            throw new NullPointerException("ERROR: El nombre de una asignatura no puede ser nulo.");
        }else if ( nombre.trim().isEmpty()){
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede estar vacío.");
        }else{
            this.nombre = nombre;
        }

    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (curso == null) {
            throw new NullPointerException("ERROR: El curso de una asignatura no puede ser nulo.");
        }
        this.curso = curso;
    }

    public int getHorasDesdoble() {
        return horasDesdoble;
    }

    public void setHorasDesdoble(int horasDesdoble)  {
        if (horasDesdoble < 0 || horasDesdoble > MAX_NUM_HORAS_DESDOBLES) {
            throw new IllegalArgumentException("ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a 6.");
        }
        this.horasDesdoble = horasDesdoble;
    }

    public int getHorasAnuales() {
        return horasAnuales;
    }

    public void setHorasAnuales(int horasAnuales) {

        if (horasAnuales <=0 || horasAnuales > MAX_NUM_HORAS_ANUALES) {
            throw new IllegalArgumentException("ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a 300.");
        }
        this.horasAnuales = horasAnuales;
    }

    public EspecialidadProfesorado getEspecialidadProfesorado() {
        return especialidadProfesorado;
    }

    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado) {
        if (especialidadProfesorado == null) {
            throw new NullPointerException("ERROR: La especialidad del profesorado de una asignatura no puede ser nula.");
        }
        this.especialidadProfesorado = especialidadProfesorado;
    }

    // Métodos equals y hashCode

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asignatura asignatura = (Asignatura) obj;
        return codigo.equals(asignatura.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    public String imprimir() {
        return "Código asignatura="+ this.codigo +", "+"nombre asignatura="+this.nombre+", "+ "ciclo formativo=Código ciclo formativo="+cicloFormativo.getCodigo()+ ", "+ "nombre ciclo formativo="+ cicloFormativo.getGrado();


    }


    @Override
    public String toString() {
        return "Código=" + codigo + ", nombre=" + nombre +", horas anuales=" + horasAnuales +
                 ", curso=" + curso  + ", horas desdoble=" + horasDesdoble + ", ciclo formativo=Código ciclo formativo="+cicloFormativo.getCodigo()+", nombre ciclo formativo="+ cicloFormativo.getGrado()+
                ", especialidad profesorado=" + especialidadProfesorado ;
    }
}


