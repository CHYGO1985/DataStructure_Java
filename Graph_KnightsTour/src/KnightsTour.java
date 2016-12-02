import java.util.HashMap;
import java.util.Map;

/**
 * 1. DFS Algorithm.
 * reference: http://codereview.stackexchange.com/questions/66888/making-backtracking-knights-tours-solution-more-efficient
 * 
 * @author jingjiejiang
 * @history Dec 1, 2016
 * Problem:
 * 1.when design DFS, need a mechanism to go through all possible route for each point: My first design of recursive solution, 
 * when it return to the start point, it just quit the function which means I need a mechanism to let the function go through
 * all the possible route for a point when it fails at one of the 8 possible routes.
 * 2.When fail, in recursive need to go back to previous status? how to handle the step.
 */
public class KnightsTour {
	
	public static final int X_AXIS = 8;
	public static final int Y_AXIS = 8;
	public static final int ROUTE_CHOICE = 8;
	public static final Map<Integer, int[]> routeMap = new HashMap<Integer, int[]>();
	
	public static void initRouteMap(Map<Integer, int[]> route) {
		
		route.put(1, new int[]{2, 1});
		route.put(2, new int[]{2, -1});
		route.put(3, new int[]{-2, 1});
		route.put(4, new int[]{-2, -1});
		route.put(5, new int[]{1, 2});
		route.put(6, new int[]{1, -2});
		route.put(7, new int[]{-1, 2});
		route.put(8, new int[]{-1, -2});

	}
	
	public static boolean isAvailablePoint(int x, int y, int[][] board) {
		
		if ( (x < X_AXIS && x >= 0) && (y < Y_AXIS && y >= 0)
				&& (0 == board[x][y]) ) {
			
			return  true;
		}
		else {
			return false;
		}
	}
	
	public static boolean DFSSearch(int x, int y, int count, int[][] board) {
		
		// check whether all the available cell has been used
		if (count > X_AXIS * Y_AXIS) {
			return true;
		}
		
		// step 2: (DFS ) check available points, if true, then enter into the available points
		// *** use sort of count to let the method go through all the 8 possible routes.
		// 1) switch method http://www.icoolxue.com/play/3161
		// 2) use POINT array
		// http://codereview.stackexchange.com/questions/66888/making-backtracking-knights-tours-solution-more-efficient
		// 3) use Map<Integer, int[]>
		
		for (int i = 1; i <= ROUTE_CHOICE; i ++) {
			
			int[] newCoord = routeMap.get(i);
			
			int newX = x + newCoord[0];
			int newY = y + newCoord[1];
			
			if ( true == isAvailablePoint(newX, newY, board) ) {
					
				board[newX][newY] = count;
				count ++;
				
				// if there is not any available point, return to previous
				// *** I was stuck on how to handling false
				if (true == DFSSearch(newX, newY, count, board)) {
					return true;
				}
				else {
					count --;
					board[newX][newY] = 0;
				}
			}
		}
		
		return false;
	}
	
	public static boolean DFSKnightsTour (int x, int y, int count, int[][] board) {
		
		// step 1: check availability of x and y
		if (x > X_AXIS || x < 0 || y > Y_AXIS || y < 0) {
			return false;
		}
		
		if (true == DFSSearch(x, y, count, board)) {
			printBoard(board);
		}
		else {
			System.out.println("There is not any solution for the coordination.");
		}
			
		return false;
	}

	public static void printBoard(int[][] board) {
		
		for (int i = 0; i < X_AXIS; i ++) {
			for (int j = 0; j < Y_AXIS; j ++) {
				
				System.out.print(board[i][j] + "\t");
				if (j == Y_AXIS - 1) {
					System.out.println();
				}
			}
		}
	}

	public static void main(String[] args) {
		
		int[][] board = new int[X_AXIS][Y_AXIS];
		
		// Initialise the board
		for (int i = 0; i < X_AXIS; i ++) {
			for (int j = 0; j < Y_AXIS; j ++) {
				
				board[i][j] = 0;
			}
		}
		
		initRouteMap(routeMap);
		
		// test case: x = 3, y = 2
		int initX = 3;
		int initY = 2;
		int count = 1;
		board[initX][initY] = count;
		count ++;
		
		DFSKnightsTour(initX, initY, count, board);
	}
}
