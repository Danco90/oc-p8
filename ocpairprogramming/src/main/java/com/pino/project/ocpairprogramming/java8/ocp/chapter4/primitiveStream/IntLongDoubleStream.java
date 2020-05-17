package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitiveStream;

import java.util.DoubleSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 
____________________________________________________________________________________________________________________________
Class		    create Stream	create DoubleStream		create IntStream	create LongStream
----------------------------------------------------------------------------------------------------------------------------
Stream			map				mapToDouble				mapToInt			mapToLong
DoubleStream	mapToObj		map						mapToInt (NO)		mapToLong (NO)
IntStream		mapToObj		mapToDouble				map					mapToLong
LongStream		mapToObj		mapToDouble				mapToInt (NO)		map
----------------------------------------------------------------------------------------------------------------------------

long longValue = 9L;
int intValue = longValue; //NO
double doubleValue = 9.0;
doubleValue = longValue;
longValue = doubleValue; //NO
 * 
 */

public class IntLongDoubleStream {
	public static void main(String[]args){
		Stream<String>objStream = Stream.of("penguin", "fish");
		IntStream intStream = objStream.mapToInt(s->s.length()); //7 5
		intStream.forEach(System.out::print);
		
		IntStream intstream2 = IntStream.of(6, 10);
		LongStream longs = intstream2.mapToLong(i -> i);
		System.out.println(longs.average().getAsDouble()); //getAverage() doesn't compile because is of LongSummaryStatistics class.
		
		/*
		IntSummaryStatistics intSummary = intStream.summaryStatistics();
		intSummary.getAverage();
		
		LongSummaryStatistics longSummary = longs.summaryStatistics();
		longSummary.getAverage();
		*/
		
		DoubleSummaryStatistics doubleSummary;
		
		LongStream streamLong = LongStream.of(9);
//		streamLong.mapToInt(p ->p).forEach(System.out::print); //DOES NOT COMPILE
		streamLong.mapToDouble(p->p).forEach(System.out::println);
		
		DoubleStream streamDouble = DoubleStream.of(9.0);
		//streamDouble.mapToInt(p->p).forEach(System.out::println);
//		streamDouble.mapToLong(p->p).forEach(System.out::println);//DOES NOT COMPILE
		
		IntStream streamInt = IntStream.of(9);
		streamInt.map(p->p).forEach(System.out::println);
		
		long longValue = 9L;
//		int intValue = longValue; //NO //DOES NOT COMPILE
		double doubleValue = 9.0;
		doubleValue = longValue;
//		longValue = doubleValue; //NO //DOES NOT COMPILE
		
	}
}
