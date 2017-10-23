
/**
 * 1.Merge Sort Implementation
 * ref: http://www.vogella.com/tutorials/JavaAlgorithmsMergesort/article.html
 * 
 * @author jingjiejiang
 * @history
 * 1. Dec 7, 2016
 * 1) this kind of compare (split half),  when need to consider when to use =
 * use the case of 2 elements
 * 2) when right part do not need to explicitly added to the result array as 
 * the left part. 
 */
public class MergeSort {

	public static void mergeSort(int[] array, int startBound, int endBound) {
		
		// if use =, then when there is two, it will end up with infinite loop
		if (startBound < endBound) {
			
			int mid = startBound + (endBound - startBound) / 2;
			mergeSort(array, startBound, mid);
			mergeSort(array, mid + 1, endBound);
			mergeDivs(array, startBound, mid, endBound);
		}
	}
	
	public static void mergeDivs(int[] array, int startBound, int mid, int endBound) {
		
		int[] tempArr = new int[array.length];
		
		// copy the content of original array to helper array
		for (int i = startBound; i <= endBound; i++) {
			
			tempArr[i] = array[i];
		}
		
		int beginLeft = startBound;
		int beginRight = mid + 1;
		int index = startBound;
		
		// compare first and second divisions, add the smallers to the array first
		// *** <= for the case when there is only two elements 
		while (beginLeft <= mid && beginRight <= endBound) {
			
			if (tempArr[beginLeft] <= tempArr[beginRight]) {
				
				array[index] = tempArr[beginLeft];
				beginLeft ++;
			}
			else {
				array[index] = tempArr[beginRight];
				beginRight ++;
			}
			
			index ++;
		}
		
		// add the rest of left part to the new array
		while (beginLeft <= mid) {
			
			array[index] = tempArr[beginLeft];
			beginLeft ++;
			index ++;
		}
		
		//*** note why the right part wont leave any elements
		// when compare: left and right, if left <= right, add left
		// else the right element can be left in where it is (in 
		// the original array, as it is in order already)
	}
	
	public static void main(String[] args) {
		
		//int[] test = {3, 7, 8, 5, 2, 1, 9, 5, 4};
		int[] test = {4,6,7,8,1,2,3,5};
		
		mergeSort(test, 0, test.length - 1);
		
		for (int i : test) {
			
			System.out.print(i + "\t");
		}
	}

}
