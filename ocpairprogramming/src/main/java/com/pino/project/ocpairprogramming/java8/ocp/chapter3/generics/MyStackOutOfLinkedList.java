package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.LinkedList;
import java.util.List;

public class MyStackOutOfLinkedList<T> {
	
	//Has-a relation
		private List<T> arr;
		
		public MyStackOutOfLinkedList() {
			arr = new LinkedList<T>();
		}
		
		public void push(T value) {
			arr.add(value);
		}
		
		public T pop() {
			return arr.remove(arr.size()-1);
		}

		@Override
		public String toString() {
			return arr.toString() ;
		}
		
		

}
