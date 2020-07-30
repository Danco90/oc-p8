package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Question 14 of the Chapter 7 assessment in the book.
 * 
 * boolean offer(E e);
 * boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException;
 * boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException;
 * E pollFirst(long timeout, TimeUnit unit) throws InterruptedException;
 * E pollLast(long timeout, TimeUnit unit) throws InterruptedException;
 * @author matteodaniele
 *
 */
public class BlockingDequeReview {
//	public void addAndPrintItems(BlockingDeque<Integer> deque) {//Compilation ERROR as both methods might throw Checked InterruptedException
	public void addAndPrintItems(BlockingDeque<Integer> deque) throws InterruptedException {
		 deque.offer(103);
		 deque.offerFirst(20, 1, TimeUnit.SECONDS);
		 deque.offerLast(85, 7, TimeUnit.HOURS);
		 System.out.print(deque.pollFirst(200, TimeUnit.NANOSECONDS));//20
		 System.out.print(" "+deque.pollLast(1, TimeUnit.MINUTES));//85
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingDequeReview main = new BlockingDequeReview();
		// create an Unbounded BlockingQueue 
		BlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
		main.addAndPrintItems(deque);//20 85 if they are not blocked.
	}

}
