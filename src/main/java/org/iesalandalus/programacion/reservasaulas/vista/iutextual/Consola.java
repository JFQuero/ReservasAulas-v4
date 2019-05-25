package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

import java.time.LocalDate;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	/* Evitar que se cree un constructor por defecto */
	private Consola() {
	}

	/* Metodos */
	public static void mostrarMenu() {
		mostrarCabecera("Reserva de Aulas");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String cadena = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}

	public static Aula leerAula() {
		String nombre = leerNombreAula();
		System.out.print("Introduce el numero de puestos del Aula: ");
		int puestos = Entrada.entero();
		return new Aula(nombre, puestos);
	}

	public static String leerNombreAula() {
		System.out.print("Introduce el nombre del Aula: ");
		String nombreAula = Entrada.cadena();
		return nombreAula;
	}

	public static Profesor leerProfesor() {
		System.out.print("Introduce el nombre: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo: ");
		String correo = Entrada.cadena();
		System.out.println("¿Desea añadir un numero de telefono?");
		System.out.println(" 1.- Si. ");
		System.out.println(" 2.- No. ");
		int respuesta;
		do {
			System.out.println("Elija una opcion: ");
			respuesta = Entrada.entero();
		} while (respuesta < 1 & respuesta > 2);
		if (respuesta == 1) {
			System.out.print("Introduce el numero de telefono: ");
			String telefono = Entrada.cadena();
			return new Profesor(nombre, correo, telefono);
		} else {
			return new Profesor(nombre, correo);
		}
	}

	public static String leerNombreProfesor() {
		System.out.print("Introduce el nombre del profesor: ");
		String nombre = Entrada.cadena();
		return nombre;
	}

	public static Tramo leerTramo() {
		System.out.println("Elija el Tramo: ");
		System.out.println(" 1.- Mañana. ");
		System.out.println(" 2.- Tarde. ");
		int respuesta;
		do {
			System.out.println("Elija una opcion: ");
			respuesta = Entrada.entero();
		} while (respuesta < 1 & respuesta > 2);
		if (respuesta == 1) {
			return Tramo.MANANA;
		} else {
			return Tramo.TARDE;
		}
	}

	public static LocalDate leerDia() {
		int dia;
		int mes;
		int ano;
		do {
			System.out.print("Introduce el dia: (1 - 31) ");
			dia = Entrada.entero();
		} while (dia < 1 | dia > 31);
		do {
			System.out.print("Introduce el mes: (01 - 12) ");
			mes = Entrada.entero();
		} while (mes < 1 | mes > 12);
		do {
			System.out.print("Introduce el año: (2019 - 20**) ");
			ano = Entrada.entero();
		} while (ano < (LocalDate.now().getYear()));
		return LocalDate.of(ano, mes, dia);
	}

	public static String leerHora() {
		System.out.print("Introduce la hora: (08:00 - 22:00) ");
		return Entrada.cadena();
	}

	public static Permanencia leerPermanencia() {
		if (elegirPermanencia() == 1) {
			return new PermanenciaPorHora(leerDia(), leerHora());
		} else {
			return new PermanenciaPorTramo(leerDia(), leerTramo());
		}
	}

	private static int elegirPermanencia() {
		int opcion;
		System.out.println("Elija el tipo de Permanencia: ");
		System.out.println(" 1.- Permanencia por Hora. ");
		System.out.println(" 2.- Permanencia por Tramo. ");
		do {
			System.out.println("Elija una opcion: ");
			opcion = Entrada.entero();
		} while (opcion < 1 | opcion > 2);
		return opcion;
	}
}
