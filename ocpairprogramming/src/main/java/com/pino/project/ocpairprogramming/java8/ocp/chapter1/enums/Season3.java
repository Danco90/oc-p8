package com.pino.project.ocpairprogramming.java8.ocp.chapter1.enums;

public enum Season3 {
	WINTER {
		public void printHours() { System.out.println("9am-3pm"); }
	},
	SPRING {
		public void printHours() { System.out.println("9am-5pm"); }
	},
	SUMMER {
		public void printHours() { System.out.println("9am-7pm"); }
	},
	FALL {
		public void printHours() { System.out.println("9am-5pm"); }
	};
	public abstract void printHours();//it makes all each and every enum value override it! otherwise they will not compile
	//if we don't want each and every enum value to have a method, we can create a default implementation and override it only for the special cases (see Season 4)

}
