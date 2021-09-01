package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.bounds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author matteodaniele
 * Table 3.2 Types of bounds
 * ----------------------------+---------------+-----------------------------------------------------------------
 * Type of bound 				Syntax			Example
 * Unbounded wildcard 			 ?				List<?> l =new ArrayList<String>();
 * Wildcard with an upper bound  ? extends type  List<? extends Exception> l =new ArrayList<RuntimeException>();
 * Wildcard with a lower bound 	 ? super type	List<? super Exception> l =new ArrayList<Object>();
 */
public class BoundsDemo {
	
	static class Sparrow extends Bird {}
	static class Bird {}
	
	public static void main(String[] args) {
		//TYPES of Generics 
		//Example: List<Number> which means ONLY Number and no subclass or superclass.
		List<String> keywords = new ArrayList<>();
		keywords.add("java");
//		printList(keywords);//(*) COMPILATION ERROR because List<String> cannot be assigned to List<Object> although String extends Object!
		
		List<Integer> numbers = new ArrayList<>();
		numbers.add(new Integer(42));
		numbers.add(33);//OK thanks to autoboxing int into Integer
//		List<Object> objects = numbers;//(*) DOES NOT COMPILE since we cannot convert from List<Object> to List<Number>
	    //(*) Due to type Erasure at runtime the ArrayList does not know what is allowed in it!
		
		//Arrays vs. ArrayList when storing the wrong Objects
		Integer[] numbers2 = { new Integer(42)};
		Object[] objects2 = numbers2; //LEGAL since the reference is a superclass, but the object is Integer[] and allows ONLY Integer or subclass at runtime
//		objects2[0] = "forty two";//OK but throws ArrayStoreException as with arrays java knows the types (Integer[]) that is allowed in there.
		
		//SUMMARY: due to type erasure, which aims at backward compatibility (not breaking old existing code)
		//java uses the compiler to prevent wrong assignment with collections
		
		// 1. Bounded parameter type (without any question marks) is a generic type which specifies a bound for the generic
		//Examples: <T extends Number>, <T super Exception> and so on.
		
		// 2. A wildcard generic type (?) is an UNKNOWN generic type
		// Types of bounds for wildcard generic type
		
		// 2.A. Unbounded Wildcards ? :  List<?> that means whatever type, such as new ArrayList<String>(); And it is not just List<Object>
		List<String> keywords2 = new ArrayList<>();////The list becomes logically IMMUTABLE!
		keywords2.add("java");
		printUnboundedList(keywords2);//Legal as long as it's a list of "Everything"
		//Use cases to BETTER understand the promise once declaring unbounded generics types
		List<String> onlyStrings = Arrays.asList(new String());
		List<?> unbounded = new ArrayList<>(onlyStrings);//OK as long as the item(s) of ? "whatever type" is/are added during declaration
		
		// 2.B. Upper-bounded <? extends Number>, for example a RuntimeException
//		List<Number> list3 = new ArrayList<Integer>();//Compilation ERROR
//		ArrayList<Number> list4 = new ArrayList<Integer>();//Compilation ERROR
		//Instead , we need to use an upper-bounded wildcard generic type
		List<? extends Number>/*(*)*/ list4 = new ArrayList<Integer>();//OK because when declaring a type we can find a type included in the bound 
		//(*)What that means is "any class that extends Number or Number itself can be used as format parameter type"
		//The list becomes logically IMMUTABLE! But technically you could still remove elements from the list.
		
		//NB: Type erasure makes java think that a generic type is an Object.
		List<Integer> integers = Arrays.asList(1,2,3,4,5);
		long tot = total(integers);
		
		//Sparrow extends Bird
		List<Bird> onlyBirds = Arrays.asList(new Bird());
		List<Sparrow> onlySparrows = Arrays.asList(new Sparrow());
		List<? extends Bird> birds = new ArrayList<Bird>();//(*IMPORTANT)
		//Use cases to BETTER understand the promise once declaring upper-bounded generics types
		List<? extends Bird> birds2 = new ArrayList<Bird>(onlyBirds);//OK as long as the item(s) is/are added during declaration
		List<? extends Bird> sparrows = new ArrayList<Bird>(onlySparrows);//OK as long as the item(s) is/are added during declaration
		List<? extends Bird> sparrows2 = new ArrayList<Sparrow>(onlySparrows);//OK as long as the item(s) is/are added during declaration
//		birds.add(new Sparrow());//DOES NOT COMPILE as we can't ** add Sparrow to List<Bird> 
//		birds.add(new Bird());//DOES NOT COMPILE as we can't ** add Bird to List<Sparrow> for example
		//(*IMPORTANT): It means creating an IMMUTABLE empty list of Bird, without allowing to add any elements!
		//The problem stems to the fact java does not know what type List<? extends Bird> really is, after initializing an empty list.
		//It could be List<Bird> or List<Sparrow> or some other generic type that hasn't even been written yet.
		//** Both scenarios are equally possible so NEITHER is ALLOWED.
		
		//Example of upper-bounded with interfaces (using extends even for interface)
		//HangGlider implements Flyer { public void fly() {} } 
		//Goose implements Flyer { public void fly() {} }
		anyFlier(new ArrayList<Flyer>());
		groupOfFlyers(new ArrayList<Flyer>());
//		anyFlier(new ArrayList<Goose>());//COMPILATION ERROR. 
		groupOfFlyers(new ArrayList<Goose>());
		groupOfFlyers(new ArrayList<HangGlider>());
		
		// 2.C. Lower-bounded <? super Exception>, such as Throwable or Object
		List<String> strings = new ArrayList<String>();
		strings.add("tweet");
		List<Object> objects = new ArrayList<Object>(strings);//OK Valid constructor
//		addSound(strings);//DOES NOT COMPILE beause there's at least one element inside an immutable list
//		addSound(new ArrayList<String>());//whereas this one WOULD COMPILE as the list is still empty and immutable
		addSound(objects);//OK COMPILES
		
		//TRICKY EXAMPLE of lower-bounded with subclasses and superclasses
		List<Exception> onlyExceptions = Arrays.asList(new Exception());
		List<? super IOException> exceptions = new ArrayList<Exception>();

		//**Use case to BETTER understand the promise once declaring lower-bounded generics type
		List<? super IOException> exceptions2 = new ArrayList<Exception>(onlyExceptions);//OK because when declaring type we can insert any supertype 
		
//		exceptions.add(new Exception());//DOES NOT COMPILE because the list could be could be List<IOException> or List<Exception> or List<Object>
		exceptions.add(new IOException());//OK VALID, and whatever subclass of it.
		exceptions.add(new FileNotFoundException());//such as this one
		List<? super Exception> exceptions3 = new ArrayList<Throwable>();
//		exceptions3.add(new Throwable());//Does not compile
		exceptions3.add(new Exception());//OK
		exceptions3.add(new IOException());//OK
		exceptions3.add(new RuntimeException());//OK
		
		exceptions3 = new ArrayList<Object>();
//		exceptions3.add(new Object());//Does not compile
//		exceptions3.add(new Throwable());//Does not compile
		exceptions3.add(new Exception());//OK
		exceptions3.add(new IOException());//OK
		exceptions3.add(new RuntimeException());//OK
		
		
		//3.2.6 Putting It All Together
		//**TRICKIEST and HARDIEST questions about generics**
		//3.2.6.1 Declaration and Initialization or variable with wildcards
		//class A
		//B extends A 
		//C extends B 
		//Variable with unbounded wildcard - Example 1
		List<?> list1 = new ArrayList<A>();//OK as long as list can be of '?' whatever type when declared, but it become an IMMUTABLE empty one
		//Variable with upper-bunded wildcard - Example 1
		List<? extends A> list2 = new ArrayList<A>();//OK too, as long as the list can be of whatever type subclass of A when declared, but becoming an IMMUTABLE empty list as well
		//Indeed you can have ArrayList<A>, ArrayList<B>, or ArrayList<C> stored in that reference. 
		list2 = new ArrayList<B>();
		list2 = new ArrayList<C>();
		
		//Variable with lower-bunded wildcard - Example 1
		List<? super A> list3 = new ArrayList<A>();//OK as well, as long as the list can contain whatever superclass of A when declared, and becoming MUTABLE, 
		//But the lowest type it can contain later (as additional item) is of type A or any subclass

//		List<? extends B> list4 = new ArrayList<A>();//DOES NOT COMPILE because A is not subclass of B, but its subclass
		//It allows ArrayList<B> or ArrayList<C> as demonstrated in the examples below
		//Variable with upper-bunded wildcard - Example 2a and 2B
		List<? extends B> list4b = new ArrayList<C>();//Indeed this is OK as C is a subclass of B
		List<? extends B> list4a = new ArrayList<B>();//And this is OK too as B itself is fine
		
		//Variable with lower-bunded wildcard - Example 2
		List<? super B> list5 = new ArrayList<A>();//Ok fine, as long as it respect the promise A super B when declaring a list not empty
        //It can contains ArrayList<A>, ArrayList<B> or ArrayList<Object>
		
		
		//Variable with unbouded wildcard - Example 2
//		List<?> list6 = new ArrayList<? extends A>();//(*)COMPIALTION ERROR as bounds are allowed only on the reference type side during the declaration
	    //(*)What happens here is that the type of the generic is to be ALWAYS known ahead of time, before compilation, for initialization purpose!
		//In brief, you ALWAYS need to know what that type will be when instantiating the ArrayList.
		
		//3.2.6.2 variable with method-specific wildcard in METHOD declaration
		//3.2.6.2.1 <T> T method1(List<? extends T> list) with upper-bound wildcard,it uses a method-specific type parameter T 
		
		//3.2.6.2.2 <T> <? extends T> method2(List<? extends T> list) with upper-bound wildcard and a NOT VALID return type because of with the bounds it isn't actually a real type
		
		//3.2.6.2.3 <B extends A> B method3(List<B> list) VALID because bounds can be used in the formal type parameter of a method,
		//given that it is not coincidentally also the name of a class
		
		//3.2.6.2.4 void method4(List<? super B> list) {//You can pass types List<B>, List<A>, List<Object>
		
		//3.2.6.2.5 Mix a method-specific type parameter with a wildcard.
		//<X> void method5(List<X super B> list) {//(*)DOES NOT COMPILE 
		//IMPORTANT(*) a wildcard MUST have a ? in it
	}
	
	<T> T method1(List<? extends T> list) {//method-specific type parameter T
		return list.get(0);
	}
	
//	<T> <? extends T> method2(List<? extends T> list) {//DOES NOT COMPILE as the return type isn't actually a type. It must be known ahead of time
//		return list.get(0);
//	}
	
	
//	<B extends A> B method3(List<B> list) {//this line compiles
//		return new B();//IT DOES NOT COMPILE. B can't be instantiated because it is both a type parameter just for this method and also the name of a class
//	}
	
	void method4(List<? super B> list) {//You can pass types List<B>, List<A>, List<Object>
	}
	
//	<X> void method5(List<X super B> list) {//(*)DOES NOT COMPILE as it tries to mix a method-specific type parameter with a wildcard.
//	}
	
	
//	public static void addSound(List<?> list) {
//		list.add("quack");//DOES NOT COMPILE, as unbounded generics are IMMUTABLE
//	}
	
//	public static void addSound(List<? extends Object> list) {
//		list.add("quack");//DOES NOT COMPILE, as upper-bounded generics are IMMUTABLE
//	}
	
//	public static void addSound(List<Object> list) {
//		list.add("quack");//YES Compiles 
//	}
	
	public static void addSound(List<? super Object> list) {
		list.add("quack");//YES Compiles because lower-bounded are MUTABLE
	}
	
	private static void anyFlier(List<Flyer> flyer) {}
	private static void groupOfFlyers(List<? extends Flyer> flyer) {}//extends used even for interface
	
	interface Flyer { void fly(); }
	class HangGlider implements Flyer { public void fly() {} } 
	class Goose implements Flyer { public void fly() {} }

	
	
	public static long total(List<? extends Number> list) {
		long count = 0;
		for (Number number: list )
			count+= number.longValue();
		return count;
	}
	
	//the previous method is being converted into the following
//	public static long total(List list) {
//		long count = 0;
//		for (Object obj: list ) {
//			Number number = (Number) obj;
//			count+= number.longValue();
//		}
//		return count;
//	}
	
	public static void printUnboundedList(List<?> list) {
		for(Object x: list) System.out.println(x);
	}
	
	public static void printList(List<Object> list) {
		for(Object x: list) System.out.println(x);
	}

}

class A {}
class B extends A {}
class C extends B {}

