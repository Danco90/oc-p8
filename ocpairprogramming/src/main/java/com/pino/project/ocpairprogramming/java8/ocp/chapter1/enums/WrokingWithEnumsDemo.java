package com.pino.project.ocpairprogramming.java8.ocp.chapter1.enums;

public class WrokingWithEnumsDemo {

	public static void main(String[] args) {
		//A) Create enum 
		Season s = Season.SUMMER;//select SUMMER value
		System.out.println(Season.SUMMER);// SUMMER (prints the name of the enum when toString() is called)
		System.out.println(s.name());// SUMMER (prints the name of the enum when toString() is called)
		System.out.println(s.ordinal());// 2, as it prints the int value in the order in which the enum value was declared 
		//NB: The int value will remain the same during your program
		System.out.println(s == Season.SUMMER);// true as it can be compared only with same enum type

		
//		System.out.println(s == Season2.SUMMER);// false as the enum Season is not compatible with Season2 
		System.out.println();
		
		//values() enum method to get an array of all of the values
		for(Season season: Season.values())// Season[]
			System.out.println(season.name() + " " + season.ordinal());
	
		//Rule1 : you can't compare enum value with primitive directly 
//		if ( Season.SUMMER == 2) {} // DOES NOT COMPILE since you cannot compare enum and int.
	
		//B) Create enum from a String
		Season s1 = Season.valueOf("SUMMER");// SUMMER
//		Season s2 = Season.valueOf("summer");//it throws exception if not found
		
		//Rule1 : You CAN'T extend an enum: The values in an enum are all that are allowed. 
//		public enum ExtendedSeason extends Season { } // DOES NOT COMPILE
		//You can't add more at runtime by extending the enum!
		
		//Using enums in switch statements 
		Season summer = Season.SUMMER; 
		switch (summer) {
//		case 0: // DOES NOT COMPILE
		case WINTER:
			System.out.println("Get out the sled!");
			break;
		case SUMMER:
			System.out.println("Time for the pool!");
			break;
		default:
			System.out.println("is it summer yet?");
		}
		System.out.println();
		//Adding extra functionality to enums
		Season2.SUMMER.printExpectedVisitors();//High
	
		//Rule2: The first time that we ask for any enum values,
		//Java constructs all of the enum values. After that, java returns the already-constructed enum values.
		//See enum OnlyOne
		
		//Using abstract methods in enum
		//See Season3
	}

}
