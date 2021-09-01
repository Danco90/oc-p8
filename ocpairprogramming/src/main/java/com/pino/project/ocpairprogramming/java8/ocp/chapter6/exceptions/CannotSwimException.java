package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

public class CannotSwimException extends Exception {
	
	public CannotSwimException() {
		super();
	}
	
	public CannotSwimException(Exception e) {
		super(e);
	}
	
	public CannotSwimException(String message) {
		super(message);
	}

}
