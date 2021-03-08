package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

public class ExampleTwo implements AutoCloseable {

	@Override
	public void close() throws Exception {
		throw new Exception("Cage door does not close");
	}

}
