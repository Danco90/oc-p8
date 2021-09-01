package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

public class ExampleOne implements AutoCloseable {

	@Override
	public void close() throws IllegalStateException {
		throw new IllegalStateException("Cage door does not close");
	}

}
