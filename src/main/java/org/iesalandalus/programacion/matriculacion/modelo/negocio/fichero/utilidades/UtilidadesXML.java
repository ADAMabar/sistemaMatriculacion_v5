package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

    public class UtilidadesXML {

        public static Document xmlToDom (String rutaXML) {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db;

            Document docDOM = null ;

            try {

                db = dbf.newDocumentBuilder();

                File ficheroXml = new File(rutaXML);

                docDOM = db.parse(ficheroXml);
                return docDOM;

            } catch(ParserConfigurationException | SAXException | IOException ex) {
                System.out.println(ex.getMessage());
            }
            return docDOM;
        }

        public static void domToXml (Document DOM, String rutaXml) {
            try {

                File f = new File(rutaXml);

                FileOutputStream fos = new FileOutputStream(f);

                DOMSource source = new DOMSource(DOM);

                StreamResult result = new StreamResult (new OutputStreamWriter (fos,"UTF-8"));

                TransformerFactory tFactory = TransformerFactory.newInstance();

                tFactory.setAttribute("indent-number", Integer.valueOf(4));

                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                transformer.transform(source, result);

            } catch (TransformerException | FileNotFoundException | UnsupportedEncodingException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public static Document crearDomVacio(String etiquetaRaiz) {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db;

            Document docDOMvacio = null ;

            try {
                 /// /////////
                db = dbf.newDocumentBuilder() ;

                docDOMvacio = db.newDocument();

                Element elementoRaiz = docDOMvacio.createElement(etiquetaRaiz);

                docDOMvacio.appendChild(elementoRaiz);

                return docDOMvacio;

            } catch (ParserConfigurationException ex) {
                System.out.println(ex.getMessage());
            }
            return docDOMvacio;
        }

    }

