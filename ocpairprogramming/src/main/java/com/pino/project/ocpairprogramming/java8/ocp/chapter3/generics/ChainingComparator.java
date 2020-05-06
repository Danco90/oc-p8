package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.Comparator;

public class ChainingComparator implements Comparator<Squirrel> {
	public int compare(Squirrel s1, Squirrel s2) {
//		Comparator<Squirrel> c = Comparator.comparing(s -> s.getSpecies())
		//c = c.thenComparingInt(s -> s.getWeight());
//		return c.compare(s1, s2);
		return Comparator.comparing(Squirrel::getSpecies).thenComparing(s -> s.getWeight()).compare(s1, s2);
	}

}
