package com.pino.project.ocpairprogramming.java8.ocp.chapter9.nio2.stream;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class NewStreamMethods {

	public static void main(String[] args) {
		/*Files.walk(path)
		 * 1.a) Stream<Path> walk(Path path) : 
		 * - returns a Stream<Path> object that traverses the directory in a depth-first (DFS), lazy* manner
		 *   lazy means that the set of elements is BUILT and READ while directory is being traversed. 
		 * - Until a specific subdirectory is reached, its child elements are NOT LOADED
		 * - By default, the method iterates UP TO Integer.MAX_VALUE directories deep
		 * - It will NOT traverse symbolic links by DEFAULT
		 * */
		
		//Example 1 - Iterates over a directory and outputs all the files ending with a java extentsion.
		Path path = Paths.get("bigcats");
		try {
			Files.walk(path)
			.filter(p -> p.toString().endsWith(".java"))
			.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("IOException : No files found.");
		}
		
		//1.b) Stream<Path> walk(Path path, int maxDirDepth) : A value 0 indicates the current path record itself.
		try {
			System.out.println("\nIterating up to 1 directory deep ..");
			Files.walk(path,1)
			.filter(p -> p.toString().endsWith(".java"))
			.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("IOException : No files found.");
		}
		
		//1.c) Stream<Path> walk(Path path, int maxDirDepth, FileVisitOption... options) 
		try {
			System.out.println("\nIterating up to 1 directory deep ..");
			Files.walk(path,1, FileVisitOption.FOLLOW_LINKS)//Enable any symlink in the the walk
			.filter(p -> p.toString().endsWith(".java"))
			.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("IOException : No files found.");
		}
		
		/*2) Files.newDirectoryStream()
		 * DirectoryStream<Path> newDirectoryStream() : it is similar to Files.walk(), 
		 * except the returned object DOES NOT inherit from java,util.stream.Stream
		 */
		System.out.println();
		
		
		//AVOIDING Circular Paths
		//A cycle is an infinite circular dependency where an entry in a directory is an ancestor of the directory.
		//directory '/birds/robin' containing a symlink 'birds/robin/allBirds'
		//symlink pointing to '/birds'
		
		//**TRICKY** Example with a cycle detected - It will throw a FileSystemLoopException when the cycle is detected.
		Path circPath = Paths.get("symlinks/path/to/birds/robin/allBirds");// allBirds -> /birds
//		try {
//			System.out.println("\nIterating up to 1 directory deep ..");
//			Files.walk(circPath)//IT WON'T TRAVERSE any symLink
//			Files.walk(circPath,FileVisitOption.FOLLOW_LINKS)//IT WILL TRAVERSE any symLink and throws unchecked FileSystemLoopException exception
//			.filter(p -> p.toString().endsWith(".java"))
//			.forEach(System.out::println);
//		} catch (IOException e) {
//			System.out.println("IOException : No files found.");
//		}
		
		//Files.find(Path,int,BiPredicate)
		Path pth = Paths.get("bigcats");
		long dateFilter = 1420070400000l;
		try {
//			System.out.println("\nIterating up to 1 directory deep ..");
//			Files.walk(circPath)//IT WON'T TRAVERSE any symLink
			Stream<Path> stream = Files.find(pth,2, 
					(p,b) -> p.toString().endsWith(".java") &&
					b.lastModifiedTime().toMillis() > dateFilter,FileVisitOption.FOLLOW_LINKS);//IT WILL TRAVERSE any symLink and throws unchecked FileSystemLoopException exception
			stream.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("IOException : No files found.");
		}
		

	}

}
