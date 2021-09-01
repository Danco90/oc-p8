package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitiveStream;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 
____________________________________________________________________________________________________________________________
Class		    create Stream		create DoubleStream		create IntStream	       		create LongStream
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
		//Creating primitive stream
		//Approach A Creating finite stream with standard static factory methods
		//A1. Empty
		IntStream empty = IntStream.empty();
		//A2. Single element
		DoubleStream oneValue = DoubleStream.of(3.14);
		//A3. Varargs
		DoubleStream varargs = DoubleStream.of(1.0, 1.1, 1.2);
		LongStream longStream1 = LongStream.of(5L,10L);
		LongStream longStream2 = LongStream.of(5,10);//int always fits into a long!
		
		//Approach B - Creating infinite stream
		//B.1 - static DoubleStream generate(DoubleSupplier s) OR the same of IntStream and LongStream
		DoubleStream random = DoubleStream.generate(Math::random);
		random.limit(3).forEach(System.out::println); 
		//B.2 - static DoubleStream iterate(final double seed, final DoubleUnaryOperator f)
		DoubleStream fractions = DoubleStream.iterate(.5, d -> d/2);
		System.out.println(); 
		fractions.limit(3).forEach(System.out::println);
		IntStream count = IntStream.iterate(1, n -> n+1);
		System.out.println();
		count.limit(5).forEach(System.out::println);
		System.out.println();
		
		//Approach C - Creating finite stream as a range of items.
		//C.1 range()
		IntStream range = IntStream.range(1, 6);
		range.forEach(System.out::println);
		System.out.println();
		//C.2 rangeClosed()
		IntStream rangeClosed = IntStream.rangeClosed(1, 5);//end included
		rangeClosed.forEach(System.out::println);
		
		//Approach D - Create a primitive stream from another stream type
		//D.1.1  <R> Stream<R> map(Function<? super T, ? extends R> mapper);
		Stream<String> objStreamIn = Stream.of("penguin", "fish");
		Stream<Character> objStreamOut = objStreamIn.map(s -> s.charAt(0));
		objStreamOut.forEach(System.out::print); //pf
		//D.1.2  mapToDouble example 1 - DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
		Stream<String> objStream = Stream.of("penguin", "fish");
		DoubleStream doubleStream = objStream.mapToDouble(s->s.length());//OK as the returned int is easily casted to a double
		doubleStream.forEach(System.out::print); //7.04.0
		System.out.println();
		
		//D.1.3 mapToInt example 1 - IntStream mapToInt(ToIntFunction<? super T> mapper);
		objStream = Stream.of("penguin", "fish");
		IntStream intStream = objStream.mapToInt(s->s.length()); //7 4
		intStream.forEach(System.out::print);//74
		System.out.println();
		
		//D.1.4 mapToLong example 1 - LongStream mapToLong(ToLongFunction<? super T> mapper);
		objStream = Stream.of("penguin", "fish");
		LongStream longStream = objStream.mapToLong(s->s.length()); //7 4
		longStream.forEach(System.out::print);//74
		System.out.println();
		
		//D.2.1 mapToObj example 1 - <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper);
		System.out.println("D.2.1 mapToObj example 1 - - <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) ");
		DoubleStream dStream = DoubleStream.of(2.0, 4.0);
		Stream<String> stream = dStream.mapToObj(d->""+d); //7 4
		stream.forEach(System.out::print);//2.04.0
		System.out.println();
		
		//D.2.2  DoubleStream map(DoubleUnaryOperator mapper)
		dStream = DoubleStream.of(2.0, 4.0);
		DoubleStream dStreamOut = dStream.map(d -> d*2);//UnaryOperator
		dStreamOut.forEach(System.out::print); //4.08.0
		
		//D.2.3 **TRICKY** IntStream mapToInt(DoubleToIntFunction mapper)
		doubleStream = DoubleStream.of(1.0, 2.0);
//		intStream = doubleStream.mapToInt(s->IntStream.of(s)); //NOT POSSIBLE
//		intStream = doubleStream.mapToInt(s->s); //NOT POSSIBLE EITHER since it cannot convert double to int
//		intStream = doubleStream.mapToInt(s->1);//AWFUL WORKAROUND but it works. Basically, any double item cannot be transform into an int
		intStream = doubleStream.mapToInt(s->(int)s);//VALID with explicit CAST
		intStream.forEach(System.out::print);
		System.out.println();
		
		//D.2.4 (TRICKY) LongStream mapToLong(DoubleToLongFunction mapper)
		// mapTolong example 3 **TRICKY** LongStream mapToLong(DoubleToLongFunction mapper);
		doubleStream= DoubleStream.of(1.0, 2.0);
//		longStream = doubleStream.mapToLong(s->s); //NOT POSSIBLE from double to long
		longStream = doubleStream.mapToLong(s->(long)s);//VALID with explicit CAST
		longStream.forEach(System.out::print);
		System.out.println();
		
		//D.3.1 mapToObj example 2 - <U> Stream<U> mapToObj(IntFunction<? extends U> mapper);
		System.out.println("\nD.3.1 mapToObj example 2 - <U> Stream<U> mapToObj(IntFunction<? extends U> mapper);) ");
		IntStream iStream = IntStream.of(2, 4);
		Stream<String> streamS = iStream.mapToObj(i->""+i); //7 4
		streamS.forEach(System.out::print);//24
		System.out.println();
		
		//D.3.2 mapToDouble example 2  DoubleStream mapToDouble(IntToDoubleFunction mapper);
		System.out.println("D.3.2 Example 2 - DoubleStream mapToDouble(IntToDoubleFunction mapper);");
		intStream= IntStream.of(1, 2);
		doubleStream = intStream.mapToDouble(s->s);
		doubleStream.forEach(System.out::print);//1.02.0
		System.out.println();
		
		//D.3.3 map example 3 - IntStream map(IntUnaryOperator mapper)
		System.out.println("\nD.3.3 map example 3 - IntStream map(IntUnaryOperator mapper)");
		iStream = IntStream.of(10, 20);
		IntStream inStreamOut = iStream.map(i -> i/2);//UnaryOperator
		inStreamOut.forEach(System.out::print); //510
		
		//D.3.4 mapToLong example 3 - LongStream mapToLong(IntToLongFunction mapper)
		System.out.println("\nD.3.4 mapToLong example 3 - LongStream mapToLong(IntToLongFunction mapper)");
		intStream = IntStream.of(3, 7);
		longStream = intStream.mapToLong(s->s); 
		longStream.forEach(System.out::print);
		System.out.println();
		// mapToLong example 3b - LongStream mapToLong(IntToLongFunction mapper);
		IntStream intstream2 = IntStream.of(6, 10);
		LongStream longs = intstream2.mapToLong(i -> i);
		System.out.println(longs.average().getAsDouble()); //getAverage() doesn't compile because is of LongSummaryStatistics class.
				
		//D.4.1 mapToObj example 3 - <U> Stream<U> mapToObj(LongFunction<? extends U> mapper);
		System.out.println("\nD.4.1 mapToObj example 3 - <U> Stream<U> mapToObj(LongFunction<? extends U> mapper);");
		LongStream lStream = LongStream.of(2L, 4L);
		Stream<String> streamSs = lStream.mapToObj(l->""+l); //7 4
		streamSs.forEach(System.out::print);//24
		System.out.println();
			
		//D.4.2  mapToDouble example 2  DoubleStream mapToDouble(LongToIntFunction mapper);
		System.out.println("\nD.4.2  mapToDouble example 2  DoubleStream mapToDouble(LongToIntFunction mapper)");
		longStream = LongStream.of(1L, 2L);
		doubleStream = longStream.mapToDouble(l->l);
		doubleStream.forEach(System.out::print);//1.02.0
		System.out.println();
		
		//D.4.3 mapToInt example 3 **TRICKY** IntStream mapToInt(LongToIntFunction mapper);
		System.out.println("\nD.4.3 mapToInt example 3 **TRICKY** IntStream mapToInt(LongToIntFunction mapper);");
		longStream= LongStream.of(1L, 2L);
//		intStream = longStream.mapToInt(s->IntStream.of(s)); //NOT POSSIBLE
		intStream = longStream.mapToInt(s->(int)s); //VALID with CAST
		intStream.forEach(System.out::print);
		System.out.println();
		
		//D.4.4 map example 4 - LongStream map(LongUnaryOperator mapper)
		System.out.println("\nD.4.4 map example 4 - LongStream map(LongUnaryOperator mapper)");
		lStream = LongStream.of(10L, 20L);
		LongStream lonStreamOut = lStream.map(i -> i/2);//LongUnaryOperator
		lonStreamOut.forEach(System.out::print); //510
	
		//Approach E - <R> Stream<R> flatMapTo<Target_Primitive_Stream>(Function<? super T, ? extends <Target_Primitive_Stream >> mapper);
		System.out.println("\nApproach E - <R> Stream<R> flatMapTo<Target_Primitive_Stream>(Function<? super T, ? extends <Target_Primitive_Stream >> mapper);");
		//E.1.2  flatMap example 1 - DoubleStream flatMap(Function<? super T, ? extends DoubleStream> mapper);
		List<String> l1 = Arrays.asList("penguin", "fish");
		List<String> l2 = Arrays.asList("peno", "fino");
//		List<String> l3 = Arrays.asList();//WATCH OUT: the empty list might throw ArrayIndexOutOfBoundException if it were accessed 
		Stream<List<String>> objListStream = Stream.of(l1, l2/*, l3*/);
		Stream<List<String>> objListStream2 = objListStream.flatMap(l->Stream.of(l.subList(0, 1))); //sublist of an empty list throws array OutOfBoundException
		objListStream2.forEach(System.out::print);//[penguin][peno] sublist list with just the first elements
		System.out.println();
		
		//E.1.2  flatMapToDouble example 1 - DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
		objStream = Stream.of("penguin", "fish");
		doubleStream = objStream.flatMapToDouble(s->DoubleStream.of(s.length())); //7 4
		System.out.print("objStream.flatMapToDouble ");
		doubleStream.forEach(System.out::print);
		System.out.println();
		
		//E.1.3 flatMapToInt example 2 - IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
		objStream = Stream.of("penguin", "fish");
		intStream = objStream.flatMapToInt(s->IntStream.of(s.length())); //7 4
		System.out.println("objStream.flatMapToInt ");
		intStream.forEach(System.out::print);
		System.out.println();
		
		//E.1.4 flatMapToLong example 3 - LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
		objStream = Stream.of("penguin", "fish");
		longStream = objStream.flatMapToLong(s->LongStream.of(s.length())); //7 4
		System.out.print("objStream.flatMapToLong ");
		longStream.forEach(System.out::print);
		System.out.println();
		
		/*IntSummaryStatistics intSummary0 = intStream.summaryStatistics();
		intSummary0.getAverage();
		
		LongSummaryStatistics longSummary0 = longs.summaryStatistics();
		longSummary0.getAverage();
		*/
		
		DoubleSummaryStatistics doubleSummary;
		
		//	DoubleStream mapToDouble(LongToDoubleFunction mapper)
		LongStream streamLong = LongStream.of(9L);
		//LongStream streamLong = LongStream.of(9);//compiles as well without L
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
		
		//Summarizing Statistics
		IntStream ints = IntStream.of(1,2,3,4,5);
		System.out.println("The max is :"+max(ints));
		ints = IntStream.of(1,2,3,4,5);
		int res = range(ints);
		System.out.println("The range is :"+res);

		
	}
	private static int range(IntStream ints) {
//		OptionalInt optMax = ints.max();
//		OptionalInt optMin = ints.min();//it will throw a exception because max() already uses up the stream
		IntSummaryStatistics stats = ints.summaryStatistics();//Therefore we do extract both properties all in ones 
		if(stats.getCount() == 0) throw new RuntimeException();
		System.out.println("Min :"+stats.getMin()+", max :"+stats.getMax()
		                  +", average :"+stats.getAverage()+", count :"+stats.getCount());
		return stats.getMax() - stats.getMin();//Minimum subtracted from the Maximum
	}
	
	private static int max(IntStream ints) {
		OptionalInt opt = ints.max();
		return opt.orElseThrow(RuntimeException::new);//If contains a value, we return it. Otherwise, we throw an exception
	}
}
