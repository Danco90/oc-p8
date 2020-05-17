package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuiltinFunctionalInterfaces {

	public static void main(String[] args) {
		
		//Lazily evaluation ** - lambdas or method references which are declared will occur later when the method is run.
		//Supplier<T> - T get();
		System.out.println("Supplier<T> - T get()");
		//STATIC Method reference 
		Supplier<LocalDate> s1 = LocalDate::now;// L.Evaluated : declared now but executed later
		Supplier<LocalDate> s2 = () -> LocalDate.now();// L.Eval. declared now but executed later
		LocalDate d1 = s1.get();//executed now!
		LocalDate d2 = s2.get();//executed now!
		System.out.println(d1);
		System.out.println(d2);
		
		//CONSTRUCTOR Method reference 
		Supplier<StringBuilder> s3 = StringBuilder::new;
		Supplier<StringBuilder> s4 = () -> new StringBuilder();
		System.out.println(s3.get());
		System.out.println(s4.get());
		
		//CONSTRUCTOR Method reference 
		Supplier<ArrayList<String>> s5 = ArrayList<String>::new;
		System.out.println(s5);//it will print com.daniele.project.pairprogramming.java8.ocp.chapter4.streams.BuiltinFunctionalInterfaces$$Lambda$9/2065951873@6acbcfc0
		//Our test class is named BuiltIns, and it is in a package 'functionalinterface. 
		//Then $$ means that the class doesn’t exist in a class file on the file system. 
		//But it exists only in memory. 
		
		ArrayList<String> a1 = s5.get();
		System.out.println(a1);
		
		//Consumer<T> -  void accept(T t);
		System.out.println("\nConsumer<T> -  void accept(T t);");
		Consumer<String> c1 = System.out::println;//Method reference on instance to be given at runtime
		Consumer<String> c2 = x -> System.out.println(x);
		c1.accept("Annie");
		c2.accept("Annie");
		
		Consumer<List<Integer>> methodRef1 = Collections::sort;
		methodRef1.accept(Arrays.asList(1,4,3));
		
		//BiConsumer<T, U> void accept(T t, U u);
		Map<String, Integer> map = new HashMap<>();
		BiConsumer<String, Integer> b1 = map::put;//method reference on a particular instance
		BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
		b1.accept("chicken", 7);
		b2.accept("chick", 1);
		System.out.println(map);
		
		Map<String, String> map2 = new HashMap<>();
		BiConsumer<String, String> b3 = map2::put;
		BiConsumer<String, String> b4 = (k, v) -> map2.put(k, v);
		b3.accept("chicken", "Cluck");
		b4.accept("chick", "Tweep");
		System.out.println(map2);
		
		
		//Predicate<T> boolean test(T t);
		Predicate<String> p1 = String::isEmpty;//m.ref on an instance to be given at runtime
		Predicate<String> p2 = x -> x.isEmpty();
		System.out.println(p1.test(""));
		System.out.println(p2.test(""));
		
		String str="abc";
		Predicate<String> methodRef2 = str::startsWith;
		boolean res = methodRef2.test(str);
		System.out.println("Does "+ str +" starts with itself? " + res);
		
		//BiPredicate<T, U> boolean test(T t, U u);
		BiPredicate<String, String> bp1 = String::startsWith;//m.ref on an instance to be determined at runtime
		BiPredicate<String, String> bp2 = (string, prefix) -> string.startsWith(prefix);
		System.out.println(bp1.test("chicken", "chick"));
		System.out.println(bp2.test("chicken", "chick"));
		
		//Function<T, R> R apply(T t);
		
		//BiFunction<T,U, R> R apply(T t, U u);
		
		//UnaryOperator<T> T apply(T t);
		
		//BinaryOperator<T, T> T apply(T t1, T t2);
		

	}

}
