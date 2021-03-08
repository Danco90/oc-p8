package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MultiCatchDemo {

	public static void main(String[] args) {
		//Multi-catch, which has the ability to catch multiple exceptions (not related with one another) in the same catch block
				try {
					Path path = Paths.get("animals/dolphinsBorn.txt");//it does not exist, therefore any attempt to read it is will throw an I/O exception
					String text = new String(Files.readAllBytes(path));//Indeed it throws a java.nio.file.NoSuchFileException because of the non-existing file
					LocalDate date = LocalDate.parse(text);//it throws DateTimeParseException
					System.out.println(date);
				} catch (DateTimeParseException | IOException e) {//These checked exception are not related to one another
					e.printStackTrace();//checked DateTimeParseException OR IOException 
					throw new RuntimeException(e);//wrapped into a runtime one
				} 
				
				//Tricky case : Compilation error when an exception child is already caught by the father
//				try {
//					throw new IOException();
//				} catch (FileNotFoundException | IOException e) { }//DOES NOT COMPILE as they are of the same family : the first one is a subclass of the second one
				//Solution of the previous tricky case:
				try {
					throw new IOException();
				} catch (IOException e) { } //Swallowing exception won't print any stack trace
				
				
				//Allowed BAD practice: Catching a single exception type allows reassigning the variable in catch 
				try {
					throw new RuntimeException();
				} catch(RuntimeException e) {
					e = new RuntimeException(); // bad approach but valid !
				}
				//RULE: Multi-catch is Effectively Final - Java prevents us from reassigning the exception variable in multi-catch
//				try {
//					throw new IOException();
//				} catch(IOException | RuntimeException e) {
//					e = new RuntimeException(); // DOES NOT COMPILE
//				}
				
				//Review multi-catch : some errors hide others, so you might not see them all in the compiler.
				new MultiCatchDemo().doesNotCompile();

	}
	
	public void doesNotCompile() {//Once you start fixing some errors, you'll see the others.
		try {
			mightThrow();
		} catch (FileNotFoundException | IllegalStateException e) {
//		} catch (InputMismatchException e | MissingResourceException e) { // compilation error-1 : extra variable name		
//		} catch (SQLException | ArrayIndexOutOfBoundException e) { // compilation error-2 : unreachable code because of SQLException never thrown	
//		} catch (FileNotFoundException | IllegalArgumentException e ) {// compilation error-3: unreachable catch block because of duplicate FileNotFoundException			
		} catch (Exception e) {// to be always placed at the end of the list
//		} catch (IOException e) {// compilation error-4 : Unreachable code. it should be inverted with the previous catch 
		}
	}
	
	private void mightThrow() throws DateTimeParseException, IOException { }
	

}
