package com.pino.project.ocpairprogramming.java8.ocp.chapter9.nio2;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	public static void main(String[] args) throws URISyntaxException, MalformedURLException {
		
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
		
	}
	
	public static void printPathInformation(Path path) {
		System.out.println("Filename is: "+path.getFileName());
		System.out.println("Root is: "    +path.getRoot());//returns null if the Path object is relative.
		Path currentParent = path;
		while((currentParent = currentParent.getParent()) != null)// returns a Path instance representing the parent path or null if there is no such parent.
			System.out.println(" Current parent is: "+currentParent);
	}

}
