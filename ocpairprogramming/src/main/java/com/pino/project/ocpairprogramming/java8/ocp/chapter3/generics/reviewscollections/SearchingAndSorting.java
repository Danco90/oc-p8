package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.reviewscollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.Duck;

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
		//Searching and sorting
		System.out.println("A) Searching and sorting with array");
		int [] numbers = {6,9,1,8};
		Arrays.sort(numbers);// 1,6,8,9 with elements starting with index 0
		for(int i : numbers) System.out.print(i);
		System.out.println("\n"+Arrays.binarySearch(numbers, 1));//0 since 1rst element
		System.out.println(Arrays.binarySearch(numbers, 6));//1, since 2nd element
		System.out.println(Arrays.binarySearch(numbers, 3));//-2 = -(1)-1. Which means we negate (-) the position where the value would need to be inserted to. And then we subtract -1
		
		System.out.println("\nB) Searching and sorting with list");
		List<Integer> numbersList = Arrays.asList(6,9,1,8);
		System.out.println("numbersList="+numbersList);
		Collections.sort(numbersList);
		System.out.println("sorted numbersList="+numbersList);
		System.out.println(Collections.binarySearch(numbersList, 6));//1, the very same as the previous array
		List<Integer> list = Arrays.asList(9,7,5,3);
		System.out.println("list="+list);
		Collections.sort(list);//3,5,7,9
		System.out.println("sorted list="+list);
		System.out.println(Collections.binarySearch(list, 3));//0
		System.out.println(Collections.binarySearch(list, 2));//-(0)-1 = -1 
		
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
