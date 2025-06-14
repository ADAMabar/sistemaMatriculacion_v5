package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.matriculacion.vista.texto.Consola;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorCrearMatriculas {

    @FXML private Button btnCrearMatricula;
    @FXML private ComboBox<Asignatura> cbAsignaturasDisponibles;
    @FXML private VBox crearMatriculas;
    @FXML private DatePicker dpFechaMatricula;
    @FXML private TextField tfCursoAcademico;
    @FXML private TextField tfDniAlumno;
    @FXML private TextField tfIdMatricula;
    @FXML private TextField tfCodigoCiclo;
    @FXML private ListView<Asignatura> listaAsignaturas;
    private final ObservableList<Asignatura> obsListadoAsignaturas = FXCollections.observableArrayList();

    ArrayList<Asignatura> asignaturasfiltradas = new ArrayList<>();



    @FXML
    public void initialize()
    {
        dpFechaMatricula.setValue(LocalDate.now());
        setAsignaturasMatriculas();
    }
    @FXML
    void insertarMatriculas(ActionEvent event) {


        Stage escenarioMatriculas = (Stage) btnCrearMatricula.getScene().getWindow();

        try{

            int idMatricula = Integer.parseInt(tfIdMatricula.getText());
            String cursoAcademico = tfCursoAcademico.getText();
            LocalDate fechaMatricula = dpFechaMatricula.getValue();
            String dniAlumno = tfDniAlumno.getText();
            Alumno alumno = VistaGrafica.getInstancia().getControlador().buscar(new Alumno("ficticio", "666666666", "ficticio@inventado.es",  dniAlumno, LocalDate.of(1991, 12, 12)));
            if (alumno == null) {
                Dialogos.mostrarDialogoInformacion("Alumno no encontrado", "El alumno con DNI " + dniAlumno + " no existe");
            }else {
                ArrayList<Asignatura> asignatura = new ArrayList<>(listaAsignaturas.getSelectionModel().getSelectedItems());
                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatricula, alumno, asignatura);
                VistaGrafica.getInstancia().getControlador().insertar(matricula);
                Dialogos.mostrarDialogoInformacion("Matricula insertada", "Matricula insertada correctamente");
                escenarioMatriculas.close();
            }
        }catch (Exception e){
            Dialogos.mostrarDialogoError("Error al insertar la matricula",e.getMessage());
        }

    }
    @FXML
    void listaAsignaturasDisponibles(ActionEvent event){
        cbAsignaturasDisponibles.getItems().setAll(asignaturasfiltradas);
    }

    @FXML
    void filtrarCicloFormativo(InputMethodEvent event) {
        String codigoCiclo = tfCodigoCiclo.getText();
        if (codigoCiclo.length() < 4) {
            return;
        }
        try {
            List<Asignatura> asignaturas = VistaGrafica.getInstancia().getControlador().getAsignaturas();
            for (Asignatura asignatura : asignaturas) {
                if (asignatura.getCicloFormativo().equals(codigoCiclo)) {
                    asignaturasfiltradas.add(asignatura);
                }
            }

        } catch (Exception e) {
            Dialogos.mostrarDialogoError("Error al filtrar las asignaturas", e.getMessage());
        }
    }
    public void setAsignaturasMatriculas() {
        try {
            List<Asignatura> asignaturas = VistaGrafica.getInstancia().getControlador().getAsignaturas();
            obsListadoAsignaturas.addAll(asignaturas);
            listaAsignaturas.setItems(obsListadoAsignaturas);
            listaAsignaturas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listaAsignaturas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> listViewActualizaAsignaturas(oldValue, newValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listViewActualizaAsignaturas(Asignatura oldValue, Asignatura newValue) {
        System.out.println("Modificado valor del ChoiceBox");
        System.out.println("El valor anteriormente seleccionado era: " + oldValue);
        System.out.println("El nuevo valor seleccionado es: " + newValue);
    }
}

