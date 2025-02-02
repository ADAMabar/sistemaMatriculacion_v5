package org.iesalandalus.programacion.matriculacion.dominio;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Alumno {
    private static String ER_TELEFONO;
    private static String ER_CORREO;
    private static String ER_DNI;
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static String ER_NIA;
    private static final int MIN_EDAD_ALUMNADO= 16;

    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    private String nia;


    private static String formateaNombre(String nombre) {

        nombre = nombre.trim();
        String [] palabras = nombre.split("\\s+");
        StringBuilder nombreFormateado = new StringBuilder();

        for (String palabra: palabras ){
            String palabraFormateada = palabra.substring(0,1).toUpperCase()+palabra.substring(1).toLowerCase();
            nombreFormateado.append(palabraFormateada).append(" ");

        }
        // Con el ".toString() retorna el strimgBuilder a una cadena normal
        return nombreFormateado.toString().trim();
    }

    private boolean comprobarLetraDni(String dni) {

        String formatoDni="^([0-9]{8})([A-Z])";
        if(!dni.matches(formatoDni)){
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }
        Pattern pattern=Pattern.compile(formatoDni);
        Matcher matcher=pattern.matcher(dni);


        if (matcher.matches()){
            String nuemeroDni=matcher.group(1);
            char letraDni=matcher.group(2).charAt(0);
            int numero= Integer.parseInt(nuemeroDni);

            int resto=numero%23;


            char letraValida;
            switch (resto) {
                case 0:letraValida = 'T';
                    break;
                case 1:letraValida = 'R';
                    break;
                case 2:letraValida = 'W';
                    break;
                case 3:letraValida = 'A';
                    break;
                case 4:letraValida = 'G';
                    break;
                case 5:letraValida = 'M';
                    break;
                case 6:letraValida = 'Y';
                    break;
                case 7:letraValida= 'F';
                    break;
                case 8:letraValida= 'P';
                    break;
                case 9:letraValida= 'D';
                    break;
                case 10:letraValida='X';
                    break;
                case 11:letraValida= 'B';
                    break;
                case 12:letraValida = 'N';
                    break;
                case 13:letraValida= 'J';
                    break;
                case 14:letraValida= 'Z';
                    break;
                case 15:letraValida = 'S';
                    break;
                case 16:letraValida = 'Q';
                    break;
                case 17:letraValida = 'V';
                    break;
                case 18:letraValida = 'H';
                    break;
                case 19:letraValida = 'L';
                    break;
                case 20:letraValida = 'C';
                    break;
                case 21:letraValida= 'K';
                    break;
                case 22:letraValida = 'E';
                    break;
                default:
                    return false;
            }

            // Comprueba la letra que recibe con la valida
            return letraDni == letraValida ;
        }

        return false;

    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
        } else if (nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío.");
        } else {
            this.nombre = formateaNombre(nombre);
        }
    }


    public String getNia() {
        String parteNombre = nombre.substring(0, 4).toLowerCase();

        String parteDni = dni.substring(5,8);

        this.nia = parteNombre + parteDni;

        return nia;
    }

    private void setNia(String nia) {
        this.nia = nia;
    }

    public void setNia() {
        if(nia==null){
            throw new NullPointerException("ERROR: El nia no puede ser nulo.");
        }

    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if (dni==null){
            throw new NullPointerException("ERROR: El dni de un alumno no puede ser nulo.");
        }

        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("ERROR: La letra del dni del alumno no es correcta.");
        } else {
            this.dni=dni;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if(telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un alumno no puede ser nulo.");
        }
        String formatoTelefono= "\\d{9}";
        if (!telefono.matches(formatoTelefono)){
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");

        }else{
            this.telefono=telefono;
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo)  {
        if (correo == null) {
            throw new NullPointerException("ERROR: El correo de un alumno no puede ser nulo.");
        }
        String formatoCorreoValido= "\\w+[\\.\\w]*@\\w+[\\.\\w]*\\.\\w{2,5}\\b\\s?";

        if (!correo.matches(formatoCorreoValido)){
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        }else{
            this.correo=correo;
        }
    }


    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new NullPointerException("ERROR: La fecha de nacimiento de un alumno no puede ser nula.");
        }

        // Formato esperado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate hoy = LocalDate.now();
        int edad = hoy.getYear() - fechaNacimiento.getYear();

        // Ajustar si aún no ha cumplido años este año
        if (hoy.isBefore(fechaNacimiento.plusYears(edad))) {
            edad--;
        }

        if (edad < MIN_EDAD_ALUMNADO) {
            throw new IllegalArgumentException("ERROR: La edad del alumno debe ser mayor o igual a " + MIN_EDAD_ALUMNADO + " años.");
        }

        try {
            // Validar el formato y convertir a String
            String fechaNacimientoStr = fechaNacimiento.format(formato);

            // Analizar nuevamente para verificar el formato
            this.fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formato);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("ERROR: La fecha no tiene el formato correcto. Debe ser 'dd/MM/yyyy'.");
        }
    }



    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    private String getIniciales(String nombre){
        String [] palabras= nombre.split("\\s+");
        StringBuilder iniciales=new StringBuilder();

        for (String palabra:palabras){
            String inicial= palabra.substring(0,1);
            iniciales.append(inicial);
        }
        return iniciales.toString();
    }
    public Alumno(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento ){
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
        } else if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío.");
        }

        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);

    }
    public Alumno(Alumno alumno){
        if (alumno == null) {
            throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
        }

        this.nombre=alumno.nombre;
        this.dni=alumno.dni;
        this.correo=alumno.correo;
        this.telefono=alumno.telefono;
        this.fechaNacimiento=alumno.fechaNacimiento;
        this.nia=alumno.nia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(dni, alumno.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    public String imprimir(){
        return String.format("Nombre: %s\nDNI: %s\nCorreo: %s\nTeléfono: %s\nFecha de nacimiento: %s",
                nombre, dni, correo, telefono, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Número de Identificación del Alumnado (NIA)=" + getNia()+ " nombre="+ getNombre() + " ("+getIniciales(nombre)+")"+", DNI="+getDni()+", correo="+getCorreo()+", teléfono="+getTelefono()+", fecha nacimiento="+fechaNacimiento.format(formato);
    }
}