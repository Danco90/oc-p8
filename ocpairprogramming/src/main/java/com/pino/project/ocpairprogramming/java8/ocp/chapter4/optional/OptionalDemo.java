package com.pino.project.ocpairprogramming.java8.ocp.chapter4.optional;

import java.util.Optional;

/**
 * Table 4 . 2 Optional instance methods
* +-------------------------+----------------------------+---------------------------------+
* |Method					|When Optional Is Empty 		|When Optional Contains a Value
* |
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
		//int res = sum / scores.length;//from OCA by DEFAULT int / int is int
		//return Optional.of(res);//It does not compile as res is autoboxed in Integer
		return Optional.of((double) sum / scores.length);
	}
	

	public static void main(String[] args) {
		//Printing an Optional Automatically unboxes : get the value it contains out of the box
		//Pay carefully attention to how to wrapping a value inside an Optional
		//Approach A - handle the returned Optional content
		System.out.println("Approach A - handle the returned Optional content");
		System.out.println(average(90, 100)); // Optional[95.0]
		System.out.println(average()); // Optional.empty an not null inside 
		
		//Demonstrate how to properly use the get()
		Optional<Double> opt = average(90, 100);
		if (opt.isPresent())
			System.out.println(opt.get()); // 95.0
		
		//1. If there's a value it runs the Consumer. It skips the Consumer otherwise.
		opt.ifPresent(System.out::println);
		
		//2. More appropriate Methods when a value isn't present
		Optional<Double> opt2 = average();
		System.out.println(opt2.orElse(Double.NaN));
		//Methods which takes Supplier
		System.out.println(opt2.orElseGet(() -> Math.random()));
//		System.out.println(opt2.orElseThrow(() -> new IllegalStateException()));
		
		//Good Vs Bad approach -
		System.out.println("Good Vs Bad approach for empty optional (value not present)");
		opt = average();
		System.out.println(opt); //GOOD. it'll print Opional.empty
//		System.out.println(opt.get()); // (*) bad . It throws a NoSuchElementException
		//(*) You CAN'T get a value out of the Option box if there's nothing inside.
		//That's why you'd better handle it with an Optional.empty return type
		
		//Approach B - using a Ternary operator 
		System.out.println("Approach B - using a Ternary operator");
		String value = "Pino";
		Optional o = (value== null) ? Optional.empty(): Optional.of(value);
		System.out.println(o);
		String valueNull = null;
		Optional o2 = (valueNull== null) ? Optional.empty(): Optional.of(valueNull);
		System.out.println(o2);
		
		//Approach C (BEST) - built-in factory STATIC method
		System.out.println("Approach C (BEST) - built-in factory STATIC method");
		o = Optional.ofNullable(value);
		System.out.println(o);
		System.out.println(Optional.ofNullable(valueNull));
		
		Optional<Double> opt3 = average(90, 100);
		System.out.println(opt3.orElse(Double.NaN));//NO NEED TO USE the method since the value exist
		Optional<Double> opt4 = average();
		System.out.println(opt4.orElse(Double.NaN));//It prints NaN as the name of the constant, not the real value
		System.out.println(opt3.orElseGet(() -> Math.random()));
		System.out.println(opt3.orElseThrow(() -> new IllegalStateException()));
		
		//Is it possible to  chain Optional calls ? It is not possible as the return void | boolean | a value
		
		
	}
	
	

}
