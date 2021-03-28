package com.pino.project.ocpairprogramming.java8.ocp.chapter4.optional;

import java.util.*; 

public class Catch {
	public static void main(String[] args) { 
		Optional opt = Optional.empty();//RAW type WARNING but compiles
		try {
			apply(opt);//WARNING: It needs unchecked conversion to conform Optional<Excepiton>
		} catch (IllegalArgumentException e) {
			System.out.println("Caught it"); }
		}
	private static void apply(Optional<Exception> opt) {
		//IF Empty optional OR value is null (considered the same cases) , The following operation return something or invoke a supplier 
		//orElseThrow(Supplier<? extends X> s);
		opt.orElseThrow(IllegalArgumentException::new);//Compiles and DOES throw the exception to the caller
		//orElseGet(Supplier<? extends X> s);
		opt.orElseGet(IllegalArgumentException::new);//Compiles but returns JUST the type without throwing any exception to the caller
		//NB: There is a huge difference between method ref or lambda and calling directly a constructor
		//opt.orElse(IllegalArgumentException::new);//DOES NOT compile with the lambda,
		//orElse(T other);
		opt.orElse(new IllegalArgumentException());//but returns JUST the type without throwing any exception to the caller
	}
}
