package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Asignaturas implements IAsignaturas {
    private List<Asignatura> coleccionAsignaturas;
    private static final String RUTA_FICHERO = "datos/asignaturas.xml";


    private static Asignaturas instancia;
    static Asignaturas getInstancia() {
        if (instancia == null) {
            instancia = new Asignaturas();
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

    @Override
    public int getTamano() {
        return coleccionAsignaturas.size();
    }
    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
        comenzar();
    }

    @Override
    public List<Asignatura> get() {
        return copiaProfundaAsignaturas();
    }

    private List<Asignatura> copiaProfundaAsignaturas() {
        List<Asignatura> copiaProfunda = coleccionAsignaturas.stream()
                .filter(asig -> asig != null)
                .map(Asignatura::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    private Asignatura elementToAsignatura(Element asignaturaDOM) {
        String codAsignatura = asignaturaDOM.getAttribute("Codigo");

        Element eNombre = (Element) asignaturaDOM.getElementsByTagName("Nombre").item(0);
        Element eCurso = (Element) asignaturaDOM.getElementsByTagName("Curso").item(0);
        Element eEspecialidadProf = (Element) asignaturaDOM.getElementsByTagName("EspecialidadProfesorado").item(0);
        Element eCicloForm = (Element) asignaturaDOM.getElementsByTagName("CicloFormativo").item(0);
        Element eHoras = (Element) asignaturaDOM.getElementsByTagName("Horas").item(0);
        Element eHorasAnual = (Element) eHoras.getElementsByTagName("Anuales").item(0);
        Element eHorasDesd = (Element) eHoras.getElementsByTagName("Desdoble").item(0);

        if (eNombre == null || eCurso == null || eEspecialidadProf == null ||
                eCicloForm == null || eHorasAnual == null || eHorasDesd == null) {
            return null;
        }

        String nombreAsignatura = eNombre.getTextContent();
        int horasAnualAsignatura = Integer.parseInt(eHorasAnual.getTextContent());
        Curso cursoAsignatura = null;
        if (eCurso.getTextContent().equalsIgnoreCase("Primero")) {
            cursoAsignatura = Curso.PRIMERO;
        } else if (eCurso.getTextContent().equalsIgnoreCase("Segundo")) {
            cursoAsignatura = Curso.SEGUNDO;
        }
        int horasDesdAsignatura = Integer.parseInt(eHorasDesd.getTextContent());
        EspecialidadProfesorado especialidadProfAsignatura = null;
        if (eEspecialidadProf.getTextContent().equalsIgnoreCase("Informatica")) {
            especialidadProfAsignatura = EspecialidadProfesorado.INFORMATICA;
        } else if (eEspecialidadProf.getTextContent().equalsIgnoreCase("FOL")) {
            especialidadProfAsignatura = EspecialidadProfesorado.FOL;
        } else if (eEspecialidadProf.getTextContent().equalsIgnoreCase("Sistemas")) {
            especialidadProfAsignatura = EspecialidadProfesorado.SISTEMAS;
        }
        CicloFormativo cicloAsignatura = null;
        Grado gradoFalsoCiclo = new GradoE("grado falso", 1, 1);
        CicloFormativo cicloFalso = new CicloFormativo(Integer.parseInt(eCicloForm.getTextContent()),
                "familia", gradoFalsoCiclo, "ciclo falso", 3);
        cicloAsignatura = CiclosFormativos.getInstancia().buscar(cicloFalso);
        if (cicloAsignatura == null) {
            return null;
        }

        return new Asignatura(codAsignatura, nombreAsignatura, horasAnualAsignatura,
                cursoAsignatura, horasDesdAsignatura, especialidadProfAsignatura, cicloAsignatura);
    }

    private void leerXML(String rutaXml) {
        Document doc = UtilidadesXML.xmlToDom(rutaXml);
        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }

        Element raizDOM = doc.getDocumentElement();
        NodeList listaNodos = raizDOM.getElementsByTagName("Asignatura");

        if (listaNodos.getLength() > 0) {
            for (int i = 0; i < listaNodos.getLength(); i++) {
                Node nodo = listaNodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element asignaturaDOM = (Element) nodo;
                    Asignatura asignatura = elementToAsignatura(asignaturaDOM);
                    if (asignatura != null) coleccionAsignaturas.add(asignatura);
                }
            }
        }
    }

    private Element asignaturaToElement(Document DOMAsignaturas, Asignatura asignatura) {
        Element asignaturaDOM = DOMAsignaturas.createElement("Asignatura");

        String codAsign = asignatura.getCodigo();
        String nombreAsign = asignatura.getNombre();
        int horasAnualAsign = asignatura.getHorasAnuales();
        Curso cursoAsign = asignatura.getCurso();
        int horasDesdAsign = asignatura.getHorasDesdoble();
        EspecialidadProfesorado especialidadProfAsign = asignatura.getEspecialidadProfesorado();
        CicloFormativo cicloAsign = asignatura.getCicloFormativo();

        asignaturaDOM.setAttribute("Codigo", codAsign);

        Element eNombre = DOMAsignaturas.createElement("Nombre");
        eNombre.setTextContent(nombreAsign);
        asignaturaDOM.appendChild(eNombre);

        String cursoAsignCad = null;
        if (cursoAsign == Curso.PRIMERO) {
            cursoAsignCad = "Primero";
        } else if (cursoAsign == Curso.SEGUNDO) {
            cursoAsignCad = "Segundo";
        }
        Element eCurso = DOMAsignaturas.createElement("Curso");
        eCurso.setTextContent(cursoAsignCad);
        asignaturaDOM.appendChild(eCurso);

        String especialidadProfAsignCad = null;
        if (especialidadProfAsign == EspecialidadProfesorado.FOL) {
            especialidadProfAsignCad = "FOL";
        } else if (especialidadProfAsign == EspecialidadProfesorado.SISTEMAS) {
            especialidadProfAsignCad = "Sistemas";
        } else if (especialidadProfAsign == EspecialidadProfesorado.INFORMATICA) {
            especialidadProfAsignCad = "Informatica";
        }
        Element eEspecialidadProf = DOMAsignaturas.createElement("EspecialidadProfesorado");
        eEspecialidadProf.setTextContent(especialidadProfAsignCad);
        asignaturaDOM.appendChild(eEspecialidadProf);

        Element eCiclo = DOMAsignaturas.createElement("CicloFormativo");
        eCiclo.setTextContent(String.valueOf(cicloAsign.getCodigo()));
        asignaturaDOM.appendChild(eCiclo);

        Element eHoras = DOMAsignaturas.createElement("Horas");
        asignaturaDOM.appendChild(eHoras);

        Element eHorasAnual = DOMAsignaturas.createElement("Anuales");
        eHorasAnual.setTextContent(String.valueOf(horasAnualAsign));
        eHoras.appendChild(eHorasAnual);
        Element eHorasDesd = DOMAsignaturas.createElement("Desdoble");
        eHorasDesd.setTextContent(String.valueOf(horasDesdAsign));
        eHoras.appendChild(eHorasDesd);

        return asignaturaDOM;
    }

    private void escribirXML() {
        Document DOMAsignaturas = UtilidadesXML.crearDomVacio("Asignaturas");
        Element raizDOM = DOMAsignaturas.getDocumentElement();

        if (!coleccionAsignaturas.isEmpty()) {
            for (Asignatura asign : coleccionAsignaturas) {
                Element asignaturaDOM = asignaturaToElement(DOMAsignaturas, asign);
                raizDOM.appendChild(asignaturaDOM);
            }
        }

        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        UtilidadesXML.domToXml(DOMAsignaturas, RUTA_FICHERO);
    }


    @Override
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        if (coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        coleccionAsignaturas.add(asignatura);
    }

    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("Error: Una asignatura nula no puede buscarse.");
        }

        int indice;
        if (coleccionAsignaturas.contains(asignatura)) {
            indice = coleccionAsignaturas.indexOf(asignatura);
            asignatura = coleccionAsignaturas.get(indice);
            return new Asignatura(asignatura);
        } else return null;
    }

    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        if (!coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: No existe esa asignatura");
        } else coleccionAsignaturas.remove(asignatura);
    }
}
