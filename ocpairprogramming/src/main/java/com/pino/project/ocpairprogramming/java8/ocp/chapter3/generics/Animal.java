package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

public class Animal implements Comparable<Animal> {
	private int id;

	public Animal(int id) {
		this.id = id;
	}
	
	public Animal() {
		super();
	}
	public int compareTo(Animal a) {
		return id - a.id;
	}
	public static void main(String[] args) {
		Animal a1 = new Animal();
		Animal a2 = new Animal();
		a1.id = 5;
		a2.id = 7;
		System.out.println(a1.compareTo(a2)); // -2
		System.out.println(a1.compareTo(a1)); // 0
		System.out.println(a2.compareTo(a1)); // 2
	}
	

}
