package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
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

public class CiclosFormativos implements ICiclosFormativos {
    private List<CicloFormativo> coleccionCiclosFormativos;
    private static final String RUTA_FICHERO = "datos/ciclos.xml";

    public CiclosFormativos() {
        this.coleccionCiclosFormativos = new ArrayList<>();
        comenzar();
    }
    private static CiclosFormativos instancia;
    static CiclosFormativos getInstancia(){
        if (instancia == null){
            instancia = new CiclosFormativos();
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
        return coleccionCiclosFormativos.size();
    }

    @Override
    public List<CicloFormativo> get(){
        return copiaProfundaCiclosFormativos();
    }


    private CicloFormativo elementToCicloFormativo(Element cicloDOM) {

        int codigoCiclo = Integer.parseInt(cicloDOM.getAttribute("Codigo"));


        Element eNombre = (Element) cicloDOM.getElementsByTagName("Nombre").item(0);
        Element eFamilia = (Element) cicloDOM.getElementsByTagName("FamiliaProfesional").item(0);
        Element eHoras = (Element) cicloDOM.getElementsByTagName("Horas").item(0);

        Element eGrado = (Element) cicloDOM.getElementsByTagName("Grado").item(0);
        String aGrado = eGrado.getAttribute("Tipo");
        if (eNombre == null || eFamilia == null || eHoras == null ||  eGrado == null ) {
            return null;
        }

        String familiaCiclo = eFamilia.getTextContent();
        String nombreCiclo = eNombre.getTextContent();
        int horasCiclo = Integer.parseInt(eHoras.getTextContent());

        Grado gradoCiclo = null;

        Element eNombreGrado = (Element) eGrado.getElementsByTagName("Nombre").item(0);
        String nombreGrado = eNombreGrado.getTextContent();
        Element eNumAnios = (Element) eGrado.getElementsByTagName("NumAnios").item(0);
        int numAniosGrado = Integer.parseInt(eNumAnios.getTextContent());

        Element eModalidad = (Element) eGrado.getElementsByTagName("Modalidad").item(0);
        String modalidadGrado = null;
        if (eModalidad != null){
            modalidadGrado = eModalidad.getTextContent();
        }
        Modalidad modalidadGDCiclo = null;

        Element eNumEdiciones = (Element) eGrado.getElementsByTagName("NumEdiciones").item(0);
        int numEdicionesGECiclo = 0;
        if (eNumEdiciones != null){
            numEdicionesGECiclo = Integer.parseInt(eNumEdiciones.getTextContent());
        }

        if (aGrado.equalsIgnoreCase("GradoD")){

            if (modalidadGrado.equalsIgnoreCase("Presencial")){
                modalidadGDCiclo = Modalidad.PRESENCIAL;
            } else
                modalidadGDCiclo = Modalidad.SEMIPRESENCIAL;

            gradoCiclo = new GradoD(nombreGrado, numAniosGrado, modalidadGDCiclo);

        } else if (aGrado.equalsIgnoreCase("GradoE")){
            gradoCiclo = new GradoE(nombreGrado, numAniosGrado, numEdicionesGECiclo);
        }
        return new CicloFormativo(codigoCiclo, familiaCiclo, gradoCiclo, nombreCiclo, horasCiclo);
    }

    private void leerXML(String rutaXml) {

        Document doc = UtilidadesXML.xmlToDom(rutaXml);

        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }

        Element raizDOM = doc.getDocumentElement();

        NodeList listaNodos = raizDOM.getElementsByTagName("CicloFormativo");

        if (listaNodos.getLength() > 0) {

            for (int i = 0; i < listaNodos.getLength(); i++) {

                Node nodo = listaNodos.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element cicloDOM = (Element) nodo;
                    CicloFormativo cicloFormativo = elementToCicloFormativo(cicloDOM);

                    if (cicloFormativo != null) coleccionCiclosFormativos.add(cicloFormativo);
                }
            }
        }
    }

    private Element cicloFormativoToElement (Document DOMCiclos, CicloFormativo cicloFormativo) {
        Element cicloDOM = DOMCiclos.createElement("CicloFormativo");

        int codigoCiclo = cicloFormativo.getCodigo();
        String familiaCiclo = cicloFormativo.getFamiliaProfesional();
        Grado gradoCiclo = cicloFormativo.getGrado();
        String nombreCiclo = cicloFormativo.getNombre();
        int horasCiclo = cicloFormativo.getHoras();

        cicloDOM.setAttribute("Codigo", String.valueOf(codigoCiclo));

        Element eNombre = DOMCiclos.createElement("Nombre");

        eNombre.setTextContent(nombreCiclo);
        cicloDOM.appendChild(eNombre);

        Element eFamilia = DOMCiclos.createElement("FamiliaProfesional");
        eFamilia.setTextContent(familiaCiclo);
        cicloDOM.appendChild(eFamilia);
        Element eHoras = DOMCiclos.createElement("Horas");
        eHoras.setTextContent(String.valueOf(horasCiclo));
        cicloDOM.appendChild(eHoras);

        Element eGrado = DOMCiclos.createElement("Grado");
        cicloDOM.appendChild(eGrado);
        Element eNombreGrado = DOMCiclos.createElement("Nombre");
        eNombreGrado.setTextContent(gradoCiclo.getNombre());
        eGrado.appendChild(eNombreGrado);
        Element eNumAniosGrado = DOMCiclos.createElement("NumAnios");
        eNumAniosGrado.setTextContent(String.valueOf(gradoCiclo.getNumAnios()));
        eGrado.appendChild(eNumAniosGrado);
        String tipoGrado = null;
        String modalidadGrado = null;
        String numEdicionesGrado = null;
        if (gradoCiclo instanceof GradoD){
            tipoGrado = "GradoD";

            if (((GradoD) gradoCiclo).getModalidad() == Modalidad.PRESENCIAL){
                modalidadGrado = "Presencial";
            } else if (((GradoD) gradoCiclo).getModalidad() == Modalidad.SEMIPRESENCIAL){
                modalidadGrado = "Semipresencial";
            }
            Element eModalidadGD = DOMCiclos.createElement("Modalidad");
            eModalidadGD.setTextContent(modalidadGrado);
            eGrado.appendChild(eModalidadGD);

        } else if(gradoCiclo instanceof GradoE) {
            tipoGrado = "GradoE";

            numEdicionesGrado = String.valueOf(((GradoE) gradoCiclo).getNumEdiciones());
            Element eNumEdicionesGE = DOMCiclos.createElement("NumEdiciones");
            eNumEdicionesGE.setTextContent(numEdicionesGrado);
            eGrado.appendChild(eNumEdicionesGE);
        }
        eGrado.setAttribute("Tipo", tipoGrado);

        return cicloDOM;
    }

    private void escribirXML() {

        Document DOMCiclos = UtilidadesXML.crearDomVacio("CiclosFormativos");


        Element raizDOM = DOMCiclos.getDocumentElement();

        if (!coleccionCiclosFormativos.isEmpty()) {
            for (CicloFormativo ciclo : coleccionCiclosFormativos) {
                Element cicloDOM = cicloFormativoToElement(DOMCiclos, ciclo);
                raizDOM.appendChild(cicloDOM);
            }
        }

        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        UtilidadesXML.domToXml(DOMCiclos, RUTA_FICHERO);
    }


    private List<CicloFormativo> copiaProfundaCiclosFormativos(){
        List<CicloFormativo> copiaProfunda = coleccionCiclosFormativos.stream()
                .filter(ciclo -> ciclo != null)
                .map(CicloFormativo::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }

    @Override
    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }

        coleccionCiclosFormativos.add(cicloFormativo);
    }


    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null){
            throw new NullPointerException("Ciclo nulo no puede buscarse.");
        }

        int indice;
        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
            cicloFormativo = coleccionCiclosFormativos.get(indice);
            return new CicloFormativo(cicloFormativo);
        }
        else return null;
    }


    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }

        if (!coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR:No existe ese ciclo fomativo.");
        }
        else coleccionCiclosFormativos.remove(cicloFormativo);
    }

}