package com.pino.project.ocpairprogramming.java8.ocp.chapter1.enums;

public enum Season2 {
	WINTER("Low"), SPRING("Medium"), SUMMER("High"), FALL("Medium");
	private String expectedVisitors;
	private Season2(String expectedVisitors) {//private because it can only be called from within the enum.
		this.expectedVisitors = expectedVisitors;
	}
	public void printExpectedVisitors() {
		System.out.println(expectedVisitors);
	}
}