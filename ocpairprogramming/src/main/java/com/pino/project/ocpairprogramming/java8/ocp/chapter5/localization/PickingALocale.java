package com.pino.project.ocpairprogramming.java8.ocp.chapter5.localization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class PickingALocale {

	public static void main(String[] args) {
		Locale locale = Locale.getDefault();
		System.out.println(locale);//it_IT
		//Creating a locale
		//1rst constants
		System.out.println(locale.GERMAN);//de
		System.out.println(locale.GERMANY);//de_DE
		
		//2nd constructors
		System.out.println(new Locale("fr"));//fr
		System.out.println(new Locale("hi","IN"));//hi_IN
		
		//3rd Builder design pattern : you can specify properties in any orders
		Locale l1 = new Locale.Builder()
				.setLanguage("en")
				.setRegion("US")
				.build();
		System.out.println(l1);
		Locale l2 = new Locale.Builder()
				.setLanguage("US")//it will automatically converts to lowercase
				.setRegion("en")//or uppercase, which is legal but BAD example!
				.build();
		System.out.println(l2);//us_EN it does not exist AT ALL
		
		//Set a new default locale
		System.out.println(Locale.getDefault());// it_IT
		Locale locale1 = new Locale("hu");
		Locale.setDefault(locale1);
		System.out.println(Locale.getDefault());//hr
		
		//Using a Resource Bundle
		Locale us = new Locale("en", "US");
		Locale france = new Locale("fr", "FR");
		Locale englishCanada = new Locale("en", "CA");
		Locale frenchCanada = new Locale("fr", "CA");
		
		printProperties(us);//it stops at Zoo_en, since it cannot find Zoo_en_US.properties
		System.out.println();
		printProperties(france);// it search for Zoo_fr_FR but it founds just Zoo_fr, which is OK.
		System.out.println();
		printProperties(englishCanada);
		//name Vancouver Zoo
		//hello Hello
		//open, The zoo is open
		//visitor=Canada visitor
		System.out.println();
		printProperties(frenchCanada);
		
		
		
	}
	
	public static void printProperties(Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("Zoo",locale);
		//Method A
//		System.out.println(rb.getString("hello"));
//		System.out.println(rb.getString("open"));
//		System.out.println(rb.getString("key"));//throws exception for missing key in Zoo_fr
//		System.out.println(rb.getString("long"));//so does it
		//Handling Variables Inside Resource Bundles
//		Locale locale1 = new Locale("hu");
//		Locale.setDefault(locale1);
		ResourceBundle rbhu = ResourceBundle.getBundle("Zoo",new Locale("hu"));
		String format = rbhu.getString("helloByName");
		String formatted = MessageFormat.format(format, "Pino");
		System.out.println(formatted);
		
		//Method B prevents exception in case of missing key
		System.out.println("---");
		Set<String> keys = rb.keySet();
		keys.stream().map(k -> k + " " + rb.getString(k))
		.forEach(System.out::println);//The boundles are walked in the reverse order 
		
		//Converting from ResourceBundle to Properties
		Locale.setDefault(new Locale("en","US"));
		ResourceBundle rb2 = ResourceBundle.getBundle("com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles.Tax",locale);
		toProperty(rb2);
		
		//Formatting Numbers
		//1. Create a NumberFormat, by the factory methods it provides in order to get the desired formatter.
		/*
		 * TabLe 5. 9 Factory methods to get a NumberFormat
		   |	Description 					|	Using Default Locale and a Specified Locale	
		   +-------------------------------+------------------------------------------------+
		   |A general purpose formatter		NumberFormat.getInstance()
		   |									NumberFormat.getInstance(locale)
		   +-------------------------------------------------------------------------------
		   |Same as getInstance				NumberFormat.getNumberInstance()
	   	   |								    NumberFormat.getNumberInstance(locale)
		   +-------------------------------+------------------------------------------------
		   |For formatting monetary amounts NumberFormat.getCurrencyInstance()
		   |								    NumberFormat.getCurrencyInstance(locale)
		   +-------------------------------+--------------------------------------------
		   |For formatting percentages      NumberFormat.getPercentInstance()
		   |									NumberFormat.getPercentInstance(locale)
		   +-------------------------------+--------------------------------------------
		   |Rounds decimal values before     NumberFormat.getIntegerInstance()
		   |displaying (not on the exam)     NumberFormat.getIntegerInstance(locale)
		   +--------------------------------+-------------------------------------------
		 */
		 //2. Call format() to turn a number into a String 
		 //3  And call parse() to turn a String into a number
		//NB: The format classes are not thread-safe. Therefore DO NOT store them in instance variables or static variables,
		//   but just local variables into methods
		
		//Formatting
		 
	}
	
	public static void toProperty(ResourceBundle rb) {
		Properties props = new Properties();
		rb.keySet().stream()
			.forEach(k -> props.put(k, 
//					rb.getString(k)));
					rb.getObject(k)));
		System.out.println(props.get("tax"));//UsTaxCode [getClass()=class com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles.UsTaxCode, hashCode()=1078694789, toString()=com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles.UsTaxCode@404b9385]
		System.out.println(props.getOrDefault("ta", new Object()));//java.lang.Object@7ba4f24f
		System.out.println(props.getProperty("key"));//Only String, it returns null as it does not exist
		System.out.println(props.getProperty("key", "default"));//Only String, it return the default value as it cannot find such key
		
	}

}
