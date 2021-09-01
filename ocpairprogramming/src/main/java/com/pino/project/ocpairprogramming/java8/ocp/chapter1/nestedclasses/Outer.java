package com.pino.project.ocpairprogramming.java8.ocp.chapter1.nestedclasses;

/**
 *  Member Inner classes have the following properties :
 * - Can be declared public, private, or protected or use default access Can extend any class and implement interfaces
   - Can be abstract or final
   - Cannot declare static fields or methods
   - Can access members of the outer class including private members
 */
public class Outer {
	private String greeting = "Hi";
	
	protected class Inner {
		public int repeat = 3;
		public void go() {
			for (int i = 0; i < repeat; i++)
				System.out.println(greeting);
		}
	}
			
	public void callInner() {
		Inner inner = new Inner();
		inner.go();
	}
	
	public static void main(String[] args) {
		Outer outer = new Outer();
		//How to call a member inner class
		//Approach1 
//		outer.callInner();
		
		//Approach2
		Inner inner = outer.new Inner();// create the inner class
		inner.go();
		
		//NB: Inner classes can have the same variable names as outer classes. There is a special way of calling this to say which class you want to access. 
		//See example class A
	}

}
