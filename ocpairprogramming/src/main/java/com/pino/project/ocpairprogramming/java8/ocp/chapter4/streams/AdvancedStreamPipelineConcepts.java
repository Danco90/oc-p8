package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.util.Optional;

public class AdvancedStreamPipelineConcepts {

	public static void main(String[] args) {
		threeDigitNoFunctional(Optional.empty());
		threeDigit(Optional.empty());
		
		threeDigitNoFunctional(Optional.of(4));
		threeDigit(Optional.of(4));
		
		threeDigitNoFunctional(Optional.of(123));
		threeDigit(Optional.of(123));
		
		Optional<String> optional = Optional.of("Pippo");
		Optional<Integer> result = optional.map(String::length);
		System.out.println("result=" + result.get());
		
		//PROBLEM 1 :
//		result = optional.map(AdvancedStreamPipelineConcepts::calculator);//DOES NOT COMPILE
		// due to Optional<Optional<Integer>> returned
		
		//SOLUTION : call flatMap()
		result = optional.flatMap(AdvancedStreamPipelineConcepts::calculator);

	}
	
	static Optional<Integer> calculator(String s){
//		return optional.map(String::length);
		return Optional.of(s).map(String::length);
	}
	
	private static void threeDigitNoFunctional(Optional<Integer> optional) {
		if (optional.isPresent()) 
		{ // outer if
			System.out.print("\nouter if ");
			Integer num = optional.get();
			String string = "" + num;
			if (string.length() == 3) // inner if
				System.out.println(string);
		} 
	}
	
	private static void threeDigit(Optional<Integer> optional) {
		optional.map(n -> ""+n)
				.filter(s -> s.length()==3 )
				.ifPresent(System.out::println);
	}

}
