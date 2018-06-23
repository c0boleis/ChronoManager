package fr.chrono.model.exceptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.chrono.controlers.Util;
import fr.chrono.model.interfaces.ICompetiteur;

public class IllegalNameException extends ModelFormatException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5954685460909689471L;
	
	public IllegalNameException(String name) {
		super(getMessageOut(name));
		
	}
	
	public static String getMessageOut(String name) {
		String message = "Name: "+name+" havn't the right format.\n"
				+ "illegal char: "+getIllageChar(name);
		if(name.endsWith("/")) {
			message+="\n olso name can't finish by '/'.";
		}else
		if(Util.numberOfChar(name, '/')>1) {
			message+="\n olso the more than one '/' in the name.";
		} 
		return message;
	}
	
	public static String getIllageChar(String name) {
		Pattern pattern = Pattern.compile(ICompetiteur.NAME_FORBIDDEN_CHAR);
		Matcher match = pattern.matcher(name);
		String out ="{";
		boolean first = true;
		while(match.find()) {
			if(match.group().length()>0) {
				if(first) {
					out+="\""+match.group()+"\"";
					first=false;
				}else {
					out+=", \""+match.group()+"\"";
				}
			}
		}
		out+="}";
		return out;
	}

}
