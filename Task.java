package Question4;

import java.util.concurrent.Callable;

// use two thread to calculate the sum of squared root from 1 to N
public class Task implements Callable<Integer> {
	private int lowerBound, upperBound;
	
	public Task(int lower, int upper ) {
		this.lowerBound = lower;
		this.upperBound = upper;
	}
	
	public Integer call() {
		int count = 0;
		for (int i = lowerBound; i <= upperBound; i++) {
			if (isPerfectNumber(i)) {
				count++;
			}
		}
		return count;
	}
	
	private boolean isPerfectNumber(int num) {
		int sum = 1;
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0) {
				sum += i;
				if ( i != num / i) {
					sum += num / i;
				}
			}
		}
		return sum == num;
	}
}
