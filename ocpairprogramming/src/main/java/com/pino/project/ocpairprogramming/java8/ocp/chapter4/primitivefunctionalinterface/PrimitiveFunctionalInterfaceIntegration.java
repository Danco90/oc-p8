package com.pino.project.ocpairprogramming.java8.ocp.chapter4.primitivefunctionalinterface;

import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToLongBiFunction;

public class PrimitiveFunctionalInterfaceIntegration {

	public static void main(String[] args) {
		
		//BiConsumer, BiPredicate and BiFunction are not provided because there's no point to use them.
		//DoubleBiConsumer  EXISTING but not present in table
		//IntBiConsumer EXISTING but not present in table
		//LongBiconsumer EXISTING but not present in table
		
		//But there are kind of BiConsumer for Object and double/int/long primitive
		ObjDoubleConsumer consumer1 = (s, i) -> System.out.printf("Consume %d %s%n", s, i);
		ObjIntConsumer consumer2 = (s, i) -> System.out.printf("Consume %d %s%n", s, i);	
		ObjLongConsumer consumer3 = (s, i) -> System.out.printf("Consume %d %s%n", s, i);
		
		//DoubleBiPredicate  EXISTING but not present in table
		//IntBiPredicate EXISTING but not present in table
		//LongBiPredicate NON EXISTING
		
		//DoubleBiFunction EXISTING but not present in table
		//IntBiFunction EXISTING but not present in table
		//LongBiFunction EXISTING but not present in table
		
		//But there are kind of BiFunction for 2 Objects T and U and return type double/int/long primitive
		ToDoubleBiFunction<String, Integer> bifunction1 = (s, i) -> ((s.length() + i)/2) ;
		ToIntBiFunction<String, String> bifunction2 = (s1, s2) -> s1.length() + s2.length() ;
		ToLongBiFunction<String, Long> bifunction3 = (s, l) -> s.length() + l ;
		
	}

}
