package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

public class ControladorCrearAsignaturas {

    @FXML private Button btnCrearAsignatura;
    @FXML private ComboBox<EspecialidadProfesorado> cbEspecialidadProfesor;
    @FXML private HBox crearAsignaturas;
    @FXML private TextField tfCodigoAsignatura;
    @FXML private TextField tfHorasAnuales;
    @FXML private TextField tfHorasDesdoble;
    @FXML private TextField tfNombreAsignatura;
    @FXML private TextField tfCodigoCicloAsignatura;
    @FXML private ComboBox<Curso> cbCursoAsignatura;
    @FXML private TextField tfNhorasDesdoble;





    private ObservableList<EspecialidadProfesorado> obsListadoOpcionesChoice=
            FXCollections.observableArrayList(EspecialidadProfesorado.values());
    private ObservableList<Curso> obsListadoCursoChoice=
            FXCollections.observableArrayList(Curso.values());



    public void initialize() {

        cargarEspecialidadProfesor();
        cargarCurso();


    }
    @FXML
    void crearAsignaturas(ActionEvent event) {
        Stage escenarioAsignaturas = (Stage) btnCrearAsignatura.getScene().getWindow();
        try{
            String codigoAsignatura = tfCodigoAsignatura.getText();
            String nombreAsignatura = tfNombreAsignatura.getText();
            int horasAnuales = Integer.parseInt(tfHorasAnuales.getText());
            int horasDesdoble = Integer.parseInt(tfNhorasDesdoble.getText());
            CicloFormativo codigoCicloAsignatura = VistaGrafica.getInstancia().getControlador().buscar(new CicloFormativo(Integer.parseInt(tfCodigoCicloAsignatura.getText()), "null", new GradoD("null", 2, Modalidad.PRESENCIAL), "null", 120));
            Curso cursoAsignatura = cbCursoAsignatura.getValue();
            EspecialidadProfesorado especialidadProfesorado = cbEspecialidadProfesor.getValue();
            if (horasAnuales<0 || horasAnuales>300){
                Dialogos.mostrarDialogoError("Error al insertar Asignatura","Horas anuales incorrectas");
                return;
            }
            if(horasDesdoble<0 || horasDesdoble>6){
                Dialogos.mostrarDialogoError("Error al insertar Asignatura","Horas desdoble incorrectas");
                return;
            }



            Asignatura asignatura = new Asignatura(codigoAsignatura,nombreAsignatura,horasAnuales,cursoAsignatura,horasDesdoble,especialidadProfesorado,codigoCicloAsignatura);
            //controlador.insertar(alumno);
            VistaGrafica.getInstancia().getControlador().insertar(asignatura);
            Dialogos.mostrarDialogoInformacion("Asignatura insertada","Asignatura insertada correctamente");
            escenarioAsignaturas.close();

        }catch (Exception e) {
            Dialogos.mostrarDialogoError("Error al insertar Asignatura",e.getMessage());
        }

    }

    @FXML void seleccionarEspecialidad(ActionEvent event) {
        System.out.println(cbEspecialidadProfesor.getValue());
    }
    private void cargarEspecialidadProfesor() {
        cbEspecialidadProfesor.setItems(obsListadoOpcionesChoice);
        cbEspecialidadProfesor.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->modificadoComboBoxListadoOpciones(oldValue,newValue));
    }
    private void modificadoComboBoxListadoOpciones(EspecialidadProfesorado oldValue, EspecialidadProfesorado newValue) {
        System.out.println(oldValue+" -> "+newValue);
    }

    @FXML void seleccionarCurso(ActionEvent event) {
        System.out.println(cbCursoAsignatura.getValue());
    }
    private void cargarCurso() {
        cbCursoAsignatura.setItems(obsListadoCursoChoice);
        cbCursoAsignatura.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->modificadoComboBoxListadoCurso(oldValue,newValue));
    }
    private void modificadoComboBoxListadoCurso(Curso oldValue, Curso newValue) {
        System.out.println(oldValue+" -> "+newValue);
    }


}