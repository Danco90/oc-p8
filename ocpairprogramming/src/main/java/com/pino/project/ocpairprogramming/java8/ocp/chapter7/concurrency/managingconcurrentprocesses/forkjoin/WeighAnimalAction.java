package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.managingconcurrentprocesses.forkjoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class WeighAnimalAction extends RecursiveAction {
	private static final long serialVersionUID = 1L;//avoid the warning on class definition, as it turns out the parent task implements Serializable
	private int start;
	private int end;
	private Double[] weights;
	public WeighAnimalAction(Double[] weights, int start, int end) {
		this.start = start;
		this.end = end;
		this.weights = weights;
	}
	
	protected synchronized void compute() {
		if(end-start <= 3) {
			for(int i=start; i<end; i++) {
				weights[i] = (double)new Random().nextInt(100);
				System.out.println("-->fork subtask[start="+start+",end="+end+"] Animal Weighed: " + i + "' executed by " +Thread.currentThread().getName()+". ");
			}
		}//implicitly join the submitted (sub)task (right or left)
		else {
			int middle = start + ((end-start)/2);
			System.out.println("task[start=" + start + ",end=" + end + "]"+Thread.currentThread().getName()+" splits into 2 more recursive subtasks [start=" + start + ",middle=" + middle + ",end=" + end + "]");
			invokeAll(new WeighAnimalAction(weights,start,middle),//implicitly fork a new subtask (left side)
					  new WeighAnimalAction(weights,middle,end));//implicitly fork another new subtask (right side)
		}
		joinTask();
	}
	
	private void joinTask() {
		if(start > 0 || end < weights.length)
			 System.out.println("join<-- subtask[start=" + start + ",end=" + end + "] executed by " +Thread.currentThread().getName()+". ");
		else System.out.println("join<-- task[start=" + start + ",end=" + end + "] executed by "    +Thread.currentThread().getName()+". ");
	}
	
	public static void main(String[] args) {
		Double[] weights = new Double[10];
		ForkJoinTask<?> task = new WeighAnimalAction(weights,0,weights.length);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(task);
		// Print results
		System.out.println();
		System.out.print("Weights: ");
		Arrays.asList(weights).stream().forEach(d -> System.out.print(d.intValue()+" "));
	}
}
