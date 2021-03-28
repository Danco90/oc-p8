package com.pino.project.ocpairprogramming.java8.ocp.chapter4.advancedstreampipeline;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

/**
 * <R, A> R collect(Collector<? super T, A, R> collector);
 * 
 * <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);
 * @author matteodaniele
 *
 */
public class CollectingResults {

	public static void main(String[] args) {
		
		Stream<String> s1 = Stream.of("3", "4", "5");
		//	averagingDouble(ToDoubleFunction f) to find arithmetic mean
		double doubleAns = s1
						.collect(Collectors
									.averagingDouble(//return a Double wrapper class
											num -> Double.parseDouble(num)));//the parse returns a primitive double
        System.out.println(doubleAns);//4.0 which is a Double
        
		//	static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper) 
        // returns a Double when passed to collect() as well because average always returns a double
        s1 = Stream.of("3", "4", "5");
        double intAns = s1
				.collect(Collectors //eventually return a Double wrapper class
							.averagingInt(
									num -> Integer.parseInt(num)));//returns an int
        System.out.println(intAns);//4.0 always a Double
        
        //	averagingLong(ToLongFunction f) returns a Double as well because average always returns a double
        s1 = Stream.of("3", "4", "5");
        double longAns = s1
				.collect(Collectors
							.averagingLong(//return a Double wrapper class
									num -> Long.parseLong(num)));//returns a long
        System.out.println(longAns);//4.0 still a Double
        
        //	static <T> Collector<T, ?, Long> counting(). See example of groupingBy(Function f,Collector dc)
        s1 = Stream.of("3", "4", "5");
        long count = s1.collect(Collectors.counting());
        System.out.println("count :"+count);
        
        // groupingBy(Function f)
        Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
//      Stream<Character> ohMyChar = Stream.of('l', 't', 'b');
  		Map<Integer, List<String>> map = //Always returns that by default
  				ohMy.collect(
  				Collectors.groupingBy(//groups String values by Integer keys. Key is the return type of the Function 
  						String::length));//the function takes a String and returns an Integer
  		System.out.println(map); // {5=[lions, bears], 6=[tigers]}
  		
  		System.out.println("groupingBy(Function<Boolean,String>) non empty Stream");
  		ohMy = Stream.of("lions", "tigers", "bears");
  		Map<Boolean,List<String>> map1 = ohMy.collect(
  				Collectors.groupingBy(s -> s.length() > 7));
  		System.out.println(map1);//{false=[lions, tigers, bears]}
  		
  		System.out.println("groupingBy(Function<Boolean,String>)  empty Stream");
  		ohMy = Stream.empty();
  		Map<Boolean,List<String>> map11 = ohMy.collect(
  				Collectors.groupingBy(s -> s.length() > 7));
  		System.out.println(map11);//{}
        
		// groupingBy(Function f,Collector dc) Example 1
  		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Integer, Long> map2 = ohMy.collect(
				Collectors.groupingBy(//the value is Long because Collector counting() returns a Long
						String::length, Collectors.counting()));
		System.out.println(map2); // {5=2, 6=1}
		
		ohMy = Stream.of("lions", "tigers", "bears");
		// groupingBy(Function f,Collector dc) Example 2
		Map<Integer, Optional<Character>> map3 = ohMy.collect(//the value is an Optional<Character> as the collector mapping() returns an Optional due to the collectors.minBy
				Collectors.groupingBy(String::length,
						Collectors.mapping(s -> s.charAt(0),//Add another level of collector and return a Collector
									       Collectors.minBy(Comparator.naturalOrder()))));//minBy() returns an inner Optional()
				System.out.println(map3); // {5=Optional[b], 6=Optional[t]}
				
		// groupingBy(Function f,Collector dc) Example 3
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Integer, Set<String>> map4 = ohMy.collect(//the value is a set since it is specified as 2nd parameters into the groupingBy
				Collectors.groupingBy(String::length, Collectors.toSet()));
		System.out.println(map4); // {5=[lions, bears], 6=[tigers]}
		
		//	groupingBy(Function f, Supplier s, Collector dc)  - Example 1
		ohMy = Stream.of("lions", "tigers", "bears");
		TreeMap<Integer, Set<String>> map5 = ohMy.collect(//The Return type has changed in TreeMap since we specified it as Supplier parameters we've introduce 
				Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));
		System.out.println(map5); // {5=[lions, bears], 6=[tigers]}
		
		//	groupingBy(Function f, Supplier s, Collector dc)  - Example 2
		ohMy = Stream.of("lions", "tigers", "bears");
		TreeMap<Integer, List<String>> map6 = ohMy.collect(//The Return type has changed in List (Default ArrayList()) since we specified it as Supplier parameters we've introduce 
				Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
		System.out.println(map6);// {5=[lions, bears], 6=[tigers]} same representation despite different type
				
		// partitioningBy(Predicate<? super T> predicate) Example 1
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Boolean, List<String>> map7 = ohMy.collect(//The Key is always Boolean. therefore. no need to change the type of Map with a Supplier parameters as per groupingBy
				Collectors.partitioningBy(s -> s.length() <= 5));
		System.out.println(map7); // {false=[tigers], true=[lions, bears]}
		
		// partitioningBy(Predicate<? super T> predicate) Example 2
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Boolean, List<String>> map8 = ohMy.collect(//The Key is always Boolean. therefore. no need to change the type of Map with a Supplier parameters as per groupingBy
				Collectors.partitioningBy(s -> s.length() <= 6));
		System.out.println(map8); // {false=[], true=[lions, tigers, bears]}
		
		// partitioningBy(Predicate<? super T> predicatem, Collector dc)
		Stream<Integer> s2 = Stream.of(3, 4, 5);
		Map<Boolean, Long> map9 = s2.collect(//The Key is always Boolean. therefore. no need to change the type of Map with a Supplier parameters as per groupingBy
				Collectors.partitioningBy(
						num -> (num > 3),
						Collectors.counting()));
		System.out.println(map9); // {false=1, true=2}
		
		System.out.println("partitioningBy(Predicate<? super T> predicate) an empty stream");
		Stream<String> ss2 = Stream.empty();
  		Map<Boolean,List<String>> map111 = ss2.collect(
  				Collectors.partitioningBy(s -> s.length() > 7));
  		System.out.println(map111);//{false=[], true=[]}
		
		//	mapping(Function f, Collector dc) Example 1
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Integer, Optional<Character>> map10 = ohMy.collect(
				Collectors.groupingBy(
						String::length,
						Collectors.mapping(//add another level of Collector
								s -> s.charAt(0),
								Collectors.minBy(Comparator.naturalOrder()))));
		System.out.println(map10); // {5=Optional[b], 6=Optional[t]}
		
		//example toMap(Function k, Function v, BinaryFunction m)
		System.out.println("\nTRICKY example toMap(Function k, Function v, BinaryFunction m)");
		Stream<String> s = Stream.of("speak", "bark", "meow", "growl"); 
		BinaryOperator<String> merge = (a, b) -> a;
		Map<Integer, String> myMap = s.collect(toMap(String::length, k -> k, merge)); 
		System.out.println(myMap.size() + " " + myMap.get(4));//(*)
		System.out.println("myMap="+myMap);
		//(*)WATCH OUT: It is not like  in a list. It does not retrieve the element at index but with key=4 
		
	}

}
