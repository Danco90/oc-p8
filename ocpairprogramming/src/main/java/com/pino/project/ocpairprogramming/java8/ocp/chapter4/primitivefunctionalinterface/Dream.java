package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.function.Consumer;

class Sheep{}
class Dream{
	int MAX_SHEEP = 10;
	int sheepCount;
	public void countSheep(Consumer<Sheep> backToSleep){
		while(sheepCount < MAX_SHEEP){
			//TODO: Apply lambda
			sheepCount++;
		}
	}
	public static void main(String[]dark){
		new Dream().countSheep(System.out::println);
	}
}
