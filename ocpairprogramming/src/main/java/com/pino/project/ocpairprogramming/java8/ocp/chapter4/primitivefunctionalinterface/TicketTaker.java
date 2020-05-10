package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.function.IntUnaryOperator;

public class TicketTaker {
	private static int AT_CAPACITY = 100;
	public int takeTicket(int currentCount, IntUnaryOperator counter){ /* (*) doesn't compile */
		return counter.applyAsInt(currentCount);
	}
	public static void main(String... theater){
		final TicketTaker bob = new TicketTaker();
		final int oldCount = 50;
		final int newCount = bob.takeTicket(oldCount, t -> {
			if(t > AT_CAPACITY){
				throw new RuntimeException("Sorry, max has been reached");
			}
			return t + 1;
		});
		System.out.println(newCount);
	}
}

//(*): IntUnaryOperator<Integer> doesn't compile. stream don't have generic type