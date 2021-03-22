package com.pino.project.ocpairprogramming.java8.ocp.chapter4.advancedstreampipeline;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class ExceptionCaseStudyHelper {
	
	private static List<String> create() throws IOException {//Checked exception
		throw new IOException();
	}
	
	private static List<String> createBetterSafe(){
		try {
			return create();//throws a checked exception
		} catch (IOException e) {
			throw new RuntimeException(e);//wrap it up in an unchecked
		}
	}
	
	public static void main(String[] args) throws IOException {
		Supplier<List<String>> s3 = ExceptionCaseStudyHelper::createBetterSafe;
	}

}
