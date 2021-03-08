package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UsingTryWithResources {

	public static void main(String[] args) throws IOException {
		UsingTryWithResources m = new UsingTryWithResources();
		//m.oldApproach(Paths.get("animals/first.txt"), Paths.get("animals/second.txt"));
		//Improved approach to ensure both resource can be closed
		//m.betterOldApproach(Paths.get("animals/first.txt"), Paths.get("animals/secondBetterOld.txt"));
		//Using try-with-resourced introduced in hava 7, the same can be rewritten with less code
		//m.newApproach(Paths.get("animals/first.txt"), Paths.get("animals/secondNew.txt"));
		
	}
	
	/**
	 * The new version has half as many lines as the old one. And there is no longer code just to close resource.
	 * @param path1
	 * @param path2
	 * @throws IOException
	 */
	public void newApproach(Path path1, Path path2) throws IOException {
		try (BufferedReader in = Files.newBufferedReader(path1);
			 BufferedWriter out = Files.newBufferedWriter(path2)){
				out.write(in.readLine());
				//Just before the end of the method, out and in are respectively closed (reverse order they were originally created)
		}
	}
	
	public void oldApproach(Path path1, Path path2) throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			in = Files.newBufferedReader(path1);
			out = Files.newBufferedWriter(path2);
			out.write(in.readLine());
		} finally {
			if (in != null) in.close();//should it fail (throw exception), it will cause out resource leak
			if (out != null) out.close();//it risks to stay unclosed since it would be never executed in case of exception of the previouse line
		}
	}
	
	/**
	 * We swallow the exceptions to ensure both close() are executed and resources are attempted to be closed.
	 * @param path1
	 * @param path2
	 * @throws IOException
	 */
	public void betterOldApproach(Path path1, Path path2) throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			in = Files.newBufferedReader(path1);
			out = Files.newBufferedWriter(path2);
			out.write(in.readLine());
		} finally {
			try {
				in.close();//should it fail (throw exception), it WON'T prevent the next line to be executed!
			} catch (IOException e) {} //swallowed to ensure both close are executed!
			try {
				out.close();//it risks to stay unclosed since it would be never executed in case of exception of the previouse line
			} catch (IOException e) {} //swallowed 
		}
	}

}
