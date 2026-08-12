package co.edu.poli.sw2.controlador;

import co.edu.poli.sw2.modelo.Dron;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DronController {

    // =========================
    // CAMPOS DEL FORMULARIO
    // =========================

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSenal;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPeso;


    // =========================
    // TABLA
    // =========================

    @FXML
    private TableView<Dron> tblDrones;

    @FXML
    private TableColumn<Dron, Integer> colId;

    @FXML
    private TableColumn<Dron, Double> colSenal;

    @FXML
    private TableColumn<Dron, String> colModelo;

    @FXML
    private TableColumn<Dron, Double> colPeso;


    // =========================
    // BOTONES
    // =========================

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;


    // =========================
    // LISTA DE DRONES
    // =========================

    private final ObservableList<Dron> listaDrones =
            FXCollections.observableArrayList();

    private Dron dronSeleccionado;


    // =========================
    // INICIALIZACIÓN
    // =========================

    @FXML
    public void initialize() {

        configurarColumnas();

        tblDrones.setItems(listaDrones);

        configurarEventos();

        limpiarFormulario();
    }


    // =========================
    // CONFIGURAR COLUMNAS
    // =========================

    private void configurarColumnas() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colSenal.setCellValueFactory(
                new PropertyValueFactory<>("senal")
        );

        colModelo.setCellValueFactory(
                new PropertyValueFactory<>("modelo")
        );

        colPeso.setCellValueFactory(
                new PropertyValueFactory<>("peso")
        );
    }


    // =========================
    // EVENTOS
    // =========================

    private void configurarEventos() {

        // Cuando seleccionamos un drone de la tabla
        tblDrones.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {

                        dronSeleccionado = seleccionado;

                        cargarDronEnFormulario(seleccionado);
                    }
                });
    }


    // =========================
    // NUEVO
    // =========================

    @FXML
    private void nuevoDron() {

        limpiarFormulario();

        dronSeleccionado = null;

        txtId.requestFocus();
    }


    // =========================
    // GUARDAR
    // =========================

    @FXML
    private void guardarDron() {

        if (!validarCampos()) {
            return;
        }

        try {

            int id = Integer.parseInt(txtId.getText());

            double senal = Double.parseDouble(txtSenal.getText());

            String modelo = txtModelo.getText();

            double peso = Double.parseDouble(txtPeso.getText());


            // Verificar si ya existe un drone con ese ID

            for (Dron dron : listaDrones) {

                if (dron.getId() == id) {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Ya existe un drone con el ID " + id
                    );

                    return;
                }
            }


            // Crear nuevo drone

            Dron nuevoDron = new Dron(
                    id,
                    senal,
                    modelo,
                    peso
            );


            listaDrones.add(nuevoDron);

            limpiarFormulario();

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Éxito",
                    "Drone registrado correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Datos inválidos",
                    "ID, señal y peso deben ser valores numéricos."
            );
        }
    }


    // =========================
    // EDITAR
    // =========================

    @FXML
    private void editarDron() {

        if (dronSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Editar drone",
                    "Selecciona un drone de la tabla."
            );

            return;
        }


        if (!validarCampos()) {
            return;
        }


        try {

            int id = Integer.parseInt(txtId.getText());

            double senal = Double.parseDouble(txtSenal.getText());

            String modelo = txtModelo.getText();

            double peso = Double.parseDouble(txtPeso.getText());


            // Si se cambia el ID, verificar que no exista

            for (Dron dron : listaDrones) {

                if (dron != dronSeleccionado &&
                        dron.getId() == id) {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Ya existe otro drone con ese ID."
                    );

                    return;
                }
            }


            // Actualizar el objeto

            dronSeleccionado.setId(id);
            dronSeleccionado.setSenal(senal);
            dronSeleccionado.setModelo(modelo);
            dronSeleccionado.setPeso(peso);


            tblDrones.refresh();

            limpiarFormulario();

            dronSeleccionado = null;


            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Éxito",
                    "Drone actualizado correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Datos inválidos",
                    "ID, señal y peso deben ser valores numéricos."
            );
        }
    }


    // =========================
    // ELIMINAR
    // =========================

    @FXML
    private void eliminarDron() {

        if (dronSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Eliminar drone",
                    "Selecciona un drone de la tabla."
            );

            return;
        }


        listaDrones.remove(dronSeleccionado);

        dronSeleccionado = null;

        limpiarFormulario();


        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Éxito",
                "Drone eliminado correctamente."
        );
    }


    // =========================
    // CARGAR DATOS
    // =========================

    private void cargarDronEnFormulario(Dron dron) {

        txtId.setText(
                String.valueOf(dron.getId())
        );

        txtSenal.setText(
                String.valueOf(dron.getSenal())
        );

        txtModelo.setText(
                dron.getModelo()
        );

        txtPeso.setText(
                String.valueOf(dron.getPeso())
        );
    }


    // =========================
    // LIMPIAR FORMULARIO
    // =========================

    private void limpiarFormulario() {

        txtId.clear();
        txtSenal.clear();
        txtModelo.clear();
        txtPeso.clear();

        tblDrones.getSelectionModel().clearSelection();
    }


    // =========================
    // VALIDAR CAMPOS
    // =========================

    private boolean validarCampos() {

        if (txtId.getText().isBlank() ||
                txtSenal.getText().isBlank() ||
                txtModelo.getText().isBlank() ||
                txtPeso.getText().isBlank()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Todos los campos son obligatorios."
            );

            return false;
        }

        return true;
    }


    // =========================
    // ALERTAS
    // =========================

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}
