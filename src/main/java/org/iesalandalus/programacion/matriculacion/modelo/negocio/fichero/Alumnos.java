package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;


import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Alumnos implements IAlumnos {

    private List<Alumno> coleccionAlumnos;
    private static final String RUTA_FICHERO = "datos/alumnos.xml";
    private static Alumnos instancia;
    static Alumnos getInstancia(){
        if (instancia == null){
            instancia = new Alumnos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        leerXML(RUTA_FICHERO);
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    public Alumnos() {
        this.coleccionAlumnos = new ArrayList<>();
        comenzar();
    }

    private void leerXML(String rutaXml) {
        Document doc = UtilidadesXML.xmlToDom(rutaXml);
        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }

        Element raizDOM = doc.getDocumentElement();
        NodeList listaNodos = raizDOM.getElementsByTagName("Alumno");

        if (listaNodos.getLength() > 0) {
            for (int i = 0; i < listaNodos.getLength(); i++) {
                Node nodo = listaNodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element alumnoDOM = (Element) nodo;
                    Alumno alumno = elementToAlumno(alumnoDOM);
                    if (alumno != null) coleccionAlumnos.add(alumno);
                }
            }
        }
        System.out.println("Se ha leido correctamente");
    }

    private Alumno elementToAlumno(Element alumnoDOM) {
        String dniAlum = alumnoDOM.getAttribute("Dni");

        Element eNombre = (Element) alumnoDOM.getElementsByTagName("Nombre").item(0);
        Element eTelefono = (Element) alumnoDOM.getElementsByTagName("Telefono").item(0);
        Element eCorreo = (Element) alumnoDOM.getElementsByTagName("Correo").item(0);
        Element eFechaNacimiento = (Element) alumnoDOM.getElementsByTagName("FechaNacimiento").item(0);

        if (eNombre == null || eTelefono == null || eCorreo == null || eFechaNacimiento == null ) {
            return null;
        }

        String nombreAlum = eNombre.getTextContent();
        String correoAlum = eCorreo.getTextContent();
        String telefonoAlum = eTelefono.getTextContent();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA);
        LocalDate fechaNacimientoAlum = LocalDate.parse(eFechaNacimiento.getTextContent(), formatoFecha);

        return new Alumno(nombreAlum, dniAlum, correoAlum, telefonoAlum, fechaNacimientoAlum);
    }

    private void escribirXML() {
        try {

            File fichero = new File(RUTA_FICHERO);
            if (fichero.getParentFile() != null && !fichero.getParentFile().exists()) {
                if (fichero.getParentFile().mkdirs()) {
                    System.out.println("Directorio creado: " + fichero.getParentFile().getAbsolutePath());
                } else {
                    System.err.println("No se pudo crear el directorio: " + fichero.getParentFile().getAbsolutePath());
                    return;
                }
            }

            
            Document DOMAlumnos = UtilidadesXML.crearDomVacio("Alumnos");
            if (DOMAlumnos == null) {
                System.err.println("Error al crear el Document vacío.");
                return;
            }


            Element raizDOM = DOMAlumnos.getDocumentElement();
            if (!coleccionAlumnos.isEmpty()) {
                for (Alumno alum : coleccionAlumnos) {
                    Element alumnoDOM = alumnoToElement(DOMAlumnos, alum);
                    raizDOM.appendChild(alumnoDOM);
                }
                System.out.println("Se añadieron " + coleccionAlumnos.size() + " alumnos al Document.");
            } else {

                Element mensaje = DOMAlumnos.createElement("Mensaje");
                mensaje.setTextContent("No hay alumnos registrados.");
                raizDOM.appendChild(mensaje);
                System.out.println("La colección de alumnos está vacía. Se añadió un mensaje al XML.");
            }


            UtilidadesXML.domToXml(DOMAlumnos, RUTA_FICHERO);
            System.out.println("Archivo XML escrito exitosamente en: " + RUTA_FICHERO);

        } catch (Exception e) {
            System.err.println("Error al escribir el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private Element alumnoToElement(Document DOMAlumnos, Alumno alumno) {
        Element alumnoDOM = DOMAlumnos.createElement("Alumno");

        String dniAlum = alumno.getDni();
        String nombreAlum = alumno.getNombre();
        String telefonoAlum = alumno.getTelefono();
        String correoAlum = alumno.getCorreo();
        LocalDate fechaNacimientoAlum = alumno.getFechaNacimiento();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA);
        String fFechaNacimientoAlum = fechaNacimientoAlum.format(formatoFecha);

        alumnoDOM.setAttribute("Dni", dniAlum);

        Element eNombre = DOMAlumnos.createElement("Nombre");
        eNombre.setTextContent(nombreAlum);
        alumnoDOM.appendChild(eNombre);

        Element eTelefono = DOMAlumnos.createElement("Telefono");
        eTelefono.setTextContent(telefonoAlum);
        alumnoDOM.appendChild(eTelefono);

        Element eCorreo = DOMAlumnos.createElement("Correo");
        eCorreo.setTextContent(correoAlum);
        alumnoDOM.appendChild(eCorreo);

        Element eFechaNacimiento = DOMAlumnos.createElement("FechaNacimiento");
        eFechaNacimiento.setTextContent(fFechaNacimientoAlum);
        alumnoDOM.appendChild(eFechaNacimiento);

        return alumnoDOM;
    }



    @Override
    public int getTamano() {
        return coleccionAlumnos.size();
    }

    @Override
    public List<Alumno> get() {
        return copiaProfundaAlumnos();
    }

    private List<Alumno> copiaProfundaAlumnos() {
        return coleccionAlumnos.stream()
                .filter(alumno -> alumno != null)
                .map(Alumno::new)
                .collect(Collectors.toList());
    }

    @Override
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        if (coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }

        coleccionAlumnos.add(alumno);
    }

    @Override
    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("Alumno nulo no puede buscarse.");
        }

        if (coleccionAlumnos.contains(alumno)) {
            int indice = coleccionAlumnos.indexOf(alumno);
            alumno = coleccionAlumnos.get(indice);
            return new Alumno(alumno);
        } else {
            return null;
        }
    }

    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }

        if (!coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }

        coleccionAlumnos.remove(alumno);
    }
}
