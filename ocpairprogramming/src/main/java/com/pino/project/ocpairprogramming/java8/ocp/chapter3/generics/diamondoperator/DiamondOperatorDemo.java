package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.diamondoperator;

import java.util.ArrayList;
import java.util.List;

public class DiamondOperatorDemo {
	
	List<String> names;
	//1. diamond operator for static variable
	static List<String> sNames = new ArrayList<>();
	
	DiamondOperatorDemo(){
		names = new ArrayList<>();//2. diamond operator for instance variable
	}

	public static void main(String[] args) {
		//3. diamond operator for Local variable
		List<String> mNames = new ArrayList<>();
		
		//TRICKY Case
		List mNames2 = new ArrayList<>();//Row type reference warning
		List mNames3 = new ArrayList();//Row type instance warning
		List<String> mNames4 = new ArrayList();//Row type instance warning

	}

}
