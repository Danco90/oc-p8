package com.pino.project.ocpairprogramming.java8.ocp.chapter4.advancedstreampipeline;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingResults {

	public static void main(String[] args) {
		
		Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
		//groupingBy(Function f,Collector dc)
		Map<Integer, Long> map = ohMy.collect(
				Collectors.groupingBy(//the value is Long because Collector counting() returns a Long
						String::length, Collectors.counting()));
		System.out.println(map); // {5=2, 6=1}
		
		ohMy = Stream.of("lions", "tigers", "bears");
		//groupingBy(Function f,Collector dc) always the same with two parameter
		Map<Integer, Optional<Character>> map2 = ohMy.collect(//the value is an Optional<Character> as the collector mapping() returns an Optional due to the collectors.minBy
				Collectors.groupingBy(String::length,
						Collectors.mapping(s -> s.charAt(0),//Add another level of collector and return a Collector
									       Collectors.minBy(Comparator.naturalOrder()))));//minBy() returns an inner Optional()
				System.out.println(map2); // {5=Optional[b], 6=Optional[t]}
	}

}
