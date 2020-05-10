package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.ArrayList;
import java.util.List;

class Boss{
	private String name;
	public Boss(String name){
		this.name = name;
	}
	public String getName(){return name.toUpperCase();}
	public String toString(){return getName();}
}
class Initech{
	public static void main(String[]args){
		final List<Boss> bosses = new ArrayList(8);
		bosses.add(new Boss("Jenny"));
		bosses.add(new Boss("Ted"));
		bosses.add(new Boss("Grace"));
//		bosses.removeIf(s -> s.equalsIgnoreCase("ted")); //(*) doesn't compile as s is not a String
		bosses.removeIf(s -> s.getName().equalsIgnoreCase("ted"));
		System.out.print(bosses);
	}
}
