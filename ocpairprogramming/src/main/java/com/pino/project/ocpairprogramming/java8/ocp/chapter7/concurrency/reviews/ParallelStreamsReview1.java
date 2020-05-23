package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * IntStream iterate(final int seed, final IntUnaryOperator f)
 * IntStream limit(long maxSize)
 * void forEach(IntConsumer action)
 * Stream<T> sorted() : returns a stream of element sorted according to the natural order
 * Optional<T> findAny() : find whatever value (inside the optional), which one is NOT guaranteed for either serial(?) or parallel stream 
 * Optional<T> findFirst() : find always the first element of the serial stream, and a non deterministic element for a parallel stream 
 * @author matteodaniele
 *
 */
public class ParallelStreamsReview1 {

	public static void main(String[] args) {
		AtomicLong value1 = new AtomicLong(0);
		final long[] value2 = {0};
		
		//Nondeterministic Output - Example 1
		IntStream.iterate(1, i -> 1).limit(100).parallel()
		  .forEach(i -> value1.incrementAndGet());//Atomic
		IntStream.iterate(1, i -> 1).limit(100).parallel()
		  .forEach(i -> ++value2[0]);//The increment operator is NOT atomic. Therefore  the threads can override  each other's work.
		System.out.println(value1+" "//value1 outputs always 100
						  +value2[0]);//value2[0] output cannot be determined ahead of time!
		
		
		//Nondeterministic Output - Example 2
		Integer i1 = Arrays.asList(1,2,3,4,5).stream().findAny().get();
		synchronized(i1) { // y1 - Synch. on the first output it DOES NOT actually impact the result of the code.
			Integer i2 = Arrays.asList(6,7,8,9,10)
				.parallelStream()
				.sorted() // y2 - sorting on parallelStream it DOES NOT mean findAny() will return the first record
				.findAny().get(); // y3 - in a parallelStream the findAny will return the FIRST value from the FIRST thread that retrieves the record.
			System.out.println(i1+" "//output NOT guaranteed for either serial(?) 
							  +i2);//or parallel stream.
		}
			
		//Deterministic Output - Example 1
		Integer i3 = Arrays.asList(1,2,3,4,5).stream().findFirst().get();
		synchronized(i3) { // y1 - Synch. on the first output it DOES NOT actually impact the result of the code.
			Integer i4 = Arrays.asList(6,7,8,9,10)
				.parallelStream()
				.sorted() // y2 - sorting on parallelStream makes findFirst() return the first record
				.findFirst().get(); // y3 - in a parallelStream the findAny will return the a non deterministic value, unless it were sorted() before 
			System.out.println(i3+" "//output  guaranteed for both serial 
							  +i4);//and parallel stream.
		}
	}

}
