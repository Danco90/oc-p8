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
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
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
		 * BASIC info about the path representative
		 * Viewing the Path with 
		 * toString(), int getNameCount(),and Path getName(int index)
		 */
		//Case Absolute path
		System.out.println("BASIC INFO : Case relative path");
		Path pt = Paths.get("land/hippo/harry.happy");//relative
		System.out.println("The Path Name is: "+pt);//automatically invokes the object's toString()
		for(int i=0; i< pt.getNameCount(); i++)
			System.out.println(" Element "+i+" is: "+pt.getName(i));//(*)
		
		//(*) getName(int) is zero-indexed, with the file system root excluded from the path
		
		//Case Absolute path
		System.out.println("\nBASIC INFO : Case absolute path");
		Path pt2 = Paths.get("/land/hippo/harry.happy");//absolute. root element / is not included. So the count is still 3
		for(int i=0; i< pt2.getNameCount(); i++)
			System.out.println(" Element "+i+" is: "+pt2.getName(i));
		
		//Case only root element /
		System.out.println("\nBASIC INFO : Case only root element '/'");
		Path rootpt = Paths.get("/");//absolute path and root element / which is not included. therefore the count is 0
		for(int i=0; i< rootpt.getNameCount(); i++)
			System.out.println(" Element "+i+" is: "+rootpt.getName(i));//if the count is 0, no element is printed
		
		/*
		 * Accessing Path Components with 
		 * Path getFileName(), Path getParent(), and Path getRoot()
		 */
		//CASE Absolute Instance of the path Object 
		System.out.println("\nCASE Absolute Instance of the path Object ");
		printPathInformation(Paths.get("/zoo/armadillo/shells.txt"));
		System.out.println();
		
		//CASE Relative Instance of the path Object : it will not traverse
		// outside the working directory to the file system root.
		printPathInformation(Paths.get("armadillo/shells.txt"));
		printPathInformation(Paths.get("zoo/armadillo/shells.txt"));
		
		//Checking Path Type with  
		//	boolean isAbsolute()  returns true if absolute, false if relative.
		//	Path toAbsolutePath() converts a rel Path obj to an absolute Path object by joining* it to the current working directory
		//  *nb: if it is already absolute, then it is directly returned without concatenation
		Path pth1 = Paths.get("C:\\birds\\egret.txt");//On Windows it is Absolute. Whereas, On mac is relative 
		System.out.println("\n"+pth1+" is Absolute? "+pth1.isAbsolute());//therefore, it prints false on Mac
		System.out.println("Absolute Path1 :"+pth1.toAbsolutePath());// concatenates "/Users/matteodaniele/git/oc-pee/ocpairprogramming"
																				// + "/" + "C:\\birds\\egret.txt"
		Path pth2 = Paths.get("birds/condor.txt");//It is Relative both on Windows and on Mac
		System.out.println("\n"+pth2+"  is Absolute? "+pth2.isAbsolute());//false
		System.out.println("Absolute Path1 :"+pth2.toAbsolutePath());///Users/matteodaniele/git/oc-pee/ocpairprogramming/birds/condor.txt
		
		Path pth3 = Paths.get("/birds/condor.txt");//It is Relative on Windows and Absolute on Mac (because of the root '/')
		System.out.println("\n"+pth3+"  is Absolute? "+pth3.isAbsolute());//true
		System.out.println("Absolute Path1 :"+pth3.toAbsolutePath());// /birds/condor.txt
		
		System.out.println(Paths.get("/stripes/zebra.exe").isAbsolute());//true on Mac, false on Windows
		System.out.println(Paths.get("c:/goats/Food.java").isAbsolute());//false on Mac, tfalse on windows (because of c:// instead of c:\\)
		
		//Creating a New Path with subpath(int,int) : returns a relative subpath of the Path object, excluding the root '/'
		//Unless getName(int), subpath(int start, int end) can include multiple path components. And end is always excluded from the interval
		Path pat = Paths.get("/mammal/carnivore/raccoon.image");//Absolute on Mac. 
		System.out.println("\nPath is: "+pat);
		System.out.println("Subpath from 0 to 3 is: "+pat.subpath(0,3));// [0,1,2[3 (*) mammal/carnivore/raccoon.image
		System.out.println("Subpath from 1 to 3 is: "+pat.subpath(1,3));// [1,2[3 carnivore/raccoon.image
		System.out.println("Subpath from 1 to 2 is: "+pat.subpath(1,2));// [1[2 carnivore
		//(*) : 0-indexed element is the one right after the root '/'. And the maximum index that can be used is the length
//		System.out.println("Subpath from 0 to 4 is: "+path.subpath(0,4)); // THROWS IllegalArgumentEXCEPTION AT RUNTIME since end is greater than Max index
//		System.out.println("Subpath from 1 to 1 is: "+path.subpath(1,1)); // THROWS EXCEPTION AT RUNTIME since start must not be equal to end
		
		//Using Path Symbols
		System.out.println("\nUsing Path Symbols '.' and  '..'");
		Path pthh = Paths.get("/how/poor/you/are.image");//Absolute on Mac. 
		System.out.println("Initial Path is: "+pthh);
		Path pthh1 = Paths.get("/how/poor/you/../we/are.image");// .. references /poor as the parent of /you/are.image
		System.out.println("New Path is: "+pthh1);
		Path pthh2 = Paths.get("/how/poor/you/../../rich/we/are.image");// .. references /how as the parent of /poor/you/are.image
		System.out.println("New Path is: "+pthh2);
		
		
		//Deriving a Path with relativize(). It constructs the relative path from a path to another, as if they were in the same CURRENT DIR
		////NB: Like normalize(), It does not check that the file actually exists!
		//CASE relativePathA .relativize( relativePathB)
		System.out.println("\n CASE relativePathA .relativize( relativePathB)");
		Path ptRelA = Paths.get("fish.txt");
		Path ptRelB = Paths.get("birds.txt");
		System.out.println(ptRelA.relativize(ptRelB));
		System.out.println(ptRelB.relativize(ptRelA));
		
		//CASE absolPathA .relativize( absolPathB) computes the relative paths from one absolute location to another
		//      It requires that both paths be either relative or both absolute, and it will throw an IllegalArgumentException if mixed
		//      In Windows it requires also than both absolute paths, if used, must have the same root dir or drive letter.
		// NB : Note that the file system is not accessed to perform this comparison and the code will compile since Java is referencing the path elements and not the actual file values.
		// NB2: it does not clean up path symbols
		System.out.println("\n absolPathA .relativize( absolPathB)");
		//Example B1 - WINDOWS
		Path absolPathA = Paths.get("E:\\habitat");
		Path absolPathB = Paths.get("E:\\sanctuary\\raven");
		System.out.println(absolPathA.relativize(absolPathB));// ../E:\sanctuary\raven
		System.out.println(absolPathB.relativize(absolPathA));// ../E:\habitat
		
		//Example B2 - MAC
		absolPathA = Paths.get("/habitat");
		absolPathB = Paths.get("/sanctuary/raven");
		System.out.println("\n"+absolPathA.relativize(absolPathB));// ../E:\sanctuary\raven
		System.out.println(absolPathB.relativize(absolPathA));// ../E:\habitat
		
		//Example B3 - Exception thrown in MAC
		Path path11 = Paths.get("/primate/chimpanzee");
		Path path22 = Paths.get("bananas.txt");
		try{
			System.out.println("\n"+path11.relativize(path22)); // THROWS EXCEPTION AT RUNTIME only in Mac
		} catch (Exception e) {
			System.out.println("caught exception "+e);
		}
		
		//Example B3 - Exception thrown in WINDOWS
		Path path111 = Paths.get("c:\\primate\\chimpanzee");
		Path path222 = Paths.get("d:\\storage\\bananas.txt");
		try{
			System.out.println("\n"+path111.relativize(path222)); // THROWS EXCEPTION AT RUNTIME ONLY in Windows
		} catch (Exception e) {
			System.out.println("caught exception "+e);
		}
		
		
		//Joining Path Objects with resolve() creating a new Path by joining an existing path to the current path.
		//Basically, the object on which the resolve() method is invoked becomes the basis of the new Path object, with the input argument being
		//appended onto the Path.
		// NB: it does not clean up path symbols
		final Path path1111 = Paths.get("/cats/../panther");
		final Path path2222 = Paths.get("food");
		try{
			System.out.println("\n"+path1111.resolve(path2222)); // /cats/../panther/food
		} catch (Exception e) {
			System.out.println("caught exception "+e);
		}
		
		System.out.println("\nCase resolve() between two absolute paths");
		final Path path1112 = Paths.get("/turkey/food");
		final Path path1113 = Paths.get("/tiger/cage");
		try{
			System.out.println(path1112.resolve(path1113));// /tiger/cage
			System.out.println(path1113.resolve(path1112));// /turkey/food
		} catch (Exception e) {
			System.out.println("caught exception "+e);
		}
		
		//Cleaning up with normalize() : it can eliminate the redundancies in the paths
		//NB: Like relativize(), It does not check that the file actually exists!
		System.out.println("\nCleaning up with normalize() ");
		//CASE FOR WINDOWS sintax
		Path path1114 = Paths.get("E:\\data");//*
		Path path1115 = Paths.get("E:\\user\\home");
		//* in windows the escape char '/' is needed before slash /. But when printed will not be considered
		Path relativePath = path1114.relativize(path1115);/* ../E:\\user\home on Mac , whereas ..\\user\home on Windows */
		try{
			System.out.println(path1114.resolve(relativePath)); //  E:\data/../E: \ user\home on Mac , whereas 'E:\data\..\ user\home' on Windows 
			System.out.println("normalized Without redundancies : " +path1114.resolve(relativePath).normalize()); // 'E:\ user\home'
		} catch (Exception e) {
			System.out.println("caught exception "+e);
		}
		
		//CASE FOR MAC
		
		//Checking for File Existence with Path toRealPath(Path) throws IOexception. 
		//A- Similarly to toAbsolutePath() method it can convert a Relative path to an absolute one, 
		//B- unlike toAbsolutePath, it also verifies that the file referenced by the path actually exists. If not, it throws an exception.
		//C- it is also the only Path method to support the NOFOLLOW_LINKS option.
		//D- as additional steps, it also remove redundant path elements. It, basically, calls normalize() on the resulting absolute path.
		//E- it's used also to gain access to the current working directory
		
		System.out.println("\nPath toRealPath(Path) throws IOexception ");
		//Example of symbolic link from /zebra/food.source -> /horse/food.txt and 
		//current working directory = /horse/schedule
		try {
			//CASE A of relatives paths, following any symbolik links BY DEFAULT (if not specified the vararg)
			System.out.println("\n"+Paths.get("zebra/food.source").toRealPath());//It would print* /horse/food.txt because of the symb.link. 
			//relative
			System.out.println(Paths.get("horse/schedule/.././food.txt").toRealPath());//It would print* /horse/food.txt since it goes one level up schedule and one level down horse
			//In this case both Absolute and relative resolve to the same ABSOLUTE file /horse/food.txt, as the symb.link points to a real file.
			System.out.println(Paths.get("horse/schedule/.././food1.txt").toRealPath());
		//(*) NB IT ACTUALLY DOES NOT PRINT ANYTHING as there is not path with symb link in the current directory
		} catch (IOException e) {
			System.out.println("caught exception "+e);
		}
		
		//C
		//Example of symbolic link from /zebra/food.source -> /horse/food.txt and 
		//current working directory = /horse/schedule
		System.out.println("\n '3. NOFOLLOW_LINKS '");
		try {
			
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
		
		//E - Gaining access to the current work dir
		System.out.println("\n E - Gaining access to the current work dir");
		try {
			System.out.println(Paths.get(".").toRealPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Interacting with Files
		System.out.println("\n** Interacting with Files **\n");
		//static boolean  exists(Path) , prevent what comes next from throwing exception if file does not exist
		System.out.println(Files.exists(Paths.get("/ostrich/feathers.png")));//check if the file exists
		System.out.println(Files.exists(Paths.get("/ostrich")));//check if the directory exists
		
		/*Testing Uniqueness with boolean isSameFile(Path, Path) throws IOException
		 * It determines  
		 * - whether two Paths relate to the same file within the fs by calling equal().
		 *    If not, then it locates each file in the fs and check if they are the same, throwing a checked IOException if either file does not exist.
		 *    isSameFile() DOES NOT compare the contents of the files. Two files are not equal if they have the SAME CONTENT BUT are in DIFFERENT LOCATIONS!
		 * 
		 * - if two Paths refere to the same directory within the fs;
		 * - It also follows symbolic links;
		 */
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/cobra1"), Paths.get("symlinks/path/to/snake")));//It MUST output true because of the sys link
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/user/tree/../monkey"), Paths.get("symlinks/path/to/user/monkey")));
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/leaves/./giraffe.exe"),Paths.get("symlinks/path/to/leaves/giraffe.exe")));
		System.out.println(Files.isSameFile(Paths.get("symlinks/path/to/flamingo/tail.data"),Paths.get("symlinks/path/to/cardinal/tail.data")));
		
		/*
		 * Making Directories 
		 * |java.io API             |  NIO.2 API
		 * +------------------------+-----------------
		 * |mkdir()  on a File      |static Path createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException 
		 * |mkdirs() on a File      |static Path createDirectories(Path dir, FileAttribute<?>... attrs) throws IOException
		 * */
		//Both methods 
		// -work only with existing parent directories (if specified in the path) 
		// -throw a checked IOException if the dir cannot be created or already exists.
		// -If the directory exists, throws FileAlreadyExistsException.
		// -If IO errors, throws IOException.
		// -accept an optional list of FileAttribute<?> values to set on
		//  the newly created directory or directories.
		
		// Only createDirectory() method
		//  If the parent directory in which the new dir resides does not exist, throws NoSuchFileException.
	
		System.out.println("\n** Making Directories with Files.createDirectory() and Files.createDirectories() **\n");
		try 
		{ Files.deleteIfExists(Paths.get("bison/field/pasture/green"));
		  Files.deleteIfExists(Paths.get("bison/field/pasture"));
		  Files.deleteIfExists(Paths.get("bison/field"));
		  Files.createDirectory(Paths.get("bison/field"));//it throws exception, since bison does not exist
		  Files.createDirectories(Paths.get("bison/field/pasture/green"));//and so does it	
		} catch (IOException e) {
			System.out.println("Caught IOException :" + e);
		}
		
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
		
		System.out.println("\n** Duplicating File Contents with copy() **\n");
		/* Duplicating File Contents with copy()
		 * 
		 * 
		 * Overloaded method 1 - Files.copy(Path source,Path dest), which throws the checked
		 * IOException, such as when the file or directory does not exist or cannot be read.
		 * 
		 * NB: Dir copies are shallow rather than deep, as that files and subdirs within the dir are not copied
		 * */
		try {
			 Files.deleteIfExists(Paths.get("panda-save/bamboo.txt"));
		     Files.deleteIfExists(Paths.get("panda-save"));
		     Files.copy(Paths.get("panda"), Paths.get("panda-save"));//if not created throws checked NoSuchFileException
		     Files.copy(Paths.get("panda/bamboo.txt"), Paths.get("panda-save/bamboo.txt"));
		} catch (IOException e) {
			System.out.println("Caught IOException :" + e);
		}
		
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
		
		System.out.println("\n** Changing a File Location with move() **\n");
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
		try {
			   Files.deleteIfExists(Paths.get("in/zoo"));
			   Files.createDirectory(Paths.get("in/zoo"));
			   Files.deleteIfExists(Paths.get("in/zoo-new/addresses2.txt"));
			   Files.deleteIfExists(Paths.get("in/zoo-new/addresses3.txt"));
			   Files.deleteIfExists(Paths.get("in/zoo-new"));
			   Files.deleteIfExists(Paths.get("in/user/addresses.txt"));
			   Files.createFile(Paths.get("in/user/addresses.txt"));
			   Files.move(Paths.get("in/zoo"), Paths.get("in/zoo-new"));//rename zoo in zoo-new, since in the same directory, by deleting atomically in/zoo
			   Files.move(Paths.get("in/user/addresses.txt"), Paths.get("in/zoo-new/addresses2.txt"));//rename address.txt to address2.txt
			   Files.move(Paths.get("in/zoo-new/addresses2.txt"), Paths.get("in/zoo-new/addresses2.txt"));
		} catch (IOException e) {
			System.out.println("Caught IOException : "+e);
		}
		
		//Case of moving a non-empty directory in the same underlying drive Macintosh SSD
		System.out.println("\n** Moving a non-empty directory in the same underlying drive Macintosh SSD **\n");
	    try {
	    	 	   Files.deleteIfExists(Paths.get("in/zoo-new2/addresses2.txt"));
			   Files.deleteIfExists(Paths.get("in/zoo-new2"));
	    	 	   Files.move(Paths.get("in/zoo-new"), Paths.get("in/zoo-new2"));//IT WORKS
	    	 	   Files.move(Paths.get("in/zoo-new2"), Paths.get("in/zoo-new2"));//IT WORKS
	    	 	   System.out.println("** Moving an already existing file **"); 
	    	 	   Files.deleteIfExists(Paths.get("in/zoo-new3/addresses2.txt"));
			   Files.deleteIfExists(Paths.get("in/zoo-new3"));
			   Files.createDirectory(Paths.get("in/zoo-new3"));
			   Files.createFile(Paths.get("in/zoo-new3/addresses2.txt"));
	    	 	   Files.move(Paths.get("in/zoo-new3/addresses2.txt"), Paths.get("in/zoo-new2/addresses2.txt"));//It throws java.nio.file.FileAlreadyExistsException
	    } catch (IOException e) {
			System.out.println("Caught IOException : "+e);
		}
		
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
	    System.out.println("\n** Removing a File with delete() and deleteIfExists() **\n");
		 try {
			 Files.createDirectory(Paths.get("in/vulture"));
			 Files.createFile(Paths.get("in/vulture/feathers.txt"));
			 Files.delete(Paths.get("in/vulture/feathers.txt"));
			 Files.delete(Paths.get("in/vulture"));
			 System.out.println("delete '/pigeon' : "+Files.deleteIfExists(Paths.get("/pigeon")));
		 } catch (IOException e) {
			// Handle file I/O exception...
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 /*
		  * Reading and Writing File Data with newBufferedReader() and newBufferedWriter()
		  * 
		  * 1) static BufferedReader newBufferedReader(Path,Charset) reads the file specified by the Path location using a java.io.BufferedReader object. 
		  *  It also requires a Charset for the character encoding to use to read the file
		  *  NOTE: characters can be encoded in bytes in a variety of ways. Charset.defaultCharset() can be used to get the default Charset for the JVM
		  * 
		  * 2) static BufferedWriter newBufferedWriter(Path,Charset) writes to a file specified at the Path location using a java.io.BufferedWriter object. 
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
		 try 
		 { final List<String> lines = Files.readAllLines(ptth);
		   for(String line: lines) 
			   System.out.println(line);
		 } catch (IOException e) {
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 /*
		  * READING APPROACH 3 : new streambased NIO.2 method that is far more performant on large files.
		  */
		 
		 System.out.println("\n9.3.4. Understanding File Attributes");
		 /*
		  * 9.3.4. Understanding File Attributes
		  * 
		  * Reading Common Attributes with isDirectory(), isRegularFile(), and isSymbolicLink() of the the class Files
		  * NOTE: They both DO NOT THROW AN EXCEPTION if the path does not exist. They both rather return false
		  * 
		  * boolean isRegular(Path p) returns true if the target is regular file, even for a symlink as long as the link resolves a regular file
		  * regular file : one that contains content
		  */
		 Path pp1 = Paths.get("canine/coyote/fur.jpg");//*
		 Path pp2 = Paths.get("canine/types.txt");
		 Path pp3 = Paths.get("canine/coyote");
		 System.out.println("Is '"+pp1 +"' a Directory ? " + Files.isDirectory(pp1));//false since not existing
		 System.out.println("Is '"+pp2 +"' a Regular ? " + Files.isRegularFile(pp2));
		 System.out.println("Is '"+pp3 +"' a SymbolicLink ? " + Files.isSymbolicLink(pp3));
		 
		 //*NB: directories can have extensions in many file systems, so it
		 //is possible for fur.jpg to be the name of a directory.
		 System.out.println();
		 Path pp4 = Paths.get("canine/coyotes");
		 //Assume that /coyotes is a symlink (previously created)
		 System.out.print("\n'"+pp3 +"' isDirectory() : " + Files.isDirectory(pp3));
		 System.out.print(", isRegularFile() : " + Files.isRegularFile(pp3));
		 System.out.print(", isSymbolicLink() : " + Files.isSymbolicLink(pp3));
		 System.out.print("\n'"+pp2 +"' isDirectory() : " + Files.isDirectory(pp2));
		 System.out.print(", isRegularFile() : " + Files.isRegularFile(pp2));
		 System.out.print(", isSymbolicLink() : " + Files.isSymbolicLink(pp2));
		 System.out.print("\n'"+pp4 +"' isDirectory() : " + Files.isDirectory(pp4));//Sys.link points to directory canine/coyote**
		 System.out.print(", isRegularFile() : " + Files.isRegularFile(pp4));//**
		 System.out.print(", isSymbolicLink() : " + Files.isSymbolicLink(pp4));
		 
		 //**NOTE: You see that the value of isDirectory() and isRegular() in Table 9.3 cannot be determined
		 //on the symbolic link /coyotes without knowledge of what the symbolic link points to.
		 System.out.println();
		 /*
		  * Checking File Visibility with isHidden()
		  * It throws the checked IOException, as there may be an I/O error reading the underlying file information.
		  */
		 System.out.println("\nChecking File Visibility with isHidden()");
		 try {
			 Path hp = Paths.get("in/.walrus.txt");
//			 final PosixFileAttributes posixAttrs  = Files.readAttributes(hp, PosixFileAttributes.class);

			 boolean isHidden = Files.isHidden(hp);
//			 System.out.println("\nHidden (Posix)  : " + ((File) posixAttrs).isHidden());
			  
			 System.out.println("\n is '"+hp+"' hidden ? " + isHidden);//In order to returns true the file should starts with '.'
		 } catch (IOException e) {
			 System.out.println("Caught IOException : "+e);
		 }
		 
		 /*
		  * Testing File Accessibility with isReadable() and isExecutable()
		  * They both do not throw exceptions if the file does not exist but instead return false.
		  */
		  System.out.println("\nTesting File Accessibility with isReadable() and isExecutable()");
		  System.out.println(Files.isReadable(Paths.get("seal/baby.png")));
		  System.out.println(Files.isExecutable(Paths.get("seal/baby.png")));
		  
		 
		 /*
		  * Reading File Length with size()
		  * 
		  * Files.size(Path) method is used to determine the size of the file in bytes
		  * 1 char is 1 byte long
		  */
		 System.out.println("\nReading File Length with size()");
		 try {
//			 Files.createDirectories(Paths.get("zoo/c"));
//			 Files.createFile(Paths.get("zoo/c/animals.txt"));
			 System.out.println("The size of 'zoo/c/animals.txt' is "+ Files.size(Paths.get("zoo/c/animals.txt"))+" bites");
		 } catch (IOException e) {
		 // Handle file I/O exception...
		 }
		 
		 
		 System.out.println("\nManaging File Modifications with getLastModifiedTime() and\n" + 
		 		"		 setLastModifiedTime()");
		 try {
			 final Path p = Paths.get("rabbit/food.jpg");
			 //1. static FileTime getLastModifiedTime(Path path, LinkOption... options) throws IOException
			 System.out.println(Files.getLastModifiedTime(p).toMillis());
			 
			 //2. static Path setLastModifiedTime(Path path, FileTime time) throws IOException
			 System.out.println(Files.setLastModifiedTime(p, FileTime.fromMillis(System.currentTimeMillis())));
			 
			 System.out.println(Files.getLastModifiedTime(p).toMillis());
			 
			 //NB: Both of these methods have the ability to throw a checked IOException when the file is
			 //accessed or modified.
		 } catch (IOException e) {
			 
		 }
		 
		 /* Managing Ownership with getOwner() and setOwner()
		  * 
		  * 	 abstract UserPrincipalLookupService getUserPrincipalLookupService()
		  *  static FileSystem getDefault(), inside FileSystems factory
		  *  FileSystem getFileSystem(); inside Path interface
		  *  abstract UserPrincipalLookupService getUserPrincipalLookupService()
		  *  abstract UserPrincipal lookupPrincipalByName(String name) throws IOException;
		  *  
		  */
		 System.out.println("\nManaging Ownership with getOwner() and setOwner()");
		 System.out.println("Retrieve UserPrincipal - Approach 1 - FileSystems.getDefault()");
		 UserPrincipal owner = FileSystems.getDefault().getUserPrincipalLookupService()
				 				.lookupPrincipalByName("matteodaniele");
		 System.out.println(owner/*.getName()*/);//it invokes toString
		 
		 System.out.println("\nRetrieve UserPrincipal - Approach 2 - path..getFileSystem()");
		 owner = Paths.get("rabbit/food.jpg").getFileSystem().getUserPrincipalLookupService()
				 							.lookupPrincipalByName("matteodaniele");
		 System.out.println(owner);
		 
		 System.out.println("\nRetrieve specific user  - Files.getOwner()");
		 try {
			 //Read owner of file
			 Path ppp = Paths.get("chicken/feathers.txt");
			 System.out.println(Files.getOwner(ppp).getName());
			 
			 //Change owner of file
			 owner = ppp.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("matteodaniele");
			 Files.setOwner(ppp, owner);
			 
			 //Output the updated owner information
			 System.out.println(Files.getOwner(ppp).getName());
		 } catch (IOException e) {
			 
		 }
		 
		 /*
		  * Improving Access with Views
		  * 
		  * View : group of related attributes for a particular file system type. it is useful to read multiple attributes of a file or dir at a time
		  * Files.readAttributes(Path,Class<A>) and 
		  * Both of these methods can throw a checked IOException, such as when the view class is unsupported.
		  */
		 System.out.println("\nImproving Access with Views");
		 System.out.println("\nAccessing view info - method 1");
		 //Method 1 - Files.readAttributes(), which returns a read-only view of the file view
		 Path ptthh = Paths.get("turtles/sea.txt");
		 BasicFileAttributes data = Files.readAttributes(ptthh, BasicFileAttributes.class);
		 System.out.println("Is path a directory? "+data.isDirectory());
		 System.out.println("Is path a regular file? "+data.isRegularFile());
		 System.out.println("Is path a symbolic link? "+data.isDirectory());
		 System.out.println("Path not a file, directory, nor symbolic link? "+data.isOther());
		 
		 System.out.println("Size (in bytes): "+data.size());
		 
		 System.out.println("Creation date/time: "+data.creationTime());
		 System.out.println("Last modified data/time: "+data.lastModifiedTime());
		 System.out.println("Last accessed date/time: "+data.lastAccessTime());
		 System.out.println("Unique file identifier within the fs (if available ): "+data.fileKey());//null if it is not supported by the file system.
		 
		 System.out.println("\nAccessing view info - method 2");
		 System.out.println(" Modifying Attributes with BasicFileAttributeView");
		 //Method 2 - Files.getFileAttributeView(Path,Class<V>), which returns the underlying attribute view, and provides a direct resource for 
		 //update the file system-dependent attributes..
		 BasicFileAttributeView view = Files.getFileAttributeView(ptthh, BasicFileAttributeView.class);
		 //NB: We can also use the view object to read the associated file system attributes
		 BasicFileAttributes myData = view.readAttributes();
		 FileTime lastModifiedTime = FileTime.fromMillis(data.lastModifiedTime().toMillis()+10_000);
		 //void setTimes(FileTime lastModifiedTime,FileTime lastAccessTime, FileTime createTime) throws IOException;
		 view.setTimes(lastModifiedTime, null, null);//Only one UPDATE method with three arguments
		 
		
	}
	
	public static void printPathInformation(Path path) {
		System.out.println(path);
		System.out.println("Filename is: "+path.getFileName());//The farther element from the root 
		System.out.println("Root is: "    +path.getRoot());//returns null if the Path object is relative. Otherwise it returns "/" on Mac
		Path currentParent = path;
		while((currentParent = currentParent.getParent()) != null)// returns a Path instance representing the closer element (all the path) from the leaf (except the leaf)  or null if there is no such parent.
			System.out.println(" Current parent is: "+currentParent);
	}

}
