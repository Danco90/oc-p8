package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class FindMovie{
	private Function<String> printer; //(*) doesn't compile
	protected FindMovie(){
		printer = s -> {System.out.println(s); return s;} //(**) doesn't compile
	}
	void printMovies(List<String> movies){
		movies.forEach(printer); //(***) doesn't compile
	}
	public static void main(String[]args){
		List<String> movies = new ArrayList<>();
		movies.add("Stream 3");
		movies.add("Lord of the Recursion");
		movies.add("Silence of the Lambdas");
		new FindMovie().printMovies(movies);
	}
}