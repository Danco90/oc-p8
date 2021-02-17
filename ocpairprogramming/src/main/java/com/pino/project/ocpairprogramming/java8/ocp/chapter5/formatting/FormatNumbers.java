package com.pino.project.ocpairprogramming.java8.ocp.chapter5.formatting;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class FormatNumbers {

	public static void main(String[] args) {
		//1. Formatting
		//1.1 Formatting Numbers
		int attendeesPerYear = 3_200_000;
		int attendeesPerMonth = attendeesPerYear / 12;
		NumberFormat us = NumberFormat.getInstance(Locale.US);//Language and Country specific
		System.out.println(us.format(attendeesPerMonth));//266,666 . In US comma separates parts of large numbers
		NumberFormat g = NumberFormat.getInstance(Locale.GERMANY);
		System.out.println(g.format(attendeesPerMonth));//266.666 .Germany uses dot for this function
		NumberFormat ca = NumberFormat.getInstance(Locale.CANADA_FRENCH);
		System.out.println(ca.format(attendeesPerMonth));//266 666 .French Canadians use neither (just space)
		
		//1.2 Formatting Currencies
		double price = 48;//*
		//*NB: in the real world you had better use int or BigDecimal for money 
		NumberFormat us2 = NumberFormat.getCurrencyInstance();//Default it_IT Locale
		System.out.println(us2.format(price));//€ 48.00 with space in between
		Locale.setDefault(Locale.US);
		us2 = NumberFormat.getCurrencyInstance();//The new default is now en_USs
		System.out.println(us2.format(price));//$48.00 without any space in between
		
		//2.Parsing (String into a number) with parse()
		//Rule 1: If Locale is the US and the number contains commas, the commas are treated as formatting symbols
		//Rule 2: If the locale is a country or language that uses commas as decimal separator, the commas is treated as a decimal point.
		//NB: parse() throw the checked exception ParseExeption if they fail to parse.
		//TIPS: You can assume that exception are always properly handled, unless you saw parsing logic inside a method.
		//In that case, make sure ParseException or Exception is handled or declared
		NumberFormat en = NumberFormat.getInstance(Locale.US);
		NumberFormat fr = NumberFormat.getInstance(Locale.FRANCE);
		String s = "40.45";//pay attention to which countryde  does
		try {
			System.out.println(en.parse(s));//40.45
			System.out.println(fr.parse(s));//40 (it gets rid of the decimal as it does not use decimal point)
		} catch (ParseException e) { e.printStackTrace(); }
		
		//Extra Characters when Parsing
		//The parse method once it reaches a character that cannot be parsed, stops and the value is returned
		NumberFormat nf = NumberFormat.getInstance();
		String one = "456abc";
		String two = "-2.5165x10";
		String three = "x85.3";
		try {
			System.out.println(nf.parse(one));//456
			System.out.println(nf.parse(two));//-2.5165
			System.out.println(nf.parse(three));//Throws java.text.ParseException: Unparseable number: "x85.3"
			System.out.println(nf.parse(three));
		} catch (ParseException e) { //
			//e.printStackTrace(); 
			System.out.println(e);}
		
		//Parsing currency
		String amt = "$92,807.99";
		String amt2 = "$92.807,99";
		String amt3 = "$92807,99";
		NumberFormat cf = NumberFormat.getCurrencyInstance();//for the default it_IT which does uses dot '.' as decimal separator
		try {
			double value = (Double) cf.parse(amt);
			System.out.println(value); // 92807.99 because comma ',' is not treated as a decimal separator for default it_IT
			System.out.println((Double) cf.parse(amt2));//92.807 , the $ is escluded for it_IT, it searches for the first decimal separator for the given locale, which is a dot, 
			//and right after the parser method stops once it has reached any further characters that cannot be parsed into a number
			//System.out.println((Double) cf.parse(amt3));//without any compatible separator, it fails by throwing java.lang.ClassCastException: java.lang.Long cannot be cast to java.lang.Double
			//double separator is only a dot
			System.out.println((Long) cf.parse(amt3));//whereas this works since it is a long		
		} catch (ParseException e) { System.out.println(e);}
		
		System.out.println("\nFormatting Dates and Times");
		LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
		System.out.println(date.getDayOfWeek()); // MONDAY
		System.out.println(date.getMonth()); // JANUARY
		System.out.println(date.getYear()); // 2020
		System.out.println(date.getDayOfYear()); // 20
		LocalTime time = LocalTime.of(11, 12, 34);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		//A) We might want an ISO standard format
		System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));//yyyy-MM-dd
		System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));//hh-mm-ss
		System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));//yyyy-MM-ddThh-mm-ss
	    //B)- or we might want a localized formatter in the predefined short format
		DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);//d/MM/yy
		System.out.println(shortDateTime.format(dateTime));//1/20/20
	    System.out.println(shortDateTime.format(date));//1/20/20
	    //System.out.println(shortDateTime.format(time));//UnsupportedTemporalTypeException
	    date = LocalDate.of(2020, Month.JANUARY, 20);
	    time = LocalTime.of(11, 12, 34);
	    dateTime = LocalDateTime.of(date, time);
	    DateTimeFormatter shortF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
	    DateTimeFormatter mediumF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
	    System.out.println(shortF.format(dateTime)); // 1/20/20 11:12 AM
	    System.out.println(mediumF.format(dateTime)); // Jan 20, 2020 11:12:34 AM
	    //You can reference the object in any order
	    System.out.println(dateTime.format(shortDateTime));//1/20/20
	    System.out.println(date.format(shortDateTime));//1/20/20
//	    System.out.println(time.format(shortDateTime));////UnsupportedTemporalTypeException
	    
	    
	    //Custom  format
	    DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm");
	    System.out.println(dateTime.format(f));//january 20, 2020, 11:12
	    /*
	     * Table 5.10 ofLocalized methods
	     * 
	     */
	    
	    
	}

}
