package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AdvancedStreamPipelineConcepts {

	public static void main(String[] args) {
		//Linking Streams to the Underlying Data
		List<String> cats = new ArrayList<>();
		cats.add("Annie");
		cats.add("Ripley");
		Stream<String> stream = cats.stream();//since a stream is lazily evaluated it isn't actually created at this line, 
		//but just the object that knows where to look for the data when it is needed
		cats.add("KC");//it'll add one more item to the stream after its execution
		System.out.println(stream.count());//here the stream pipeline actually runs, looking at the source and seeing three elements.
		
		//Chaining Optionals - A few of intermediate operations for streams are available for Optional, 
		// such as map() and filter(), which returns an Optional regardless the fulfillment of their conditions
		System.out.println("Chaining Optionals");
		System.out.println("Case with empty optional");
		threeDigitNoFunctional(Optional.empty());
		threeDigit(Optional.empty());
		
		System.out.println("\nCase with non-empty optional (1 elem) and NON matching filter condition");
		threeDigitNoFunctional(Optional.of(4));
		threeDigit(Optional.of(4));
		
		System.out.println("\nCase with non-empty optional (1 elem) and matching filter condition");
		threeDigitNoFunctional(Optional.of(123));
		threeDigit(Optional.of(123));
		
		//Get an Optional<Integer> resulted as a function of a value contained into another Optional.
		System.out.println("\n Optional of another Optional example");
		
		Optional<String> optional = Optional.of("Pippo");
		Optional<Integer> result = optional.map(String::length);
		System.out.println("result=" + result.get());
		
		//PROBLEM 1 - static helper method to calculate something and return an Optional:
		System.out.println("\nPROBLEM 1 - static helper method to calculate something and return an Optional:");
		//result = optional.map(AdvancedStreamPipelineConcepts::calculator);//DOES NOT COMPILE 
		// due to Optional<Optional<Integer>> returned
		
		//SOLUTION : call flatMap() to remove one Optional level
		result = optional.flatMap(AdvancedStreamPipelineConcepts::calculator);

	}
	
	static Optional<Integer> calculator(String s){
//		return optional.map(String::length);
		return Optional.of(s).map(String::length);
	}
	
	private static void threeDigitNoFunctional(Optional<Integer> optional) {
		if (optional.isPresent()) 
		{ // outer if
			System.out.print("inside outer if (threeDigitNoFunctional) ");
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
