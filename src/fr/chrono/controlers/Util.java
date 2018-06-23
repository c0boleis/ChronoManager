package fr.chrono.controlers;

public class Util {
	
	public static int numberOfChar(String text,char c) {
		if(text==null) {
			return -1;
		}
		char[] tab = text.toCharArray();
		int numberOfChar = 0;
		for(char cTest : tab) {
			if(cTest==c) {
				numberOfChar++;
			}
		}
		return numberOfChar;
	}

}
