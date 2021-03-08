package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WorkingWithStreams {

	public static void main(String[] args) throws IOException {
		// The FileInputStream and FileOutputStream Classes
		// read bytes from a file or write bytes to a file
		//They both have constructors that take a File object or a String
		File source = new File("animals/Zoo.class");
		File destination = new File("animals/ZooCopy.class");
		copy(source,destination);
		copyBuffer(source,destination);
	}
	
	public static void copy(File source, File destination) throws IOException {
		try(InputStream in = new FileInputStream(source);
				OutputStream out = new FileOutputStream(destination)){
			int b;
			while((b = in.read()) != -1){//untill the end of the file it is reached
				out.write(b);
			}
			
		}
	}
	
	public static void copyBuffer(File source, File destination) throws IOException {
		try(InputStream in = new BufferedInputStream(new FileInputStream(source));
				OutputStream out = new BufferedOutputStream(
										new FileOutputStream(destination))){
				byte[] buffer = new byte[1024];
				int lengthRead;
				while ((lengthRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, lengthRead);
					out.flush();
				}
		}
	}

}
