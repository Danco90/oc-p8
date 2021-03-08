package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

/**
 * Example of a control flow invariant
 * @author matteodaniele
 *
 */
public class TestAssertions {
	public static void test(Seasons s) {
		switch (s) {
		case SPRING:
		case FALL:
			System.out.println("Shorter hours");
			break;
		case SUMMER:
			System.out.println("Longer hours");
			break;	
		default:
			assert false: "Invalid season";
		}
	}
	
	public static void main(String[] args) {
//		test(Seasons.FALL);
		test(Seasons.WINTER);//Added later on throws java.lang.AssertionError: Invalid season
	}
}
