package org.iesalandalus.programacion.reservasaulas.vista;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;

public class VistaReservasAulas implements IVistaReservasAulas {

	private static final String ERROR = "ERROR: ";
	private static final String VACIO = "No hay reservas que listar.";
	private static final String NOMBRE_VALIDO = "Juan Fernandez Quero";
	private static final String CORREO_VALIDO = "correo@valido.com";
	private IControladorReservasAulas controlador;

	/* Constructores */
	public VistaReservasAulas() {
		Opcion.setVista(this);
	}

	/* Metodos */
	@Override
	public void setControlador(IControladorReservasAulas controlador) {
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

	public void salir() {
		controlador.salir();
	}

	public void insertarAula() {
		Consola.mostrarCabecera("Insertar Aula");
		try {
			Aula aula = Consola.leerAula();
			controlador.insertarAula(aula);
			System.out.println("Aula añadida con exito.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarAula() {
		Consola.mostrarCabecera("Borrar Aula");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(), 69);
			List<Reserva> buscarReservas = controlador.getReservasAula(aula);
			if (!buscarReservas.isEmpty()) {
				System.out.println(ERROR + "No se puede borrar un aula con reservas activas.");
			} else {
				controlador.borrarAula(aula);
				System.out.println("Aula borrada con exito.");
			}
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarAula() {
		Consola.mostrarCabecera("Buscar Aula");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(), 69);
			aula = controlador.buscarAula(aula);
			if (aula != null) {
				System.out.println("Aula encontrado con exito.");
				System.out.println("El aula buscada es: " + aula);
			} else {
				System.out.println("El aula buscada no existe.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarAulas() {
		Consola.mostrarCabecera("Listar Aulas");
		List<String> aulas = controlador.representarAulas();
		if (!aulas.isEmpty()) {
			for (String aula : aulas) {
				System.out.println(aula);
			}
		} else {
			System.out.println("No hay aulas que listar.");
		}
	}

	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar Profesor");
		try {
			Profesor profesor = Consola.leerProfesor();
			controlador.insertarProfesor(profesor);
			System.out.println("Profesor registrado con exito.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar Profesor");
		try {
			Profesor profesor = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
			List<Reserva> buscarReservas = controlador.getReservasProfesor(profesor);
			if (!buscarReservas.isEmpty()) {
				System.out.println(ERROR + "No se puede borrar un profesor con reservas activas.");
			} else {
				controlador.borrarProfesor(profesor);
				System.out.println("Profesor borrado con exito.");
			}
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar Profesor");
		try {
			Profesor profesor = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
			profesor = controlador.buscarProfesor(profesor);
			if (profesor != null) {
				System.out.println("Profesor encontrado con exito.");
				System.out.println("El profesor buscado es: " + profesor);
			} else {
				System.out.println("El profesor buscado no existe.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarProfesores() {
		Consola.mostrarCabecera("Listar Profesores");
		List<String> profesores = controlador.representarProfesores();
		if (!profesores.isEmpty()) {
			for (String profesor : profesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores que listar.");
		}
	}

	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar Reserva");
		try {
			Profesor profesor = controlador.buscarProfesor(new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO));
			Reserva reserva = leerReserva(profesor);
			controlador.realizarReserva(reserva);
			System.out.println("Reserva realizada con exito.");
		} catch (IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	private Reserva leerReserva(Profesor profesor) {
		Permanencia permanencia = Consola.leerPermanencia();
		Aula aula = controlador.buscarAula(new Aula(Consola.leerNombreAula(), 69));
		return new Reserva(profesor, aula, permanencia);
	}

	public void anularReserva() {
		Consola.mostrarCabecera("Anular una Reserva");
		try {
			Profesor profesor = new Profesor(NOMBRE_VALIDO, CORREO_VALIDO);
			Reserva reserva = leerReserva(profesor);
			controlador.anularReserva(reserva);
			System.out.println("Reserva anulada con exito.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservas() {
		Consola.mostrarCabecera("Listar Reservas");
		try {
			List<String> reservas = controlador.representarReservas();
			if (!reservas.isEmpty()) {
				for (String reserva : reservas) {
					System.out.println(reserva);
				}
			} else {
				System.out.println(VACIO);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas de un aula");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(), 69);
			List<Reserva> reservas = controlador.getReservasAula(aula);
			if (controlador.buscarAula(aula) == null) {
				System.out.println("El aula introducida no esta registrada.");
			}
			if (reservas.isEmpty()) {
				System.out.println(VACIO);
			} else {
				for (Reserva reserva : reservas) {
					System.out.println(reserva);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Listar reservas de un profesor");
		try {
			Profesor profesor = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
			List<Reserva> reservas = controlador.getReservasProfesor(profesor);
			if (controlador.buscarProfesor(profesor) == null) {
				System.out.println("El profesor introducido no esta registrado.");
			}
			if (reservas.isEmpty()) {
				System.out.println(VACIO);
			} else {
				for (Reserva reserva : reservas) {
					System.out.println(reserva);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservasPermanencia() {
		Consola.mostrarCabecera("Listar reservas de una permanencia");
		try {
			Permanencia permanencia = Consola.leerPermanencia();
			List<Reserva> reservas = controlador.getReservasPermanencia(permanencia);
			if (reservas.isEmpty()) {
				System.out.println(VACIO);
			} else {
				for (Reserva reserva : reservas) {
					System.out.println(reserva);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Consultar Disponibilidad");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(), 69);
			if (controlador.buscarAula(aula) == null) {
				System.out.println("El aula introducida no esta registrada.");
			} else {
				Permanencia permanencia = Consola.leerPermanencia();
				if (controlador.consultarDisponibilidad(aula, permanencia)) {
					System.out.println("El aula " + aula + " esta disponible para la permanencia " + permanencia + ".");
				} else {
					System.out.println("El aula " + aula + " no esta disponible para la permanencia elegida.");
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}
}
