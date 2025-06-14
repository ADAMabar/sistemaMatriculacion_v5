package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

public class ControladorCrearCiclosFormativos {

    @FXML private Button btnAceptarCrearCiclo;
    @FXML private ComboBox <TiposGrado> cbElegirGrado;
    @FXML private TextField tfFamiliaCiclo;
    @FXML private TextField tfHorasCiclo;
    @FXML private TextField tfNombreCiclo;
    @FXML private TextField tfcodigoCiclo;
    @FXML private TextField tfEdicionesCiclo;
    @FXML private ComboBox<Modalidad> cbModalidad;
    @FXML private RadioButton check1ano;
    @FXML private RadioButton check2anos;
    @FXML private RadioButton check3anos;
    @FXML private Label lbEdiciones;
    @FXML private Label lbModalidad;


    @FXML
    public void initialize() {

        cargarOpcionesGrados();
        cargarModalidad();

    }

    private ObservableList<TiposGrado> obsListadoOpcionesChoice=
            FXCollections.observableArrayList(TiposGrado.values());

    private void cargarOpcionesGrados() {
        cbElegirGrado.setItems(obsListadoOpcionesChoice);
        cbElegirGrado.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->modificadoListaGrados(oldValue,newValue));
        cbElegirGrado.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->modificadoComboBoxListadoOpciones(oldValue,newValue));
    }

    private void modificadoComboBoxListadoOpciones(TiposGrado oldValue, TiposGrado newValue) {
        System.out.println(oldValue+" -> "+newValue);
    }
    @FXML
    void seleccionarGrado(ActionEvent event) {
        System.out.println(cbElegirGrado.getValue());
    }


    private ObservableList<Modalidad> obsListadoModalidadChoice=
            FXCollections.observableArrayList(Modalidad.values());

    private void cargarModalidad() {
        cbModalidad.setItems(obsListadoModalidadChoice);
        cbModalidad.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->modificadoComboBoxListadoOpciones(oldValue,newValue));
    }

    private void modificadoComboBoxListadoOpciones(Modalidad oldValue, Modalidad newValue) {
        System.out.println(oldValue+" -> "+newValue);
    }

    @FXML
    void seleccionarModalidad(ActionEvent event) {
        System.out.println(cbModalidad.getValue());
    }


    @FXML
    void CrearCicloFormativo(ActionEvent event) {

        Stage escenarioCiclos = (Stage) btnAceptarCrearCiclo.getScene().getWindow();
        try {
            int ediciones = 0;
            int codigo = Integer.parseInt(tfcodigoCiclo.getText());
            String familia = tfFamiliaCiclo.getText();
            TiposGrado tg = cbElegirGrado.getSelectionModel().getSelectedItem();
            String nombre = tfNombreCiclo.getText();

            Modalidad modalidad = cbModalidad.getSelectionModel().getSelectedItem();
            Grado grado = null;

            int anios = 0;
            if (check1ano.isSelected()){
                anios = 1;
            }
            if (check2anos.isSelected()){
                anios = 2;
            }
            if (check3anos.isSelected()){
                anios = 3;
            }


            if(tg.toString().equals("Grado D")){
                grado = new GradoD(nombre, anios, modalidad);
            }else{
                ediciones = Integer.parseInt(tfEdicionesCiclo.getText());
                grado = new GradoE(nombre, anios, ediciones);
            }

            int horas = Integer.parseInt(tfHorasCiclo.getText());

            if (codigo < 0 || codigo > 9999 || familia.trim().isBlank() || (grado instanceof  GradoD && modalidad == null ) || (grado instanceof  GradoE && ediciones < 1) || anios == 0 || grado == null || nombre.trim().isBlank() || horas < 0) {
                return;
            }
            CicloFormativo ciclo = new CicloFormativo(codigo, familia, grado, nombre, horas);
            VistaGrafica.getInstancia().getControlador().insertar(ciclo);
            Dialogos.mostrarDialogoInformacion("Ciclo Formativo insertado","Ciclo Formativo insertado correctamente");
            escenarioCiclos.close();
        } catch (Exception e) {
            Dialogos.mostrarDialogoError("Error al insertar Ciclo Formativo",e.getMessage());
        }
    }

    private void modificadoListaGrados(TiposGrado oldValue, TiposGrado newValue)
    {
        System.out.println("Modificado valor del ListView");
        System.out.println("El nuevo valor seleccionado es: " + newValue);

        if (newValue.equals(TiposGrado.GRADOD)) {
            lbModalidad.setVisible(true);
            cbModalidad.setVisible(true);
            lbEdiciones.setVisible(false);
            tfEdicionesCiclo.setVisible(false);
            check1ano.setVisible(false);
            check2anos.setVisible(true);
            check3anos.setVisible(true);
        } else if (newValue.equals(TiposGrado.GRADOE)) {
            lbEdiciones.setVisible(true);
            tfEdicionesCiclo.setVisible(true);
            lbModalidad.setVisible(false);
            cbModalidad.setVisible(false);
            check1ano.setVisible(true);
            check2anos.setVisible(false);
            check3anos.setVisible(false);
        }
    }



}