package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.workingwithgenerics;

import java.util.ArrayList;
import java.util.List;

public class LegacyAutoboxing {

	public static void main(String[] args) {
		List numbers = new ArrayList<>();
		numbers.add(5);//int is autoboxed in Integer,which is an Object
//		int result = numbers.get(0);//Compilation ERROR. the returned Object has to be casted into an Integer before unboxing. 
		int result = (Integer)  numbers.get(0);//OK
		System.out.print(result);
	}

}
