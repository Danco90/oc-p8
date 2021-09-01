package com.pino.project.ocpairprogramming.java8.ocp.chapter8.io;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IntroducingStreams {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//Low-Level vs. High-Level Streams
		Console console = System.console();
		try (
				BufferedReader br = new BufferedReader(
									new FileReader("in/source-data.txt"))) {
//			System.out.println(br.readLine());
			if(console == null) 
			{ System.out.println(br.readLine());
				//throw new RuntimeException("Console not available"); 
			} 
			else { console.writer().write(br.readLine()); }
		}

	}

}
