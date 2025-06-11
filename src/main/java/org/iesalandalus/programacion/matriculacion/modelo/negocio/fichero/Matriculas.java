package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;


import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

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

public class Matriculas implements IMatriculas {
    private List<Matricula> coleccionMatriculas;
    private static final String RUTA_FICHERO = "datos/matriculas.xml";

    public Matriculas() {
        this.coleccionMatriculas = new ArrayList<>();
        comenzar();
    }

    private Matricula elementToMatricula(Element matriculaDOM) throws OperationNotSupportedException {
        int idMatr = Integer.parseInt(matriculaDOM.getAttribute("Id"));
        String alumnoMatrDni = matriculaDOM.getAttribute("Alumno");
        Alumno alumnoFalso = new Alumno("Alumno falso", alumnoMatrDni, "correofalso@gmail.com", "111111111", LocalDate.of(2000, 11, 11));
        Alumno alumnoMatr = Alumnos.getInstancia().buscar(alumnoFalso);

        Element eCursoAca = (Element) matriculaDOM.getElementsByTagName("CursoAcademico").item(0);
        Element eFechaMatriculacion = (Element) matriculaDOM.getElementsByTagName("FechaMatriculacion").item(0);
        Element eFechaAnulacion = (Element) matriculaDOM.getElementsByTagName("FechaAnulacion").item(0);
        Element eAsignaturas = (Element) matriculaDOM.getElementsByTagName("Asignaturas").item(0);

        List<Asignatura> listaAsignaturasMatr = new ArrayList<>();
        if (eAsignaturas != null) {
            NodeList nodelAsignatura = eAsignaturas.getElementsByTagName("Asignatura");
            for (int i = 0; i < nodelAsignatura.getLength(); i++) {
                Element eAsignatura = (Element) nodelAsignatura.item(i);
                String codAsignatura = eAsignatura.getAttribute("Codigo");
                CicloFormativo cicloFalso = new CicloFormativo(1000, "familia prof", new GradoE("gradoe", 1, 1), "nombre ciclo", 2);
                Asignatura asignaturaFalsa = new Asignatura(codAsignatura, "asignatura falsa", 3, Curso.PRIMERO, 2, EspecialidadProfesorado.FOL, cicloFalso);
                Asignatura asignatura = Asignaturas.getInstancia().buscar(asignaturaFalsa);
                if (asignatura != null) {
                    listaAsignaturasMatr.add(asignatura);
                }
            }
        }

        if (eCursoAca == null || eFechaMatriculacion == null || alumnoMatr == null || idMatr == 0 || listaAsignaturasMatr.isEmpty()) {
            return null;
        }

        String cursoAcaMatr = eCursoAca.getTextContent();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA);
        LocalDate fechaMatr = LocalDate.parse(eFechaMatriculacion.getTextContent(), formatoFecha);

        Matricula matricula = null;
        try {
            matricula = new Matricula(idMatr, cursoAcaMatr, fechaMatr, alumnoMatr, listaAsignaturasMatr);
            if (eFechaAnulacion != null && !eFechaAnulacion.getTextContent().isBlank()) {
                LocalDate fechaAnulaMatr = LocalDate.parse(eFechaAnulacion.getTextContent(), formatoFecha);
                matricula.setFechaAnulacion(fechaAnulaMatr);
                return matricula;
            }
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        return matricula;
    }

    private void leerXML(String rutaXml) throws OperationNotSupportedException {
        Document doc = UtilidadesXML.xmlToDom(rutaXml);
        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }

        Element raizDOM = doc.getDocumentElement();
        NodeList listaNodos = raizDOM.getElementsByTagName("Matricula");

        if (listaNodos.getLength() > 0) {
            for (int i = 0; i < listaNodos.getLength(); i++) {
                Node nodo = listaNodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element matriculaDOM = (Element) nodo;
                    Matricula matricula = elementToMatricula(matriculaDOM);
                    if (matricula != null) coleccionMatriculas.add(matricula);
                }
            }
        }
    }

    private Element matriculaToElement(Document DOMMatriculas, Matricula matricula) {
        Element matriculaDOM = DOMMatriculas.createElement("Matricula");

        int idMatr = matricula.getIdMatricula();
        String cursoAcaMatr = matricula.getCursoAcademico();
        Alumno alumnoMatr = matricula.getAlumno();
        List<Asignatura> asignaturasMatr = matricula.getColeccionAsignaturas();
        LocalDate fechaMatr = matricula.getFechaMatriculacion();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA);
        String fFechaMatr = fechaMatr.format(formatoFecha);
        LocalDate fechaAnulaMatr = matricula.getFechaAnulacion();

        matriculaDOM.setAttribute("Id", String.valueOf(idMatr));
        matriculaDOM.setAttribute("Alumno", alumnoMatr.getDni());

        Element eCursoAca = DOMMatriculas.createElement("CursoAcademico");
        eCursoAca.setTextContent(cursoAcaMatr);
        matriculaDOM.appendChild(eCursoAca);

        Element eFechaMatriculacion = DOMMatriculas.createElement("FechaMatriculacion");
        eFechaMatriculacion.setTextContent(fFechaMatr);
        matriculaDOM.appendChild(eFechaMatriculacion);

        Element eFechaAnulacion = DOMMatriculas.createElement("FechaAnulacion");
        if (fechaAnulaMatr != null) {
            String fFechaAnulaMatr = fechaAnulaMatr.format(formatoFecha);
            eFechaAnulacion.setTextContent(fFechaAnulaMatr);
        }
        matriculaDOM.appendChild(eFechaAnulacion);

        Element eAsignaturas = DOMMatriculas.createElement("Asignaturas");
        matriculaDOM.appendChild(eAsignaturas);
        for (Asignatura asign : asignaturasMatr) {
            String codAsignMatr = asign.getCodigo();
            Element eAsignatura = DOMMatriculas.createElement("Asignatura");
            eAsignatura.setAttribute("Codigo", codAsignMatr);
            eAsignaturas.appendChild(eAsignatura);
        }

        return matriculaDOM;
    }

    private void escribirXML() {
        Document DOMMatriculas = UtilidadesXML.crearDomVacio("Matriculas");
        Element raizDOM = DOMMatriculas.getDocumentElement();

        if (!coleccionMatriculas.isEmpty()) {
            for (Matricula matr : coleccionMatriculas) {
                Element matriculaDOM = matriculaToElement(DOMMatriculas, matr);
                raizDOM.appendChild(matriculaDOM);
            }
        }

        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        UtilidadesXML.domToXml(DOMMatriculas, RUTA_FICHERO);
    }

    private static Matriculas instancia;
    static Matriculas getInstancia() {
        if (instancia == null){
            instancia = new Matriculas();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        try {
            leerXML(RUTA_FICHERO);
        } catch (OperationNotSupportedException e) {
            e.getMessage();
        }
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    @Override
    public int getTamano() {
        return coleccionMatriculas.size();
    }

    @Override
    public List<Matricula> get() throws OperationNotSupportedException {
        return this.coleccionMatriculas;
    }

    private List<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
        List<Matricula> copiaProfunda = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula != null) {
                copiaProfunda.add(new Matricula(matricula));
            }
        }
        return copiaProfunda;
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese código.");
        }
        coleccionMatriculas.add(matricula);
    }

    @Override
    public Matricula buscar(Matricula matricula){
        try {
            if (matricula == null) {
                throw new NullPointerException("Matrícula nula no puede buscarse.");
            }

            int indice;
            if (coleccionMatriculas.contains(matricula)) {
                indice = coleccionMatriculas.indexOf(matricula);
                matricula = coleccionMatriculas.get(indice);
                return new Matricula(matricula);
            } else return null;
        } catch ( OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        if (!coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
        else coleccionMatriculas.remove(matricula);
    }

    @Override
    public List<Matricula> get(Alumno alumno){
        return coleccionMatriculas.stream()
                .filter(matricula -> matricula.getAlumno().equals(alumno))
                .collect(Collectors.toList());
    }

    @Override
    public List<Matricula> get(String cursoAcademico){
        return coleccionMatriculas.stream()
                .filter(matricula -> matricula.getCursoAcademico().equals(cursoAcademico))
                .collect(Collectors.toList());
    }

    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        return coleccionMatriculas.stream()
                .filter(matricula -> matricula.getColeccionAsignaturas().stream()
                        .anyMatch(asignatura -> asignatura.getCicloFormativo().equals(cicloFormativo)))
                .collect(Collectors.toList());
    }
}
