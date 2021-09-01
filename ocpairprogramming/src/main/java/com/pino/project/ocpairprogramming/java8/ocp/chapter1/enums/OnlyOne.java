package com.pino.project.ocpairprogramming.java8.ocp.chapter1.enums;

public enum OnlyOne {
	ONCE(true);
	private OnlyOne(boolean b) {
		System.out.println("constructing");
	}
	
	public static void main(String[] args) {
		OnlyOne firstCall = OnlyOne.ONCE; // prints constructing
		OnlyOne secondCall = OnlyOne.ONCE; // doesn't print anything
	}

}
