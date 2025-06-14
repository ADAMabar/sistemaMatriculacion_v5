package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Matriculas;
import org.iesalandalus.programacion.matriculacion.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.matriculacion.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ControladorVentanaPrincipal {

    @FXML private Tab tabAlumnos;
    @FXML private Tab tabCiclos;
    @FXML private Tab tabMatriculas;
    @FXML private Tab tabAsignaturas;
    @FXML private TableView<Alumno> tablCentralBusquedasAlumno;
    @FXML private TableView<CicloFormativo> tablCiclosFormativos;
    @FXML private TableView<Asignatura> tablAsignaturasMostrar;
    @FXML private TableView<Matricula> tablMatriculasMostrar;
    @FXML private TableColumn<Alumno, String> tablColum1;
    @FXML private TableColumn<Alumno, Integer> tablColum2;
    @FXML private TableColumn<Alumno, String> tablColum3;
    @FXML private TableColumn<Alumno, String> tablColum4;
    @FXML private TableColumn<Alumno, LocalDate> tablColum5;
    @FXML private TableView<Matricula> tablFiltro;
    @FXML private TableColumn<Matricula, String> nombreAlumno;
    @FXML private TableColumn<Matricula, String> cicloFormativoAlumno;
    @FXML private TableColumn<Matricula, String> FechaMatriculacionAlumno;
    @FXML private TableColumn<Matricula, String> FechaAnulacionAlumno;
    @FXML private TableColumn<Matricula, String> asignaturasAlumno;
    @FXML private TableColumn<Matricula, String> cursoAcademico;
    @FXML private TextField tfbuscarAsignaturas;
    @FXML private TextField tfBusquedaAlumno;
    @FXML private TextField buscarCiclos;
    @FXML private TextField buscarMatriculas;
    @FXML private TableColumn<CicloFormativo, Integer> columCicloCodigo;
    @FXML private TableColumn<CicloFormativo, String> columCicloFamPro;
    @FXML private TableColumn<CicloFormativo, Integer> columCicloHoras;
    @FXML private TableColumn<CicloFormativo, String> columCicloNombre;
    @FXML private TableColumn<CicloFormativo, String> columCicloGrado;
    @FXML private TableColumn<Asignatura, String> columCodigoAsignatura;
    @FXML private TableColumn<Asignatura, String> ColumNombreAsignatura;
    @FXML private TableColumn<Asignatura, Integer> columHorasAnuales;
    @FXML private TableColumn<Asignatura, Curso> columCurso;
    @FXML private TableColumn<Asignatura, Integer> columHorasDesdoble;
    @FXML private TableColumn<Asignatura, EspecialidadProfesorado> columEspecialidad;
    @FXML private TableColumn<Asignatura, String> columCodigoCiclo;
    @FXML private TableColumn<Matricula, Integer> columIdMatricula;
    @FXML private TableColumn<Matricula, String> columCursoAcademico;
    @FXML private TableColumn<Matricula, LocalDate> columFechaMatriculacion;
    @FXML private TableColumn<Matricula, LocalDate> columFechaAnulacion;
    @FXML private TableColumn<Matricula, Alumno> columDniMatricula;
    @FXML private TableView<Matricula> tabFiltroCiclo;
    @FXML private TableColumn<Matricula, String> cicloAlumnos;
    @FXML private TableColumn<Matricula, String> cicloAsignaturas;
    @FXML private TableColumn<Matricula, Integer> cicloCodigo;
    @FXML private TableColumn<Matricula, String> cicloCurso;
    @FXML private TableColumn<Matricula, Integer> cicloIdMatricula;
    @FXML private TableColumn<Matricula, String> cicloNombre;
    @FXML private TableView<Matricula> tablFiltroCursoAcademico;
    @FXML private TableColumn<Matricula, String> matriculaAlumno;
    @FXML private TableColumn<Matricula, String> matriculaAnulacionFecha;
    @FXML private TableColumn<Matricula, String> matriculaAsignaturas;
    @FXML private TableColumn<Matricula, String> matriculaCursoAcademico;
    @FXML private TableColumn<Matricula, String> matriculaDniAlumno;
    @FXML private TableColumn<Matricula, Integer> matriculaId;
    @FXML private TableColumn<Matricula, String> matriculaMatriculacionFecha;
    @FXML private TabPane tabPanePrincipal;
    @FXML private TabPane tabPaneMatriculas;
    @FXML private Tab filtradoAlumnos;
    @FXML private Tab filtradoCiclos;
    @FXML private Tab filtradoMatriculas;
    private List<Alumno> coleccionAlumnos = new ArrayList<>();
    private ObservableList<Alumno> alumnosObservable = FXCollections.observableArrayList();

    private List<CicloFormativo> coleccionCicloFormativo = new ArrayList<>();
    private ObservableList<CicloFormativo> cicloFormativoObservable = FXCollections.observableArrayList();

    private final ObservableList<Asignatura> asignaturasObservable = FXCollections.observableArrayList();
    private List<Asignatura> coleccionAsignaturas = new ArrayList<>();
    private List<Matricula> coleccionMatriculas = new ArrayList<>();
    private ObservableList<Matricula> matriculasObservable = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            mostrarTablaAlumno();
            mostrarTablaCiclosFormativos();
            mostrarTablaAsignaturas();
            mostrarTablaMatriculas();
            mostrarTablas();
            tfBusquedaAlumno.textProperty().addListener((obs, oldVal, newVal) -> filtraAlumnos(newVal));
            buscarCiclos.textProperty().addListener((obs, oldVal, newVal) -> filtraCiclos(newVal));
            tfbuscarAsignaturas.textProperty().addListener((obs, oldVal, newVal) -> filtraAsignaturas(newVal));
            buscarMatriculas.textProperty().addListener((obs, oldVal, newVal) -> filtraMatriculas(newVal));

            tablCentralBusquedasAlumno.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    mostrarMatriculaAlumno();
                } else {
                    tablFiltro.getItems().clear();
                }
            });
            tablCiclosFormativos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    mostrarMatriculaCiclos();
                } else {
                    tabFiltroCiclo.getItems().clear();
                }
            });
            tablMatriculasMostrar.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    mostrarMatriculaCursoAcademico();
                } else {
                    tablFiltroCursoAcademico.getItems().clear();
                }
            });

            tablCentralBusquedasAlumno.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    mostrarFiltrado();
                } else {
                    tablFiltro.getItems().clear();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @FXML
    public void crearAlumnos(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/CrearAlumnos.fxml"));
            Parent raiz = null;

            raiz = fxmlLoader.load();

            Stage escenarioAlumnos = new Stage();
            Scene escena = new Scene(raiz/*, 600, 600*/);
            escenarioAlumnos.setTitle("Crear Alumnos");
            escenarioAlumnos.setScene(escena);
            escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
            escenarioAlumnos.setOnCloseRequest(e->confirmaCierreAlumnos(escenarioAlumnos,e));
            escenarioAlumnos.setResizable(false);
            escenarioAlumnos.showAndWait();

            mostrarTablaAlumno();
        } catch (IOException e) {
            Dialogos.mostrarDialogoError("Creador de Alumnos", "Error al crear el Alumno");
        }
    }
    private void confirmaCierreAlumnos(Stage escenarioAlumnos, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Creador de Alumnos", "¿Realmente quieres salir sin guardar el Alumno?"))
        {
            escenarioAlumnos.close();
        }
        else
            e.consume();
    }

    @FXML
    void crearCiclos(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/CrearCiclos.fxml"));
            Parent raiz = null;

            raiz = fxmlLoader.load();

            Stage escenarioCiclosFormativos = new Stage();
            Scene escena = new Scene(raiz/*, 600, 600*/);
            escenarioCiclosFormativos.setTitle("Crear Ciclos Formativos");
            escenarioCiclosFormativos.setScene(escena);
            escenarioCiclosFormativos.initModality(Modality.APPLICATION_MODAL);
            escenarioCiclosFormativos.setOnCloseRequest(e->confirmaCierreCiclos(escenarioCiclosFormativos,e));
            escenarioCiclosFormativos.setResizable(false);
            escenarioCiclosFormativos.showAndWait();

            mostrarTablaCiclosFormativos();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void confirmaCierreCiclos(Stage escenarioCiclosFormativos, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Creador de Ciclos Formativos", "¿Realmente quieres salir sin guardar el Ciclo Formativo?"))
        {
            escenarioCiclosFormativos.close();
        }
        else
            e.consume();
    }

    @FXML
    void crearAsignaturas(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/CrearAsignaturas.fxml"));
            Parent raiz = null;

            raiz = fxmlLoader.load();

            Stage escenarioAsignaturas = new Stage();
            Scene escena = new Scene(raiz/*, 600, 600*/);
            escenarioAsignaturas.setTitle("Crear Asignaturas");
            escenarioAsignaturas.setScene(escena);
            escenarioAsignaturas.initModality(Modality.APPLICATION_MODAL);
            escenarioAsignaturas.setOnCloseRequest(e->confirmaCierreAsignaturas(escenarioAsignaturas,e));
            escenarioAsignaturas.setResizable(false);
            escenarioAsignaturas.showAndWait();

            mostrarTablaAsignaturas();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmaCierreAsignaturas(Stage escenarioAsignaturas, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Creador de Asignaturas", "¿Realmente quieres salir sin guardar la Asignatura?"))
        {
            escenarioAsignaturas.close();
        }
        else
            e.consume();
    }
    @FXML
    void crearMatriculas(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/CrearMatriculas.fxml"));
            Parent raiz = null;

            raiz = fxmlLoader.load();

            Stage escenarioMatriculas = new Stage();
            Scene escena = new Scene(raiz/*, 600, 600*/);
            escenarioMatriculas.setTitle("Crear Matriculas");
            escenarioMatriculas.setScene(escena);
            escenarioMatriculas.initModality(Modality.APPLICATION_MODAL);
            escenarioMatriculas.setOnCloseRequest(e->confirmaCierreMatriculas(escenarioMatriculas,e));
            escenarioMatriculas.setResizable(false);
            escenarioMatriculas.showAndWait();

            mostrarTablaMatriculas();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmaCierreMatriculas(Stage escenarioMatriculas, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Creador de Matriculas", "¿Realmente quieres salir sin guardar la Matricula?"))
        {
            escenarioMatriculas.close();
        }
        else
            e.consume();
    }

    private void mostrarTablaAlumno() {
        try {
            tablColum1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tablColum2.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            tablColum3.setCellValueFactory(new PropertyValueFactory<>("correo"));
            tablColum4.setCellValueFactory(new PropertyValueFactory<>("dni"));
            tablColum5.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            tablColum5.setCellFactory(column -> new TableCell<Alumno, LocalDate>() {
                @Override
                protected void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setText(empty || date == null ? "" : date.format(formatter));
                }
            });

            tablCentralBusquedasAlumno.setItems(FXCollections.observableArrayList(obtenerListaAlumnos()));
            tablCentralBusquedasAlumno.setItems(alumnosObservable);
            coleccionAlumnos = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getAlumnos());
            alumnosObservable.setAll(coleccionAlumnos);

            tablCentralBusquedasAlumno.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> muestraPersonaSeleccionada(newValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarTablaCiclosFormativos() {
        try {

            columCicloCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            columCicloFamPro.setCellValueFactory(new PropertyValueFactory<>("familiaProfesional"));
            columCicloGrado.setCellValueFactory(new PropertyValueFactory<>("grado"));
            columCicloNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columCicloHoras.setCellValueFactory(new PropertyValueFactory<>("horas"));


            tablCiclosFormativos.setItems(cicloFormativoObservable);
            coleccionCicloFormativo = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getCiclosFormativos());
            cicloFormativoObservable.setAll(coleccionCicloFormativo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void mostrarTablaAsignaturas() {
        try {
            columCodigoAsignatura.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            ColumNombreAsignatura.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columHorasAnuales.setCellValueFactory(new PropertyValueFactory<>("horasAnuales"));
            columCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
            columHorasDesdoble.setCellValueFactory(new PropertyValueFactory<>("horasDesdoble"));
            columEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidadProfesorado"));
            columCodigoCiclo.setCellValueFactory(new PropertyValueFactory<>("cicloFormativo"));

            tablAsignaturasMostrar.setItems(asignaturasObservable);
            coleccionAsignaturas = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getAsignaturas());
            asignaturasObservable.setAll(coleccionAsignaturas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void mostrarTablaMatriculas() {
        try {
            columIdMatricula.setCellValueFactory(new PropertyValueFactory<>("idMatricula"));
            columCursoAcademico.setCellValueFactory(new PropertyValueFactory<>("cursoAcademico"));
            columFechaMatriculacion.setCellValueFactory(new PropertyValueFactory<>("fechaMatriculacion"));
            columFechaAnulacion.setCellValueFactory(new PropertyValueFactory<>("fechaAnulacion"));
            columDniMatricula.setCellValueFactory(new PropertyValueFactory<>("alumno"));


            tablMatriculasMostrar.setItems(matriculasObservable);
            coleccionMatriculas = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getMatriculas());
            matriculasObservable.setAll(coleccionMatriculas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void muestraPersonaSeleccionada(Object newValue) {

    }

    private void mostrarFiltrado(){
        Alumno alumnoSeleccion = tablCentralBusquedasAlumno.getSelectionModel().getSelectedItem();


    }
    List<Alumno> obtenerListaAlumnos() {
        Alumnos alumnosBD = Alumnos.getInstancia();
        try {
            return alumnosBD.get();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void borrarAlumno()  {
        Alumno alumnoSeleccion = tablCentralBusquedasAlumno.getSelectionModel().getSelectedItem();
        if (alumnoSeleccion != null) {
            if (Dialogos.mostrarDialogoConfirmacion("Borrar Alumno", "¿Realmente quieres borrar el Alumno?")) {
                Alumnos.getInstancia().borrar(alumnoSeleccion);
                mostrarTablaAlumno();
            }
        }
    }

    public void borrarCiclo()  {
        CicloFormativo cicloFormativoSeleccion = tablCiclosFormativos.getSelectionModel().getSelectedItem();
        if (cicloFormativoSeleccion != null) {
            if (Dialogos.mostrarDialogoConfirmacion("Borrar Ciclo", "¿Realmente quieres borrar el Ciclo?")) {
                CiclosFormativos.getInstancia().borrar(cicloFormativoSeleccion);
                mostrarTablaCiclosFormativos();
            }
        }
    }
    public void borrarAsignatura()  {
        Asignatura asignaturaSeleccion = tablAsignaturasMostrar.getSelectionModel().getSelectedItem();
        if (asignaturaSeleccion != null) {
            if (Dialogos.mostrarDialogoConfirmacion("Borrar Asignatura", "¿Realmente quieres borrar la Asignatura?")) {
                Asignaturas.getInstancia().borrar(asignaturaSeleccion);
                mostrarTablaAsignaturas();
            }
        }
    }
    public void anularMatricula() throws OperationNotSupportedException {
        Matricula matriculaSeleccion = tablMatriculasMostrar.getSelectionModel().getSelectedItem();
        if (matriculaSeleccion != null) {
            if (Dialogos.mostrarDialogoConfirmacion("Anular Matricula", "¿Realmente quieres anular la matricula?")) {
                matriculaSeleccion.setFechaAnulacion(LocalDate.now());
                Matriculas.getInstancia().borrar(matriculaSeleccion);
                mostrarTablaMatriculas();
            }
        }
    }

    @FXML
    void buscarAlumno(ActionEvent event) {
        try {
            String alumnoBuscar = tfBusquedaAlumno.getText().toLowerCase();
            if (alumnoBuscar.isBlank()) {
                coleccionAlumnos = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getAlumnos());
                alumnosObservable.setAll(coleccionAlumnos);
            } else {
                List<Alumno> coleccionAlumnos = new ArrayList<>();

                for (Alumno alumno : VistaGrafica.getInstancia().getControlador().getAlumnos()) {
                    if (alumno.getNombre().toLowerCase().contains(alumnoBuscar)) {
                        coleccionAlumnos.add(alumno);
                    }
                }
                alumnosObservable.setAll(coleccionAlumnos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filtraAlumnos(String newValue) {
        FilteredList<Alumno> alumnosBusqueda = new FilteredList<>(alumnosObservable, alumno -> true);

        alumnosBusqueda.setPredicate(alumno -> {

            if (newValue.isBlank())
                return true;

            String alumnoFiltrado = newValue.toLowerCase();

            return alumno.getNombre().toLowerCase().contains(alumnoFiltrado);

        });

        tablCentralBusquedasAlumno.setItems(alumnosBusqueda);
    }
    @FXML
    void buscarCiclos(ActionEvent event) {
        try {
            String ciclosBuscar = buscarCiclos.getText().toLowerCase();
            if (ciclosBuscar.isBlank()) {
                coleccionCicloFormativo = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getCiclosFormativos());
                cicloFormativoObservable.setAll(coleccionCicloFormativo);
            } else {
                List<CicloFormativo> coleccionCicloFormativo = new ArrayList<>();

                for (CicloFormativo cicloFormativo : VistaGrafica.getInstancia().getControlador().getCiclosFormativos()) {
                    if (cicloFormativo.getNombre().toLowerCase().contains(ciclosBuscar)) {
                        coleccionCicloFormativo.add(cicloFormativo);
                    }
                }
                cicloFormativoObservable.setAll(coleccionCicloFormativo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filtraCiclos(String newValue) {
        FilteredList<CicloFormativo> ciclosBusqueda = new FilteredList<>(cicloFormativoObservable, cicloFormativo -> true);

        ciclosBusqueda.setPredicate(cicloFormativo -> {

            if (newValue.isBlank())
                return true;

            String cicloFiltrado = newValue.toLowerCase();

            return cicloFormativo.getNombre().toLowerCase().contains(cicloFiltrado);

        });

        tablCiclosFormativos.setItems(ciclosBusqueda);
    }


    @FXML
    public void buscarAsignaturas (ActionEvent event) {
        try{
            String asignaturaBuscar = tfbuscarAsignaturas.getText().toLowerCase();
            if (asignaturaBuscar.isBlank()) {
                coleccionAsignaturas = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getAsignaturas());
                asignaturasObservable.setAll(coleccionAsignaturas);
            } else {
                List<Asignatura> coleccionAsignaturas = new ArrayList<>();

                for (Asignatura asignatura : VistaGrafica.getInstancia().getControlador().getAsignaturas()) {
                    if (asignatura.getNombre().toLowerCase().contains(asignaturaBuscar)) {
                        coleccionAsignaturas.add(asignatura);
                    }
                }
                asignaturasObservable.setAll(coleccionAsignaturas);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filtraAsignaturas(String newValue) {
        FilteredList<Asignatura> asignaturasBusqueda = new FilteredList<>(asignaturasObservable, asignatura -> true);

        asignaturasBusqueda.setPredicate(asignatura-> {

            if (newValue.isBlank())
                return true;

            String asignaturaFiltrada = newValue.toLowerCase();

            return asignatura.getNombre().toLowerCase().contains(asignaturaFiltrada);

        });

        tablAsignaturasMostrar.setItems(asignaturasBusqueda);
    }

    @FXML
    void buscarMatricula(ActionEvent event) {
        try {
            String matriculaBuscar = buscarMatriculas.getText().toLowerCase();
            if (matriculaBuscar.isBlank()) {
                coleccionMatriculas = new ArrayList<>(VistaGrafica.getInstancia().getControlador().getMatriculas());
                matriculasObservable.setAll(coleccionMatriculas);
            } else {
                List<Matricula> coleccionMatriculas = new ArrayList<>();

                for (Matricula matricula : VistaGrafica.getInstancia().getControlador().getMatriculas()) {
                    if ((matricula.getIdMatricula() + "").toLowerCase().contains(matriculaBuscar)) {
                        coleccionMatriculas.add(matricula);
                    }
                }
                matriculasObservable.setAll(coleccionMatriculas);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void filtraMatriculas(String newValue) {
        FilteredList<Matricula> matriculasBusqueda = new FilteredList<>(matriculasObservable, matricula -> true);

        matriculasBusqueda.setPredicate(matricula-> {

            if (newValue.isBlank())
                return true;

            String matriculaFiltrada = newValue.toLowerCase();
            return (matricula.getIdMatricula()+ "").toLowerCase().contains(matriculaFiltrada);

        });

        tablMatriculasMostrar.setItems(matriculasBusqueda);
    }



    private void mostrarMatriculaAlumno() {
        try {
            Alumno alumnoSeleccionado = tablCentralBusquedasAlumno.getSelectionModel().getSelectedItem();

            List<Matricula> matriculasAlumno = VistaGrafica.getInstancia().getControlador().getMatriculas()
                    .stream()
                    .filter(m -> m.getAlumno().equals(alumnoSeleccionado))
                    .toList();

            ObservableList<Matricula> matriculasAlumnoObservable = FXCollections.observableArrayList(matriculasAlumno);
            tablFiltro.setItems(matriculasAlumnoObservable);

            nombreAlumno.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getAlumno().getNombre()));

            cicloFormativoAlumno.setCellValueFactory(nombreCiclo -> {
                List<Asignatura> asignaturas = nombreCiclo.getValue().getColeccionAsignaturas();
                String nombreCicloF = "Sin Ciclo Formativo";

                if (asignaturas != null && !asignaturas.isEmpty() && asignaturas.get(0).getCicloFormativo() != null) {
                    nombreCicloF = asignaturas.get(0).getCicloFormativo().getNombre();
                }

                return new SimpleStringProperty(nombreCicloF);
            });



            FechaMatriculacionAlumno.setCellValueFactory(celda -> {
                LocalDate fecha = celda.getValue().getFechaMatriculacion();
                return new SimpleStringProperty(
                        fecha != null ? fecha.format(DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA)) : "Sin fecha"
                );
            });

            FechaAnulacionAlumno.setCellValueFactory(celda -> {
                LocalDate fecha = celda.getValue().getFechaAnulacion();
                return new SimpleStringProperty(
                        fecha != null ? fecha.format(DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA)) : "No anulada"
                );
            });

            cursoAcademico.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getCursoAcademico()));

            asignaturasAlumno.setCellValueFactory(celda -> {
                List<Asignatura> listaAsignaturas = celda.getValue().getColeccionAsignaturas();
                String nombres = listaAsignaturas.stream()
                        .map(Asignatura::getNombre)
                        .collect(Collectors.joining(", "));
                return new SimpleStringProperty(nombres);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void mostrarMatriculaCiclos() {
        try {
            CicloFormativo cicloSeleccionado = tablCiclosFormativos.getSelectionModel().getSelectedItem();

            if (cicloSeleccionado == null) {
                return;
            }

            List<Matricula> matriculasCiclos = VistaGrafica.getInstancia().getControlador().getMatriculas()
                    .stream()
                    .filter(m -> m.getColeccionAsignaturas().stream()
                            .anyMatch(a -> a.getCicloFormativo().equals(cicloSeleccionado)))
                    .toList();

            ObservableList<Matricula> matriculasCicloObservable = FXCollections.observableArrayList(matriculasCiclos);
            tabFiltroCiclo.setItems(matriculasCicloObservable);

            cicloCodigo.setCellValueFactory(cCiclo -> {
                List<Asignatura> asignaturas = cCiclo.getValue().getColeccionAsignaturas();
                int codigo = (asignaturas != null && !asignaturas.isEmpty())
                        ? asignaturas.get(0).getCicloFormativo().getCodigo()
                        : 0;
                return new SimpleIntegerProperty(codigo).asObject();
            });
            cicloNombre.setCellValueFactory(nombreCiclo -> {
                List<Asignatura> lista = nombreCiclo.getValue().getColeccionAsignaturas();
                String nombres = lista.stream()
                        .map(Asignatura::getNombre)
                        .collect(Collectors.joining(", "));
                return new SimpleStringProperty(nombres);
            });

            cicloAsignaturas.setCellValueFactory(nombreAsignatura -> {
                List<Asignatura> asignaturas = nombreAsignatura.getValue().getColeccionAsignaturas();
                String nombre = (asignaturas != null && !asignaturas.isEmpty())
                        ? asignaturas.get(0).getCicloFormativo().getNombre()
                        : "Sin asignaturas";
                return new SimpleStringProperty(nombre);
            });



            cicloIdMatricula.setCellValueFactory(celda ->
                    new SimpleIntegerProperty(celda.getValue().getIdMatricula()).asObject());

            cicloCurso.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getCursoAcademico()));

            cicloAlumnos.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getAlumno().getNombre()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarMatriculaCursoAcademico() {
        try {
            Matricula cursoSeleccionado = tablMatriculasMostrar.getSelectionModel().getSelectedItem();
            if (cursoSeleccionado == null) {
                tablMatriculasMostrar.getItems().clear();
                return;
            }

            List<Matricula> matriculasFiltradas = VistaGrafica.getInstancia().getControlador().getMatriculas()
                    .stream()
                    .filter(m -> m.getCursoAcademico().equalsIgnoreCase(cursoSeleccionado.getCursoAcademico()))
                    .toList();

            ObservableList<Matricula> matriculasObservable = FXCollections.observableArrayList(matriculasFiltradas);
            tablFiltroCursoAcademico.setItems(matriculasObservable);

            matriculaAlumno.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getAlumno().getNombre()));

            matriculaDniAlumno.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getAlumno().getDni()));

            matriculaCursoAcademico.setCellValueFactory(celda ->
                    new SimpleStringProperty(celda.getValue().getCursoAcademico()));

            matriculaAsignaturas.setCellValueFactory(asignaturas -> {
                List<Asignatura> listaAsignaturas = asignaturas.getValue().getColeccionAsignaturas();
                StringBuilder nombres = new StringBuilder();
                for (int i = 0; i < listaAsignaturas.size(); i++) {
                    nombres.append(listaAsignaturas.get(i).getNombre());
                    if (i < listaAsignaturas.size() - 1) {
                        nombres.append(", ");
                    }
                }
                return new SimpleStringProperty(nombres.toString());
            });

            matriculaId.setCellValueFactory(celda ->
                    new SimpleIntegerProperty(celda.getValue().getIdMatricula()).asObject());

            matriculaMatriculacionFecha.setCellValueFactory(celda -> {
                LocalDate fecha = celda.getValue().getFechaMatriculacion();
                return new SimpleStringProperty(
                        fecha != null ? fecha.format(DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA)) : "Sin fecha"
                );
            });

            matriculaAnulacionFecha.setCellValueFactory(celda -> {
                LocalDate fecha = celda.getValue().getFechaAnulacion();
                return new SimpleStringProperty(
                        fecha != null ? fecha.format(DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA)) : "No anulada"
                );
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void mostrarTablas(){
    }



}









