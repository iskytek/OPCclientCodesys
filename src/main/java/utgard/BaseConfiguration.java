package utgard;

import org.openscada.opc.lib.common.ConnectionInformation;

import java.io.IOException;
import java.util.Properties;



public final class BaseConfiguration {

	private final static ConnectionInformation ci;
	private final static Properties prop;

	public final static String CONFIG_USERNAME = "opc.username";
	public final static String CONFIG_PASSWORD = "opc.password";
	public final static String CONFIG_HOST = "opc.host";
	public final static String CONFIG_DOMAIN = "opc.domain";
	public final static String CONFIG_CLSID = "opc.clsid";
	public final static String CONFIG_PROGID = "opc.progid";
	public final static String CONFIG_ITEMS = "opc.items";
	private final static String CONFIG_FILE_NAME = "opc.properties";


	static {
		ci = new ConnectionInformation();
		prop = new Properties();
		try {
			prop.load(BaseConfiguration.class.getClassLoader()
					.getResourceAsStream(CONFIG_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String getEntryValue(String name) {
		return prop.getProperty(name);
	}


	public static ConnectionInformation getCLSIDConnectionInfomation() {
		ci.setProgId(null);
		getConnectionInfomation();
		ci.setClsid(prop.getProperty(CONFIG_CLSID));
		return ci;
	}


	public static ConnectionInformation getPROGIDConnectionInfomation() {
		ci.setClsid(null);
		getConnectionInfomation();
		ci.setProgId(prop.getProperty(CONFIG_PROGID));
		return ci;
	}


	private static void getConnectionInfomation() {
		ci.setHost(prop.getProperty(CONFIG_HOST));
		ci.setDomain(prop.getProperty(CONFIG_DOMAIN));
		ci.setUser(prop.getProperty(CONFIG_USERNAME));
		ci.setPassword(prop.getProperty(CONFIG_PASSWORD));
	}
	

	public static String[]  getItems() {
		String[] items = null;
		String sArray = prop.getProperty(CONFIG_ITEMS);
		if(sArray.length()>0){
			items = sArray.split(",");
		}

		return items;
	}
}
