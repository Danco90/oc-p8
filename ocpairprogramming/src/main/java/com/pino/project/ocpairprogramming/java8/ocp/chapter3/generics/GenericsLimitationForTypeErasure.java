package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

public class GenericsLimitationForTypeErasure {
	
	/*Type Erasure replace T with Object at compile time*/
	class Crate2<T>/*Object*/{
		private T/*Object*/ contents;
		//You can't create static variable as generic because type is linked to instance
		//private static T property;//COMPILER ERROR
		public T/*Object*/ emptyCrate() {return contents; }
		public void packCrate(T/*Object*/ contents) {
			this.contents = contents;
		}
		
		
	}
		
	//Approach 1 - Specicying the generic type in the implementation class
	class ShippableRobotCrate implements Shippable<Robot>{
		@Override
		public void ship(Robot t) { }
		
	}
	
	//Approach 2 - Create a generc class
	class ShippableAbstractCrate<U> implements Shippable<U>{
		@Override
		public void ship(U t) { }
		
	}
	
	//Approach 3 - Without using Generics at all 
	class ShippableCrate2 implements Shippable{//WARNING - it might cause class cast exception
		@Override
		public void ship(Object t) {
		}
	}
	

	//In static methods you HAVE TO add <format type parameter(s) > before return type
	public static <T>/*<-formal type param. */ Crate<T> ship(T/*Object*/ t) {
		System.out.println("preparing " + t);
		return new Crate<T>();
	}
	
	//In static methods you HAVE TO add <format type parameter(s) > before return type
	public static <T>/*<-formal type param. */ Crate<T> ship(String/*Object*/ s) {
			System.out.println("preparing " + s);
			return new Crate<T>();
	}

	
	public static void main(String[] args) {
		GenericsLimitationForTypeErasure glfte = new GenericsLimitationForTypeErasure();
		GenericsLimitationForTypeErasure.Crate2<Robot> c = glfte.new Crate2();
		
		//The compiler adds relevant cast to work with the type erasure
		Robot r = /*(Robot)*/ c.emptyCrate();//Since Initially c.emptyCrate() returns Object
		
		//Static method invocation
		//Approach A - Specifying the type esplicitly by ClassName.<form type param>methodName()
		GenericsLimitationForTypeErasure.<Robot>ship(new Robot());
		
		//Approach B - Calling it normally - The compiler will figure out the type
		GenericsLimitationForTypeErasure.ship(new Robot());
		GenericsLimitationForTypeErasure.ship("Pino");//What is the instance ?
		
	}

}
