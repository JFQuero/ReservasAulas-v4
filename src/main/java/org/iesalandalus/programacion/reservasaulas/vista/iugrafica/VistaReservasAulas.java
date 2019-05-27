package org.iesalandalus.programacion.reservasaulas.vista.iugrafica;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas.ControladorVentanaPrincipal;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaReservasAulas extends Application implements IVistaReservasAulas {

	private IControladorReservasAulas controladorMVC = null;
	private static VistaReservasAulas instancia = null;

	/* Constructores */
	public VistaReservasAulas() {
		if (instancia != null) {
			controladorMVC = instancia.controladorMVC;
		} else {
			instancia = this;
		}
	}

	@Override
	public void start(Stage ventanaPrincipal) {
		try {
			FXMLLoader cargadorVentanaPrincipal = new FXMLLoader(getClass().getResource("vistas/VentanaPrincipal.fxml"));
			VBox raiz = cargadorVentanaPrincipal.load();
			ControladorVentanaPrincipal controlVentanaPrincipal = cargadorVentanaPrincipal.getController();
			controlVentanaPrincipal.setControlador(controladorMVC);
			controlVentanaPrincipal.actualizaProfesores();
			controlVentanaPrincipal.actualizaAulas();
			controlVentanaPrincipal.actualizaReservas();
			Scene escena = new Scene(raiz);
			ventanaPrincipal.setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal, e));
			ventanaPrincipal.setTitle("Reserva de Aulas por Juan Fernandez Quero");
			ventanaPrincipal.setScene(escena);
			ventanaPrincipal.setResizable(false);
			ventanaPrincipal.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void confirmarSalida(Stage ventanaPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Cerrar la aplicación?", ventanaPrincipal)) {
			controladorMVC.salir();
			ventanaPrincipal.close();
		} else {
			e.consume();
		}
	}

	@Override
	public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}

	@Override
	public void comenzar() {
		launch(this.getClass());
	}
}
