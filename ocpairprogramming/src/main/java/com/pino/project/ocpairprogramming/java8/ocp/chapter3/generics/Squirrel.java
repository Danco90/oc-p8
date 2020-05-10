package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

public class Squirrel {
	private int weight;
	private String species;
	public Squirrel(String theSpecies) {
//	if (theSpecies == null) throw new IllegalArgumentException();
	species = theSpecies;
	}
	
	public Squirrel(int weight, String species) {
		super();
		this.weight = weight;
		this.species = species;
	}

	public int getWeight() { return weight; }
	public void setWeight(int weight) { this.weight = weight; }
	public String getSpecies() { return species; }
	
	
	
	@Override
	public String toString() {
		return "Squirrel [weight=" + weight + ", species=" + species + "]";
	}

	public static void main(String[] args) {
		

	}

}
