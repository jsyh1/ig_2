package co.edu.poli.sw2.controlador;

import java.util.List;

import co.edu.poli.servicios.crearDronAgricultura;
import co.edu.poli.servicios.crearDronVigilancia;
import co.edu.poli.servicios.factoriaDrones;

import co.edu.poli.sw2.dao.DronDAO;
import co.edu.poli.sw2.dao.DronDAOImplementado;

import co.edu.poli.sw2.modelo.Agricultura;
import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

import javafx.collections.FXCollections;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;


public class DronController {


    // =========================================================
    // CAMPOS GENERALES
    // =========================================================

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSerial;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPeso;


    // =========================================================
    // TIPO DE DRON
    // =========================================================

    @FXML
    private ChoiceBox<String> cbTipo;


    // =========================================================
    // AGRICULTURA
    // =========================================================

    @FXML
    private TextField txtCapacidadTanque;


    // =========================================================
    // VIGILANCIA
    // =========================================================

    @FXML
    private CheckBox cbDeteccionTermica;


    // =========================================================
    // TABLA
    // =========================================================

    @FXML
    private TableView<Dron> tblDrones;

    @FXML
    private TableColumn<Dron, Integer> colId;

    @FXML
    private TableColumn<Dron, String> colSenal;

    @FXML
    private TableColumn<Dron, String> colModelo;

    @FXML
    private TableColumn<Dron, Double> colPeso;


    // =========================================================
    // DAO
    // =========================================================

    private final DronDAO dronDAO =
            new DronDAOImplementado();


    // =========================================================
    // INICIALIZAR
    // =========================================================

    @FXML
    public void initialize() {

        configurarTabla();

        cargarDrones();

        // Cuando se selecciona un drone en la tabla
        tblDrones.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, seleccionado) -> {

                            if (seleccionado != null) {

                                mostrarDronSeleccionado(
                                        seleccionado
                                );
                            }
                        }
                );
    }


    // =========================================================
    // CONFIGURAR TABLA
    // =========================================================

    private void configurarTabla() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colSenal.setCellValueFactory(
                new PropertyValueFactory<>("serial")
        );

        colModelo.setCellValueFactory(
                new PropertyValueFactory<>("modelo")
        );

        colPeso.setCellValueFactory(
                new PropertyValueFactory<>("peso")
        );
    }


    // =========================================================
    // CARGAR DRONES DESDE LA BASE DE DATOS
    // =========================================================

    private void cargarDrones() {

        List<Dron> drones =
                dronDAO.listar();

        tblDrones.setItems(
                FXCollections.observableArrayList(
                        drones
                )
        );
    }


    // =========================================================
    // MOSTRAR DRON SELECCIONADO EN EL FORMULARIO
    // =========================================================

    private void mostrarDronSeleccionado(
            Dron dron
    ) {

        // Datos generales

        txtId.setText(
                String.valueOf(
                        dron.getId()
                )
        );

        txtSerial.setText(
                dron.getSerial()
        );

        txtModelo.setText(
                dron.getModelo()
        );

        txtPeso.setText(
                String.valueOf(
                        dron.getPeso()
                )
        );


        // =====================================================
        // SI ES AGRICULTURA
        // =====================================================

        if (dron instanceof Agricultura) {

            Agricultura agricultura =
                    (Agricultura) dron;

            cbTipo.setValue(
                    "Agricultura"
            );

            txtCapacidadTanque.setText(
                    String.valueOf(
                            agricultura.getCapacidadTanque()
                    )
            );

            cbDeteccionTermica.setSelected(
                    false
            );
        }


        // =====================================================
        // SI ES VIGILANCIA
        // =====================================================

        else if (dron instanceof Vigilancia) {

            Vigilancia vigilancia =
                    (Vigilancia) dron;

            cbTipo.setValue(
                    "Vigilancia"
            );

            cbDeteccionTermica.setSelected(
                    vigilancia.isDeteccionTermica()
            );

            txtCapacidadTanque.clear();
        }
    }


    // =========================================================
    // NUEVO
    // =========================================================

    @FXML
    private void nuevoDron() {

        limpiarCampos();

        tblDrones.getSelectionModel()
                .clearSelection();
    }


    // =========================================================
    // GUARDAR
    // =========================================================

    @FXML
    private void guardarDron() {

        try {

            // =============================================
            // VALIDAR CAMPOS GENERALES
            // =============================================

            if (!validarCamposGenerales()) {
                return;
            }


            String serial =
                    txtSerial.getText().trim();

            String modelo =
                    txtModelo.getText().trim();

            double peso =
                    Double.parseDouble(
                            txtPeso.getText().trim()
                    );

            String tipo =
                    cbTipo.getValue();


            // =============================================
            // VALIDAR TIPO
            // =============================================

            if (tipo == null ||
                    tipo.equals("Seleccionar")) {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error",
                        "Debe seleccionar un tipo de dron."
                );

                return;
            }


            Dron dron;


            // =================================================
            // AGRICULTURA
            // =================================================

            if (tipo.equals("Agricultura")) {

                if (txtCapacidadTanque
                        .getText()
                        .trim()
                        .isEmpty()) {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Debe ingresar la capacidad del tanque."
                    );

                    return;
                }


                double capacidadTanque =
                        Double.parseDouble(
                                txtCapacidadTanque
                                        .getText()
                                        .trim()
                        );


                // Crear mediante Factory

                factoriaDrones factoria =
                        new crearDronAgricultura();

                dron =
                        factoria.crearDrone();


                // Datos generales

                dron.setSerial(serial);

                dron.setModelo(modelo);

                dron.setPeso(peso);


                // Dato específico

                Agricultura agricultura =
                        (Agricultura) dron;

                agricultura.setCapacidadTanque(
                        capacidadTanque
                );


                // Guardar

                boolean creado =
                        dronDAO.crear(
                                agricultura
                        );


                if (creado) {

                    mostrarAlerta(
                            Alert.AlertType.INFORMATION,
                            "Éxito",
                            "Dron de Agricultura guardado correctamente."
                    );

                    cargarDrones();

                    limpiarCampos();

                } else {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "No fue posible guardar el dron."
                    );
                }
            }


            // =================================================
            // VIGILANCIA
            // =================================================

            else if (tipo.equals("Vigilancia")) {

                // Crear mediante Factory

                factoriaDrones factoria =
                        new crearDronVigilancia();

                dron =
                        factoria.crearDrone();


                // Datos generales

                dron.setSerial(serial);

                dron.setModelo(modelo);

                dron.setPeso(peso);


                // Dato específico

                Vigilancia vigilancia =
                        (Vigilancia) dron;

                vigilancia.setDeteccionTermica(
                        cbDeteccionTermica.isSelected()
                );


                // Guardar

                boolean creado =
                        dronDAO.crear(
                                vigilancia
                        );


                if (creado) {

                    mostrarAlerta(
                            Alert.AlertType.INFORMATION,
                            "Éxito",
                            "Dron de Vigilancia guardado correctamente."
                    );

                    cargarDrones();

                    limpiarCampos();

                } else {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "No fue posible guardar el dron."
                    );
                }
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "El peso y la capacidad del tanque deben ser números válidos."
            );
        }
    }


    // =========================================================
    // EDITAR
    // =========================================================

    @FXML
    private void editarDron() {

        try {

            // =============================================
            // VALIDAR ID
            // =============================================

            if (txtId.getText().trim().isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error",
                        "Seleccione un dron de la tabla para editar."
                );

                return;
            }


            if (!validarCamposGenerales()) {
                return;
            }


            int id =
                    Integer.parseInt(
                            txtId.getText().trim()
                    );

            String serial =
                    txtSerial.getText().trim();

            String modelo =
                    txtModelo.getText().trim();

            double peso =
                    Double.parseDouble(
                            txtPeso.getText().trim()
                    );

            String tipo =
                    cbTipo.getValue();


            if (tipo == null ||
                    tipo.equals("Seleccionar")) {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error",
                        "Debe seleccionar un tipo de dron."
                );

                return;
            }


            Dron dron;


            // =================================================
            // EDITAR AGRICULTURA
            // =================================================

            if (tipo.equals("Agricultura")) {

                if (txtCapacidadTanque
                        .getText()
                        .trim()
                        .isEmpty()) {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Debe ingresar la capacidad del tanque."
                    );

                    return;
                }


                double capacidadTanque =
                        Double.parseDouble(
                                txtCapacidadTanque
                                        .getText()
                                        .trim()
                        );


                // Crear objeto Agricultura mediante Factory

                factoriaDrones factoria =
                        new crearDronAgricultura();

                dron =
                        factoria.crearDrone();


                // ID importante para UPDATE

                dron.setId(id);

                dron.setSerial(serial);

                dron.setModelo(modelo);

                dron.setPeso(peso);


                Agricultura agricultura =
                        (Agricultura) dron;

                agricultura.setCapacidadTanque(
                        capacidadTanque
                );


                boolean actualizado =
                        dronDAO.actualizar(
                                agricultura
                        );


                if (actualizado) {

                    mostrarAlerta(
                            Alert.AlertType.INFORMATION,
                            "Éxito",
                            "Dron de Agricultura actualizado correctamente."
                    );

                    cargarDrones();

                    limpiarCampos();

                } else {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "No fue posible actualizar el dron."
                    );
                }
            }


            // =================================================
            // EDITAR VIGILANCIA
            // =================================================

            else if (tipo.equals("Vigilancia")) {

                // Crear objeto Vigilancia mediante Factory

                factoriaDrones factoria =
                        new crearDronVigilancia();

                dron =
                        factoria.crearDrone();


                // ID importante para UPDATE

                dron.setId(id);

                dron.setSerial(serial);

                dron.setModelo(modelo);

                dron.setPeso(peso);


                Vigilancia vigilancia =
                        (Vigilancia) dron;

                vigilancia.setDeteccionTermica(
                        cbDeteccionTermica.isSelected()
                );


                boolean actualizado =
                        dronDAO.actualizar(
                                vigilancia
                        );


                if (actualizado) {

                    mostrarAlerta(
                            Alert.AlertType.INFORMATION,
                            "Éxito",
                            "Dron de Vigilancia actualizado correctamente."
                    );

                    cargarDrones();

                    limpiarCampos();

                } else {

                    mostrarAlerta(
                            Alert.AlertType.ERROR,
                            "Error",
                            "No fue posible actualizar el dron."
                    );
                }
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "El ID, peso y capacidad del tanque deben ser números válidos."
            );
        }
    }


    // =========================================================
    // ELIMINAR
    // =========================================================

    @FXML
    private void eliminarDron() {

        if (txtId.getText().trim().isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Seleccione un dron de la tabla para eliminar."
            );

            return;
        }


        try {

            int id =
                    Integer.parseInt(
                            txtId.getText().trim()
                    );


            boolean eliminado =
                    dronDAO.eliminar(id);


            if (eliminado) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Éxito",
                        "Dron eliminado correctamente."
                );

                cargarDrones();

                limpiarCampos();

            } else {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error",
                        "No fue posible eliminar el dron."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "El ID no es válido."
            );
        }
    }


    // =========================================================
    // VALIDAR CAMPOS GENERALES
    // =========================================================

    private boolean validarCamposGenerales() {

        if (txtSerial.getText().trim().isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Debe ingresar el serial."
            );

            return false;
        }


        if (txtModelo.getText().trim().isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Debe ingresar el modelo."
            );

            return false;
        }


        if (txtPeso.getText().trim().isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Debe ingresar el peso."
            );

            return false;
        }


        return true;
    }


    // =========================================================
    // LIMPIAR CAMPOS
    // =========================================================

    private void limpiarCampos() {

        txtId.clear();

        txtSerial.clear();

        txtModelo.clear();

        txtPeso.clear();

        txtCapacidadTanque.clear();

        cbTipo.setValue(
                "Seleccionar"
        );

        cbDeteccionTermica.setSelected(
                false
        );

        tblDrones.getSelectionModel()
                .clearSelection();
    }


    // =========================================================
    // MOSTRAR ALERTAS
    // =========================================================

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje
    ) {

        Alert alerta =
                new Alert(tipo);

        alerta.setTitle(titulo);

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}