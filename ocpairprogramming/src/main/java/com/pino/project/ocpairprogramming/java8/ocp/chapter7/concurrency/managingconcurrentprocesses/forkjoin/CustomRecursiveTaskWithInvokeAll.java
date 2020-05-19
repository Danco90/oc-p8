package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.managingconcurrentprocesses.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * In our RecursiveTask<V> example we used the invokeAll() method to submit a sequence of subtasks to the pool. 
 * The same job can be done with fork() and join(), though this has consequences for the ordering of the results.
 * @author matteodaniele
 * 
 * NB: To avoid confusion, it is generally a good idea to use invokeAll() method to submit more than one task to the ForkJoinPool.
 *
 */
public class CustomRecursiveTaskWithInvokeAll extends RecursiveTask<Integer>{
	private int[] arr;
    private static final int THRESHOLD = 20;
    
    public CustomRecursiveTaskWithInvokeAll(int[] arr) {
        this.arr = arr;
    }

	@Override
	protected Integer compute() {
		if(arr.length > THRESHOLD) {//RECURSIVE CASE
			//generally a good idea to use invokeAll() method to submit more than one task to the ForkJoinPool.
			return ForkJoinTask.invokeAll(createSubtasks())//for each subtask recursively created, it invokes its compute methods, resulting in a collection of Integer results
					.stream()//So, we leverage the power of a stream to do a performing calculation 
					.mapToInt(ForkJoinTask::join)//But such results need to be converted in primitive in order to do the aggregation sum 
					.sum();//as terminal operation of a stream.
		} else {//BASE CASE
			return processing(arr);
		}
		
	}
	
	private Collection<CustomRecursiveTaskWithInvokeAll> createSubtasks() {
		List<CustomRecursiveTaskWithInvokeAll> dividedTasks = new ArrayList<>();
		dividedTasks.add(new CustomRecursiveTaskWithInvokeAll(
					Arrays.copyOfRange(arr, 0, arr.length / 2)));
		dividedTasks.add(new CustomRecursiveTaskWithInvokeAll(
					Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
		return dividedTasks;
	}
	
	 private Integer processing(int[] arr) {
		 return Arrays.stream(arr)
				 .filter(a -> a > 10 && a < 27)
				 .map(a -> a * 10)
				 .sum();
	 }
	 
		
	public static void main(String[] args) {
		Random random = new Random();
        int[] arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(35);
        }
      
		//Submit task to the thread
		CustomRecursiveTaskWithInvokeAll task = new CustomRecursiveTaskWithInvokeAll(arr);
		ForkJoinPool pool = new ForkJoinPool();
		
		//A. Using submit() or execute() method on the pool
//		pool.execute(task);
//		int result = task.join();
//		System.out.print("result : "+result);
		
		//B. Using invoke() method, to fork the task and wait for the result, and DOESN'T NEED any manual JOINING
		int result = pool.invoke(task);
		System.out.println("result : "+result);
		
		/*C. invokeAll() method, method is the most convenient way to submit a sequence of ForkJoinTasks to the ForkJoinPool. 
		 *It takes tasks as parameters (two tasks, var args, or a collection), forks then returns a collection of Future objects 
		 *in the order in which they were produced.*/
		
		/*
		 * D. Alternatively, you can use separate fork() and join() methods. The fork() method submits a task to a pool, 
		 * but it doesn't trigger its execution. The join() method must be used for this purpose. 
		 * In the case of RecursiveAction, the join() returns nothing but null; 
		 * for RecursiveTask<V>, it returns the result of the task's execution:
		 * 
		 * 	customRecursiveTaskFirst.fork();
		 * 	result = customRecursiveTaskLast.join();
		 * */
		
		
		
		
	}

}
