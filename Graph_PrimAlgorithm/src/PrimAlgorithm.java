
/**
 * The implementaiton of Prim's algorithm to calculate the minimum spannning tree.
 * The tricky part of this code is to use two array: one to keep prefix of each vertex in the spanning tree, and one
 * to keep the min cost to connect to the vertex in the spanning tree set. This use avoid traditional method (like using set)
 * to keep the vertext in the spanning tree set, and then each round will need to iterate through the set to get a vertex A, 
 * then iterate through all the vertex that A connects to, in order to get the lowest weight.
 * 
 * @author jingjiejiang
 * @history 
 * 1. Dec 4, 2017
 * ref: blog.fishc.com
 */
public class PrimAlgorithm {
	
	public static final int VER_NUM = 9;
	public static final int X = 65535;
	
	public static void getPrimSpanTree(int[][] metrix) {
		
		// the array is to keep the prefix of each vertex in the spanning tree
		// this is to match the lowCost array
		int[] adjVertex = new int[VER_NUM];
		
		// keep the cost from (vertex in the spanning tree set) to other vertex
		// *** note: here the use of this array
		// 1) use set to keep the vertex in a spanning tree
		// need to iterate through the set to get a vertex A and iterate all the vertex that X connected to
		// 2) use array
		// can keep the connect information of previous vertex that have been added to set, 
		int[] lowCost = new int[VER_NUM];
		
		lowCost[0] = 0;
		adjVertex[0] = 0;
		
		for (int i = 0; i < VER_NUM; i ++) {
			
			lowCost[i] = metrix[0][i];
			adjVertex[i] = 0;
		}
		
		// coz vertex 0 is the start vertex, so here i starts from 1.
		for (int i = 1; i < VER_NUM; i++) {
		    
			int minWeight = X;
		    int j = 1;
		    int k = 0;
		    
		    while (j < VER_NUM) {
		    	
		    	if (lowCost[j] != 0 && lowCost[j] < minWeight) {
		    		
		    		minWeight = lowCost[j];
		    		k = j;
		    	}
		    	
		    	j ++;
		    }
		    
		    // output the prefix and the vertex that has the min weight among all.
		    System.out.println(adjVertex[k] + " " + k);
		    
		    // k has been added to spanning tree, do not need to check afterwards, so set as 0
		    lowCost[k] = 0;
		    
		    for (int m = 1; m < VER_NUM; m ++) {
		    	
		    	// update lowCost array, as new vertex A has been add to the set
		    	// Q: how the lowCost to keep the connecting infor of previous vertex in the set?
		    	// A: if from previous vertex connect to B, and weight is M, if from new A to B < M
		    	// then update the infor, otherwise keep the previous B's information.
		    	if ( lowCost[m] != 0 && metrix[k][m] < lowCost[m]) {
		    		
		    		lowCost[m] = metrix[k][m];
		    		adjVertex[m] = k;
		    	}
		    }
		}
	}

	public static void main(String[] args) {

		// test case, start from 0.
		int[][] metrix = {
			{0, 10, X, X, X, 11, X, X, X},
			{10, 0, 18, X, X, X, 16, X, 12},
			{X, 18, 0, 22, X, X, X, X, 8},
			{X, X, 22, 0, 20, X, X, 16, 21},
			{X, X, X, 20, 0, 26, X, 7, X},
			{11, X, X, X, 26, 0, 17, X, X},
			{X, 16, X, X, X, 17, 0, 19, X},
			{X, X, X, 16, 7, X, 19, 0, X},
			{X, 12, 8, 21, X, X, X, X, 0},
		};
		
		getPrimSpanTree(metrix);
	}

}
