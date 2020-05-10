package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpperboundedWildcards {
	
	static class Sparrow extends Bird { }
	static class Bird { }

	public static void main(String[] args) {
		//Immutability of upper-bounded wildcard list
		
		List<? extends Bird> birds = new ArrayList<Bird>();
		//That being said, ? might be Bird itself, of whatever child
		// and Java doesn't actually know what the real type is
		//NB: it will be replaced by List during compilation time
		//Therefore as an immutable object, birds can't be neither modified
		//nor be added any items (fixed size) because of the following possible reasons:

		//birds.add(new Bird());//Compilation Error because it can't add
		//Bird to a possible List<Sparrow> or a List of some subclass
		
//		birds.add(new Sparrow());//Compilation Error always because
		//we can't add a Sparrow to a List<Bird> !
		
		//From Java’s point of view, both scenarios 
		//(List<Bird> and List<Sparrow>) are equally possible 
		//so neither is allowed.
		
		//Whereas we can specify the content of the list in the declaration
		List<? extends Bird> birds2 = new ArrayList<Bird>(
				Arrays.asList(new Bird(), new Bird(), new Bird()));
		

		
	}

}
