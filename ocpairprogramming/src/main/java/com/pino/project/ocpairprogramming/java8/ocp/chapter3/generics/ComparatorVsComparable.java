package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparatorVsComparable {

	public static void main(String[] args) {
		
		MultiFieldComparator byWeightGivenSameSpecies = new MultiFieldComparator();
		Squirrel sqrl = new Squirrel(2, "brown");
		Squirrel sqrl2 = new Squirrel(1, "brown");
		List<Squirrel> squirrels = new ArrayList<>();
		squirrels.add(sqrl);
		squirrels.add(sqrl2);
		System.out.println("A - overrided method - Sorting squirrels by weight given the same species");
		Collections.sort(squirrels, byWeightGivenSameSpecies); // sort by weight
		System.out.println(squirrels);
		
		ChainingComparator bySpeciesAndByWeightWithUtil = new ChainingComparator();
		List<Squirrel> squirrels2 = new ArrayList<>();
		squirrels2.add(new Squirrel(2, "null"));
		squirrels2.add(new Squirrel(1, "brown"));
		System.out.println("B - Comparator util static methods - Sorting squirrels by weight given the same species");
		Collections.sort(squirrels2, bySpeciesAndByWeightWithUtil); // sort by weight
		System.out.println(squirrels2);

	}

}
