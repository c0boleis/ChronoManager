package fr.chrono.model.exceptions;

public class IllegalCategoryException extends ModelFormatException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2918781986322466573L;

	public IllegalCategoryException(String category) {
		super("Category: "+category+" havn't the right format");
	}

}
