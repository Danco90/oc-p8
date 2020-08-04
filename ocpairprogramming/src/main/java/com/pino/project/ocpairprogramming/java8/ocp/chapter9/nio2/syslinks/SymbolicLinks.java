package com.pino.project.ocpairprogramming.java8.ocp.chapter9.nio2.syslinks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class SymbolicLinks {
	
	public void createSymbolicLink(Path link, Path target) throws IOException {
        if (Files.exists(link)) {
            Files.delete(link);
        }
        Files.createSymbolicLink(link, target);
    }
	public void createHardLink(Path link, Path target) throws IOException {
	        if (Files.exists(link)) {
	            Files.delete(link);
	        }
	        Files.createLink(link, target);
	}
	
	public Path createTextFile(String file) throws IOException {		
	    byte[] content = IntStream.range(0, 10000)
	      .mapToObj(i -> i + System.lineSeparator())
	      .reduce("", String::concat)
	      .getBytes(StandardCharsets.UTF_8);
	    Path filePath = Paths.get(file);
	    Files.write(filePath, content, CREATE, TRUNCATE_EXISTING);
	    return filePath;		
	}
	
	public Path createTextFile() throws IOException {		
	    byte[] content = IntStream.range(0, 10000)
	      .mapToObj(i -> i + System.lineSeparator())
	      .reduce("", String::concat)
	      .getBytes(StandardCharsets.UTF_8);
	    Path filePath = Paths.get("", "target_link.txt");
	    Files.write(filePath, content, CREATE, TRUNCATE_EXISTING);
	    return filePath;		
	}
	
	public void createSymbolicLink() throws IOException {
	    Path target = createTextFile();
	    Path link = Paths.get(".","symbolic_link.txt");
	    if (Files.exists(link)) {
	        Files.delete(link);
	    }
	    Files.createSymbolicLink(link, target);
	}

	public void createHardLink() throws IOException {
	    Path target = createTextFile();
	    Path link = Paths.get(".", "hard_link.txt");
	    if (Files.exists(link)){
	        Files.delete(link);
	    }
	    Files.createLink(link, target);
	}
	public void printLinkFiles(Path path) throws IOException {
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
	        for (Path file : stream) {
	            if (Files.isDirectory(file)) {
	                printLinkFiles(file);
	            } else if (Files.isSymbolicLink(file)) {
	                System.out.format("File link '%s' with target '%s' %n", 
	                  file, Files.readSymbolicLink(file));
	            }
	        }
	    }
	}
	
	public static void main(String[] args) throws IOException {
		SymbolicLinks m = new SymbolicLinks();
		//m.createTextFile("symlinks/path/to/snake");//Create only file and throws exception if mets any missing parent directories
		m.createSymbolicLink(Paths.get("symlinks/path/to/cobra1"), Paths.get("snake"/**/));
		//m.printLinkFiles(Paths.get("."));
		m.printLinkFiles(Paths.get("symlinks/path/to"));
		//**NB : the 2nd parameter is to be relative to the 1rst parameter current directory. 
		// Therefore an existing file named 'snake'
		// is expected to be found inside directory 'symlinks/path/to'
		
		m.createTextFile("symlinks/path/to/user/monkey");
		m.createTextFile("symlinks/path/to/leaves/giraffe.exe");
		m.createTextFile("symlinks/path/to/flamingo/tail.data");
		m.createTextFile("symlinks/path/to/cardinal/tail.data");

	}

}
