/**
 * 
 * insert sort for sorting an array.
 * 
 * ref: https://courses.cs.washington.edu/courses/cse373/01wi/slides/Measurement/sld010.htm
 * 
 * idea: swapping
 * index  0 1 2 3 4		
 *        2 3 1 4 5 i = 3
 * 1)  j = 1, nums[j] > temp (1), move the value to next position
 * 0 1 2 3 4
 * 2 3 2 4 5
 * 
 * 2) j = 0, 
 * 0 1 2 3 4
 * 2 2 3 4 5
 * 
 * 3) j = -1, nums[j+1] = temp; 
 * 0 1 2 3 4
 * 1 2 3 4 5
 * 
 * @author jingjiejiang
 * @history
 * 1. Oct 23, 2017
 */
public class InsertSortArray {
	
	public static void insertSort(int[] nums) {
		
		for (int i = 0; i < nums.length; i ++) {
			
			int temp = nums[i];
			int j = i - 1;
			
			// *** the index keeps changing, so use temp here, instead of nums[i]
			for (; j >= 0 && nums[j] >= temp; j --){
				nums[j + 1] = nums[j];  
			}
			
			nums[j + 1] = temp;
		}
	}

	public static void main(String[] args) {
		
		int[] nums = new int[]{1};
		insertSort(nums);
		for (int i = 0; i < nums.length; i ++) {
			System.out.print(nums[i]);
		}
		
	}
}
