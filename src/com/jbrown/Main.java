package com.jbrown;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import org.springframework.web.util.WebUtils;

import com.jbrown.ui.Launcher;

 

public class Main {
	public static void main(String[] args) throws IOException{
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.setProperty("javax.swing.plaf.ColorUIResource.useLegacyMergeSort", "true");
		
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("launcher.xml");
		
		Launcher obj = (Launcher) context.getBean("launcher");
	    obj.launch();
	}
}
