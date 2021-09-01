package com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Tax_en_US extends ListResourceBundle {

	protected Object[][] getContents() {
		return new Object[][] {{"tax", new UsTaxCode()}};
	}
	    
	public static void main(String[] args) {
//		ResourceBundle rb = ResourceBundle.getBundle(
//				"com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles.Tax");	//uses the default locale
//		System.out.println(rb.getObject("tax"));//MissingResourceException because it can't find bundle with it_IT
		ResourceBundle rb2 = ResourceBundle.getBundle(
				"com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles.Tax", Locale.US);	//package
		System.out.println(rb2.getObject("tax"));//prints object
		
		Locale locale = new Locale("en", "CA");
		ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
		System.out.print(rb.getString("hello"));
		System.out.print(". ");
		System.out.print(rb.getString("name"));
		System.out.print(" ");//Vancouver Zoo
		System.out.print(rb.getString("open"));//is open. 
		System.out.print(" ");
		System.out.print(rb.getString("visitor"));//Canada visitor
		
		
	}
}
