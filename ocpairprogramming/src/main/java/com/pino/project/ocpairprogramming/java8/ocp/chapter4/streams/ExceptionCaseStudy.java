package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * Checked Exceptions and Functional Interfaces
 */
public class ExceptionCaseStudy {
	
	public static void main(String[] args) {
		
			//Problem 1: 
			try {
				ExceptionCaseStudy.create().stream().count();//fine
			} catch (IOException e) {
				e.printStackTrace();
			}//the calling method handles or declares checked exception thrown by create()
		
			//#Problem 2: method reference without exception declaration
			//Supplier<List<String>> s = ExceptionCaseStudy::create();//DOES NOT COMPILE because
			//the lambda to which this method reference expands does declare an exception.
			
			//Solution to #problem 2 : using a wrapper method for exception handling
			Supplier<List<String>> s = ExceptionCaseStudy::createSafe;
	}
	
	private static List<String> createSafe() {
		try {
			 return create();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static List<String> create() throws IOException {
		throw new IOException();
	}

}
