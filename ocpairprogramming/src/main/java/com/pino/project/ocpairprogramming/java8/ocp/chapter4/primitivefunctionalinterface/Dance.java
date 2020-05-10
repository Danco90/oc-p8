package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.function.BiFunction;

/*
 * (*) doesn't compile because autoboxing doesn't work for Functional Interface
 */
class Dance{
	public static Integer rest(BiFunction<Integer, Double, Integer> takeAbreak){
		return takeAbreak.apply(3, 10.2);
	}
	public static void main(String[] args){
		rest((s,w) -> (int)(2*w));
		//rest((s,e) -> s.intValue()+e.intValue()); //(*)
		testManual(0, 4.0);
	}
	
	private static Integer testManual(Integer n, Double d){
		return (int) (n + d);
	}
}
