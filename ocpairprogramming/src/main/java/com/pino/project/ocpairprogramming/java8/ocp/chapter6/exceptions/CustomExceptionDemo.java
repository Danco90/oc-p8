package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.MissingResourceException;

public class CustomExceptionDemo {

	public static void main(String[] args) throws CannotSwimException {
		
		//1
//		throw new CannotSwimException();
		//2) Wrap an exception
		//throw new CannotSwimException(new RuntimeException());
		
		//3)Pass a custom message
		//throw new CannotSwimException("broken fin");//Java automatically print the stacktrace
		
		//Print stacktrace on your own
//		try {
//			throw new CannotSwimException("broken fin");
//		} catch (CannotSwimException e) {
//			e.printStackTrace();
//		}
		
		//Approach A : try-catch - Duplicating code example
//		try {
//			Path path = Paths.get("animals/dolphinsBorn.txt");//it does not exist, therefore any attempt to read it is will throw an I/O exception
//			String text = new String(Files.readAllBytes(path));//Indeed it throws a java.nio.file.NoSuchFileException because of the non-existing file
//			LocalDate date = LocalDate.parse(text);//it throws DateTimeParseException
//			System.out.println(date);
//		} catch (DateTimeParseException e) {
//			e.printStackTrace();//checked parsing exception
//			throw new RuntimeException(e);//wrapped into a runtime one
//		} catch (IOException e) {
//			e.printStackTrace();//(*)checked I/O exception
//			throw new RuntimeException(e);//(*)wrapped into a runtime one
//		}
		//(*) Duplicating code is bad if we want to change the code in the future
		
		//Approach B (Improved) : try-catch with no duplication!
//		try {
//			Path path = Paths.get("animals/dolphinsBorn.txt");//it does not exist, therefore any attempt to read it is will throw an I/O exception
//			String text = new String(Files.readAllBytes(path));//Indeed it throws a java.nio.file.NoSuchFileException because of the non-existing file
//			LocalDate date = LocalDate.parse(text);//it throws DateTimeParseException
//			System.out.println(date);
//		} catch (Exception e) {// BAD approach since it catches other exceptions too!
//			e.printStackTrace();//checked IOException
//			throw new RuntimeException(e);//wrapped into a runtime one
//		} 
		
		//Approach C : extract the duplicate code into a helper method
//		try {
//			Path path = Paths.get("animals/dolphinsBorn.txt");//it does not exist, therefore any attempt to read it is will throw an I/O exception
//			String text = new String(Files.readAllBytes(path));//Indeed it throws a java.nio.file.NoSuchFileException because of the non-existing file
//			LocalDate date = LocalDate.parse(text);//it throws DateTimeParseException
//			System.out.println(date);
//		} catch (DateTimeParseException e) {
//			handleException(e);//(*)
//		} catch (IOException e) {
//			handleException(e);//(*)
//		}
		//(*) We still have a little duplication in here. 
		
		
	}
	
	
	private static void handleException(Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}

}
