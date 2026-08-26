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
import javafx.scene.layout.HBox;

/**
 * Controlador principal para la gestión de drones mediante la interfaz gráfica
 * desarrollada con JavaFX.
 *
 * <p>
 * Esta clase permite realizar las operaciones CRUD sobre los drones,
 * incluyendo la creación, consulta, actualización y eliminación.
 * </p>
 *
 * <p>
 * También permite seleccionar entre drones de tipo Agricultura y Vigilancia,
 * mostrando los campos específicos correspondientes a cada tipo.
 * </p>
 *
 * <p>
 * Para la creación de los diferentes tipos de drones se utiliza el patrón
 * Factory mediante las clases {@link crearDronAgricultura} y
 * {@link crearDronVigilancia}.
 * </p>
 */
public class DronController {

	// =========================================================
	// CAMPOS GENERALES
	// =========================================================

	/**
	 * Campo de texto utilizado para mostrar o ingresar el identificador del dron.
	 */
	@FXML
	private TextField txtId;

	/**
	 * Campo de texto utilizado para ingresar el serial del dron.
	 */
	@FXML
	private TextField txtSerial;

	/**
	 * Campo de texto utilizado para ingresar el modelo del dron.
	 */
	@FXML
	private TextField txtModelo;

	/**
	 * Campo de texto utilizado para ingresar el peso del dron.
	 */
	@FXML
	private TextField txtPeso;

	// =========================================================
	// TIPO DE DRON
	// =========================================================

	/**
	 * ChoiceBox utilizado para seleccionar el tipo de dron.
	 */
	@FXML
	private ChoiceBox<String> cbTipo;

	// =========================================================
	// AGRICULTURA
	// =========================================================

	/**
	 * Contenedor que agrupa los controles relacionados con la capacidad
	 * del tanque de los drones de agricultura.
	 */
	@FXML
	private HBox hbCapacidadTanque;

	/**
	 * Campo de texto utilizado para ingresar la capacidad del tanque
	 * de un dron de agricultura.
	 */
	@FXML
	private TextField txtCapacidadTanque;

	// =========================================================
	// VIGILANCIA
	// =========================================================

	/**
	 * CheckBox utilizado para indicar si el dron de vigilancia cuenta
	 * con detección térmica.
	 */
	@FXML
	private CheckBox cbDeteccionTermica;

	// =========================================================
	// TABLA
	// =========================================================

	/**
	 * Tabla utilizada para mostrar los drones registrados.
	 */
	@FXML
	private TableView<Dron> tblDrones;

	/**
	 * Columna que muestra el identificador del dron.
	 */
	@FXML
	private TableColumn<Dron, Integer> colId;

	/**
	 * Columna que muestra el serial del dron.
	 */
	@FXML
	private TableColumn<Dron, String> colSenal;

	/**
	 * Columna que muestra el modelo del dron.
	 */
	@FXML
	private TableColumn<Dron, String> colModelo;

	/**
	 * Columna que muestra el peso del dron.
	 */
	@FXML
	private TableColumn<Dron, Double> colPeso;

	// =========================================================
	// DAO
	// =========================================================

	/**
	 * Objeto DAO utilizado para realizar las operaciones CRUD
	 * relacionadas con los drones.
	 */
	private final DronDAO dronDAO = new DronDAOImplementado();

	// =========================================================
	// INICIALIZAR
	// =========================================================

	/**
	 * Inicializa el controlador una vez que la interfaz FXML ha sido cargada.
	 *
	 * <p>
	 * Configura la tabla, carga los drones desde la base de datos,
	 * establece el comportamiento de selección de la tabla y configura
	 * los eventos relacionados con el tipo de dron seleccionado.
	 * </p>
	 */
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

		// Estado inicial
		ocultarTermica();
		ocultarCapacidad();

		// Escuchar cambios en el tipo de dron
		cbTipo.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, anterior, nuevo) -> {

					if ("Vigilancia".equals(nuevo)) {

						mostrarTermica();
						ocultarCapacidad();

					} else if ("Agricultura".equals(nuevo)) {

						ocultarTermica();
						mostrarCapacidad();

					} else {

						ocultarTermica();
						ocultarCapacidad();
					}
				});
	}

	// =========================================================
	// OCULTAR Y MOSTRAR CAMPOS ESPECÍFICOS
	// =========================================================

	/**
	 * Muestra el campo utilizado para indicar la detección térmica
	 * de los drones de vigilancia.
	 */
	private void mostrarTermica() {

		cbDeteccionTermica.setVisible(true);
		cbDeteccionTermica.setManaged(true);
	}

	/**
	 * Oculta el campo de detección térmica y desmarca su selección.
	 */
	private void ocultarTermica() {

		cbDeteccionTermica.setVisible(false);
		cbDeteccionTermica.setManaged(false);

		cbDeteccionTermica.setSelected(false);
	}

	/**
	 * Muestra los controles utilizados para ingresar la capacidad
	 * del tanque de los drones de agricultura.
	 */
	private void mostrarCapacidad() {

		hbCapacidadTanque.setVisible(true);
		hbCapacidadTanque.setManaged(true);
	}

	/**
	 * Oculta los controles de capacidad del tanque y limpia su contenido.
	 */
	private void ocultarCapacidad() {

		hbCapacidadTanque.setVisible(false);
		hbCapacidadTanque.setManaged(false);

		txtCapacidadTanque.clear();
	}

	// =========================================================
	// CONFIGURAR TABLA
	// =========================================================

	/**
	 * Configura las columnas de la tabla para asociarlas con las propiedades
	 * correspondientes de la clase {@link Dron}.
	 */
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

	/**
	 * Obtiene todos los drones registrados mediante el DAO y los carga
	 * en la tabla de la interfaz.
	 */
	private void cargarDrones() {

		List<Dron> drones = dronDAO.listar();

		tblDrones.setItems(
				FXCollections.observableArrayList(
						drones
				)
		);
	}

	// =========================================================
	// MOSTRAR DRON SELECCIONADO EN EL FORMULARIO
	// =========================================================

	/**
	 * Muestra en el formulario los datos del dron seleccionado en la tabla.
	 *
	 * <p>
	 * Dependiendo de si el dron es una instancia de {@link Agricultura}
	 * o {@link Vigilancia}, también muestra sus atributos específicos.
	 * </p>
	 *
	 * @param dron dron seleccionado en la tabla
	 */
	private void mostrarDronSeleccionado(Dron dron) {

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

	/**
	 * Limpia los campos del formulario y elimina la selección actual
	 * de la tabla para permitir el ingreso de un nuevo dron.
	 */
	@FXML
	private void nuevoDron() {

		limpiarCampos();

		tblDrones.getSelectionModel()
				.clearSelection();
	}

	// =========================================================
	// GUARDAR
	// =========================================================

	/**
	 * Guarda un nuevo dron en la base de datos.
	 *
	 * <p>
	 * El tipo de dron seleccionado determina qué fábrica se utiliza
	 * para crear la instancia correspondiente. Posteriormente se
	 * asignan sus datos generales y específicos antes de almacenarlo
	 * mediante el DAO.
	 * </p>
	 *
	 * <p>
	 * También realiza validaciones sobre los campos obligatorios y
	 * controla posibles errores al convertir valores numéricos.
	 * </p>
	 */
	@FXML
	private void guardarDron() {

		try {

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

				dron.setSerial(serial);
				dron.setModelo(modelo);
				dron.setPeso(peso);

				Agricultura agricultura =
						(Agricultura) dron;

				agricultura.setCapacidadTanque(
						capacidadTanque
				);

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

				dron.setSerial(serial);
				dron.setModelo(modelo);
				dron.setPeso(peso);

				Vigilancia vigilancia =
						(Vigilancia) dron;

				vigilancia.setDeteccionTermica(
						cbDeteccionTermica.isSelected()
				);

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

	/**
	 * Actualiza los datos de un dron existente en la base de datos.
	 *
	 * <p>
	 * El dron debe estar previamente seleccionado en la tabla. El método
	 * utiliza la fábrica correspondiente según el tipo seleccionado y
	 * conserva el identificador del dron para realizar la actualización.
	 * </p>
	 *
	 * <p>
	 * También valida los campos obligatorios y controla errores relacionados
	 * con valores numéricos inválidos.
	 * </p>
	 */
	@FXML
	private void editarDron() {

		try {

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

				factoriaDrones factoria =
						new crearDronAgricultura();

				dron =
						factoria.crearDrone();

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

				factoriaDrones factoria =
						new crearDronVigilancia();

				dron =
						factoria.crearDrone();

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

	/**
	 * Elimina un dron de la base de datos utilizando el identificador
	 * ingresado o seleccionado en el formulario.
	 *
	 * <p>
	 * Antes de realizar la eliminación se verifica que exista un ID válido.
	 * </p>
	 */
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
	// BUILDER
	// =========================================================
	@FXML
	private void builderdron() {
		
		
		
	}
	
	
	// =========================================================
	// PROTOTYPE
	// =========================================================
	@FXML
	private void clonardron() {
		
		
		
	}
	

	// =========================================================
	// VALIDAR CAMPOS GENERALES
	// =========================================================

	/**
	 * Valida que los campos generales del formulario contengan información.
	 *
	 * @return {@code true} si todos los campos obligatorios están diligenciados;
	 *         {@code false} en caso contrario
	 */
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

	/**
	 * Limpia todos los campos del formulario y restablece los controles
	 * a su estado inicial.
	 */
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

	/**
	 * Muestra una ventana de alerta al usuario.
	 *
	 * @param tipo tipo de alerta que se mostrará
	 * @param titulo título de la ventana de alerta
	 * @param mensaje mensaje que se mostrará al usuario
	 */
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
