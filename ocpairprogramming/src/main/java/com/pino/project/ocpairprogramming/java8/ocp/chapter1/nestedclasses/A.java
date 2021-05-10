package com.pino.project.ocpairprogramming.java8.ocp.chapter1.nestedclasses;

public class A {
	private int x = 10;
	class B {
		private int x = 20;
		class C {
			private int x = 30;
			public void allTheX() {
				System.out.println(x);// 30
				System.out.println(this.x);// 30
				//There is a special way of calling this to say which class you want to access.
				System.out.println(B.this.x);// 20
				System.out.println(A.this.x);// 10
			}
		}
	}
	public static void main(String[] args) {
		// How to instantiate nested classes
		A a = new A();
//		B b = new B();//Compilation error since it cannot find B
		B b = a.new B();//It is enough the type B because that is available at the member level of B.
		//Class A sees B, but not C. Therefore ..
//		C c = b.new C();//DOES NOT COMPILE either
		B.C c = b.new C();//or A.B.C
		c.allTheX();
		
	}

}
