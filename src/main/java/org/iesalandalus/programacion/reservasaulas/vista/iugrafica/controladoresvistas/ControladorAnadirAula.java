package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControladorAnadirAula {

	private static final String ER_OBLIGATORIO = ".+";
	private IControladorReservasAulas controladorMVC;
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();

	@FXML	private TextField tfNombreAula;
	@FXML	private TextField tfPuestos;
	@FXML	private HBox hbTelefono;
	@FXML	private TextField tfTelefono;
	@FXML	private Button btCancelar;
	@FXML	private Button btAceptar;

	@FXML	private void initialize() {
		tfNombreAula.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfNombreAula));
		tfPuestos.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfPuestos));
	}

	@FXML	private void anadirAula() {
		Aula aula = null;
		try {
			aula = getAula();
			controladorMVC.insertarAula(aula);
			aulas.add(aula);
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			limpiarCampoTexto();
			Dialogos.mostrarDialogoInformacion("Añadir aula", "Aula añadida con éxito", propietario);

		} catch (Exception e) {
			Dialogos.mostrarDialogoInformacion("Añadir Aula", e.getMessage());
		}
	}

	@FXML	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	private Aula getAula() {
		String nombre = tfNombreAula.getText();
		int puestos = Integer.parseInt(tfPuestos.getText());
		return new Aula(nombre, puestos);
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
		tfNombreAula.setText("");
		tfPuestos.setText("");
	}

	public void setControladorMVC(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setAulas(ObservableList<Aula> aulas) {
		this.aulas = aulas;
	}

}
