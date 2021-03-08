package com.pino.project.ocpairprogramming.java8.ocp.chapter6.exceptions;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;

public class RethrowingExceptions {
	
	public void parseData() throws SQLException, DateTimeParseException { 
		throw new SQLException();
//		throw new DateTimeParseException();
	}
	
	/**
	 * It requires double changes once we add or remove exception
	 * @throws SQLException
	 * @throws DateTimeParseException
	 */
	public void multiCatch() throws SQLException, DateTimeParseException { 
		try {
			parseData();
		} catch (SQLException | DateTimeParseException e) {
			System.err.println(e);// java.sql.SQLException 
			throw e; 
		} 
	}
	
	/**
	 * It is better for refactoring. Only signature needs to be changed. The Exception in the catch is automatically restricted to the ones in the signatures
	 * @throws SQLException
	 * @throws DateTimeParseException
	 */
	public void rethrowing() throws SQLException, DateTimeParseException {
		try {
			parseData();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}

	public static void main(String[] args) {
		RethrowingExceptions r = new RethrowingExceptions();
		try {
//			r.multiCatch();
			r.rethrowing();
		} catch (DateTimeParseException e) {
//			e.printStackTrace(); //Swallowed
		} catch (SQLException e) {
//			e.printStackTrace(); //Swallowed
		}

	}

}
