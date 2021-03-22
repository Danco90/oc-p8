package com.pino.project.ocpairprogramming.java8.ocp.chapter4.advancedstreampipeline;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class ExceptionCaseStudy {
	private static List<String> create() throws IOException {//Checked exception
		throw new IOException();
	}
	
	private static List<String> createSafe() {//Checked exception
		try {
			return ExceptionCaseStudy.create();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//Functional interfaces do not declare checked exceptions
		
		//Using a class with a method that throws a checked exception
		
		//Case a : use in a stream
		ExceptionCaseStudy.create().stream().count();
		
		//Case b : method reference expanding to a lambda which does not declare a checked exception
//		Supplier<List<String>> s = ExceptionCaseStudy::create; //DOES NOT COMPILE because exception type is unhandled
		
		//Ugly solution : we catch the checked exception and turn it into an unchecked one
		Supplier<List<String>> s = () -> {
			try {
				return ExceptionCaseStudy.create();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};
			
		//Proper solutions : we catch the checked exception and turn it into an unchecked one
		Supplier<List<String>> s2 = ExceptionCaseStudy::createSafe;
		
	}
}
