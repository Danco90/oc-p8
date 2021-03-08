package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonStreamOperations {

	public static void main(String[] args) throws IOException {
		//Closing
		
		//Flushing
		
		//Marking : to move the stream back to an earlier position. It is not supported by FileInputStream and ObjectInputStream.
		
		try(BufferedInputStream is = new BufferedInputStream(new FileInputStream("animals/animal.txt"))){
			System.out.print ((char)is.read());//A
			if(is.markSupported()) {
				is.mark(100);
				System.out.print ((char)is.read());//B
				System.out.print ((char)is.read());//C
				is.reset();
			}
			System.out.print ((char)is.read());//B since it is supported. 
			System.out.print ((char)is.read());//C since it is supported
			System.out.print ((char)is.read());//D
		}
		
		//Skipping over Data
		try(BufferedInputStream is = new BufferedInputStream(new FileInputStream("animals/tiger.txt"))){
			System.out.println();
			System.out.print ((char)is.read());//T
			is.skip(2);//skip 2 bytes, that are 2 chars
			is.read();//E
			System.out.print ((char)is.read());//R 
			System.out.print ((char)is.read());//S
		}
		
		FilterInputStream f=null;
		
		InputStreamReader isr =null;
		FileReader fr = null;
	}

}
