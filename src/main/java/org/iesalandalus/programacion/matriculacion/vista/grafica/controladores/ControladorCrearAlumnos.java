package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import java.time.LocalDate;

public class ControladorCrearAlumnos {

    private Controlador controlador;
    @FXML
    private Button btnAceptarAlumno;
    @FXML
    private DatePicker dpFechaNac;
    @FXML
    private HBox hbCorreoAlumno;
    @FXML
    private HBox hbDniAlumno;
    @FXML
    private HBox hbFechaNac;
    @FXML
    private HBox hbNombreAlumno;
    @FXML
    private HBox hbTelefonoAlumno;
    @FXML
    private TextField tfCorreoAlumno;
    @FXML
    private TextField tfDniAlumno;
    @FXML
    private TextField tfNombreAlumno;
    @FXML
    private TextField tfTelefonoAlumno;

    @FXML
    public void initialize()
    {
        dpFechaNac.setValue(LocalDate.now());
    }
    @FXML
    void insertarAlumno(ActionEvent event) {

        Stage escenarioAlumnos = (Stage) btnAceptarAlumno.getScene().getWindow();

        try{

            String nombre = tfNombreAlumno.getText();
            String telefono = tfTelefonoAlumno.getText();
            String correo = tfCorreoAlumno.getText();
            String dni = tfDniAlumno.getText();
            LocalDate fechaNac = dpFechaNac.getValue();

            if(nombre.trim().isBlank()||telefono.trim().isBlank()||correo.trim().isBlank()||dni.trim().isBlank()||fechaNac==null){
                return;
            }


            Alumno alumno = new Alumno(nombre,telefono,correo,dni,fechaNac);
            //controlador.insertar(alumno);
            VistaGrafica.getInstancia().getControlador().insertar(alumno);
            Dialogos.mostrarDialogoInformacion("Alumno insertado","Alumno insertado correctamente");
            escenarioAlumnos.close();
        }catch (Exception e){
            Dialogos.mostrarDialogoError("Error al insertar alumno",e.getMessage());

        }

    }



}