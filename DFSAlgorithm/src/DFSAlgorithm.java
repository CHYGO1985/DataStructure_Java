import java.util.LinkedList;
import java.util.List;

public class DFSAlgorithm {
	
	public static List<Integer> DFSSearch(int[][] metrix, List<Integer> list
			, boolean[] status) {
		
		// assume the graph searches from the first node "V0"
		list.add(0);
		status[0] = true;
		
		DFSSearchHelper(metrix, 0, list, status);
		
		return list;
	}
	
	public static void DFSSearchHelper (int[][] metrix, int rowNum, List<Integer> list
			, boolean[] status) {
		
		if (rowNum >= metrix.length) {
			return ; 
		}
		
		for (int i = rowNum; i < metrix.length; i ++) {
			for (int j = 0; j < metrix[rowNum].length; j ++) {
				
				if(1 == metrix[rowNum][j] && false == status[j]) {
					
					list.add(j);
					status[j] = true;
					DFSSearchHelper(metrix, j, list, status);
				}
			}
		}
	}

	public static void main(String[] args) {
		
		/**
		 * test case: 1. undirected graph, adjacent metrix (check onenote "data structure"-->"graph")
		 * [0,1,0,1,1,0,0,0]
		 * [1,0,1,0,1,0,0,0]
		 * [0,1,0,0,0,1,0,0]
		 * [1,0,0,0,0,0,1,0]
		 * [1,1,0,0,0,0,1,0]
		 * [0,0,1,0,0,0,0,0]
		 * [0,0,0,1,1,0,0,1]
		 * [0,0,0,0,0,0,1,0]
		 * 
		 */
		
		int[][] adjMetrix = {
		    {0,1,0,1,1,0,0,0},
		    {1,0,1,0,1,0,0,0},
		    {0,1,0,0,0,1,0,0},
		    {0,1,0,1,1,0,0,0},
		    {1,0,0,0,0,0,1,0},
		    {0,0,1,0,0,0,0,0},
		    {0,0,0,1,1,0,0,1},
		    {0,0,0,0,0,0,1,0},
		};
		
		boolean[] nodeStatus = new boolean[8];
		
		for (int i = 0; i < nodeStatus.length; i ++) {
			
			nodeStatus[i] = false;
		}
		
		List<Integer> list = new LinkedList<Integer>();
		DFSSearch(adjMetrix, list, nodeStatus);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
	}

}
