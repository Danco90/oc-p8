package com.pino.project.ocpairprogramming.java8.ocp.chapter4.usingstreams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class UsingStreams {

	public static void main(String[] args) {
		
		//How to create a STREAM
		//CASE 1) Factory Stream.of()
		Stream<String> empty = Stream.empty();//(*)
		Stream<Integer> singleElement = Stream.of(1);//(*)
		Stream<Integer> fromArray = Stream.of(1,2,3);//(*)
		//(*)nb: The Stream is lazily-evaluated : declared now and evaluated later.
		System.out.println(empty);
		System.out.println(singleElement);
		System.out.println(fromArray+"\n");
		
		//long count()
		System.out.println(empty.count());//the terminal operation applied to the source is destructive, which means it ends the stream. 
		System.out.println(singleElement.count());
		System.out.println(fromArray.count()+"\n");
//		System.out.println(empty.count());//the stream is no more available and it throws an Exception
//		System.out.println(singleElement.count());
//		System.out.println(fromArray.count()+"\n");
		
		//CASE 2) Starting from a collection : applying .stream() to a collection
		List<String> list = Arrays.asList("a", "b", "c");
		//2.a) creating a SERIAL stream from a collection
		Stream<String> fromList = list.stream();
		//2.b) creating a PARALLEL stream from a collection
		Stream<String> fromListParallel = list.parallelStream();
		
		//CASE 3) Starting from another stream : applying .parallel() to a serial stream 
		Stream<String> fromStreamParallel = fromList.parallel();
		
		//CASE 4) Infinite Streamds
		//4.1) static<T> Stream<T> generate(Supplier<T> s)
		Stream<Double> randoms = Stream.generate(Math::random);
		//4.2) static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);//It gives more control.
		
		Stream<String> s = Stream.of("monkey", "ape", "bonobo");
		//Optional<T> min(<? super T> comparator)
		Optional<String> min = s.min((s1, s2) -> s1.length()-s2.length());
		min.ifPresent(System.out::println);//ape
		s = Stream.of();
		s.min((s1, s2) -> s1.length()-s2.length())
		 .ifPresent(System.out::println);//NOT PRESENT: So ifPresent DOES NOT invoke the consumer
		
		//Optional<T> max(<? super T> comparator)
		Optional<?> minEmpty = Stream.empty().max((s1, s2) -> 0);//Since empty Stream, The Comparator is never called 
		System.out.println(minEmpty.isPresent()); // false
		
		//Optional<T> findAny()
		Stream<String> s1 = Stream.of("monkey", "gorilla", "bonobo");
		Stream<String> infinite = Stream.generate(() -> "chimp");
		s1.findAny()//returns a not empty Optional<String>
		  .ifPresent(System.out::println);// monkey (in the serial stream always the first )
		infinite.findAny()
		     .ifPresent(System.out::println);// chimp is definitely the one and only one it can finds repeatedly 
		
		//Optional<T> findFirst() in the serial stream always the first, whereas in parallel will find the one whose thread is running
		Stream<Integer> finiteIntegers = Stream.of(1,2,3,4,5,6,7,8,9,10);
		Stream<Integer> infiniteIntegers = Stream.iterate(1, i -> i+1);
		Stream<Integer> infiniteParallelIntegers = Stream.iterate(1, i -> i+1).parallel();
		finiteIntegers.findFirst().ifPresent(System.out::println);
		infiniteIntegers.findFirst().ifPresent(System.out::println);
		infiniteParallelIntegers.findFirst().ifPresent(System.out::println);
		
		//void forEach(Consumer<? super T> action) :IT IS NOT A REDUCTION; on INFINITE stream DOES NOT TERMINATE
		Stream<String> sf = Stream.of("Monkey", "Gorilla", "Bonobo");
		//A) foreach called on a stream
		sf.forEach(System.out::print); // MonkeyGorillaBonobo
		//B) foreach called on a Collection
		List<String> myList = Arrays.asList("Monkey", "Gorilla", "Bonobo");
		myList.forEach(System.out::print);
		
		//NB: Streams CAN'T USE a TRADITIONAL for loop because they donàt implement Iterable 
//		Stream ss = Stream.of(1);
//		for (Integer i: s) {} // DOES NOT COMPILE
		
		//reduce() : It is a REDUCTION that COMBINES a stream INTO a SINGLE OBJ
		//Signature1 :  T reduce(T identity, BinaryOperator<T> accumulator)
		Stream<String> stream = Stream.of("w", "o", "l", "f");
		String word = stream.reduce("", (ss, c) ->ss + c);
		System.out.println(word); // wolf
		
		Stream<Integer> stream2 = Stream.of(3, 5, 6);
		System.out.println(stream2.reduce(1, (a, b) -> a*b));
		
		//Signature2 :  Optional<T> reduce(BinaryOperator<T> accumulator) : when you don't specify an identity, java returns an Optional
		stream2 = Stream.of(3, 5, 6);//rewritten example
		Optional<?> res = stream2.reduce((a, b) -> a*b);//In this case the Identity is not needed
		System.out.println(res);//The accumulator is applied
		//(*)NB : if the stream is empty, en empty Optional is returned; if the stream has one elem the accum is not even called and the only elem is returnedM;
		//Whereas if the stream has more than one element, the accumulator is applied to combine them.
		BinaryOperator<Integer> op = (a, b) -> a * b;
		Stream<Integer> empty1 = Stream.empty();
		Stream<Integer> oneElement = Stream.of(3);
		Stream<Integer> threeElements = Stream.of(3, 5, 6);
		
		empty1.reduce(op)
			.ifPresent(System.out::println);//no output
		oneElement.reduce(op).ifPresent(System.out::println);// 3 and the accum is not called
		threeElements.reduce(op).ifPresent(System.out::println);// 90  and the accum is called
		
		//Signature3 : <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator,BinaryOperator<U> combiner)
		//It's used for PARALLEL streams, as java assumes that a stream might be parallel
		System.out.println(stream2.reduce(1, op, op)); // 90 (*)
		//(*) BinaryOperator<U> is a special case of BiFunction<U,U,U>
		
		//collect()
		
	}

}
