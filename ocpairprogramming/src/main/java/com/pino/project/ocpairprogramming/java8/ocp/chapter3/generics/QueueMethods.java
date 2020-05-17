package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueMethods {

	public static void main(String[] args) {
		//Queue (FIFO)
		System.out.println("queue (_(FIFO() - Queue<Integer> queue = new ArrayDeque<>();");
		Queue<Integer> queue = new ArrayDeque<>();
		System.out.println(queue + ", queue.offer(10) : " + queue.offer(10) + ", (_(10)<-"); // true
		System.out.println("(10(_()" + ", queue.offer(4) : " + queue.offer(14) + ", (10(_(4)<-" ); // true
		System.out.println("(10(4(_()" + ", queue.peek() : " + queue.peek()); // 10
		System.out.println("(10(4(_()" + ", queue.poll() : " + queue.poll() ); // 10
		System.out.println("(4(_()" + ", queue.poll() : " + queue.poll()); // 4
		System.out.println("(_()" + ", queue.peek() : " + queue.peek()+"\n"); // null
		
		//Stack (LIFO)
		System.out.println("stack (LIFO(_() - ArrayDeque<Integer> stack = new ArrayDeque<>();");
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		System.out.println("(_()" + ", stack.push(10) "); 
		stack.push(10); // true
		System.out.println("(10(_()" + ", stack.push(4) ");
		stack.offer(14); // true
		System.out.println("(10(4(_()" + ", stack.peek() : " + stack.peek()); // 10
		System.out.println("(10(4(_()" + ", stack.poll() : " + stack.poll() ); // 10
		System.out.println("(10(_()" + ", stack.poll() : " + stack.poll()); // 4
		System.out.println("(_()" + ", stack.peek() : " + stack.peek()+"\n"); // null
		
		//LinkedList as a queue (not only a queue)
		System.out.println("linkedlist queue (FIFO [_[] - LinkedList<String> qList = new LinkedList<>();;");
		List<String> qList = new LinkedList<>();
		System.out.println("[[]" + ", qList.add(\"SD\"),  "); // the add() ovverride is void in List
		qList.add("SD"); // [SD]
		System.out.println("[SD[]" + ", qList.add(\"NY\") , "); 
		qList.add("NY"); // [SD,NY]
		System.out.println("[SD|NY[]" + ", qList.remove()) , "); //
		qList.remove(0); // [NY]
		System.out.println("[NY[]" + ", qList.remove() , " ); // 
		qList.remove(0); System.out.println(qList);// []
		
		//LinkedList as a stack (not only a stack)
		System.out.println("linkedlist stack (LIFO [_[] - LinkedList<String> sList = new LinkedList<>();;");
		List<String> sList = new LinkedList<>();
		System.out.println("[_|]" + ", sList.add(\"SD\"),  "); // the add() ovverride is void in List
		sList.add("SD"); // [SD]
		System.out.println(sList + ", sList.add(\"NY\") , "); 
		sList.add("NY"); // [SD,NY]
		System.out.println(sList + ", sList.size()-1) , "); //
		sList.remove(sList.size()-1); // [SD]
		System.out.println(sList + ", sList.size()-1 , " ); // 
		sList.remove(sList.size()-1); System.out.println(sList);// []
		
		//LinkedList as a stack (only a stack)
		System.out.println("linkedlist only as stack (LIFO [_[] - MyStackOutOfLinkedList<String> myStack = new MyStackOutOfLinkedList<>();");
		MyStackOutOfLinkedList<String> myStack = new MyStackOutOfLinkedList<>();
		System.out.println("[_|]" + ", myStack.push(\"SD\"),  "); // the add() ovverride is void in List
		myStack.push("SD"); // [SD]
		System.out.println(myStack + ", myStack.push(\"NY\") , "); 
		myStack.push("NY"); // [SD,NY]
		System.out.println(myStack + ", sList.size()-1) , "); //
		myStack.pop(); // [SD]
		System.out.println(myStack + ", myStack.size()-1 , " ); // 
		myStack.pop(); System.out.println(myStack);// []
		
	}

}
