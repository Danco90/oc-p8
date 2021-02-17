package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

public class InteractingWithUsers {
	
	public static void main(String[] args) throws IOException {
		Console console = System.console();
		//The Old Way
//		systemInSample();
		//The New way
//		consoleSample(console);
		//format() and printf()
		try {
			consoleSamplePrint(console);
		} catch (RuntimeException runEx)	{
			System.out.print(runEx.getMessage());
			System.out.println("Welcome to Our Zoo!");
			System.out.format("\nOur zoo has %d %s and %s %d people.", 391, "animals", "employs", 25);
			System.out.println();
			System.out.printf("The zoo spans %.2f acres.", 128.91);// up to 2 decimal
		}
		//flush(),which forces any buffered output to be written immediately.
		//and readline()
		try {
			consoleReadInputSample(console);
		} catch (RuntimeException runEx) {
			System.out.println(runEx);
			System.out.print("How excited are you about your trip today? ");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in));
			String excitementAnswer = reader.readLine();
			System.out.print("Please enter your name: ");
			String name = reader.readLine();
			Integer age = null;
			System.out.print("What is your age? ");
			String value = reader.readLine();//throw IOException
			age = Integer.valueOf(value);
			System.out.println();
			
			System.out.format("Your name is %s %n", name);
			System.out.println();
			System.out.format("Your age is %d", age);
			System.out.println();
			System.out.printf("Your excitement level is: "+ excitementAnswer);
		}
		//readPassword(), which returns a Character Array so that as soon as the data is read and used, 
		//the sensitive pwd data in the array can be erased by writing garbage data. This would remove the password from the mem
		//before it would be removed by garbage collection if a String value were used.
		try {
			passwordCompareSample(console);
		} catch (RuntimeException runEx) {
			System.out.println(runEx+". The pwd will be visible, WATCH your back! ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please enter your password: ");
			String [] password = reader.readLine().split(".");//any character
			System.out.format("Enter your password again:");
			String [] verify = reader.readLine().split(".");
			boolean match = Arrays.equals(password, verify);
			
			//Immediately clear passwords from memory
			for(int i =0; i<password.length; i++) 
			{
				// Approach A)
				password[i]="x";
				// Or Approach B)
				Arrays.fill(password,'x');
			}
			
			System.out.format("Your password was %s", (match ? "correct" : "incorrect"));
		}
	}
	
	private static void passwordCompareSample(Console console) throws IOException {
		System.out.println(":: Password Compare Sample ::");
		if(console == null) {
			throw new RuntimeException("Console not available");
		} else {
			char [] password = console.readPassword("Enter your password:");
			console.format("Enter your password again:");
			console.flush();//right after a print through a Console or PrintWriter
			char [] verify = console.readPassword();
			boolean match = Arrays.equals(password, verify);
			
			//Immediately clear passwords from memory
			// Approach A)
			for(int i =0; i<password.length; i++) 
			{
				password[i]='x';
				verify[i]='y';
			}
			// Or Approach B)
			console.format("Your password was %s", (match ? "correct" : "incorrect"));
		}
	}
	
	private static void systemInSample() throws IOException {
		System.out.println("Old way: System.in sample ");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("Please type something .. and press enter : ");
		String userInput = reader.readLine();
		System.out.println("You entered the following: "+userInput);
	}
	
	private static void consoleSample(Console console) throws IOException {
		System.out.print("\nNew way: Console sample ");
		if(console != null) {
			console.writer().println ("available");
			console.writer().println ("Please type something .. and press enter : ");
			String userInput = console.readLine();
			console.writer().println ("You entered the following: "+userInput);
		} else {
			System.out.print("not available");
		}
	}
	
	private static void consoleSamplePrint(Console console) throws RuntimeException{
		System.out.print("\nConsole Sample Print ");
		if(console == null) {
			throw new RuntimeException("Console not available");
		} else {
			console.writer().println("Welcome to Our Zoo!");
			console.format("Our zoo has %d %s and %s %d people.", 391, "animals", "employs", 25);
			console.writer().println();
			console.printf("The zoo spans %f acres.", 128.91);//up to 2 decimal
		}
	}
	
	private static void consoleReadInputSample(Console console) throws IOException {
		if(console == null) {
			throw new RuntimeException("Console not available");
		} else {
			console.writer().print("How excited are you about your trip today? ");
			console.flush();
			String excitementAnswer = console.readLine();
			String name = console.readLine("Please enter your name: ");
			Integer age = null;
			console.writer().print("What is your age? ");
			console.flush();
			BufferedReader reader = new BufferedReader(console.reader());
			String value = reader.readLine();//throw IOException
			age = Integer.valueOf(value);
			console.writer().println();
			
			console.format("Your name is %s %n", name);
			console.writer().println();
			console.format("Your age is %d", age);
			console.printf("Your ecitement level is: "+ excitementAnswer);
		}
		
	}

}
