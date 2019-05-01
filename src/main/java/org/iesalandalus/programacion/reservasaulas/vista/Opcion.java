package org.iesalandalus.programacion.reservasaulas.vista;

public enum Opcion {
	SALIR("Salir") {
		public void ejecutar() {
			vista.salir();
		}
	},

	INSERTAR_AULA("Insertar Aula") {
		public void ejecutar() {
			vista.insertarAula();
		}
	},

	BORRAR_AULA("Borrar Aula") {
		public void ejecutar() {
			vista.borrarAula();
		}
	},

	BUSCAR_AULA("Buscar Aula") {
		public void ejecutar() {
			vista.buscarAula();
		}
	},

	LISTAR_AULAS("Listar Aulas") {
		public void ejecutar() {
			vista.listarAulas();
		}
	},

	INSERTAR_PROFESOR("Insertar Profesor") {
		public void ejecutar() {
			vista.insertarProfesor();
		}
	},

	BORRAR_PROFESOR("Borrar Profesor") {
		public void ejecutar() {
			vista.borrarProfesor();
		}
	},

	BUSCAR_PROFESOR("Buscar Profesor") {
		public void ejecutar() {
			vista.buscarProfesor();
		}
	},

	LISTAR_PROFESORES("Listar Profesores") {
		public void ejecutar() {
			vista.listarProfesores();
		}
	},

	INSERTAR_RESERVA("Realizar Reserva") {
		public void ejecutar() {
			vista.realizarReserva();
		}
	},

	BORRAR_RESERVA("Cancelar Reserva") {
		public void ejecutar() {
			vista.anularReserva();
		}
	},

	LISTAR_RESERVAS("Listar Reservas") {
		public void ejecutar() {
			vista.listarReservas();
		}
	},

	LISTAR_RESERVAS_AULA("Listar las reservas de un Aula") {
		public void ejecutar() {
			vista.listarReservasAula();
		}
	},

	LISTAR_RESERVAS_PROFESOR("Listar las reservas de un Profesor") {
		public void ejecutar() {
			vista.listarReservasProfesor();
		}
	},

	LISTAR_RESERVAS_PERMANENCIA("Listar las reservas de una Permanencia") {
		public void ejecutar() {
			vista.listarReservasPermanencia();
		}
	},

	CONSULTAR_DISPONIBILIDAD("Consulta la disponibilidad de un Aula") {
		public void ejecutar() {
			vista.consultarDisponibilidad();
		}
	};

	private String mensajeAMostrar;
	private static VistaReservasAulas vista;

	/* Constructores */
	private Opcion(String mensajeAMostrar) {
		this.mensajeAMostrar = mensajeAMostrar;
	}

	/* Metodos */
	public String getMensaje() {
		return mensajeAMostrar;
	}

	public abstract void ejecutar();

	protected static void setVista(VistaReservasAulas vista) {
		Opcion.vista = vista;
	}

	@Override
	public String toString() {
		return String.format("%d.- %s", ordinal(), mensajeAMostrar);
	}

	public static Opcion getOpcionSegunOrdinal(int ordinal) {
		if (esOrdinalValido(ordinal)) {
			return values()[ordinal];
		} else {
			throw new IllegalArgumentException("Ordinal de la opcion no válido.");
		}
	}

	public static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal <= values().length - 1);
	}
}
