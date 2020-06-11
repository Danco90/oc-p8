package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitiveStream;

import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 
____________________________________________________________________________________________________________________________
Class		    create Stream		create DoubleStream		create IntStream	create		create LongStream
----------------------------------------------------------------------------------------------------------------------------
Stream			map(Function<>)		mapToDouble(Function<>)	mapToInt(Function<>)			mapToLong(Function<>)
				flatMap				flatMapToDouble			flatMapToInt					flatMapToLong

DoubleStream		mapToObj				map						mapToInt (NO)				mapToLong (NO)
IntStream		mapToObj				mapToDouble				map							mapToLong
LongStream		mapToObj				mapToDouble				mapToInt (NO)				map
----------------------------------------------------------------------------------------------------------------------------
NO means the conversion is not possible between that couple of primitive streams as it is forbidden with normal primitive types.
A double (floating point) cannot turn into an int (or long). Whereas it is possible transforming an int (or long) to a double by adding .0
And neither can a long primitive be cut into a smaller.
 
long longValue = 9L;
int intValue = longValue; //NO
double doubleValue = 9.0;
doubleValue = longValue;
longValue = doubleValue; //NO
 * 
 */

public class IntLongDoubleStream {
	public static void main(String[]args){
		
		//<R> Stream<R> map(Function<? super T, ? extends R> mapper);
		
		// mapToDouble example 1 - DoubleStream mapToDouble(ToIntFunction<? super T> mapper);
		Stream<String> objStream = Stream.of("penguin", "fish");
		DoubleStream doubleStream = objStream.mapToDouble(s->s.length());
		doubleStream.forEach(System.out::print); //7 4
		System.out.println();
	
		//  mapToDouble example 2 - DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
		objStream = Stream.of("penguin", "fish");
		doubleStream = objStream.flatMapToDouble(s->DoubleStream.of(s.length())); //7 4
		System.out.print("objStream.flatMapToDouble ");
		doubleStream.forEach(System.out::print);
		System.out.println();
		
		//  mapToDouble example 3  DoubleStream mapToDouble(IntToDoubleFunction mapper);
		IntStream intStream= IntStream.of(1, 2);
		doubleStream = intStream.mapToDouble(s->s);
		doubleStream.forEach(System.out::print);//1 2
		System.out.println();
		
		//  mapToDouble example 4  DoubleStream mapToDouble(LongToIntFunction mapper);
		LongStream longStream = LongStream.of(1L, 2L);
		doubleStream = longStream.mapToDouble(l->l);
		doubleStream.forEach(System.out::print);//1 2
		System.out.println();
		
		//	mapToInt example 1 - IntStream mapToInt(ToIntFunction<? super T> mapper);
		objStream = Stream.of("penguin", "fish");
		intStream = objStream.mapToInt(s->s.length()); //7 4
		intStream.forEach(System.out::print);
		System.out.println();
	
		// mapToInt example 2 - IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
		objStream = Stream.of("penguin", "fish");
		intStream = objStream.flatMapToInt(s->IntStream.of(s.length())); //7 4
		System.out.print("objStream.flatMapToInt ");
		intStream.forEach(System.out::print);
		System.out.println();
		
		// mapToInt example 3 **TRICKY** IntStream mapToInt(DoubleToIntFunction mapper);
		doubleStream = DoubleStream.of(1.0, 2.0);
//		intStream = doubleStream.mapToInt(s->IntStream.of(s)); //NOT POSSIBLE
//		intStream.forEach(System.out::print);
//		System.out.println();
		
		// mapToInt example 4 **TRICKY** IntStream mapToInt(LongToIntFunction mapper);
		longStream= LongStream.of(1L, 2L);
//		intStream = longStream.mapToInt(s->IntStream.of(s)); //NOT POSSIBLE
//		intStream.forEach(System.out::print);
//		System.out.println();
		
		// mapToLong example 1.1 - LongStream mapToLong(ToLongtFunction<? super T> mapper);
		objStream = Stream.of("penguin", "fish");
		longStream = objStream.mapToLong(s->s.length()); //OK int fits into a long
		longStream.forEach(System.out::print);//7 4
		System.out.println();
		
		// mapToLong example 1.2 - LongStream mapToLong(IntToLongFunction mapper);
		IntStream intstream2 = IntStream.of(6, 10);
		LongStream longs = intstream2.mapToLong(i -> i);
		System.out.println(longs.average().getAsDouble()); //getAverage() doesn't compile because is of LongSummaryStatistics class.
		
		// mapToLong example 2 - LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
		objStream = Stream.of("penguin", "fish");
		longStream = objStream.flatMapToLong(s->LongStream.of(s.length())); //7 4
		System.out.print("objStream.flatMapToLong ");
		longStream.forEach(System.out::print);
		System.out.println();
		
		// mapTolong example 3 **TRICKY** LongStream mapToLong(DoubleToLongFunction mapper);
		doubleStream= DoubleStream.of(1.0, 2.0);
//		longStream = doubleStream.mapToLong(s->s); //NOT POSSIBLE from double to long
//		longStream.forEach(System.out::print);
//		System.out.println();
		
		// mapTolong example 4 LongStream mapToLong(IntToLongFunction mapper);
		intStream= IntStream.of(1, 2);
		longStream = intStream.mapToLong(i->i); //OK
//		longStream.forEach(System.out::print);
//		System.out.println();
		
		/*
		IntSummaryStatistics intSummary = intStream.summaryStatistics();
		intSummary.getAverage();
		
		LongSummaryStatistics longSummary = longs.summaryStatistics();
		longSummary.getAverage();
		*/
		
		DoubleSummaryStatistics doubleSummary;
		
		//	DoubleStream mapToDouble(LongToDoubleFunction mapper)
		LongStream streamLong = LongStream.of(9);
//		streamLong.mapToInt(p ->p).forEach(System.out::print); //DOES NOT COMPILE
		streamLong.mapToDouble(p->p).forEach(System.out::println);
		
		// TRICKY** IntStream mapToInt(DoubleToIntFunction mapper) DOES NOT WORK
		DoubleStream streamDouble = DoubleStream.of(9.0);
//		streamDouble.mapToInt(p->p).forEach(System.out::println);//DOES NOT COMPILE
		//Indeed ..
		double doubleValue = 9.0;
		long longValue = 9L;
//		int intValue = doubleValue; //ABSOLUTELY NOPE, IT DOES NOT COMPILE
//		int intValue = longValue; //NO //DOES NOT COMPILE
		
		// TRICKY** LongStream mapToLong(DoubleToLongFunction mapper) DOES NOT WORK
//		streamDouble.mapToLong(p->p).forEach(System.out::println);//DOES NOT COMPILE
		//Indeed
//		longValue = doubleValue; //NO //DOES NOT COMPILE
		doubleValue = longValue;//YES	
		
		//	IntStream map(IntUnaryOperator mapper)
		IntStream streamInt = IntStream.of(9);
		streamInt.map(p->p).forEach(System.out::println);
		
//		longValue = doubleValue; //NO //DOES NOT COMPILE
		
	}
}
