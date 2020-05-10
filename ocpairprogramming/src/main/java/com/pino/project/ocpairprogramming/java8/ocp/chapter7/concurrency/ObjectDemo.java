package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ObjectDemo extends Object {

	   private List synchedList;

	   public ObjectDemo() {
	      // create a new synchronized list to be used
	      synchedList = Collections.synchronizedList(new LinkedList());
	   }

	   // method used to remove an element from the list
	   public String removeElement() throws InterruptedException {
	      synchronized (synchedList) {

	         // while the list is empty, wait
	         while (synchedList.isEmpty()) {
	            System.out.println(Thread.currentThread().getName()+" found the list empty...");
	            synchedList.wait();
	            System.out.println(Thread.currentThread().getName()+" is not Waiting...");
	         }
	         String element = (String) synchedList.remove(0);

	         return element;
	      }
	   }

	   // method to add an element in the list
	   public void addElement(String element) {
	      System.out.println(Thread.currentThread().getName()+" is now Opening the list...");
	      synchronized (synchedList) {

	         // add an element and notify all that an element exists
	         synchedList.add(element);
	         System.out.println("New Element:'" + element + "' has been added by "+Thread.currentThread().getName());

	         synchedList.notifyAll();
	         System.out.println(Thread.currentThread().getName()+" called notifyAll");
	      }
	      System.out.println(Thread.currentThread().getName()+" is now Closing...");
	   }

	   public static void main(String[] args) {
	      final ObjectDemo demo = new ObjectDemo();

	      Runnable runA = new Runnable() {

	         public void run() {
	            try {
	               String item = demo.removeElement();
	               System.out.println("" + item);
	            } catch (InterruptedException ix) {
	               System.out.println("Interrupted Exception!");
	            } catch (Exception x) {
	               System.out.println("Exception thrown.");
	            }
	         }
	      };

	      Runnable runB = new Runnable() {

	         // run adds an element in the list and starts the loop
	         public void run() {
	            demo.addElement("Hello!");
	         }
	      };

	      try {
	         Thread threadA1 = new Thread(runA, "thread-A");
	         threadA1.start();
	         long start = System.currentTimeMillis();
	         Thread.sleep(500);
	         System.out.println("Sleep time in ms = "+(System.currentTimeMillis()-start));
	         
	         Thread threadA2 = new Thread(runA, "thread-B");
    	         threadA2.start();
    	         start = System.currentTimeMillis();
	         Thread.sleep(500);
	         System.out.println("Sleep time in ms = "+(System.currentTimeMillis()-start));
	         

	         Thread threadB = new Thread(runB, "thread-C");
	         threadB.start();
	         start = System.currentTimeMillis();

	         Thread.sleep(1000);
	         System.out.println("Sleep time in ms = "+(System.currentTimeMillis()-start));
	         

	         threadA1.interrupt();
	         threadA2.interrupt();
	      } catch (InterruptedException x) {
	      }
	   }
	}
