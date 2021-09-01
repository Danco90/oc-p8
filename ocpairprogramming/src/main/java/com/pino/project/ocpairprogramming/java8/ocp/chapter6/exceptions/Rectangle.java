package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

/**
 * Example of class invariant ç assert the validity of an object's state
 * @author matteodaniele
 *
 */
public class Rectangle {
	private int width, height;
	
	public Rectangle(int width, int height) { 
//		if(width < 0 || height < 0) {
//			throw new IllegalArgumentException(); 
//		}
		this.width = width;
		this.height = height;
	}
	
	public int getArea() {
		assert isValid(): "Not a valid Rectangle";//Class inviariant
		return width * height;
	}
	
	/**
	 * This method is an example of class invariant
	 */
	private boolean isValid() {
		return (width >= 0 && height >= 0);
	}
	
	public static void main(String[] args) {
		Rectangle one = new Rectangle(5,12);
		Rectangle two = new Rectangle(-4,10);
		System.out.println("Area one = " + one.getArea());
		System.out.println("Area two = " + two.getArea());
	}

}
