package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.List;

public class ListMethods {

	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		System.out.print(list + ", list.add(\"SD\"),  "); // the add() ovverride is void in List
		list.add("SD"); System.out.println(list);// [SD]
		System.out.print(list + ", list.add(0, \"NY\") , "); 
		list.add(0, "NY"); System.out.println(list);// [NY,SD]
		System.out.print(list + ", list.set(1, \"FL\") , " ); 
		list.set(1, "FL"); System.out.println(list);// [NY,FL]
		System.out.print(list + ", list.remove(\"NY\")) , "); //
		list.remove("NY"); System.out.println(list);// [FL]
		System.out.print(list + ", list.remove(0) , " ); // 
		list.remove(0); System.out.println(list);// []
		
		System.out.print("list.add(\"OH\") , list.add(\"CO\"), list.add(\"NJ\") " ); // prints false
		list.add("OH"); list.add("CO"); list.add("NJ"); 
		// // [OH,CO,NJ]
		System.out.println(list + ", list.get(0) : " + list.get(0) ); // 
		String state = list.get(0); // OH
		
		//IndexOf() : its efficiency O(n) will be the same for all the List implementations
		//given a small number of elements. 
		//indexOf looks through the whole list ( O(n) linear time)
		System.out.println(list + ", list.indexOf(\"NJ\") : " + list.indexOf("NJ") ); // 
		int index = list.indexOf("NJ"); // 2
	}

}
