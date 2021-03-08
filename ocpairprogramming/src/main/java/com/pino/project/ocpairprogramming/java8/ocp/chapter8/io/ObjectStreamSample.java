package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectStreamSample {
	
	public static List<Animal> getAnimals(File dataFile) throws IOException, ClassNotFoundException{
		
		List<Animal> animals = new ArrayList<Animal>();
		try (ObjectInputStream in = new ObjectInputStream(//H.L
				new BufferedInputStream(//H.L for performance reason
						new FileInputStream(dataFile)))){//L.Level 
				while(true) {
					Object obj = in.readObject();
					if(obj instanceof Animal)
						animals.add((Animal)obj);
				}
		} catch (EOFException e) {//checks the End of file has been reached !
			
		}
		return animals;
	}
	
	public static void createAnimalsFile(List<Animal> animals, File dataFile) throws IOException, ClassNotFoundException{
		try(ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(dataFile)))){
			for(Animal animal: animals)
				out.writeObject(animal);
			
		} 
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		List<Animal> animals = new ArrayList<Animal>(); 
		animals.add(new Animal("Tommy Tiger",5,'T')); 
		animals.add(new Animal("Peter Penguin",8,'P'));//if type is static, it will be always 'P' after deserializ.
		
		File dataFile = new File("animals/animal.data");
		createAnimalsFile(animals, dataFile);
		System.out.println(getAnimals(dataFile));

	}

}
