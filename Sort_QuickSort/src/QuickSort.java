
/**
 * 1.Two different types of implementation in terms of the position of pivot:
 * 1) middle 
 * ref: http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
 * 2) first element
 * ref: https://en.wikipedia.org/wiki/Quicksort
 * 
 * The core of recursive method is that: put ele that is larger than pivot to rear part and put smaller ones to
 * front part.
 * 1) how to get pivot does not matter to the algorithm. either front, mid or rear.
 * 
 * @author jingjiejiang
 * @history
 * 1. Dec 6, 2016
 * 1) the design of recursive core.
 * two pointers tech, one from front and one from rear, exchange those in the front are bigger than pivot with those
 * in the rear smaller then pivot (the pos of pivot ele can change as well). every time, seperate the array with pivot 
 * 2) two conditions: startBound < startSec / endBound > startFst
 */
public class QuickSort {
	
	public static void swap(int[] array, int front, int rear) {
		
		int temp = array[front];
		array[front] = array[rear];
		array[rear] = temp;
	}
	
	/**
	 * Quick sorting by get the pivot from the middle of an array. 
	 *  
	 * @param array
	 */
	public static void quickSort(int[] array, int startBound, int endBound) {
		
		// special case: length = 0/1/2
		
		if (null == array || 0 == array.length || 1 == array.length) {
			
			return;
		}
		
		// *** note here: pivot can be in the middle or front or rear, it
		// does not matter
		// int pivot = array[startBound + (endBound - startBound) / 2];
		int pivot = array[startBound];
		int startFst = startBound;
		int startSec = endBound;
		
		while (startFst <= startSec) {

			// can not be >= , in case all the element on one side are bigger than pivot
			// in this case, must stop at the pivot position, then swap pivot
			// when length = 2. arrry[startFst] = array[pivot], startFst will not change.
			while (array[startFst] < pivot) {
				
				startFst ++;
			}
			
			while (array[startSec] > pivot) {
				
				startSec --;
			}
			
			// swap the mismatch elements in the first and second half
			if (startFst <= startSec) {
				
				swap(array, startFst, startSec);
				startFst ++;
				startSec --;
			}
		}
		
		// *** note: the settings of boundry, startSec is for finding ele that are smaller than pivot
		// so until it reach to the start, still need to sort; same for startFst and endBound
		if (startBound < startSec) {
			quickSort(array, startBound, startSec);
		}
		
		if (endBound > startFst) {
			quickSort(array, startFst, endBound);
		}
		
	}


	public static void main(String[] args) {

		//int[] test = {3, 7, 8, 5, 2, 1, 9, 5, 4};
		int[] test = {3, 0, 1, 8, 7, 2, 5, 4, 9, 6};
		
		quickSort(test, 0, test.length - 1);
		
		for (int i : test) {
			
			System.out.print(i + "\t");
		}
	}

}
