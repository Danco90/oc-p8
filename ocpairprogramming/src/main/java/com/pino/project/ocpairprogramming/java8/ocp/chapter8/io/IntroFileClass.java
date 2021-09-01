package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.File;

public class IntroFileClass {

	public static void main(String[] args) {
		//How to retrieve the local separator character
		System.out.println(System.getProperty("File.separator"));// Linux-based is /
		System.out.println(java.io.File.separator);
		
		//Creating a File Object
		//1rst constructor
		File file = new File("/home/smith/data/zoo.txt");//IT DOES NOT ACTUALLY CREATE a file because there's no such a directory called /smith
		//file.exists() output true only if the file already exists!
		System.out.println(file.exists());//false because it has been never created before.
		file = new File("/Users/matteodaniele/git/oc-pee/ocpairprogramming/smith/data/zoo.txt");
		System.out.println(file.exists());//true
		//2rst constructor
		File parent = new File("/Users/matteodaniele/git/oc-pee/ocpairprogramming/smith");
		File child = new File(parent,"/data/zoo.txt");
		System.out.println(child.exists());//true
		
		//Working with a File Object
		//Commonly used java.io.File methods
		File f1 = new File("/Users/matteodaniele/git/oc-pee/ocpairprogramming/smith/data/zoo.txt");
		System.out.println("File Exists: "+f1.exists());//true
		if(f1.exists()) {
			System.out.println("Absolute path :"+f1.getAbsolutePath());// /Users/matteodaniele/git/oc-pee/ocpairprogramming/smith/data/zoo.txt"
			System.out.println("Is Directory :"+f1.isDirectory());//false
			System.out.println("Parent path:"+f1.getParent());// /Users/matteodaniele/git/oc-pee/ocpairprogramming/smith/data
			if(f1.isFile()) {
				System.out.println("File size (byte) :"+f1.length());//6 (1 byte for each character)
				System.out.println("File lastModified (millis since epoch when it was modified):"+f1.lastModified());//1612706958000
				
			} else {
				for(File subfile: f1.listFiles()) {
					System.out.println("\t "+subfile.getName());//prints nothing since no subfiles
				}
			}
		
		}
		
		//
	}

}
