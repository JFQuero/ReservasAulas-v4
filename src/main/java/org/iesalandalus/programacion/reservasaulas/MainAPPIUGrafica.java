package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.controlador.ControladorReservasAulas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.ModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iutextual.Consola;
import org.iesalandalus.programacion.reservasaulas.vista.iutextual.VistaReservasAulas;

public class MainAPPIUGrafica {

	public static void main(String[] args) {
		Consola.mostrarCabecera("Programa para la gestión de reservas de aulas del IES Al-Ándalus");
		Consola.mostrarCabecera("Juan Fernández Quero - 1ºDAM");
		IVistaReservasAulas vista = new VistaReservasAulas();
		IModeloReservasAulas modelo = new ModeloReservasAulas();
		IControladorReservasAulas controlador = new ControladorReservasAulas(vista, modelo);
		controlador.comenzar();
	}

}