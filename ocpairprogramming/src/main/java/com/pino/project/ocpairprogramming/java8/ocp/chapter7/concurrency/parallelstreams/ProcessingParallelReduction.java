package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.parallelstreams;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessingParallelReduction {

	public static void main(String[] args) {
		
		//Performing Order-Based Tasks
		System.out.println("Arrays.asList(1,2,3,4,5,6)");
		System.out.println(" .stream().findAny().get() : " + Arrays.asList(1,2,3,4,5,6).stream().findAny().get());
		System.out.println("whereas applying parallel stream"); 
		System.out.println(" .parallelStream().findAny().get() : " + Arrays.asList(1,2,3,4,5,6).parallelStream().findAny().get());

		//Stream OPERATIONS that are BASED ON ORDER
		System.out.println("\n. stream().skip(5).limit(2).findFirst().get() : " + 
							Arrays.asList(1,2,3,4,5,6).stream().skip(5).limit(2).findFirst().get());
		//NB: Any stream operation that is based on order, including findFirst(), limit(), or
		//skip(), may actually perform more slowly in a parallel environment.
		System.out.println("and applying parallel stream will behave same way\n .parallelStream().skip(5).limit(2).findFirst().get() : " + 
							Arrays.asList(1,2,3,4,5,6).parallelStream().skip(5).limit(2).findFirst().get());
		
		
		//Creating UNORDERED STREAMS
		//That's because parallel processing task being forced to coordinate all of its threads in a synchronized-like fashion			
		System.out.println("\nFor serial streams, using an unordered version has no effect\n .stream().unordered().skip(5).limit(2).findFirst().get() : " + 
				Arrays.asList(1,2,3,4,5,6).stream().unordered().skip(5).limit(2).findFirst().get());

		//**TRICKY !!!**
		System.out.println("but on parallel streams, the results can greatly improve performance:\n .stream().unordered().parallel().skip(5).limit(2).findFirst().get() : " + 
				Arrays.asList(1,2,3,4,5,6).stream()
											.unordered()//ID DOESN'T actually WORK
											.parallel()
											.skip(5)//it still DOES SKIP the first 5 . WHY ???
											.limit(2).findFirst().get());
				/*This method does not actually reorder the elements; it just tells the JVM that if an
				order-based stream operation is applied, the order can be ignored. For example, calling
				skip(5) on an unordered stream will skip any 5 elements, not the first 5 required on an
				ordered stream.*/
		
		//Example-A) Combining Results with Three-Argument reduce(T identity, BiFunction<U, ? super T, U> accumulator,
        //BinaryOperator<U> combiner) Method
		System.out.println("\nArrays.asList('w', 'o', 'l', 'f')\n .stream()\n .reduce(\"\"//identity, such that for all elements in the stream u, combiner.apply(identity, u) is equal to u .\n	,(c,s1) -> c + s1 //accumulator operator must be associative and stateless, (a op b) op c is equal to a op (b op c) .\n	,(s2,s3) -> s2 + s3 //combiner operator must also be associative and stateless and compatible with the\n" + 
				"identity,)); "
							+ Arrays.asList('w', 'o', 'l', 'f')
							.stream()
							.reduce("",(c,s1) -> c + s1,
							(s2,s3) -> s2 + s3));
		
		//Example-B)  Using Two-Argument reduce(T identity, BinaryOperator<T> accumulator-combiner) Method with a  non-associative accumulator
		System.out.println("\nExample using a non-associative accumulator-combiner");
		System.out.print("Arrays.asList(1,2,3,4,5,6)\n .parallelStream()\n .reduce(0,(a,b) -> (a-b))); result (random): ");
		System.out.println(Arrays.asList(1,2,3,4,5,6)
				.parallelStream()
				.reduce(0,(a,b) -> (a-b))); // NOT AN ASSOCIATIVE ACCUMULATOR might output different result
		
		//Example-C)  Using Two-Argument reduce(identity. Accumulator) with Identity parameter that is not truly an identity value
		System.out.print("\nExample with a not real identity\n (Arrays.asList(\"w\",\"o\",\"l\",\"f\")\n .parallelStream()\n .reduce(\"X\",String::concat)); ");
		System.out.println(Arrays.asList("w","o","l","f")
				.parallelStream()
				.reduce("X",String::concat));// identity 'X' can't be considered an identity
		//Indeed it will output XwXoXlXf
		
		//Combing Results with collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)
		//Example 2.1.a - Three-argument collect() )
		System.out.println("\nExample with collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)");
		System.out.print("Stream.of(\"w\", \"o\", \"l\", \"f\")\n .parallel();\n .collect(ConcurrentSkipListSet::new, Set::add,Set::addAll);");
		Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
//		SortedSet<String> sortedSet = stream.collect(ConcurrentSkipListSet::new, Set::add,
//		Set::addAll);
		//NB: elements in a ConcurrentSkipListSet are sorted according to their natural ordering (String implement .compareTo() )
//		System.out.println(sortedSet); // [f, l, o, w]
		
		//Example 2.1.b) - One-argument collect(Collector<? super T, A, R> collector) )
		Stream<String> stream2 = Stream.of("w", "o", "l", "f").parallel();
		Set<String> set = stream2.collect(Collectors.toSet());
		//NB: elements in a HashSet<String> ( new HashSet<T> as default) are NOT sorted 
		System.out.println(set); // [f, w, l, o]
		
		
		/*IMPORTANT Requirements for Parallel Reduction with collect()
			-parallel stream
			-collect(Collector.Characteristics.CONCURRENT)
			-EITHER i) the stream is UNORDERED, or ii) the collector has characteristic collect(Collector.Characteristics.UNORDERED)
		*/
		
		//Example 2.1.c) - Unordered parallel stream  collect(Collector<? super T, A, R> collector) )
		Stream<String> unorderedParStream = Stream.of("w", "o", "l", "f").unordered().parallel();
		Set<String> set2 = unorderedParStream.collect(Collectors.toSet());
		
		//Example 2.1.d) - ordered (by default) parallel stream  with a custom collect(MyCollector has characteristic override returning Collector.Characteristics.CONCURRENT)
//		Stream<String> orderedParStream = Stream.of("w", "o", "l", "f").parallel();
//		Set<String> set3 = unorderedParStream.collect(Collector.Characteristics.CONCURRENT);
		
		//Example 2.1.e) - ordered (by default) parallel stream  with collect(Collector including concurrent characteristics)
		Stream<String> ohMy = Stream.of("lions", "tigers", "bears").parallel();
		ConcurrentMap<Integer, String> map = ohMy
		.collect(Collectors.toConcurrentMap(String::length, k -> k,
		(s1, s2) -> s1 + "," + s2));
	
	}
	
	private static class MyCollector implements
    		Collector<String, StringBuffer, String> {

		@Override
		public Supplier<StringBuffer> supplier () {
			return () -> {
			System.out.println("supplier call");
			return new StringBuffer();
	};
		}
		
		@Override
		public BiConsumer<StringBuffer, String> accumulator () {
			return (sb, s) -> {
				System.out.println("accumulator function call,"
				                + " accumulator container: "
				                + System.identityHashCode(sb)
				                + " thread: "
				                + Thread.currentThread().getName()
				                + ", processing: " + s);
				sb.append(" ").append(s);
			};
		}
		
		@Override
		public BinaryOperator<StringBuffer> combiner () {
			return (stringBuilder, s) -> {
				System.out.println("combiner function call");
				return stringBuilder.append(s);
			};
		}
		
		@Override
		public Function<StringBuffer, String> finisher () {
		return stringBuilder -> stringBuilder.toString();
		}
		
		@Override
		public Set<Characteristics> characteristics () {
		//  return Collections.emptySet();
		return EnumSet.of(Characteristics.CONCURRENT);
		}
	}

}
