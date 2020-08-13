package com.pino.project.ocpairprogramming.java8.ocp.chapter9.nio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Absolute vs. Relative Is File System Dependent
 * 
 ■■ If a path starts with a forward slash, it is an absolute path, such as /bird/parrot.
 ■■ If a path starts with a drive letter, it is an absolute path, such as C:\bird\emu.
 ■■ Otherwise, it is a relative path, such as ..\eagle.
 * @author matteodaniele
 *
 */
public class PathsAndFiles {

	public static void main(String[] args) throws URISyntaxException, IOException {
		
//		construct a Path using the Paths class
		
		//A - get(String path)
		//Path reference to a relative file into the current working directory ('/ocpairprogramming')
		Path path1 = Paths.get("pandas/cuddly.png");
		
		//Path reference to an absolute file in a Windows-based system
		Path path2 = Paths.get("c:\\zooinfo\\November\\employees.txt");
		
		//Path reference to an absolute directory ('/home') in a Linux or Mac-based system
		Path path3 = Paths.get("/home/zoodirector");
		
		//B - get(String..varargs)
		path1 = Paths.get("pandas","cuddly.png");
		path2 = Paths.get("c:","zooinfo","November","employees.txt");
		path3 = Paths.get("/","home","zoodirector");
		
		//C - get(URI uri) - (*) we can use URI values, referencing absolute path, for both local and network paths
		//path1 = Paths.get(new URI("file://pandas/cuddly.png")); // THROWS EXCEPTION (**) AT RUNTIME
		//WORKAROUND 1 - Trying to use URI.getPath() before passing it to Paths.get()
		URL url = new URL("file:///c:/zoo-info/November/employees.txt");
		path1 = Paths.get(url.toURI().getPath());
		File file = Paths.get(url.toURI().getPath()).toFile();//throws MalformedURLException
		//path2 = Paths.get(new URI("file:///c:/zoo-info/November/employees.txt"));//throws java.nio.file.FileSystemNotFoundException: Provider "http" not installed
		path2 = Paths.get(new URL("file:///c:/zoo-info/November/employees.txt").toURI().getPath());//throws MalformedURLException
		
		//path3 = Paths.get(new URI("file:///home/zoodirectory"));
		path3 = Paths.get(new URL("file:///home/zoodirectory").toURI().getPath());//throws MalformedURLException
		
		//(*) new URI(String) does throw a checked URISyntaxException 
		//(**) as URIs must reference ABSOLUTE paths at runtime. 
		/*nb: The URI class does have an isAbsolute() method, although this is related to whether or not the URI has
		a schema, not the file location.*/
		
		//Additional example which are No needed for OCP exam
//		Path path4 = Paths.get(new URI("http://www.wiley.com"));
		Path path4 = Paths.get(new URL("http://www.wiley.com").toURI().getPath());
//		Path path5 = Paths.get(new URI("ftp://username:password@ftp.the-ftp-server.com"));
		Path path5 = Paths.get(new URL("ftp://username:password@ftp.the-ftp-server.com").toURI().getPath());
		
		//reciprocal method toUri()
//		path4 = Paths.get(new URI("http://www.wiley.com"));
		path4 = Paths.get(new URL("http://www.wiley.com").toURI().getPath());
		URI uri4 = path4.toUri();
		
		//Accessing the underlying Object
		//FileSystem getDefault()
		Path p1 = FileSystems.getDefault().getPath("pandas/cuddly.png");
		Path p2 = FileSystems.getDefault().getPath("c:", "zooinfo", "November");// c:/zooinfo/November
		Path p3 = FileSystems.getDefault().getPath("/home/zoodirector");
		
		//Working with Legacy File Interface
		File ff = new File("pandas/cuddly.png");
		Path path = file.toPath();
		file = path.toFile();//for backward compatibility
		
		
		/*
		 * Providing Optional Arguments
		 */
		
		/*
		 * Using Path Objects 
		 * 
		 * Viewing the Path with 
		 * toString(), getNameCount(),and getName()
		 */
		//Case Absolute path
		Path pt = Paths.get("/land/hippo/harry.happy");//relative
		System.out.println("The Path Name is: "+pt);//automatically invokes the object's toString()
		for(int i=0; i< pt.getNameCount(); i++)
			System.out.println(" Element "+i+" is: "+pt.getName(i));//(*)
		
		//(*) getName(int) is zero-indexed, with the file system root excluded from the path
		
		//Case Absolute path
		Path pt2 = Paths.get("/land/hippo/harry.happy");//absolute. root element / is not included. So the count is still 3
		for(int i=0; i< pt2.getNameCount(); i++)
			System.out.println(" Element "+i+" is: "+pt2.getName(i));
		
		//Case only root element /
		Path rootpt = Paths.get("/");//absolute path and root element / which is not included. therefore the count is 0
		for(int i=0; i< rootpt.getNameCount(); i++)
			System.out.println(" Element "+i+" is: "+rootpt.getName(i));//if the count is 0, no element is printed
		
		/*
		 * Accessing Path Components with 
		 * getFileName(), getParent(), and getRoot()
		 */
		//CASE Absolute Instance of the path Object 
		printPathInformation(Paths.get("/zoo/armadillo/shells.txt"));
		System.out.println();
		
		//CASE Relative Instance of the path Object : it will not traverse
		// outside the working directory to the file system root.
		printPathInformation(Paths.get("armadillo/shells.txt"));
		printPathInformation(Paths.get("zoo/armadillo/shells.txt"));
		
		//Checking Path Type with isAbsolute() and toAbsolutePath()
		Path pth1 = Paths.get("C:\\birds\\egret.txt");//On Windows it is Absolute. Whereas, On mac is relative 
		System.out.println("Path1 is Absolute? "+pth1.isAbsolute());//therefore, it prints false on Mac
		System.out.println("Absolute Path1 :"+pth1.toAbsolutePath());// concatenates "/Users/matteodaniele/git/oc-pee/ocpairprogramming"
																				// + "/" + "C:\\birds\\egret.txt"
		Path pth2 = Paths.get("birds/condor.txt");//It is Relative both on Windows and on Mac
		System.out.println("Path2 is Absolute? "+pth2.isAbsolute());//false
		System.out.println("Absolute Path1 :"+pth2.toAbsolutePath());///Users/matteodaniele/git/oc-pee/ocpairprogramming/birds/condor.txt
		
		Path pth3 = Paths.get("/birds/condor.txt");//It is Relative on Windows and Absolute on Mac (because of the root '/')
		System.out.println("Path2 is Absolute? "+pth3.isAbsolute());//true
		System.out.println("Absolute Path1 :"+pth3.toAbsolutePath());// /birds/condor.txt
		
		System.out.println(Paths.get("/stripes/zebra.exe").isAbsolute());//true on Mac, false on Windows
		System.out.println(Paths.get("c:/goats/Food.java").isAbsolute());//false on Mac, tfalse on windows (because of c:// instead of c:\\)
		
		//Creating a New Path with subpath(int,int) : returns a relative subpath of the Path object, excluding the root '/'
		//Unless getName(int), subpath(int start, int end) can include multiple path components. And end is always excluded from the interval
		Path pat = Paths.get("/mammal/carnivore/raccoon.image");//Absolute on Mac. 
		System.out.println("Path is: "+pat);
		System.out.println("Subpath from 0 to 3 is: "+pat.subpath(0,3));// [0,1,2[3 (*) mammal/carnivore/raccoon.image
		System.out.println("Subpath from 1 to 3 is: "+pat.subpath(1,3));// [1,2[3 carnivore/raccoon.image
		System.out.println("Subpath from 1 to 2 is: "+pat.subpath(1,2));// [1[2 carnivore
		//(*) : 0-indexed element is the one right after the root '/'. And the maximum index that can be used is the length
//		System.out.println("Subpath from 0 to 4 is: "+path.subpath(0,4)); // THROWS IllegalArgumentEXCEPTION AT RUNTIME since end is greater than Max index
//		System.out.println("Subpath from 1 to 1 is: "+path.subpath(1,1)); // THROWS EXCEPTION AT RUNTIME since start must not be equal to end
		
		//Using Path Symbols
		Path pthh = Paths.get("/how/poor/you/are.image");//Absolute on Mac. 
		System.out.println("Initial Path is: "+pthh);
		Path pthh1 = Paths.get("/how/poor/you/../we/are.image");// .. references /poor as the parent of /you/are.image
		System.out.println("New Path is: "+pthh1);
		Path pthh2 = Paths.get("/how/poor/you/../../rich/we/are.image");// .. references /how as the parent of /poor/you/are.image
		System.out.println("New Path is: "+pthh2);
		
		
		//Deriving a Path with relativize(). It construct the relative path from on path to another.
		//CASE relativePathA .relativize( relativePathB)
		Path ptRelA = Paths.get("fish.txt");
		Path ptRelB = Paths.get("birds.txt");
		System.out.println(ptRelA.relativize(ptRelB));
		System.out.println(ptRelB.relativize(ptRelA));
		
		//CASE absolPathA .relativize( absolPathB)
		//Example B1
		Path absolPathA = Paths.get("E:\\habitat");
		Path absolPathB = Paths.get("E:\\sanctuary\\raven");
		System.out.println(absolPathA.relativize(absolPathB));// ../E:\sanctuary\raven
		System.out.println(absolPathB.relativize(absolPathA));// ../E:\habitat
		
		//Example B2
		absolPathA = Paths.get("/habitat");
		absolPathB = Paths.get("/sanctuary/raven");
		System.out.println(absolPathA.relativize(absolPathB));// ../E:\sanctuary\raven
		System.out.println(absolPathB.relativize(absolPathA));// ../E:\habitat
		
		//Checking for File Existence with Path toRealPath(Path) throws IOexception. 
		//A- Similarly to toAbsolutePath() method it can convert a Relative path to an absolute one, 
		//B- unlike toAbsolutePath, it also verifies that the file referenced by the path actually exists. If not, it throws an exception.
		//C- it is also the only Path method to support the NOFOLLOW_LINKS option.
		//D- as additional steps, it also remove redundant path elements. It, basically, calls normalize() on the resulting absolute path.
		//E- it's used also to gain access to the current working directory
		
		System.out.println();
		//Example of symbolic link from /zebra/food.source -> /horse/food.txt and 
		//current working directory = /horse/schedule
		try {
			//Absolute
			System.out.println(Paths.get("/zebra/food.source").toRealPath());//It would print* /horse/food.txt because of the symb.link. 
			//relative
			System.out.println(Paths.get(".././food.txt").toRealPath());//It would print* /horse/food.txt since it goes one level up schedule and one level down horse
			//In this case both Absolute and relative resolve to the same ABSOLUTE file /horse/food.txt, as the symb.link points to a real file.
		
		//(*) NB IT ACTUALLY DOES NOT PRINT ANYTHING as there is not path with symb link in the current directory
		} catch (IOException e) {
		// Handle file I/O exception...
		}
		
		//E - Gaining access to the current work dir
		try {
			System.out.println(Paths.get(".").toRealPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Interacting with Files
		//static boolean  exists(Path) , prevent what comes next from throwing exception if file does not exist
		System.out.println(Files.exists(Paths.get("/ostrich/feathers.png")));//check if the file exists
		System.out.println(Files.exists(Paths.get("/ostrich")));//check if the directory exists
		
		/*Testing Uniqueness with isSameFile(Path, Path)
		 * It determines  
		 * - whether two Paths relate to the same file within the fs by calling equal().
		 *    If not, then it locates each file in the fs and check if they are the same, throwing a checked IOException if either file does not exist.
		 *    isSameFile() DOES NOT compare the contents of the files. Two files are not equal if they have the SAME CONTENT BUT are in DIFFERENT LOCATIONS!
		 * 
		 * - if two Paths refere to the same directory within the fs;
		 * - It also follows symbolic links;
		 */
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/cobra1"), Paths.get("symlinks/path/to/snake")));
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/user/tree/../monkey"), Paths.get("symlinks/path/to/user/monkey")));
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/leaves/./giraffe.exe"),Paths.get("symlinks/path/to/leaves/giraffe.exe")));
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/flamingo/tail.data"),Paths.get("symlinks/path/to/cardinal/tail.data")));
		
		/*
		 * Making Directories 
		 * |java.io API             |  NIO.2 API
		 * +------------------------+-----------------
		 * |mkdir()  on a File      |Files.createDirectory() 
		 * |mkdirs() on a File      |Files.createDirectories()
		 * */
		//Path createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException, where attrs is optional
		// -It works only with existing parent directories (if specified in the path) 
		// -It throws a checked IOException if the dir cannot be created or already exists.
		//  If the parent directories not exist, throws NoSuchFileException.
		//  If the directory exists, throws FileAlreadyExistsException.
		//  If IO errors, throws IOException.
//		try 
//		{ 
//		  Files.createDirectory(Paths.get("bison/field"));//it throws exception, since bison does not exist
//		  Files.createDirectories(Paths.get("bison/field/pasture/green"));//and so does it		  
//		} catch (IOException e) {
//		// Handle file I/O exception...
//			System.out.println("Caught IOException :" + e);
//		}
		
		try 
		{   
//		    Files.createDirectory(Paths.get("bison") );//But this does work*. 
//			Files.createDirectory(Paths.get("bison/field"));//and so does* this
			Files.createDirectories(Paths.get("bison/field/pasture/green"));//and obviously this one works* as well
			//(*) or throws exception if already existing
		} catch (IOException e) {
			// Handle file I/O exception...
				System.out.println("Caught IOException :" + e);
	    }
		System.out.println();
		/* Duplicating File Contents with copy()
		 * 
		 * Overloaded method 1 - Files.copy(Path source,Path dest), which throws the checked
		 * IOException, such as when the file or directory does not exist or cannot be read.
		 * 
		 * NB: Dir copies are shallow rather than deep, as that files and subdirs within the dir are not copied
		 * */
//		try {
//		  Files.copy(Paths.get("panda"), Paths.get("panda-save"));//if not created throws checked NoSuchFileException
//		  Files.copy(Paths.get("panda/bamboo.txt"),
//				  Paths.get("panda-save/bamboo.txt"));
//		} catch (IOException e) {
//			// Handle file I/O exception...
//			System.out.println("Caught IOException :" + e);
//		}
//		System.out.println();
		
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
		System.out.println();
		
		/*
		 * Copying Files with java.io and NIO.2
		 * 
		 * NIO.2 Files API contains two overloaded copy() methods for copying files using java.io.streams
		 * 1rs Path copy(java.io.InputStream source, Path target, CopyOption... options) . It reads the contents from the stream and writes the output to a file represented by a Path object
		 * 2nd Path copy(Path source, java.io.OutputStream target) . It reads the contents of the file and writes the output to the stream. It does not support optional vararg values,
		 *                                                          Since the data is being written to a stream that MAY NOT represent a file system resource.
		 */
		try (InputStream is = new FileInputStream("in/source-data.txt");
				OutputStream out = new FileOutputStream("out/output-data.txt")) {
			//Copy stream* data to a file
//			Files.copy(is, Paths.get("out/mammals/wolf.txt"));//If already existing, it throws java.nio.file.FileAlreadyExistsException
			Files.copy(is, Paths.get("out/mammals/wolf.txt"), StandardCopyOption.REPLACE_EXISTING);
			
			//Copy file data to stream*
			Files.copy(Paths.get("in/fish/clown.xsl"), out);
		} catch (IOException e) {
			System.out.println("Caught IOException : "+e);
		}
		//*In this example, the InputStream and OutputStream parameters could refer to any valid
		//stream, including website connections, in-memory stream resources, and so forth.
		
		/*
		 * Changing a File Location with move()
		 * 
		 * The Files.move(Path,Path, CopyOption) method moves or renames a file or directory within the file system.
		 * - It throws the checked IOException in the event that the file or dir could not be found or moved.
		 * By default, the move() method will 
		 * 	follow links, 
		 * 	throw an exception if the file already exists, 
		 *  and not perform an atomic move.
		 *  NOTE1: If the fs does not support atomic moves (when ATOMIC_MOVE specified), an 
		 *  AtomicMoveNotSupportedException will be thrown
		 *  
		 *  NOTE2:The Files.move() method can be applied to non-empty directories only if
		 *  they are on the same underlying drive. While moving an empty directory
		 *  across a drive is supported, moving a non-empty directory across a drive
		 *  will throw an NIO.2 DirectoryNotEmptyException .
		 */
//		try {
//		   Files.deleteIfExists(Paths.get("in/zoo-new"));
//		   Files.move(Paths.get("in/zoo"), Paths.get("in/zoo-new"));//rename zoo in zoo-new, since in the same directory
//		   Files.move(Paths.get("in/user/addresses.txt"), Paths.get("in/zoo-new/addresses.txt"));//*
//		} catch (IOException e) {
//			System.out.println("Caught IOException : "+e);
//		}
		//NB* : Unlike mentioned in the OCP book, it does not rename address.txt in address2.txt
		
		/*
		 * Removing a File with delete() and deleteIfExists()
		 * 
		 * The Files.delete(Path) deletes a file or empty directory within the file system.
		 * 	-It throws the checked IOException under a variety of circumstances,
		 * 	 such as if the the path represents a non-empty directory, the operation will throw the DirectoryNotEmptyException
		 * 	-If the target of the path is a symlink, then ONLY the symlink will be deleted and NOT the target
		 * 
		 * The boolean deleteIfExists(Path) is identical to the previous method, except that 
		 * It WON'T throw an exception if the file or dir does not exist, but instead it will return false.
		 * It will still throw an exception if the file or dir DOES exist but FAILS, such as not empty dir
		 */
		
		 try {
			 Files.createDirectory(Paths.get("in/vulture"));
			 Files.createFile(Paths.get("in/vulture/feathers.txt"));
			 Files.delete(Paths.get("in/vulture/feathers.txt"));
			 Files.delete(Paths.get("in/vulture"));
			 Files.deleteIfExists(Paths.get("/pigeon"));
		 } catch (IOException e) {
			// Handle file I/O exception...
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 /*
		  * Reading and Writing File Data with newBufferedReader() and newBufferedWriter()
		  * 
		  * 1) newBufferedReader(Path,Charset) reads the file specified by the Path location using a java.io.BufferedReader object. 
		  *  It also requires a Charset for the character encoding to use to read the file
		  *  NOTE: characters can be encoded in bytes in a variety of ways. Charset.defaultCharset() can be used to get the default Charset for the JVM
		  * 
		  * 2) newBufferedWriter(Path,Charset) writes to a file specified at the Path location using a java.io.BufferedWriter object. 
		  *  It also takes a Charset for the character encoding to use to read the file
		  *  
		  *  NOTE: Both of these methods use buffered streams rather than low-level file streams.
		  *        Buffered stream are much more performant, so much so that ("tanto (è vero) che") the NIO.2 API includes methods that
		  *        specifically return these stream classes.
		  * */
		 Path pthr = Paths.get("animals/gopher.txt");
		 try (BufferedReader br = Files.newBufferedReader(pthr, Charset.forName("US-ASCII"))) {
			// Read from the stream
			 String currentLine = null;
			 while((currentLine = br.readLine()) != null)
				 System.out.println(currentLine);
		 } catch (IOException e) {
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 //BufferedWriter newBufferedWriter(Path path, Charset cs, OpenOption... options), which also supports taking additional enum
		 //values in an optional vararg, such as appending to an existing file instead of overwriting it,
		 Files.deleteIfExists(Paths.get("animals/gorilla.txt"));
		 Path pthw = Paths.get("animals/gorilla.txt");
		 try (BufferedWriter bw = Files.newBufferedWriter(pthw, Charset.forName("UTF-16"))) {
			 char[] cbuf = new char[] {'1','a','?','b'};
			 bw.write(cbuf);
			 bw.write("\n");
			 bw.write(1);
			 bw.write("\n");
			 bw.write("a string");
			 bw.write("\n");
			 bw.write(cbuf, 1, 2);
			 bw.write("\n");
			 bw.write("supercalifragilispichespiralidoso", 9, 7);
			 
		 } catch (IOException e) {
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 /* READING APPROACH 2 : 
		  * Reading Files with readAllLines()
		  * 
		  * Files.readAllLines() method reads all of the lines of a text file and returns the results as an ordered List of String values.
		  * It throws a IOException if the file cannot be read.
		  * NOTE: Be aware that the entire file is read when readAllLines() is called, with the resulting
		  *       String array storing all of the contents of the file in memory at once.
		  *       Therefore, if the file is significantly large, you may encounter an OutOfMemoryError 
		  *       trying to load all of it into memory.
		  */
		 Path ptth = Paths.get("fish/sharks.log");
		 try {
			 final List<String> lines = Files.readAllLines(ptth);
			 for(String line: lines) 
				 System.out.println(line);
				 
		 } catch (IOException e) {
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 /*
		  * READING APPROACH 3 : new streambased NIO.2 method that is far more performant on large files.
		  */
		
	}
	
	public static void printPathInformation(Path path) {
		System.out.println("Filename is: "+path.getFileName());
		System.out.println("Root is: "    +path.getRoot());//returns null if the Path object is relative.
		Path currentParent = path;
		while((currentParent = currentParent.getParent()) != null)// returns a Path instance representing the parent path or null if there is no such parent.
			System.out.println(" Current parent is: "+currentParent);
	}

}
