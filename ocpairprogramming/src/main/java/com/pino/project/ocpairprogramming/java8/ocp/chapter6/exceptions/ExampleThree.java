package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

public class ExampleThree implements AutoCloseable {
	static int count = 0;
	public void close() {
		count++;
	}

}
