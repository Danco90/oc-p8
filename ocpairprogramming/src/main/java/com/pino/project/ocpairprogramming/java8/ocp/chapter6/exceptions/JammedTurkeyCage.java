package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

public class JammedTurkeyCage implements AutoCloseable {

	public void close() throws IllegalStateException {
		throw new IllegalStateException("Cage door does not close"); 
	}
	
	public static void main(String[] args) {
		try (JammedTurkeyCage t = new JammedTurkeyCage()) {
			System.out.println("put turkeys in"); 
		} catch (IllegalStateException e) {
			System.out.println("caught: " + e.getMessage()); 
		}
		
		//Suppressed Exception: 
		//Example 1 of try block also throwing an exception
		System.out.println("\nSuppressed Exceptions");
		try (JammedTurkeyCage t = new JammedTurkeyCage()) {
			throw new IllegalStateException("turkeys ran off"); 
		} catch (IllegalStateException e) {
			System.out.println("caught: " + e.getMessage());
			for(Throwable t: e.getSuppressed())
				System.out.println(t.getMessage());
		}
		//Example 2 of primary exception not matching the catch clause.  
//		System.out.println();
//		try (JammedTurkeyCage t = new JammedTurkeyCage()) {
//			throw new RuntimeException("turkeys ran off"); 
//		} catch (IllegalStateException e) {//
//			System.out.println("caught: " + e.getMessage());
////			for(Throwable t: e.getSuppressed())
////				System.out.println(t.getMessage());
//		}//
		
		//Example 3 of two exception thrown while closing resources.
		System.out.println();
		try (JammedTurkeyCage t1 = new JammedTurkeyCage();
				JammedTurkeyCage t2 = new JammedTurkeyCage()) {
			System.out.println("turkeys entered cages"); 
		} catch (IllegalStateException e) {//
			System.out.println("caught: " + e.getMessage());
			for(Throwable t: e.getSuppressed())
				System.out.println(t.getMessage());
		}//
		
		//Example 4 - Bad programming practice - Exceptions thrown in both try and finally
		System.out.println("Example 4 - Exceptions thrown in both try and finally");
		try (JammedTurkeyCage t = new JammedTurkeyCage()) {
			throw new IllegalStateException("turkeys ran off"); //once it throws the exception java tries to close the resource and add a suppressed exception to it.
		} 
		finally { //it runs after all this. 
			throw new RuntimeException("and we couldn't find them");//since it throws an exception, the previous exception is lost. 
		}
		
		//Example 5 - Bad programming practice - Exceptions thrown in both try, catch and finally
//		System.out.println("Example 5 - Exceptions thrown in both try and finally");
//		try (JammedTurkeyCage t = new JammedTurkeyCage()) {
//			throw new IllegalStateException("turkeys ran off"); //once it throws the exception java tries to close the resource and add a suppressed exception to it.
//		} catch (IllegalStateException e) {
//			throw new RuntimeException(e);
//		}
//		finally { //it runs after all this. 
//			throw new RuntimeException("and we couldn't find them");//since it throws an exception, the previous exceptions are lost. 
//		}
	}

}
