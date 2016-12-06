/**
 * Shell sort. ***Think about why it works 
 * ref: https://en.wikipedia.org/wiki/Shellsort
 * 
 * @author jingjiejiang
 * @history
 * 1. Dec 6, 2016
 */
public class ShellSort {
	
	public static void shellSort(int[] array) {
		
		int gap = array.length; 
				
		do {
			
			gap = gap / 3 + 1;
			
			for (int i = gap; i < array.length; i ++) {
				
				if ( array[i] < array[i - gap] ) {
					
					int shift = array[i];
					int j = i - gap;
					
					// when gap is samll, the ele needs to shift to the front part of the array
					// so need a for loop before finish the swapping
					for (; j >=0 && array[j] > shift; j -= gap) {
						
						array[j + gap] = array[j];
					}
					
					array[j + gap] = shift;
				}
			}
		}
		while (gap > 1);
	}
	
	
	public static void main(String[] args) {
		
		
		// int[] test = {49, 38, 65, 97, 76, 13, 27, 49, 55, 04};
		
		int[] test = {62, 83, 18, 53, 7, 17, 95, 86, 47, 69, 25, 28};
		
		shellSort(test);
		
		for (int i: test) {
			
			System.out.print(i + "\t");
		}
	}

}
