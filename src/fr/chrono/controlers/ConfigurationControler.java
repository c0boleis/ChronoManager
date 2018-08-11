package fr.chrono.controlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import fr.chrono.model.interfaces.DomainType;

public class ConfigurationControler {
	
	public static final String CM_VERSION = "BETA-0.0.1";
	
	public static DomainType domainType = DomainType.ARRIVAL;
	
	public static final String DEFAULT_CONFIGURATIon_FOLDER = "conf";
	
	public static boolean setProperty(Class<?> type, String key, String value) throws FileNotFoundException, IOException {
		File propertyFile = getPropertyFile(type);
		if(propertyFile == null) {
			return false;
		}
		if(!propertyFile.exists()) {
			if(!propertyFile.createNewFile()) {
				return false;
			}
		}
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertyFile));
		properties.setProperty(key, value);
		properties.store(new FileOutputStream(propertyFile), null);
		return true;
	}
	
	public static String getProperty(Class<?> type, String key) throws FileNotFoundException, IOException {
		File propertyFile = getPropertyFile(type);
		if(propertyFile == null) {
			return null;
		}
		if(!propertyFile.exists()) {
			return null;
		}
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertyFile));
		return properties.getProperty(key);
	}
	
	private static File getPropertyFile(Class<?> type) {
		if(type == null) {
			return null;
		}
		File folderConf = new File(DEFAULT_CONFIGURATIon_FOLDER);
		if(!folderConf.exists()) {
			folderConf.mkdirs();
		}
		return new File(DEFAULT_CONFIGURATIon_FOLDER+File.separator+type.getCanonicalName());
	}
	

}
