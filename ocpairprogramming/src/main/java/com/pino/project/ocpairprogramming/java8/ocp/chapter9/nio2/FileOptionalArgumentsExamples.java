package com.pino.project.ocpairprogramming.java8.ocp.chapter9.nio2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

/*
 * Table 9.1 Common optional arguments in NIO.2
 * ____________________________________________
 * Enum Value Usage Description
 * --------------------------------------------
 * LinkOption.NOFOLLOW_LINKS 	        Test file existing		If provided, symbolic links when encountered
 * 				       	                Read file data			will not be traversed. Useful for performing				
 * 					                    Copy 					fileoperations on symbolic links themselves rather
 *					                    Move 					file than their target.
 * FileVisitOption.FOLLOW_LINKS         Traverse a directory     If provided, symbolic links when encountered
 * 					                    tree						will be traversed.
 * StandardCopyOption.COPY_ATTRIBUTES   Copy file                If provided, all metadata about a file will be
 *									                             copied with it.
 * StandardCopyOption.REPLACE_EXISTING  Copy file				If provided and the target file exists, it will
 * 					                    Move file		        be replaced; otherwise, if it is not provided,
 *											                    an exception will be thrown if the file already
 * 											                    exists.
 * StandardCopyOption.ATOMIC_MOVE		Move file               The operation is performed in an atomic manner
 * 											                    within the file system, ensuring that
 *											                    any process using the file sees only a complete
 * 											                    record. Method using it may throw an exception
 * 											                    if the feature is unsupported by the file system.
 */
public class FileOptionalArgumentsExamples {

	public static void main(String[] args) {
		// 1. NOFOLLOW_LINKS : it is supported only by Path toRealPath(LinkOption... options) method, which takes a path that may not point to an existing file within the fs,
	    // 					   and it returns a reference to e real (absolute) path within the fs. It can convert a relative path to an absolute one. 
		
		//Example of symbolic link from /zebra/food.source -> /horse/food.txt and 
		//current working directory = /horse/schedule
		System.out.println("\n '3. NOFOLLOW_LINKS '");
		try {
			//CASE A of relatives paths, following any symbolik links BY DEFAULT (if not specified the vararg)
			System.out.println(Paths.get("zebra/food.source").toRealPath());//It prints* the absolute path /Users/matteodaniele/git/oc-pee/ocpairprogramming/horse/food.txt because of the symb.link. 
			System.out.println(Paths.get("horse/schedule/.././food.txt").toRealPath());//It prints* the absol. /Users/matteodaniele/git/oc-pee/ocpairprogramming/horse/food.txt since it goes one level up schedule and one level down horse
			//In this case both Absolute and relative resolve to the same ABSOLUTE file /horse/food.txt, as the symb.link points to a real file.
		
			//CASE B - same example without following any syslinks, it stops 
			System.out.println(Paths.get("zebra/food.source")
					.toRealPath(LinkOption.NOFOLLOW_LINKS));//It would stop and print* zebra/food.source because it does not cross any symb.link. 
			System.out.println(Paths.get("horse/schedule/.././food.txt").toRealPath(LinkOption.NOFOLLOW_LINKS));//It would print* horse/food.txt since it goes one level up schedule and one level down horse
			//In this case both Absolute and relative resolve to the same ABSOLUTE file /horse/food.txt, as the symb.link points to a real file.
		//(*) NB IT MIGHT NOT PRINT ANYTHING if there is not path with symb link in the current directory
		} catch (IOException e) {
		// Handle file I/O exception...
			System.out.println("Caught IOException : "+e);
		}
		
		// 2. FOLLOW_LINKS
		//Stream<Path> walk(Path start, FileVisitOption... options) : walk up to Integer.MAX_VALUE
		//Example 1: Tricky case with a sys.link LOOP 
		System.out.println("\n '3. FOLLOW_LINKS '");
//		try (Stream<Path> s = Files.walk(Paths.get("symlinks/path/to"), FileVisitOption.FOLLOW_LINKS)) {
//			s.forEach(System.out::println);//It will throws java.nio.file.FileSystemLoopException: symlinks/path/to/birds/robin/allBirds
//		} catch(IOException ex) {
//			System.out.println("Unexpected IOException " + ex);
//	    }
		
		//Example 2
//		try {//  www.java2s.com
//	        Path source = Paths.get("/home");
//	        Path target = Paths.get("/backup");
//	        Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
//	                new CopyDirectory(source, target));
//	    } catch (IOException ex) {
//	        ex.printStackTrace();
//	    }
		
		
		// 3. COPY_ATTRIBUTES : copy Any metadata provided about a file, such as e.g. last-modified-time attribute.
		System.out.println("\n '3. COPY_ATTRIBUTES '");
		try {
//			Files.createFile(Paths.get("in/source"));
			Path sour = FileSystems.getDefault().getPath("in/source");
			Path dest = FileSystems.getDefault().getPath("out/target");
			System.out.println("'in/source' initial modified attributes : "+ Files.getLastModifiedTime(sour).toMillis());
			Files.setLastModifiedTime(sour, FileTime.fromMillis(System.currentTimeMillis()));
			System.out.println("'in/source' last modified : "+ Files.getLastModifiedTime(sour).toMillis());
			System.out.println("Copying files ...with attributes");
			Files.copy(sour, dest, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING );
			System.out.println("Done. 'out/target' last modified : "+ Files.getLastModifiedTime(dest).toMillis());
		} catch (IOException e) {
			System.out.println("Copying failed: " + e.toString());
		}
		
		// 4. REPLACE_EXISTING : it avoids Exception caught when a resource is already existing and should not be overwritten
		//Path copy(Path source, Path target, CopyOption... options): it copies a resource from source to target only if not already existing (default behavior)
		System.out.println("\n '3. REPLACE_EXISTING '");
		try {
//			  Files.createDirectory(Paths.get("panda"));
//			  Files.createFile(Paths.get("panda/bamboo.txt"));
//			  Files.createDirectory(Paths.get("panda-save"));
//			  Files.copy(Paths.get("panda"), Paths.get("panda-save"), StandardCopyOption.REPLACE_EXISTING);//"2nd time it Throws DirectoryNotEmptyException
			  Files.copy(Paths.get("panda/bamboo.txt"),
					     Paths.get("panda-save/bamboo.txt"), StandardCopyOption.REPLACE_EXISTING );
		} catch (IOException e) {
			// Handle file I/O exception...
			System.out.println("Caught IOException :" + e);
		}
		
		
		// 5. ATOMIC_MOVE
		//Path move(Path source, Path target, CopyOption... options) : it replaces the destination if already existing.
		System.out.println("\n '3. ATOMIC_MOVE '");
		Path source = FileSystems.getDefault().getPath("test/test-source");
		Path destination = FileSystems.getDefault().getPath("test/test-destination");
		try {
			Files.createFile(Paths.get("test/test-source"));
			System.out.println("Moving files ...");
			Files.move(source, destination, StandardCopyOption.ATOMIC_MOVE);
			System.out.println("Done.");
		} catch (IOException e) {
			System.out.println("Moving failed: " + e.toString());
		}

	}
	
	private static void validateFileSystemLoopException(Path start, Path... causes) {
	    try (Stream<Path> s = Files.walk(start, FileVisitOption.FOLLOW_LINKS)) {
	        try {
	            int count = s.mapToInt(p -> 1).reduce(0, Integer::sum);
//	            fail("Should got FileSystemLoopException, but got " + count + "elements.");
	        } catch (UncheckedIOException uioe) {
	            IOException ioe = uioe.getCause();
	            if (ioe instanceof FileSystemLoopException) {
	                FileSystemLoopException fsle = (FileSystemLoopException) ioe;
	                boolean match = false;
	                for (Path cause: causes) {
	                    if (fsle.getFile().equals(cause.toString())) {
	                        match = true;
	                        break;
	                    }
	                }
//	                assertTrue(match);
	            } else {
//	                fail("Unexpected UncheckedIOException cause " + ioe.toString());
	            }
	        }
	    } catch(IOException ex) {
//	        fail("Unexpected IOException " + ex);
	    }
	}

}
