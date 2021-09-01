package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.Serializable;

public class Animal implements Serializable /*extends Object*/{
	private static final long serialVersionUID = /*1L*/2L;//modified version
	private /**/ transient /**/String name;//not to be serialized, it will be null after deserialization
	private /**/ transient /**/int age = 10;//same here, it will be 0 after deserialization
	private  /**/ static /**/ char type = 'C';//not serialized but will it be the last value set after deser.
	{this.age = 14;}
	//Rule: //during deserializ. this is the 1rst no-arg constructor called for the 1rst non serializ parent class
	public Animal() {
		//this();//after compiling Object() constructor is called in this case
		this.name = "Unknown";//skippend during Deserialization
		this.age = 12;//skippend during Deserialization
		this.type = 'Q';//skippend during Deserialization
	}
	public Animal(String name, int age, char type) { 
		this.name = name;
		this.age = age;
		this.type = type;
	}
	public String getName() { return name; } public int getAge() { return age; } public char getType() { return type; }
	public String toString() {
		return "Animal [name=" + name + ", age=" + age + ", type=" + type + "]";
	}
}
