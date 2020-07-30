package com.pino.project.ocpairprogramming.java8.ocp.chapter1.instanceOf;

public class BlackDog extends Dog {

	public static void main(String ...args) {
		BlackDog blackdog = new BlackDog();
		
		assert blackdog instanceof BlackDog;
		assert blackdog instanceof Dog;
		assert blackdog instanceof Canine;
		assert blackdog instanceof Animal;
		//assert !(blackdog instanceof Pino); //does not compile because Pino is 
		//a class not related to BlackDog

		Dog bdog = new BlackDog();

		assert bdog instanceof BlackDog;
		assert bdog instanceof Dog;
		assert bdog instanceof Canine;
		assert bdog instanceof Animal;
		//assert bdog instanceof Pino; //does not compile, same as above
		
		Dog dog = new Dog();
		
		assert !(dog instanceof BlackDog);
		assert dog instanceof Dog;
		assert dog instanceof Canine;
		assert dog instanceof Animal;
		//assert dog instanceof Pino; //idem as above

		
		Canine canine = new Dog(); 
		
		assert !(canine instanceof BlackDog);
		assert canine instanceof Dog;
		assert canine instanceof Canine;
		assert canine instanceof Animal;
		assert !(canine instanceof Human);//compiles, compiler leaves us a choice to define a class 
		//that implements Human and extends Canine at same time, in the future
		//assert canine instanceof Pino; //does not compile, compiler knows that Canine and Pino are unrelated

		//here is that class -> HumanCanine
		Canine humanCanine = new HumanCanine();
		assert humanCanine instanceof Human;
		
		
		//with interface references, everything is possible
		Animal animal = new BlackDog();
		assert animal instanceof BlackDog;
		assert animal instanceof Dog;
		assert !(animal instanceof Human); //compiles, compiler leaves us a choice to define a class 
		//that implements animal and Human at same time, in the future
		assert !(animal instanceof Pino); //compiles, compiler leaves us a choice to define a class 
		//that implements animal and extends Pino, in the future
		
		//here is that class -> HalfAnimalHalfPino
		Animal newpino = new HalfAnimalHalfPino();
		assert newpino instanceof Animal;
		assert newpino instanceof Human;
		assert newpino instanceof Pino;

		//with null object
		Animal nullAnimal = null;
		assert !(nullAnimal instanceof Animal);
		assert !(nullAnimal instanceof Object); 
		assert !(null instanceof Object);
	}
}


class Dog extends Canine {}

abstract class Canine implements Animal {}

class HumanCanine extends Canine implements Human {}

interface Animal {}

interface Human {}

class Pino implements Human {}

class HalfAnimalHalfPino extends Pino implements Animal {}