package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.function.DoubleToIntFunction;

class Bank{
	private int savingsInCents;
	private static class ConvertToCents{
//		static DoubleToIntFunction f = p -> p*100; //doesn't compile
		static DoubleToIntFunction f = p -> (int)p*100;
	}
	public static void main(String ... currency){
		Bank creditUnion = new Bank();
		creditUnion.savingsInCents = 100;
		double deposit = 1.5;

		creditUnion.savingsInCents += ConvertToCents.f.applyAsInt(deposit);
		System.out.println(creditUnion.savingsInCents);
	}
}
