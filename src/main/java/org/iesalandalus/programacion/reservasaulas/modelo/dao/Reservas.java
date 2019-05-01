package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;

public class Reservas {

	private static final String NOMBRE_FICHERO_RESERVAS = "ficheros" + File.separator + "reservas.dat";
	private List<Reserva> coleccionReservas;
	private static final float MAX_PUNTOS_PROFESOR_MES = 200;

	/* Constructores */
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}

	public Reservas(Reservas reservas) {
		setReservas(reservas);
	}

	/* Metodos */
	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new IllegalArgumentException("No se pueden copiar reservas nulas.");
		}
		coleccionReservas = copiaProfundaReservas(reservas.coleccionReservas);
	}

	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> copiaReservas = new ArrayList<>();
		for (Reserva reserva : reservas) {
			copiaReservas.add(new Reserva(reserva));
		}
		return copiaReservas;
	}

	public List<Reserva> getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}

	public int getNumReservas() {
		return coleccionReservas.size();
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede realizar una reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("La reserva ya existe.");
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		if (getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException(
					"Esta reserva excede los puntos máximos por mes para dicho profesor.");
		}
		if (getReservaDia(reserva.getPermanencia().getDia()) != null) {
			if (getReservaDia(reserva.getPermanencia().getDia()).getPermanencia() instanceof PermanenciaPorTramo
					& reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException(
						"Ya se ha realizado una reserva por tramo para este día y aula.");
			}
			if (getReservaDia(reserva.getPermanencia().getDia()).getPermanencia() instanceof PermanenciaPorHora
					& reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException(
						"Ya se ha realizado una reserva por hora para este día y aula.");
			}
		}
		coleccionReservas.add(new Reserva(reserva));

	}

	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		return (reserva.getPermanencia().getDia().getMonthValue() > LocalDate.now().getMonthValue())
				|| reserva.getPermanencia().getDia().getYear() > LocalDate.now().getYear();
	}

	private float getPuntosGastadosReserva(Reserva reserva) {
		float sumaPuntos = 0;
		for (Reserva puntos : getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia())) {
			sumaPuntos += puntos.getPuntos();
		}
		return sumaPuntos + reserva.getPuntos();
	}

	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate dia) {
		List<Reserva> reservasProfesorMes = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if (reserva.getProfesor().equals(profesor)
					& reserva.getPermanencia().getDia().getMonth().equals(dia.getMonth())) {
				reservasProfesorMes.add(new Reserva(reserva));
			}
		}
		return reservasProfesorMes;
	}

	private Reserva getReservaDia(LocalDate dia) {
		for (Reserva reserva : coleccionReservas) {
			if (reserva.getPermanencia().getDia().equals(dia)) {
				return new Reserva(reserva);
			}
		}
		return null;
	}

	public Reserva buscar(Reserva reserva) {
		int indice = coleccionReservas.indexOf(reserva);
		if (indice != -1) {
			return new Reserva(coleccionReservas.get(indice));
		} else {
			return null;
		}
	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede anular una reserva nula.");
		}
		if (!coleccionReservas.remove(reserva)) {
			throw new OperationNotSupportedException("La reserva a anular no existe.");
		}
	}

	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			representacion.add(new Reserva(reserva).toString());
		}
		return representacion;
	}

	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("No se pueden buscar las reservas de un profesor nulo.");
		}
		List<Reserva> busquedaProfesor = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if (reserva.getProfesor().equals(profesor)) {
				busquedaProfesor.add(new Reserva(reserva));
			}
		}
		return busquedaProfesor;
	}

	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new IllegalArgumentException("No se pueden buscar las reservas de una aula nula.");
		}
		List<Reserva> busquedaAula = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if (reserva.getAula().equals(aula)) {
				busquedaAula.add(new Reserva(reserva));
			}
		}
		return busquedaAula;
	}

	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new IllegalArgumentException("No se pueden buscar las reservas por una permanencia nula.");
		}
		List<Reserva> busquedaPermanencia = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if (reserva.getPermanencia().equals(permanencia)) {
				busquedaPermanencia.add(new Reserva(reserva));
			}
		}
		return busquedaPermanencia;
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de un aula nula.");
		}
		if (permanencia == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de una permanencia nula.");
		}

		for (Reserva reserva : coleccionReservas) {
			if (reserva.getAula().equals(aula) & reserva.getPermanencia().equals(permanencia)) {
				return false;
			}
		}
		return true;
	}

	public void leer() {
		File lectura = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(lectura))) {
			Reserva reserva = null;
			do {
				reserva = (Reserva) entrada.readObject();
				insertar(reserva);
			} while (reserva != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No se encuentra la clase a leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No se puede abrir el fichero de Reservas.");
		} catch (EOFException e) {
			System.out.println("El fichero de Reservas se leyó con éxito.");
		} catch (IOException e) {
			System.out.println("Error inesperado de E/S.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void escribir() {
		File escritura = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(escritura))) {
			for (Reserva reserva : coleccionReservas) {
				salida.writeObject(reserva);
			}
			System.out.println("Fichero de Reservas se escribió con éxito.");
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido crear el fichero de Reservas.");
		} catch (IOException e) {
			System.out.println("Error inesperado de E/S.");
			e.printStackTrace();
		}
	}
}