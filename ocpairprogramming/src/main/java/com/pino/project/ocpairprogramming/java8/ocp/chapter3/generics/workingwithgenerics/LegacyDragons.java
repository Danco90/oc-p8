package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.workingwithgenerics;

import java.util.ArrayList;
import java.util.List;

class Dragon {}
class Unicorn {}
public class LegacyDragons {
	public static void main(String[] args) {
		//Class Cast Exception
		//Example A
		List unicorns = new ArrayList();//WARNINGS: raw type List reference plus initialization
		unicorns.add(new Unicorn());
		catchAndSwallowHandler(unicorns);//It compiles as long the list is declared as raw type, as well as List<Object>
		unicorns = new ArrayList();
		unicorns.add(new Dragon() { 
			public String toString() {
				return "Dragon Pino";
			}});
		unicorns.add(new Object());
		catchAndSwallowHandler(unicorns);
		
		//Example B
		List<Unicorn> unicorns2 = new ArrayList<>();
		addUnicornToList(unicorns2);
		try {
			Unicorn unicorn = unicorns2.get(0);// It throws ClassCastException
		} catch (ClassCastException e) {
			System.out.println("Caught Exception (swallowed) :"+e); 
		}
		
	}
	
	private static void catchAndSwallowHandler(List<Dragon> dragons) {
		try {
			printDragons(dragons);//It compiles as long the list is declared as raw type, as well as List<Object>
		} catch(ClassCastException e) { System.out.println("Caught Exception (swallowed) :"+e); }
	}
	
	private static void printDragons(List<Dragon> dragons) {
		for(Dragon dragon: dragons) { // But it throws a ClassCastException as long as there's at least an item not subclass of Dragon 
			System.out.println(dragon);
		}
	}
	
	private static void addUnicornToList(List unicorns) {//WARNING: raw type List
		unicorns.add(new Dragon());//WARNING add(Object) belongs to the raw type
	}

}
