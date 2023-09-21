package Question4;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Driver {

	public static void main(String[] args) {
		
		int nThreads = 0;
		int upperBound = 0;
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			try {
				System.out.print("Enter the upper bound (N): ");
				upperBound = sc.nextInt();
				
				if (upperBound < Math.pow(10, 6)|| upperBound > Math.pow(10, 9)) {
					throw new NumberFormatException();
				}
				
				System.out.print("Enter the number of threads (1-10): ");
				nThreads = sc.nextInt();
				
				if (nThreads < 1 || nThreads > 10) {
					throw new NumberFormatException();
				}
				
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter valid values.");
				System.out.println("");
			}
		}
		
		long startTime = System.currentTimeMillis();
		
		// Create an executor service with a fixed thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
		
		List<Future<Integer>> futures = new ArrayList<>();
		
		// Split the task into sub task and submit them to the thread pool
		int blockSize = upperBound / nThreads;
		int lower = 1;
		int upper;
		
		for (int i = 0; i < nThreads; i++) {
			upper = i == nThreads - 1 ? upperBound : lower + blockSize - 1;
			Task task = new Task(lower, upper);
			futures.add(executorService.submit(task));
			lower = upper + 1;
		}
		
		// Collect partial counts from each thread
		int totalCount = 0;
		for (Future<Integer> future : futures) {
			try {
				totalCount += future.get();
			
			} catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
			
		}
		
		// Shutdown the executor service
		executorService.shutdown();
		
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		
		// Display results
		System.out.println("Total perfect numbers between 1 and " + upperBound + ": " + totalCount);
		System.out.println("Execution time: " + executionTime + " ms");
	}

}
