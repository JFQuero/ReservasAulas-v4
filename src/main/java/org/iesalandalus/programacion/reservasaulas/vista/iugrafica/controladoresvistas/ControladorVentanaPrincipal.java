package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.*;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControladorVentanaPrincipal {

	private IControladorReservasAulas controladorMVC;
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private Image logo = new Image(getClass().getResourceAsStream("../imagenes/ReservaAulasJFQuero2.png"), 130, 80, true, true);
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private Stage anadirReserva;
	private Stage anadirProfesor;
	private Stage anadirAula;

	@FXML
	private MenuItem miSalir;
	@FXML
	private MenuItem miAcercaDe;
	@FXML
	private ImageView imgLogoPrincipal;
	
	/* Menu Lateral */
    @FXML
    private Button btAnadirReserva;
    @FXML
    private Button btBorrarReserva;
    @FXML
    private Button btAnadirProfesor;
    @FXML
    private Button btBorrarProfesor;
    @FXML
    private Button btAnadirAula;
    @FXML
    private Button btBorrarAula;

	/* Tabla de Reservas */
    @FXML
	private Tab tabReservas;
	@FXML
	private TextField tfFiltrarReservasProfesor;
	@FXML
	private TextField tfFiltrarReservasAula;
	@FXML
	private TableView<Reserva> tvTablaReservas;
	@FXML
	private TableColumn<Reserva, String> tcProfesorReserva;
	@FXML
	private TableColumn<Reserva, String> tcAulaReserva;
	@FXML
	private TableColumn<Reserva, String> tcDiaReserva;
	@FXML
	private TableColumn<Reserva, String> tcTipoReserva;
	@FXML
	private TableColumn<Reserva, String> tcTramoHoraReserva;

	/* Tabla de Profesores */
	@FXML
	private Tab tabProfesores;
	@FXML
	private TextField tfFiltrarProfesoresNombre;
	@FXML
	private TableView<Profesor> tvTablaProfesores;
	@FXML
	private TableColumn<Profesor, String> tcNombreProfesor;
	@FXML
	private TableColumn<Profesor, String> tcTelefonoProfesor;
	@FXML
	private TableColumn<Profesor, String> tcCorreoProfesor;

	/* Tabla de Aulas */
	@FXML
	private Tab tabAulas;
	@FXML
	private TextField tfFiltrarAulasNombre;
	@FXML
	private TableView<Aula> tvTablaAulas;
	@FXML
	private TableColumn<Aula, String> tcNombreAula;
	@FXML
	private TableColumn<Aula, String> tcPuestosAula;
	
	
	/* Otros Metodos */
	@FXML
	private void initialize() {
		imgLogoPrincipal.setImage(logo);
		tvTablaReservas.setItems(reservas);
		tvTablaProfesores.setItems(profesores);
		tvTablaAulas.setItems(aulas);
		//Reservas
		tcProfesorReserva.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		tcAulaReserva.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		tcDiaReserva.setCellValueFactory(reserva -> new SimpleStringProperty(FORMATO_FECHA.format(reserva.getValue().getPermanencia().getDia())));
		tcTipoReserva.setCellValueFactory(reserva -> new SimpleStringProperty(getTipoReserva(reserva.getValue())));
		tcTramoHoraReserva.setCellValueFactory(reserva -> new SimpleStringProperty(getTramoUHoraReserva(reserva.getValue())));

		//Profesores
		tcNombreProfesor.setCellValueFactory( profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		tcTelefonoProfesor.setCellValueFactory( profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
		tcCorreoProfesor.setCellValueFactory( profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		//Aulas
		tcNombreAula.setCellValueFactory( aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		tcPuestosAula.setCellValueFactory( aula -> new SimpleStringProperty(Integer.toString(aula.getValue().getPuestos())));
	}

	public void actualizaReservas() {
		reservas.setAll(controladorMVC.getReservas());
	}

	public void actualizaProfesores() {
		profesores.setAll(controladorMVC.getProfesores());
	}

	public void actualizaAulas() {
		aulas.setAll(controladorMVC.getAulas());
	}

	public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}
	
	private String getTipoReserva(Reserva reserva) {
		if (reserva.getPermanencia() instanceof PermanenciaPorTramo) {
			return "Tramo reservado";
		} else {
			return "Hora reservada";
		}
	}
	
	private String getTramoUHoraReserva(Reserva reserva) {
		if (reserva.getPermanencia() instanceof PermanenciaPorTramo) {
			return ((PermanenciaPorTramo) reserva.getPermanencia()).getTramo().toString();
		} else {
			return ((PermanenciaPorHora) reserva.getPermanencia()).getHora().toString();
		}
	}
	
	@FXML
	private void comprobarTabSeleccionada() {
		if (tabReservas.isSelected()) {
			btAnadirReserva.setDisable(false);
			btBorrarReserva.setDisable(false);
			btAnadirProfesor.setDisable(true);
			btBorrarProfesor.setDisable(true);
			btAnadirAula.setDisable(true);
			btBorrarAula.setDisable(true);
			
		} else if (tabProfesores.isSelected()) {
			btAnadirReserva.setDisable(true);
			btBorrarReserva.setDisable(true);
			btAnadirProfesor.setDisable(false);
			btBorrarProfesor.setDisable(false);
			btAnadirAula.setDisable(true);
			btBorrarAula.setDisable(true);
		} else {
			btAnadirReserva.setDisable(true);
			btBorrarReserva.setDisable(true);
			btAnadirProfesor.setDisable(true);
			btBorrarProfesor.setDisable(true);
			btAnadirAula.setDisable(false);
			btBorrarAula.setDisable(false);
		}
	}
	
	/* Menu Reservas */
	
	
	/* Menu Profesores */
	@FXML
	private void anadirProfesor(ActionEvent event) throws IOException {
		crearAnadirProfesor();
		anadirProfesor.showAndWait();
	}
	
	@FXML
	private void crearAnadirProfesor() throws IOException {
		if (anadirProfesor == null) {
			anadirProfesor = new Stage();
			FXMLLoader cargadorAnadirProfesor = new FXMLLoader(getClass().getResource("../vistas/VentanaAnadirProfesor.fxml"));
			VBox raizAnadirProfesor = cargadorAnadirProfesor.load();
			ControladorAnadirProfesor controlAnadirProfesor = cargadorAnadirProfesor.getController();
			controlAnadirProfesor.setControladorMVC(controladorMVC);
			controlAnadirProfesor.setProfesores(profesores);
			Scene escenaAnadirProfesor = new Scene(raizAnadirProfesor);
			anadirProfesor.setTitle("Añadir profesor");
			anadirProfesor.initModality(Modality.APPLICATION_MODAL);
			anadirProfesor.setScene(escenaAnadirProfesor);
		}
	}
	
	@FXML
	private void borrarProfesor(ActionEvent event) {
		Profesor profesor = null;
		try {
			profesor = tvTablaProfesores.getSelectionModel().getSelectedItem();
			if (profesor == null) {
				Dialogos.mostrarDialogoInformacion("Borrar profesor", "Selecciona primero el profesor a borrar.");
			}
			if (profesor != null && Dialogos.mostrarDialogoConfirmacion("Borrar profesor", "¿Está seguro de que desea borrar el profesor?", null)) {
				controladorMVC.borrarProfesor(profesor);
				profesores.remove(profesor);
				Dialogos.mostrarDialogoInformacion("Borrar profesor", "Profesor borrado con éxito.");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar profesor", e.getMessage());
		}
	}
	
	/* Menus Aulas */
	@FXML
	private void anadirAula(ActionEvent event) throws IOException {
		crearAnadirAula();
		anadirAula.showAndWait();
	}
	
	@FXML
	private void crearAnadirAula() throws IOException {
		if (anadirAula == null) {
			anadirAula = new Stage();
			FXMLLoader cargadorAnadirAula = new FXMLLoader(getClass().getResource("../vistas/VentanaAnadirAula.fxml"));
			VBox raizAnadirAula = cargadorAnadirAula.load();
			ControladorAnadirAula controlAnadirAula = cargadorAnadirAula.getController();
			controlAnadirAula.setControladorMVC(controladorMVC);
			controlAnadirAula.setAulas(aulas);
			Scene escenaAnadirAula = new Scene(raizAnadirAula);
			anadirAula.setTitle("Añadir Aula");
			anadirAula.initModality(Modality.APPLICATION_MODAL);
			anadirAula.setScene(escenaAnadirAula);
		}
	}
	
	@FXML
	private void borrarAula(ActionEvent event) {
		Aula aula = null;
		try {
			aula = tvTablaAulas.getSelectionModel().getSelectedItem();
			if (aula == null) {
				Dialogos.mostrarDialogoInformacion("Borrar aula", "Selecciona primero el aula a borrar.");
			}
			if (aula != null && Dialogos.mostrarDialogoConfirmacion("Borrar aula", "¿Está seguro de que desea borrar el aula?", null)) {
				controladorMVC.borrarAula(aula);
				aulas.remove(aula);
				Dialogos.mostrarDialogoInformacion("Borrar aula", "Aula borrada con éxito.");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar aula", e.getMessage());
		}
	}
	
	/* Menu Barra Superior */
	@FXML
	private void confirmarSalida(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Cerrar la aplicación?" , null)) {
			controladorMVC.salir();
			System.exit(0);
		} else {
			event.consume();
		}
	}
	
	@FXML
	private void acercaDe() {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.setTitle("Acerca de...");
		DialogPane panelDialogo = dialogo.getDialogPane();
		panelDialogo.lookupButton(ButtonType.OK).setId("btAceptar");
		VBox contenido = new VBox();
		contenido.setAlignment(Pos.CENTER);
		contenido.setPadding(new Insets(20,20,0,20));
		contenido.setSpacing(20);
		Image logo = new Image(getClass().getResourceAsStream("../imagenes/ReservaAulasJFQuero.png"), 200, 200, true, true);
		Label titulo = new Label("Modulo de Programación - JavaFX");
		Label texto = new Label("ReservaAulas-v4 - Diseñado por Juan Fernandez Quero");
		texto.setStyle("-fx-font: 20 Arial");
		contenido.getChildren().addAll(new ImageView(logo),titulo,texto);
		panelDialogo.setHeader(contenido);
		dialogo.showAndWait();
	}

}
