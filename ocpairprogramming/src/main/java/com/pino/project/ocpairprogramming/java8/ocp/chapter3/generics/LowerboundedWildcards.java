package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.List;

public class LowerboundedWildcards {
	
	//Erasure List
	public static void addSound(List<?> list) {
		//list.add("quack");//COMPILATION ERROR because of list immutability
	}
	
	//Erasure List
	public static void addSound2(List<? extends Object> list) {
		//list.add("quack");//COMPILATION ERROR because of list immutability
	}
	
	//erasure List<Object>
	public static void addSound3(List<Object> list){
		list.add("quack");
	}
	
	
	public static void addSound4(List<? super String> list) {
		list.add("quack");//OK because mutable list
	}

	public static void main(String[] args) {
		List<String> strings = new ArrayList<String>();
		strings.add("tweet");
		List<Object> objects = new ArrayList<Object>(strings);
		addSound(strings);
		addSound(objects);
		
		addSound2(strings);
		addSound2(objects);
		
		//addSound3(strings);//COMPILATION ERROR because subtype not admitted
		addSound3(objects);
		
		addSound4(strings);
		addSound4(objects);

	}

}
