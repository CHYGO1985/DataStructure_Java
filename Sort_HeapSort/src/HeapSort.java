import java.util.Arrays;


/**
 * 1.Heap sort:
 * Note:
 * 1. use length/2 to identify the last non-leaf node directly
 * 2. use index*2 to locate left child directly
 * ref: fishc.com
 * 
 * @author jingjiejiang
 * @history
 * 1.Dec 5, 2016
 */
public class HeapSort {
	
	public static void swap(int[] array, int front, int rear) {
		
		int temp = array[front];
		array[front] = array[rear];
		array[rear] = temp;
	}
	
	public static void adjustHeap(int[] array, int checkPos, int adjLen) {
		
		int temp = array[checkPos];
		
		// ***note: tricky part, user index*2 to locate the child. 
		// check the children of checkPos. 
		for (int i = 2 * checkPos; i <= adjLen; i = i * 2 ) {
			
			// if left child of checkPos < right child of checkPos
			if ( i < adjLen && array[i] < array[i + 1]) {
				i ++;
			}
			
			// father > child
			if (temp >= array[i]) {
				break;
			}
			else {
				array[checkPos] = array[i];
				// 1. checkPos represent the num that has already been used to replace the
				// original checkPos
				// 2. it may happen that checkPos is till smaller than its children, so do
				// not finish the swap here
				checkPos = i;
			}
		}
		
		// finish the last step of swapping 
		array[checkPos] = temp;
	}
	
	public static void heapSort(int[] array) {
		
		// start comparing from the first non leaf node
		int length = array.length - 1;
		
		// ***note: here is the tricky part, use length/2 to find the first none
		// leaf node directly
		// shift the biggest number to the top of the array
		for (int i = length/2; i > 0; i --) {
		    adjustHeap(array, i, length);	
		}
		
		// the process of swapping the biggest to the rear and
		// get the biggest to the top in the rest numbers.
		for (int i = length; i > 0; i --) {
			
			swap(array, 1, i);
			
			// *** after the adjust, bigger ele have been upraded, so start
			// from 1 and the last one was swapped to the first one
			adjustHeap(array, 1, i - 1);
		}
	}

	public static void main(String[] args) {

		// test case
		// -1 is to let the array start from 1, then easy to cal the
		int[] test = {-1, 5, 6, 2, 0, 3, 9, 1, 7, 4};
		heapSort(test);

		for (int i = 1; i < test.length; i ++) {
			
			System.out.println(i + " ");
		}
		
	}

}
