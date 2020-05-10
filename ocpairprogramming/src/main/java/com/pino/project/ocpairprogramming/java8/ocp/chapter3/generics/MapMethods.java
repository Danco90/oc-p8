package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapMethods {

	public static void main(String[] args) {
		System.out.print("Map<String,String> map = new HashMap<>();\n");
		Map<String,String> map = new HashMap<>();
		map.put("koala", "bamboo");
		map.put("lion", "meat");
		map.put("giraffe", "leaf");
		String food = map.get("koala"); // bamboo
		for (String key: map.keySet())
		System.out.print(key + ","); // koala,giraffe,lion,
		
		System.out.println("\nMap<String,String> treeMap = new TreeMap<>();");
		Map<String,String> treeMap = new TreeMap<>();
		treeMap.put("koala", "bamboo");
		treeMap.put("lion", "meat");
		treeMap.put("giraffe", "leaf");
		food = map.get("koala"); // bamboo
		for (String key: treeMap.keySet())
		System.out.print(key + ","); // giraffe,koala,lion,
	}

}
