package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitiveStream;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


/**
 * Table 4 . 8 	Optional types for primitives
 * ------------------------+-------------------+---------------+---------------+
 * Getting as a primitive	OptionalDouble 		OptionalInt 		OptionalLong
 * 
 * orElseGet() 				getAsDouble() 		getAsInt() 		getAsLong()
 * parameter type			DoubleSupplier 		IntSupplier 		LongSupplier
 * 
 * Return type of max() 		OptionalDouble 		OptionalInt 		OptionalLong
 * 
 * Return type of sum() 	*	double 				int 				long
 * Return type of average()*	OptionalDouble 		OptionalDouble 	OptionalDouble
 * ------------------------+-------------------+---------------+---------------+
 * (*) new methods just for primitive streams 
 * NB: avg() method DOES NOT EXIST. The book is wrong. 
 * @author matteodaniele
 *
 */
public class OptionalWithPrimitiveStream {
	
	private static int max(IntStream ints) {
		OptionalInt optional = ints.max();
		return optional.orElseThrow(RuntimeException::new);
	}
	
	private static int range(IntStream ints) {
		IntSummaryStatistics stats = ints.summaryStatistics();
		if(stats.getCount() == 0) throw new RuntimeException();
		return stats.getMax()-stats.getMin();
	}
	
	private static long range(LongStream longs) {
		LongSummaryStatistics stats = longs.summaryStatistics();
		if(stats.getCount() == 0) throw new RuntimeException();
		return stats.getMax()-stats.getMin();
	}
	
	private static double range(DoubleStream doubles) {
		DoubleSummaryStatistics stats = doubles.summaryStatistics();
		if(stats.getCount() == 0) throw new RuntimeException();
		return stats.getMax()-stats.getMin();
	}

	public static void main(String[] args) {
		
		//Example 1 - OptionalDouble not empty
		IntStream stream = IntStream.rangeClosed(1,10);
		OptionalDouble optional = stream.average();
		//void ifPresent(DoubleConsumer consumer)
		optional.ifPresent(System.out::println);//if present, it will print the primitive double inside the OptionalDouble
		System.out.println(optional.getAsDouble());//it will print the double contained inside the OptionalDouble if exists. 
		//double orElseGet(DoubleSupplier other)
		System.out.println(optional.orElseGet(() -> Double.NaN));//if present it will print the content of the Optional. If no content, it will print a constant holding a Not-a-Number
		System.out.println();
		
		//Example 2 - Empty OptionalDouble
		stream = IntStream.of();
		optional = stream.average();
		//void ifPresent(DoubleConsumer consumer)
		optional.ifPresent(System.out::println);//if present, it will print the primitive double inside the OptionalDouble
//		System.out.println(optional.getAsDouble());//WATCH OUT : it will throw an unchecked exception if no content inside the OptionalDouble
		
		//double orElseGet(DoubleSupplier other)
		System.out.println(optional.orElseGet(() -> Double.NaN));//if present it will print the content of the Optional. If no content, it will print a constant holding a Not-a-Number
		
		
		//The primitive stream implementations also add two new methods that return an optional.
		//	long sum()
		LongStream longs = LongStream.of(5, 10);
		long sum = longs.sum();
		System.out.println(sum);
		
//		OptionalDouble average()   *
		longs = LongStream.of(5, 10);
//		OptionalDouble average = longs.avg();//DOES NOT COMPILE because it doesn't exist
		OptionalDouble average = longs.average();
		System.out.println(average);
		//(*) ERROR : the method is called average() instead of avg(). As usual the book is wrong.
		
		
		//Summarizing Statistics
		
		
	}

}
