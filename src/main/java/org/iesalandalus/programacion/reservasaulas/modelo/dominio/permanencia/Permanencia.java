package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Permanencia implements Serializable {

	protected LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/* Constructores */
	protected Permanencia() {
	}

	protected Permanencia(LocalDate dia) {
		setDia(dia);
	}

	protected Permanencia(String permanencia) {
		setDia(permanencia);
	}

	/* Metodos */
	public LocalDate getDia() {
		return dia;
	}

	protected void setDia(LocalDate dia) {
		if (dia == null) {
			throw new IllegalArgumentException("El día de una permanencia no puede ser nulo.");
		}
		this.dia = dia;
	}

	protected void setDia(String dia) {
		if (dia == null) {
			throw new IllegalArgumentException("El día de una permanencia no puede ser nulo.");
		}
		try {
			this.dia = LocalDate.parse(dia, FORMATO_DIA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("El formato del día de la permanencia no es correcto.");
		}
	}

	public abstract int getPuntos();

	/* Otros Metodos */

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();

}
