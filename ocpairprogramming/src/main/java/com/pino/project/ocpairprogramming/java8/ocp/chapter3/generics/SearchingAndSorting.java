package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SearchingAndSorting {
	
	static class Rabbit{ 
		int id; 
		Rabbit(){	}
		Rabbit(int id){
			this.id = id;
		}
	}
	
	static class Rabbit2{ 
		int id; 
		Rabbit2(){	}
		Rabbit2(int id){
			this.id = id;
		}
	}

	public static void main(String[] args) {
		List<Rabbit> rabbits = new ArrayList<>();
		rabbits.add(new Rabbit(1));
//		Collections.sort(rabbits); // DOES NOT COMPILE
		
		//FIX
//		Comparator<Rabbit> c = (r1, r2) -> r1.id - r2.id;
		rabbits.add(new Rabbit(2));
		Collections.sort(rabbits, (r1, r2) -> r1.id - r2.id);
		System.out.println("rabbits="+rabbits);
		
		List<String> names = Arrays.asList("Fluffy", "Hoppy");
		System.out.println("List<String> names = Arrays.asList(\"Fluffy\", \"Hoppy\");\n");
		System.out.println(names + "(In order of insertion). Searching 'Hoppy' , index: " 
							+ Collections.binarySearch(names, "Hoppy"));//prints 1
		Comparator<String> cr = Comparator.reverseOrder();
		int index = Collections.binarySearch(names, "Hoppy", cr);//-1
		System.out.println(names + "(still ordered by asc) but reverse searching of 'Hoppy' : " + index);//-1
		index = Collections.binarySearch(names, "Hoppy");//-1
		System.out.println(names + "(still ordered by asc) but direct searching of 'Hoppy' : " + index);//1
		
		Collections.sort(names, cr);//DESC
		System.out.println("\nreversing the order to desc : " + names);//[Happy,Fluffy]
		System.out.println("What's happened to the Indexes?");
		int iFirst = Collections.binarySearch(names, "Hoppy");//0	
		int iSecond = Collections.binarySearch(names, "Fluffy",cr);//-1	
		
		System.out.println(names + "(ordered by desc) but directly searching 'Hoppy' : " + iFirst + " and 'Fluffy' : " + iSecond );//0
		
//		index = Collections.binarySearch(names, "Fluffly");//1
//		System.out.println("and directly searching 'Fluffy' : " + index);
//		
//		Collections.sort(names);
//		System.out.println("forwarding the order back to asc : " + names);
		
		Set<Duck> ducks = new TreeSet<>();
		ducks.add(new Duck("Puddles"));
		Set<Rabbit2> rabbit = new TreeSet<>();
//		rabbit.add(new Rabbit2()); // Compiles but throws an exception because it doesn't implement Comparable
	
		//Solution
		rabbit = new TreeSet<>(new Comparator<Rabbit2>(){
			@Override
			public int compare(Rabbit2 o1, Rabbit2 o2) {
				return o1.id - o2.id;
			}
			
		});
		rabbit.add(new Rabbit2()); //now it's fine
		
	}

}
