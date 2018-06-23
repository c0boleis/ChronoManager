package fr.chrono.model.exceptions;

public abstract class ModelFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4563309481015265842L;

	public ModelFormatException(String name) {
		super(name);
	}
}
