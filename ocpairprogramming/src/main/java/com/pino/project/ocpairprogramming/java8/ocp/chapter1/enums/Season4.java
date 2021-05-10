package com.pino.project.ocpairprogramming.java8.ocp.chapter1.enums;

/**
 * 
 * @author matteodaniele
 * if we don't want each and every enum value to have a method, 
 * we can create a default implementation and override it only for the special cases (see Season 4)
 */
public enum Season4 {
	WINTER {
		public void printHours() { System.out.println("shprt hours"); }
	},
	SUMMER {
		public void printHours() { System.out.println("long hours"); }
	},
	SPRING, FALL ;
	//Default implementation 
	public void printHours() { System.out.println("default hours");}//it is used by who does not override it, such as SPRING and FALL
	//
}
