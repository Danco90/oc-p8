package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

public class StuckTurkeyCage implements AutoCloseable {

	@Override
	public void close() throws Exception {//Java STRONGLY recommends that close() not actually throw Exception, but a more specific one
		throw new Exception("Cage door does not close");//And also recommends to make close()  idempotent
	}

	public static void main(String[] args) {
//		try (StuckTurkeyCage t = new StuckTurkeyCage()) {// DOES NOT COMPILE because the checked exception is neither handled nor declared!
//			System.out.println("put turkeys in");
//		}
	}

}
