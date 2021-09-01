package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

public class UsingVariablesInLambdas {
	
	interface Gorilla { String move(); }//It is a functional interface Supplier-like. Indeed move() looks like get() 
	
	class GorillaFamily {
	  //RULE1: static variables can be passed into the lambdas
		
	  //RULE2: instance variables can be passed into the lambdas
	  String walk = "walk";//instance variable
	  void everyonePlay(boolean baby) {//method parameter 
		  //RULE3: local variable must be final or effective final
		  String approach = "amble";//local variable MUST STAY EFFECTIVELY final if used in lambdas
		  //approach = "run";//We had better not change it, so as to keep it effectively final. 
		  //But if we change it, it will cause () -> approach to fail because approach won't be eff.final anymore
		  //Here are a few LAMBDAS, as Anonymous ways to implement the functional interface Gorilla
		  
		  //RULE4: method parameter(must be final or effective final)
		  //baby = false;//Compilation error
		  play(() -> walk);//instance variable
		  play(() -> baby ? "hitch a ride": "run");//method parameter(must be final or effective final)
		  play(() -> approach);//local variable (effectively final)
	  }
	  void play(Gorilla g) {
		  System.out.println(g.move());
	  }
	}

	public static void main(String[] args) {
		
	}

}
