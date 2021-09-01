package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvanceFilteringDemo {
	
	public static boolean dinamicFiltering(String severity, String messageType) {
		List<Predicate<String>> allPredicates = new ArrayList<Predicate<String>>();
		String [] options = severity.split(",");
		Stream<String> stream1 = Arrays.stream(options);
		stream1.forEach(s -> 
				allPredicates.add(str -> s.equals(str)));
		return allPredicates.stream().reduce(x -> false, Predicate::or).test(messageType);
	}

	public static void main(String[] args) {
		String severity = "WARN,ERROR";
		String messageType1 = "WARN";
		System.out.println(dinamicFiltering(severity, messageType1));
		String messageType2 = "ERROR";
		System.out.println(dinamicFiltering(severity, messageType2));
		String messageType3 = "INFO";
		System.out.println(dinamicFiltering(severity, messageType3));
	    
	}

}
