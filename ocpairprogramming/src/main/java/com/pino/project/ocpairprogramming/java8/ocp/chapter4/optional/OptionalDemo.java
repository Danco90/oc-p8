package com.pino.project.ocpairprogramming.java8.ocp.chapter4.optional;

import java.util.Optional;

/**
 * Table 4 . 2 Optional instance methods
* +-------------------------+----------------------------+---------------------------------+
* |Method					|When Optional Is Empty 		|When Optional Contains a Value
* |get() 					|Throws an exception 		|Returns value
* |ifPresent(Consumer c) 	|Does nothing 				|Calls Consumer c with value
* |isPresent() 				|Returns false 				|Returns true
* |orElse(T other) 			|Returns other parameter		|Returns value
* +-------------------------+----------------------------+---------------------------------+
* |orElseGet(Supplier s) 	|Returns result of calling	|Returns value
* |							|Supplier
* +-------------------------+----------------------------+---------------------------------+
* |orElseThrow(Supplier s)	|Throws exception created	|Returns value
* | 							|by calling Supplier			|
* +-------------------------+----------------------------+---------------------------------+
 * 
 * @author matteodaniele
 *
 */
public class OptionalDemo {
	
	public static Optional<Double> average(int...scores) {
		if (scores.length == 0) return Optional.empty();
		int sum = 0;
		for(int score: scores) sum += score;
		return Optional.of((double) sum / scores.length);
	}
	

	public static void main(String[] args) {
		//Printing an Optional Automatically unboxes : get the value it contains out of the box
		//Pay carefully attention to how to wrapping a value inside an Optional
		//Approach A - handle the returned Optional content
		System.out.println(average(90, 100)); // Optional[95.0]
		System.out.println(average()); // Optional.empty
		
		//Demonstrate how to properly use the get()
		Optional<Double> opt = average(90, 100);
		if (opt.isPresent())
			System.out.println(opt.get()); // 95.0
		
		//1. If there's a value it runs the Consumer. It skips the Consumer otherwise.
		opt.ifPresent(System.out::println);
		
		//2. More appropriate Methods when a value isn't present
		Optional<Double> opt2 = average();
		System.out.println(opt2.orElse(Double.NaN));
		//Methods which take Supplier
		System.out.println(opt2.orElseGet(() -> Math.random()));
//		System.out.println(opt2.orElseThrow(() -> new IllegalStateException()));
		
		//Bad approach -
		opt = average();
//		System.out.println(opt.get()); // (*) bad . It throws a NoSuchElementException
		//(*) You CAN'T get a value out of the Option box if there's nothing inside.
		//That's why you'd better handle it with an Optional.empty return type
		
		//Approach B - using a Ternary operator 
		String value = "Pino";
		Optional o = (value== null) ? Optional.empty(): Optional.of(value);
		System.out.println(o);
		
		//Approach C - built-in factory STATIC method
		o = Optional.ofNullable(value);
		System.out.println(o);
		
		Optional<Double> opt3 = average(90, 100);
		System.out.println(opt3.orElse(Double.NaN));//NO NEED TO USE the method since the value exist
		System.out.println(opt3.orElseGet(() -> Math.random()));
		System.out.println(opt3.orElseThrow(() -> new IllegalStateException()));
		
		//You can chain  Optional calls
		
	}
	
	

}
