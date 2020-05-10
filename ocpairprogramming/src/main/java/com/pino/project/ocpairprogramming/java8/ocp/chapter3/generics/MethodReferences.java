package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReferences {

	public static void main(String[] args) {
		//Static method
		Consumer<List<Integer>> mr = Collections::sort;
		Consumer<List<Integer>> lambda = l ->  Collections.sort(l);
		
		//2)Instance method on a particular instance
		String str = "abc";
		Predicate<String> methodRef2 = str::startsWith;
		Predicate<String> lambda2 = s -> str.startsWith(s);
		
		//3)Instance method on an instance determined at runtime
		Predicate<String> methodRef3 = String::isEmpty;
		Predicate<String> lambda3 = s -> s.isEmpty();
		
		//4)Constructor Reference
		Supplier<ArrayList> methodRef4 = ArrayList::new;
		Supplier<ArrayList> lambda4 = () -> new ArrayList();
		

	}

}
