package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

import java.io.Serializable;
import java.time.LocalDate;

public class PermanenciaPorTramo extends Permanencia implements Serializable {

	private static final int PUNTOS = 10;
	private Tramo tramo;

	/* Constructores */
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}

	public PermanenciaPorTramo(String dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}

	public PermanenciaPorTramo(PermanenciaPorTramo permanencia) {
		super();
		if (permanencia == null) {
			throw new IllegalArgumentException("No se puede copiar una permanencia nula.");
		}
		setDia(permanencia.getDia());
		setTramo(permanencia.getTramo());
	}

	/* Metodos */
	public Tramo getTramo() {
		return tramo;
	}

	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new IllegalArgumentException("El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;
	}

	@Override
	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tramo == null) ? 0 : tramo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PermanenciaPorTramo)) {
			return false;
		}
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		if (dia == null) {
			if (other.dia != null) {
				return false;
			}
		} else if (!dia.equals(other.dia)) {
			return false;
		}
		if (tramo != other.tramo) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("[dia=%s, tramo=%s]", dia.format(FORMATO_DIA), tramo);
	}
}
