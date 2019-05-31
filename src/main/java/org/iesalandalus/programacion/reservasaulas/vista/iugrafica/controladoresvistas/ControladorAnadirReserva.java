package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import java.time.LocalDate;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorAnadirReserva {

	private IControladorReservasAulas controladorMVC;
	private ObservableList<Profesor> profesores;
	private ObservableList<Reserva> reservas;
	private ObservableList<Aula> aulas;
	private ObservableList<String> horasPermitidas = FXCollections.observableArrayList("08:00", "09:00", "10:00",
			"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00");
	private ObservableList<String> tramosPermitidos = FXCollections.observableArrayList("Mañana", "Tarde");
	private ToggleGroup tgTipoReserva = new ToggleGroup();

	@FXML	private ComboBox<String> cbProfesores;
	@FXML	private ComboBox<String> cbAulas;
	@FXML	private Label lbHora;
	@FXML	private Label lbTramo;
	@FXML	private ComboBox<String> cbTramo;
	@FXML	private ComboBox<String> cbHora;
	@FXML	private DatePicker dpFecha;
	@FXML	private RadioButton rbTramo;
	@FXML	private RadioButton rbHoras;
	@FXML	private Button btCancelar;
	@FXML	private Button btAceptar;

	@FXML	private void initialize() {
		rbTramo.setToggleGroup(tgTipoReserva);
		rbHoras.setToggleGroup(tgTipoReserva);
		limitarFecha();
	}

	@FXML	private void anadirReserva() {
		Reserva reserva = null;
		try {
			reserva = getReserva();
			controladorMVC.realizarReserva(reserva);
			reservas.add(reserva);
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Realizar reserva", "Reserva realizada con éxito", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoInformacion("Realizar reserva", e.getMessage());
		}
	}

	private Reserva getReserva() {
		Profesor profesor = new Profesor(cbProfesores.getValue(), "correo@valido.es");
		Aula aula = new Aula(cbAulas.getValue(), 50);
		Permanencia permanencia = getTipoPermanencia(dpFecha.getValue());
		return new Reserva(profesor,aula,permanencia);
	}

	private Permanencia getTipoPermanencia(LocalDate dia) {
		if (rbHoras.isSelected()) {
			return new PermanenciaPorHora(dia, cbHora.getValue());
		} else {
			if (cbTramo.getValue().equals("Mañana")) {
				return new PermanenciaPorTramo(dia, Tramo.MANANA);
			} else {
				return new PermanenciaPorTramo(dia, Tramo.TARDE);
			}
		}

	}

	@FXML	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	@FXML	private void comprobarSeleccionTipo() {
		if (rbTramo.isSelected()) {
			lbTramo.setOpacity(1);
			cbTramo.setDisable(false);
			lbHora.setOpacity(0.5);
			cbHora.setDisable(true);
			cbTramo.setItems(tramosPermitidos);
		} else {
			lbTramo.setOpacity(0.5);
			cbTramo.setDisable(true);
			lbHora.setOpacity(1);
			cbHora.setDisable(false);
			cbHora.setItems(horasPermitidas);
		}
	}

	private void limitarFecha() {
		dpFecha.setDayCellFactory(limite -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.now().plusMonths(1)));
			}
		});
	}

	private void getNombresProfesores() {
		ObservableList<String> nombresProfesores = FXCollections.observableArrayList();
		if (!profesores.isEmpty()) {
			for (Profesor profesor : controladorMVC.getProfesores()) {
				nombresProfesores.add(profesor.getNombre());
			}
		} else {
			nombresProfesores.add("Sin profesores");
		}
		cbProfesores.setItems(nombresProfesores);
	}

	private void getNombresAulas() {
		ObservableList<String> nombresAulas = FXCollections.observableArrayList();
		if (!aulas.isEmpty()) {
			for (Aula aula : aulas) {
				nombresAulas.add(aula.getNombre());
			}
		} else {
			nombresAulas.add("Sin aulas");
		}
		cbAulas.setItems(nombresAulas);

	}

	public void setControladorMVC(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setDatos(ObservableList<Profesor> profesores, ObservableList<Aula> aulas,
			ObservableList<Reserva> reservas) {
		this.profesores = profesores;
		this.aulas = aulas;
		this.reservas = reservas;
		getNombresProfesores();
		getNombresAulas();
	}
}
