package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class DogSearch{
	void reduceList(List<String> names, Predicate<String> tester){
		names.removeIf(tester);
	}
	public static void main(String[]args){
		int MAX_LENGTH = 2;
		DogSearch search = new DogSearch();
		List<String> names = new ArrayList<>();
		names.add("Lassie");
		names.add("Benji");
		names.add("Brian");
		MAX_LENGTH += names.size(); //(*) doesn't compile
		search.reduceList(names, d -> d.length() > MAX_LENGTH);
		System.out.println(names.size());
	}
}
