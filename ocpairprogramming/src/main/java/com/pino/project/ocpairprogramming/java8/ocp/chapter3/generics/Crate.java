package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

public class Crate<T> {
	private T/*Object*/ contents;
	//You can't create static variable as generic because type is linked to instance
	//private static T property;//COMPILER ERROR
	public T/*Object*/ emptyCrate() {return contents; }
	public void packCrate(T/*Object*/ contents) {
		this.contents = contents;
	}
}
