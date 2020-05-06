package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.List;

public class MyStackOutOfArrayList<T> {
	//Has-a relation
	private List<T> arr;
	
	public MyStackOutOfArrayList() {
		arr = new ArrayList<T>();
	}
	
	public void push(T value) {
		arr.add(value);
	}
	
	public T pop() {
		return arr.remove(arr.size()-1);
	}

}
