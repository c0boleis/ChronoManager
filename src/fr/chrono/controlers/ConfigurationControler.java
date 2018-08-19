package fr.chrono.controlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import fr.chrono.model.Auxiliaire;
import fr.chrono.model.interfaces.ConfigurationType;
import fr.chrono.model.interfaces.DomainType;
import fr.chrono.model.interfaces.IAuxiliare;

public class ConfigurationControler {
	
	private static final Logger LOGGER = Logger.getLogger(ConfigurationControler.class);

	public static final String CM_VERSION = "BETA-0.0.1";

	public static String KEY_DOMAIN_TYPE = "domain_type";

	public static String KEY_CONFIGURATION_TYPE = "configuration_type";

	private static DomainType domainType = DomainType.ARRIVAL;

	private static ConfigurationType configurationType = ConfigurationType.CONFIGURATION_1;

	public static final String CONFIGURATION_FOLDER = "conf";

	private static List<IAuxiliare> auxiliares = new ArrayList<IAuxiliare>();

	public static void init() throws FileNotFoundException, IOException {
		String domainTypeValue = getProperty(ConfigurationControler.class, KEY_DOMAIN_TYPE);
		String configurationTypeValue = getProperty(ConfigurationControler.class, KEY_CONFIGURATION_TYPE);
		domainType = DomainType.valueOf(domainTypeValue);
		configurationType = ConfigurationType.valueOf(configurationTypeValue);
		switch (configurationType) {
		case CONFIGURATION_2:
			initConfig2();
			break;
		case CONFIGURATION_3:

			break;
		case CONFIGURATION_4:

			break;
		}
	}

	private static void initConfig2() {
		switch (domainType) {
		case ARRIVAL:
			auxiliares.add(new Auxiliaire(DomainType.START, 0));
			break;
		case START:
			auxiliares.add(new Auxiliaire(DomainType.ARRIVAL, 0));
			break;
		default:
			break;
		}
	}

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
		File folderConf = new File(CONFIGURATION_FOLDER);
		if(!folderConf.exists()) {
			folderConf.mkdirs();
		}
		String path = CONFIGURATION_FOLDER+File.separator+type.getCanonicalName()+".properties";
		LOGGER.debug("getPropertyFile =>"+path);
		return new File(path);
	}

	public static IAuxiliare[] getAuxiliares() {

		return auxiliares.toArray(new IAuxiliare[0]);
	}

}
