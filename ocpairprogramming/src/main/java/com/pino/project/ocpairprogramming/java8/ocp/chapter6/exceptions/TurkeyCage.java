package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

//import java.io.Closeable;

public class TurkeyCage implements AutoCloseable {

	@Override
	public void close() /*throws Exception*/ {//An overriding method is allowed to declare more specific exceptions than the parent or even none at all.
		System.out.println("Close gate");
	}

	public static void main(String[] args) throws Exception {
		try (TurkeyCage t = new TurkeyCage()) {
			System.out.println("put turkeys in");
		}
	}
}
