package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

public class UsingVariablesInLambdas {
	
	interface Gorilla { String move(); }
	
	class GorillaFamily {
	  String walk = "walk";
	  void everyonePlay(boolean baby) {
		  String approach = "amble";
		  //approach = "run";//We had better not change it, so as to keep it effectively final. 
		  //But if we change it, it will cause () -> approach to fail because aproach won't be eff.final anymore
		  play(() -> walk);//instance variable
		  play(() -> baby ? "hitch a ride": "run");//method parameter(effective final)
		  play(() -> approach);//local variable (effectively final)
	  }
	  void play(Gorilla g) {
		  System.out.println(g.move());
	  }
	}

	public static void main(String[] args) {
		
	}

}
