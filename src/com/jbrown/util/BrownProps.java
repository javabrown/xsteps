package com.jbrown.util;

import static com.jbrown.robo.KeysI.*;

public class BrownProps {
	static{
		System.setProperty(APP_LAUNCHER_K, APP_LAUNCHER_CLASS_K);
		System.setProperty(APP_TEST_MODULE_K, APP_TEST_MODULE_CLASS_K);
	}
	
	public static String getProps(String propertyName){
		return System.getProperty(propertyName);
	}
	
	public static Class getTestLauncher() {
		String cName = getProps(APP_TEST_MODULE_K);
		try {
			return Class.forName(cName);
		} catch (ClassNotFoundException e) {
			System.out.println("Error creating module-class. Verfify setting");
			e.printStackTrace();
		}

		return null;
	}
	
	public static Class getLauncher() {
		String cName = getProps(APP_LAUNCHER_K);
		try {
			return Class.forName(cName);
		} catch (ClassNotFoundException e) {
			System.out.println("Error creating module-class. Verfify setting");
			e.printStackTrace();
		}

		return null;
	}
	
	public static boolean instanceOfLauncherOrModuleApp(Object object){
		return (getLauncher() != null) && getLauncher().isInstance(object) || 
				(getTestLauncher() != null) && getTestLauncher().isInstance(object);
	}
}
