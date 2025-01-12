package org.iesalandalus.programacion.matriculacion.dominio;

public class CicloFormativo {
    public static int MAXIMO_NUMERO_HORAS=2000;
    private int codigo;
    private String familiaProfesional;
    private Grado grado;
    private String nombre;
    private int horas;



    public CicloFormativo(int codigo, String familiaProfesional, Grado grado, String nombre, int horas) {
        setCodigo(codigo);
        setFamiliaProfesional(familiaProfesional);
        setGrado(grado);
        setNombre(nombre);
        setHoras(horas);
    }

    public CicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo==null){
            throw new NullPointerException("ERROR: No es posible copiar un ciclo formativo nulo.");
        }
        this.codigo = cicloFormativo.codigo;
        this.familiaProfesional = cicloFormativo.familiaProfesional;
        this.grado = cicloFormativo.grado;
        this.nombre = cicloFormativo.nombre;
        this.horas = cicloFormativo.horas;
    }



    public int getCodigo() {
        return codigo;
    }

    private void setCodigo(int codigo) {

        String codigoStr = String.valueOf(codigo);
        if (!codigoStr.matches("^\\d{4}$")) {
            throw new IllegalArgumentException("El código debe ser un número de 4 dígitos.");
        } else {

            this.codigo = codigo;
        }

    }


    public String getFamiliaProfesional() {
        return familiaProfesional;
    }

    public void setFamiliaProfesional(String familiaProfesional) {
        if (familiaProfesional == null) {
            throw new NullPointerException("ERROR: La familia profesional de un ciclo formativo no puede ser nula.");
        }else if(familiaProfesional.trim().isEmpty()){
            throw new IllegalArgumentException("ERROR: La familia profesional no puede estar vacía.");
        }
        this.familiaProfesional = familiaProfesional;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        if (grado == null) {
            throw new NullPointerException("ERROR: El grado de un ciclo formativo no puede ser nulo.");
        }
        this.grado = grado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null ) {
            throw new NullPointerException("ERROR: El nombre de un ciclo formativo no puede ser nulo.");
        }else if(nombre.trim().isEmpty()){
            throw new IllegalArgumentException("ERROR: El nombre de un ciclo formativo no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        if (horas <= 0 || horas > MAXIMO_NUMERO_HORAS) {
            throw new IllegalArgumentException("ERROR: El número de horas de un ciclo formativo no puede ser menor o igual a 0 ni mayor a 2000.");
        }
        this.horas = horas;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CicloFormativo that = (CicloFormativo) obj;
        return this.codigo == that.codigo;
    }


    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }


    public String imprimir() {
        return "Código ciclo formativo="+getCodigo()+", nombre ciclo formativo="+grado.toString();
    }


    @Override
    public String toString() {
        return "Código ciclo formativo="+getCodigo()+", familia profesional="+getFamiliaProfesional()+", grado="+getGrado()+", nombre ciclo formativo="+getNombre()+", horas="+ getHoras();
    }
}


