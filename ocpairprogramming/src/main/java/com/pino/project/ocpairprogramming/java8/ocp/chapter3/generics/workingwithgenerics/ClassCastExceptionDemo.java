package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.workingwithgenerics;

import java.util.ArrayList;
import java.util.List;

public class ClassCastExceptionDemo {
	
	static void printNames(List list) {//WARNING: raw type list declaration
		for(int i=0; i< list.size(); i++) {
			String name = (String) list.get(i); //It throws class cast exception at runtime
			//as java.lang.StringBuilder cannot be cast to java.lang.String
			System.out.println(name);
		
		}
	}

	public static void main(String[] args) {
		List names = new ArrayList();//Multiple markers WARNING: Raw type List reference + initialization
		names.add(new StringBuilder("Webby"));//WARNING: add(Object) belongs to raw type List. reference should be parameterized
		printNames(names);
	}

}
