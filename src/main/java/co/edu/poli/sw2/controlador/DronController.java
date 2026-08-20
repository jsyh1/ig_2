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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de la interfaz gráfica para la gestión de drones.
 *
 * <p>
 * Se encarga de recibir los datos de la vista y comunicarse con {@link DronDAO}
 * para realizar las operaciones CRUD.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class DronController {

	/**
	 * DAO utilizado para acceder a los datos de los drones.
	 */
	private final DronDAO dronDAO = new DronDAOImplementado();

	// =========================
	// CAMPOS DEL FORMULARIO
	// =========================

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtSerial;

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
	
	
	
	@FXML
	private CheckBox cbDeteccionTermica;
	
	@FXML
	private TextField txtCapacidadTanque;

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

	@FXML
	private ChoiceBox<String> cbTipo;

	// =========================
	// DATOS
	// =========================

	/**
	 * Lista observable utilizada para mostrar los drones en la tabla de JavaFX.
	 */
	private final ObservableList<Dron> listaDrones = FXCollections.observableArrayList();

	/**
	 * Drone seleccionado actualmente en la tabla.
	 */
	private Dron dronSeleccionado;

	// =========================
	// INICIALIZACIÓN
	// =========================

	/**
	 * Inicializa el controlador.
	 *
	 * <p>
	 * Configura las columnas, los eventos de la tabla, establece la lista de drones
	 * y carga los datos almacenados en la base de datos.
	 * </p>
	 */
	@FXML
	public void initialize() {

		configurarColumnas();

		tblDrones.setItems(listaDrones);

		configurarEventos();

		cargarDrones();

		limpiarFormulario();
	}

	// =========================
	// CONFIGURACIÓN
	// =========================

	/**
	 * Configura las columnas de la tabla con los atributos correspondientes de la
	 * clase {@link Dron}.
	 */
	private void configurarColumnas() {

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));

		colSenal.setCellValueFactory(new PropertyValueFactory<>("senal"));

		colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

		colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
	}

	/**
	 * Configura los eventos de selección de la tabla.
	 */
	private void configurarEventos() {

		tblDrones.getSelectionModel().selectedItemProperty().addListener((observable, anterior, seleccionado) -> {

			if (seleccionado != null) {

				dronSeleccionado = seleccionado;

				cargarDronEnFormulario(seleccionado);
			}
		});
	}

	// =========================
	// CREATE
	// =========================

	/**
	 * Guarda un nuevo drone en la base de datos.
	 *
	 * <p>
	 * Obtiene los datos del formulario, crea un objeto {@link Dron} y lo envía al
	 * DAO para realizar la inserción en la base de datos.
	 * </p>
	 */
	@FXML
	private void guardarDron() {

		if (!validarCampos()) {
			return;
		}

		try {

			int id = Integer.parseInt(txtId.getText());

			String serial = txtSerial.getText();

			String modelo = txtModelo.getText();

			double peso = Double.parseDouble(txtPeso.getText());

			String tipo = cbTipo.getValue();

			double capacidadTanque = Double.parseDouble(txtCapacidadTanque.getText());
			
			boolean deteccionTermica = cbDeteccionTermica.isSelected();
			if (tipo.equals("Agricultura")) {

				// 2. Crear la Factory de Agricultura

				factoriaDrones factoria = new crearDronAgricultura();

				// 3. La Factory crea el objeto Agricultura

				Dron dron = factoria.crearDrone();

				// 4. Llenar los datos que hereda de Dron

				dron.setId(id);

				dron.setSerial(serial);

				dron.setModelo(modelo);

				dron.setPeso(peso);

				// 5. Convertirlo a Agricultura para acceder
				// al atributo específico

				Agricultura agricultura = (Agricultura) dron;

				agricultura.setCapacidadTanque(capacidadTanque);
				if (dronDAO.crear(dron)) {

					listaDrones.add(dron);

					limpiarFormulario();

					mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Drone registrado correctamente.");

				} else {

					mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el drone.");
				}

			} else if (tipo.equals("Vigilancia")) {
				
				// 2. Crear la Factory de Agricultura

				factoriaDrones factoria = new crearDronVigilancia();
				
				// 3. La Factory crea el objeto Agricultura

				Dron dron = factoria.crearDrone();

				// 4. Llenar los datos que hereda de Dron

				dron.setId(id);

				dron.setSerial(serial);

				dron.setModelo(modelo);

				dron.setPeso(peso);
				
				Vigilancia vigilancia = (Vigilancia) dron;
				
				if (dronDAO.crear(dron)) {

					listaDrones.add(dron);

					limpiarFormulario();

					mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Drone registrado correctamente.");

				} else {

					mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el drone.");
				}

			}
			

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos", "ID, señal y peso deben ser valores numéricos.");
		}
	}
	// =========================
	// READ
	// =========================

	/**
	 * Carga todos los drones almacenados en la base de datos y los muestra en la
	 * tabla.
	 */
	private void cargarDrones() {

		listaDrones.clear();

		List<Dron> drones = dronDAO.listar();

		if (drones != null) {
			listaDrones.addAll(drones);
		}
	}

	/**
	 * Busca un drone por su identificador.
	 *
	 * @param id identificador del drone que se desea buscar
	 */
	private void buscarDron(int id) {

		Dron dron = dronDAO.buscarPorId(id);

		if (dron != null) {

			cargarDronEnFormulario(dron);

		} else {

			mostrarAlerta(Alert.AlertType.INFORMATION, "Búsqueda", "No se encontró un drone con el ID " + id);
		}
	}

	// =========================
	// UPDATE
	// =========================

	/**
	 * Actualiza los datos del drone seleccionado.
	 *
	 * <p>
	 * Obtiene los nuevos valores del formulario, modifica el objeto seleccionado y
	 * solicita al DAO actualizar los datos en la base de datos.
	 * </p>
	 */
	@FXML
	private void editarDron() {

		if (dronSeleccionado == null) {

			mostrarAlerta(Alert.AlertType.WARNING, "Editar drone", "Selecciona un drone de la tabla.");

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

			dronSeleccionado.setId(id);
			dronSeleccionado.setSenal(senal);
			dronSeleccionado.setModelo(modelo);
			dronSeleccionado.setPeso(peso);

			if (dronDAO.actualizar(dronSeleccionado)) {

				tblDrones.refresh();

				limpiarFormulario();

				dronSeleccionado = null;

				mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Drone actualizado correctamente.");

			} else {

				mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el drone.");
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos", "ID, señal y peso deben ser valores numéricos.");
		}
	}

	// =========================
	// DELETE
	// =========================

	/**
	 * Elimina de la base de datos el drone seleccionado.
	 */
	@FXML
	private void eliminarDron() {

		if (dronSeleccionado == null) {

			mostrarAlerta(Alert.AlertType.WARNING, "Eliminar drone", "Selecciona un drone de la tabla.");

			return;
		}

		int id = dronSeleccionado.getId();

		if (dronDAO.eliminar(id)) {

			listaDrones.remove(dronSeleccionado);

			dronSeleccionado = null;

			limpiarFormulario();

			mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Drone eliminado correctamente.");

		} else {

			mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el drone.");
		}
	}

	// =========================
	// NUEVO
	// =========================

	/**
	 * Prepara el formulario para registrar un nuevo drone.
	 */
	@FXML
	private void nuevoDron() {

		limpiarFormulario();

		dronSeleccionado = null;

		txtId.requestFocus();
	}

	// =========================
	// FORMULARIO
	// =========================

	/**
	 * Carga los datos de un drone en los campos del formulario.
	 *
	 * @param dron drone cuyos datos serán mostrados
	 */
	private void cargarDronEnFormulario(Dron dron) {

		txtId.setText(String.valueOf(dron.getId()));

		txtSenal.setText(String.valueOf(dron.getSenal()));

		txtModelo.setText(dron.getModelo());

		txtPeso.setText(String.valueOf(dron.getPeso()));
	}

	/**
	 * Limpia todos los campos del formulario.
	 */
	private void limpiarFormulario() {

		txtId.clear();
		txtSenal.clear();
		txtModelo.clear();
		txtPeso.clear();

		tblDrones.getSelectionModel().clearSelection();
	}

	/**
	 * Verifica que todos los campos obligatorios tengan datos.
	 *
	 * @return true si los campos están completos, false en caso contrario
	 */
	private boolean validarCampos() {

		if (txtId.getText().isBlank() || txtSenal.getText().isBlank() || txtModelo.getText().isBlank()
				|| txtPeso.getText().isBlank()) {

			mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Todos los campos son obligatorios.");

			return false;
		}

		return true;
	}

	// =========================
	// ALERTAS
	// =========================

	/**
	 * Muestra una ventana de alerta al usuario.
	 *
	 * @param tipo    tipo de alerta
	 * @param titulo  título de la ventana
	 * @param mensaje mensaje mostrado al usuario
	 */
	private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {

		Alert alerta = new Alert(tipo);

		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);

		alerta.showAndWait();
	}
}