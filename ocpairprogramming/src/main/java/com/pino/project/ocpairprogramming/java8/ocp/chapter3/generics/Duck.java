package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Duck implements Comparable<Duck> {
	private String name;
	private int weight;
	public Duck(String name, int weight) {
	this.name = name;
	this.weight = weight;
	}
	public String getName() { return name; }
	public int getWeight() { return weight; }
	
	public Duck(String name) {
	this.name = name;
	}
	public String toString() { // use readable output
	return name;
	}
	public int compareTo(Duck d) {
	return name.compareTo(d.name); // call String's compareTo
	}
	
	public static void main(String[] args) {
//		List<Duck> ducks = new ArrayList<>();
//		ducks.add(new Duck("Quack"));
//		ducks.add(new Duck("Puddles"));
//		Collections.sort(ducks); // sort by name
//		System.out.println(ducks); // [Puddles, Quack]
	
		Comparator<Duck> byWeight = new Comparator<Duck>() {
			public int compare(Duck d1, Duck d2) {
				return d1.getWeight()-d2.getWeight();
			}
		};
		List<Duck> ducks = new ArrayList<>();
		ducks.add(new Duck("Quack",7));
		ducks.add(new Duck("Puddles",10));
		Collections.sort(ducks); // sort by name
		System.out.println(ducks); // [Puddles, Quack]
		System.out.println("Sorting by weight (inner class Comparator<Duck>)");
		Collections.sort(ducks,byWeight); // sort by weight
		System.out.println(ducks); // [Quack,Puddles]
		
		//SInce Comparator is a Functional Interface we can rewrite
		//B) as a lambda
		Comparator<Duck> byWeight2 = (d1, d2) -> d1.getWeight()-d2.getWeight();
		Comparator<Duck> byWeight3 = (Duck d1, Duck d2) -> d1.getWeight()-d2.getWeight();
		Comparator<Duck> byWeight4 = (d1, d2) -> { return d1.getWeight()-d2.getWeight(); };
		Comparator<Duck> byWeight5 = (Duck d1, Duck d2) -> {return d1.getWeight()
																- d2.getWeight(); };
		System.out.println("Sorting by weight2 (lambda)");
		Collections.sort(ducks, byWeight2); // sort by weight2 (lambda)
		System.out.println(ducks); // [Quack,Puddles]
		//Whereas, not as a method reference because there's no compatibility
	} 
	

}
