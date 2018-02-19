
/**
 * 1. Dijkstra algorithm is a greedy pattern based algorithm. Every route in the shortest route is the
 * shortest route.Therefore, it is useful to use arrays to keep tracking the route from start point to
 * every node in the graph.
 * 2. Core is to compare path(a, c) and path(a, b) + path(b, c)
 * 
 * 1). use an array to track adjacent vertex instead of using original matrix.
 * 2) in greedy algorithm, pay attention to the "core set" that is ought to change.
 * 
 * @author jingjiejiang 
 * @history
 * 1. Dec 2, 2016
 * First implementation
 * 
 */
public class DijkstraAlgorithm {

	public static final int X = 65535;
	// number of vertex in a graph
	public static final int VERTEX = 9;
	
	// the array is to store whether the shortest path for a node has been found
	public static int[] vexChecker = new int[VERTEX];
	// keep tracking the prefix of each node in the shortest path
	public static int[] pathIndic = new int[VERTEX];
	// the shortest path from start point to every other node in the graph
	public static int[] shortRoute = new int[VERTEX];
	
	public static void initialisation() {
		
		for (int i = 0; i < VERTEX; i ++) {
			
			vexChecker[i] = 0;
			pathIndic[i] = 0;
			shortRoute[i] = 0;
		}
	}
	
	public static void output() {
		
		for (int i = 0; i < VERTEX; i ++) {
			System.out.print(shortRoute[i] + "\t");
		}
		
		System.out.println();
		
		for (int i = 0; i < VERTEX; i ++) {
			System.out.print(pathIndic[i] + "\t");
		}
		
		System.out.println();
		

		for (int i = 0; i < VERTEX; i ++) {
			System.out.print(vexChecker[i] + "\t");
		}
	}

	public static boolean calDijkstraRoute (int startPoint, int[][] metrix) {
		
		boolean hasConnect = false;
		// store the lastest node that is add it to vexChecker
		int lastNode = startPoint;
		
		// initialise the vexChecker, path and shortRoute for start node
		for (int i = 0; i < metrix[startPoint].length; i ++) {
			
			if (metrix[startPoint][i] != X) {
				hasConnect = true;
			}
			shortRoute[i] = metrix[startPoint][i];
		}
		
		if (false == hasConnect) {
			return false;
		}
		
		vexChecker[startPoint] = 1;
		
		// from each node, updating the path, shortRoute and vexChecker
		for (int nodeCount = 1; nodeCount < VERTEX; nodeCount ++) {
			
			int minWeight = X;
			// This is to get the vertex which has min weight that connects to the lastNode;
			// then update lastNode and minWeight, and vexChecker
			for (int i = 0; i < VERTEX; i ++) {
			    if ( (vexChecker[i] == 0) && (shortRoute[i] < minWeight) ) {
			    	lastNode = i;
			    	minWeight = shortRoute[i];
			    }
			}
			
			vexChecker[lastNode] = 1;
			
			// adjust shortRoute[] according to lastNode
			for (int i = 0; i < VERTEX; i ++) {
				if ( (vexChecker[i] == 0) &&
						(minWeight + metrix[lastNode][i] < shortRoute[i]) ) {
					
					shortRoute[i] = minWeight  + metrix[lastNode][i];
					pathIndic[i] = lastNode;
				}
			}
		}
		
		return true;
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
		
		initialisation();
		
		if (true == calDijkstraRoute(0, metrix)) {
			output();
		}
		else {
			System.out.println("The start vertex does not connect to any other vertex in the graph.");
		}
	}

}
