
/**
 * 1. the design of Floyd-Wasshall is based on the theory of comparing path(a, c) and path(a, b) + path(b, c).
 * No matter how many vertex are there, I can consider them as a whole, so in essense, it is to compare 
 * path(a, c) and path(a, b) + path(b, c)
 * 
 * @author jingjiejiang
 * @history Dec 02, 2016
 * 1. Problem
 * 1) the order of i, j, k. think about the meaning of floyd (found many to many short route)
 * and each time add one vertex to the set
 * 2) how to change pathIndic[][]
 *
 */
public class FloydWarshallAlgorithm {
	
	public static final int X = 65535;
	// number of vertex in a graph
	public static final int VERTEX = 9;
	
	public static int[][] shortRoute = new int[VERTEX][VERTEX]; 
	// keep tracking the prefix of each vertex in the shortest path
	public static int[][] pathIndic = new int[VERTEX][VERTEX];
	
	public static void init(int[][] metrix) {
		
		for (int i = 0; i < VERTEX; i ++) {
			for (int j = 0; j < VERTEX; j ++) {
				
				shortRoute[i][j] = metrix[i][j];
				pathIndic[i][j] = j;
			}
		}
	}
	
	public static void calFloydRoute(int[][] metrix) {
		
		// core is to compare whether path(a, c) > path(a, b) + path(a, c)
		// so need 3 for loops
		for (int i = 0; i < VERTEX; i ++) {
			for (int j = 0; j < VERTEX; j ++) {
				for (int k = 0; k < VERTEX; k ++) {
					
					// 1.the meaning is from j(0...8) to k(0...8),check whether it go through i (0...8)
					// 2.if use "(shortRoute[i][k] > shortRoute[i][j] + shortRoute[j][k]) ", then everytime, there will
					// be repeat of 01, 02, 03... for j(from 0 to 9)
					// 3.cause the idea is to find vertex in between, so fixed comnination of 01,02,03 is not exact the same.
					if (shortRoute[j][k] > shortRoute[j][i] + shortRoute[i][k]) {
						
						shortRoute[j][k] = shortRoute[j][i] + shortRoute[i][k];
						// *** note: can not use pathIndic[i][k] here
						// reason: when init pathIndic[i][j], the value is j, so the value of pathIndic[j][k] and pathIndic[i][k]
						// are both k, so should use pathIndic[j][i] whose value is i
						// means from j to k, there should be a vertex i between them
						pathIndic[j][k] = pathIndic[j][i];
					}
					
					// ***note: different order of i,j,k will generate different result
					/*
					if (shortRoute[i][k] > shortRoute[i][j] + shortRoute[j][k]) {
						
						shortRoute[i][k] = shortRoute[i][j] + shortRoute[j][k];
						pathIndic[i][k] = pathIndic[i][j];
					}
					*/
				}
			}
		}
	}
	
	public static void output() {
		
		System.out.println(" The short routes are: ");
		
		for (int i = 0; i < VERTEX; i ++) {
			for (int j = 0; j < VERTEX; j ++) {
				
				System.out.print(shortRoute[i][j] + "\t");
			}
			
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		System.out.println(" The prefix data are are: ");
		
		for (int i = 0; i < VERTEX; i ++) {
			for (int j = 0; j < VERTEX; j ++) {
				
				System.out.print(pathIndic[i][j] + "\t");
			}
			
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		// test case, start from 0.
		int[][] metrix = {
			{0, 1, 5, X, X, X, X, X, X},
			{1, 0, 3, 7, 5, X, X, X, X},
			{5, 3, 0, X, 1, 7, X, X, X},
			{X, 7, X, 0, 2, X, 3, X, X},
			{X, 5, 1, 2, 0, 3, 6, 9, X},
			{X, X, 7, X, 3, 0, X, 5, X},
			{X, X, X, 3, 6, X, 0, 2, 7},
			{X, X, X, X, 9, 5, 2, 0, 4},
			{X, X, X, X, X, X, 7, 4, 0},
		};
		
		init(metrix);
		calFloydRoute(metrix);
		output();
	}

}
