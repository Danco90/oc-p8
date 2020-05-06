package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class ReviewQuestionsChapter4 {

	public static void main(String[] args) {
//		Predicate<? super String> predicate = s -> s.length() > 3;
//		Stream<String> stream = Stream.iterate("", (s) -> s + s);
//		boolean b1 = stream.noneMatch(predicate);//hangs
//		boolean b2 = stream.anyMatch(predicate);//hangs
//		System.out.println(b1 + " " + b2);
		
		Predicate<? super String> predicate = s -> s.startsWith("g");
		Stream<String> stream1 = Stream.generate(() -> "grow! ");
		Stream<String> stream2 = Stream.generate(() -> "grow! ");
		boolean b1 = stream1.anyMatch(predicate);
		boolean b2 = stream2.allMatch(predicate);
		System.out.println(b1 + " " + b2);
			
	}

}
