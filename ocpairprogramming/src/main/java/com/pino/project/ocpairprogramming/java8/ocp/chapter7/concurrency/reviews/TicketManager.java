package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

/**
 * Question 19 of the Chapter 7 assessment in the book.
 * Tricky example of a not thread-safe class : Race condition
 * @author matteodaniele
 *
 */
public class TicketManager {
	private TicketManager() { super(); }//Nobody can instantiate the class except the getInstance() ONLY the first time is called.
	private static TicketManager instance;// the lock acquires on CLASS object
	public static synchronized TicketManager getInstance() { // k1  - JUST the access to instance is synchronized*, but any methods Called on it MUST BE Synchronized to guarantee thread-safety
		if (instance == null) instance = new TicketManager(); // k2 - Instance object is SINGLETON (created only once thanks to the static method)
		return instance;
	}
	private int tickets;
	public int getTicketCount() { return tickets; }//** CONCURRENCY ISSUE
	public void makeTicketsAvailable(int value) { tickets += value; } // k3 - *** RACE CONDITION : Not synchronized 
	public void sellTickets(int value) {
		synchronized (this) { // k4 // this means the lock acquires on the only instance new TicketManager() object
		tickets -= value;
		}
	}
	
	//(*)  TRICKY  : If multiple thread access to instance, then there might be one (the quickest) which call makeTicketsAvailable concurrently causing overlapping (RACE CONDITION) of results. 
	//(**) TRICKY  : Since static, by default it is initialized with TicketManager.class object 
	//(**) TRICKY  : when accessed by multiple threads, it might happen that an invalid number of tickets is reached.

}
