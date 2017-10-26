import java.awt.datatransfer.StringSelection;


public class QuickSortImp {
	
	public static void swap (int[] nums, int pre, int post) {
		
		int temp = nums[pre];
		nums[pre] = nums[post];
		nums[post] = temp;
	}
	
	public static void quickSort(int[] nums, int front, int rear) {
		
		if (nums == null || front >= rear)
			return ;
		
		int pivot = nums[front];
		int big = front;
		int small = rear;
		
		// *** here can just use <
		while (big < small) {
			
			while (nums[big] < pivot) 
				big ++;
			
			while (nums[small] > pivot)
				small --;
			
			// *** why use <= here? if not use >= when big = small - 1, it will end up with infinite loop
			if (big <= small) {
				swap(nums, small, big);
				big ++;
				small--;
			}
		}
		
		if (front < small)
			quickSort(nums, front, small);
		
		if (rear > big)
			quickSort(nums, big, rear);
		
	}
	
	public static void main(String[] args) {
		
		//int[] nums = new int[]{3,2,1,4,5,6,7,6,8};
		int[] nums = new int[]{3,1};
		quickSort(nums, 0, nums.length - 1);
		for (int i = 0; i < nums.length; i ++)
			System.out.println(nums[i]);
	}

}
