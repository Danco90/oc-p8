package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.managingconcurrentprocesses.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class WeighAnimalTask extends RecursiveTask<Double> {
	private static final long serialVersionUID = 1L;
	private int start;
	private int end;
	private Double[] weights;
	
	public WeighAnimalTask(Double[] weights, int start, int end) {
		this.start = start;
		this.end = end;
		this.weights = weights;
	}
	
	protected Double compute() {
		if(end-start <= 3) {
			double sum = 0;
			for(int i=start; i<end; i++) {
				weights[i] = (double)new Random().nextInt(100);
				System.out.println("Animal Weighed: "+i);
				sum += weights[i];
			}
			return sum;
		} else {
			int middle = start+((end-start)/2);
			System.out.println("[start="+start+",middle="+middle+",end="+end+"]");
			RecursiveTask<Double> otherTask = new WeighAnimalTask(weights,start,middle);
			//Case a) Multi-threaded
			otherTask.fork();//instructs the fork/join framework to complete the other task (subtask-right) in a separate thread
			return new WeighAnimalTask(weights,middle,end).compute()//complete one more new task (subtask-left)
										+ otherTask.join();//and sum the contribute (Double) of the previous subtask (subtask-right), 
			                            //since the join method, which returns a Double in our case, causes the current thread to wait for the results.
			
			//CASE b) Single-threaded  (by chaining fork().join())
			//Double otherResult = otherTask.fork().join();//WATCH OUT from the order in which fork and join are applied
			//return new WeighAnimalTask(weights,middle,end).compute() + otherResult;
		}
	}
	
	public static void main(String[] args) {
		Double[] weights = new Double[10];
		ForkJoinTask<Double> task = new WeighAnimalTask(weights,0,weights.length);
		ForkJoinPool pool = new ForkJoinPool();
		Double sum = pool.invoke(task);
		System.out.println("Sum: "+sum);//it changes all the time because the random weight
	}
}
