package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControladorAnadirProfesor {

	private static final String ER_OBLIGATORIO = ".+";
	private static final String ER_TELEFONO = "\\d{9}";
	private static final String ER_CORREO = "\\w+(?:\\.\\w+)*@\\w+\\.\\w{2,5}";
	private IControladorReservasAulas controladorMVC;
	private ObservableList<Profesor> profesores;

	@FXML	private TextField tfNombreProfesor;
	@FXML	private TextField tfCorreoProfesor;
	@FXML	private HBox hbTelefono;
	@FXML	private CheckBox cbTelefono;
	@FXML	private TextField tfTelefono;
	@FXML	private Button btCancelar;
	@FXML	private Button btAceptar;

	@FXML	private void initialize() {
		tfNombreProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfNombreProfesor));
		tfCorreoProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfCorreoProfesor));
		tfCorreoProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreoProfesor));
		tfTelefono.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_TELEFONO, tfTelefono));
	}

	@FXML	private void seleccionTelefono() {
		if (cbTelefono.isSelected()) {
			hbTelefono.visibleProperty().set(true);
		} else {
			hbTelefono.visibleProperty().set(false);
		}
	}

	@FXML	private void anadirProfesor() {
		Profesor profesor = null;
		try {
			profesor = getProfesor();
			controladorMVC.insertarProfesor(profesor);
			profesores.add(profesor);
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			limpiarCampoTexto();
			Dialogos.mostrarDialogoInformacion("Añadir profesor", "Profesor añadido con éxito", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoInformacion("Añadir profesor", e.getMessage());
		}
	}

	@FXML	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	private Profesor getProfesor() {
		if (cbTelefono.isSelected() && tfTelefono != null) {
			String nombre = tfNombreProfesor.getText();
			String correo = tfCorreoProfesor.getText();
			String telefono = tfTelefono.getText();
			return new Profesor(nombre, correo, telefono);
		} else {
			String nombre = tfNombreProfesor.getText();
			String correo = tfCorreoProfesor.getText();
			return new Profesor(nombre, correo);
		}
	}

	private void compruebaCampoTexto(String er, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green");
		} else {
			campoTexto.setStyle("-fx-border-color: red");
		}
	}

	private void limpiarCampoTexto() {
		tfNombreProfesor.setText("");
		tfCorreoProfesor.setText("");
		cbTelefono.selectedProperty().set(false);
		hbTelefono.visibleProperty().set(false);
		tfTelefono.setText("");
	}

	public void setControladorMVC(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setProfesores(ObservableList<Profesor> profesores) {
		this.profesores = profesores;
	}

}
