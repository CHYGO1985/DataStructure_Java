import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 1. Insert Sort. Finish within the collection that is waiting for sorting.
 * Insert sort: get element from unsorted part one by one, then insert into sorted part.
 * ref: http://courses.cs.washington.edu/courses/cse373/01wi/slides/Measurement/sld010.htm
 * 
 * @author jingjiejiang
 * @history Dec 4, 2016
 * 
 */
public class InsertSort {

	public static final int VER_NUM = 9;
	public static final int X = 65535;

	public static class Edge {
		
		int begin;
		int end;
		int weight;

		public Edge (int begin, int end, int weight) {
			
			this.begin = begin;
			this.end = end;
			this.weight = weight;
		}
	}
	
	public static List<Edge> initEdgeList (int[][] metrix) {
		
		// Problem: for non directed graph, to check repeated: begin and end ( it may
		// happend that newBegin = oldEnd & newEnd = oldBegin)
	    // Solution: for undirected graph: only check half of the metrix

		int startPoint = 1;
		List<Edge> list = new LinkedList<Edge>();
		
		for (int i = 0; i < metrix.length; i ++) {
			
			for (int j = startPoint; j < metrix[i].length; j ++) {
				
				if (metrix[i][j] != X) {
						
					Edge edge = new Edge(i, j, metrix[i][j]);
					list.add(edge);
				}
			}
			startPoint ++;
		}
		
		return list;
	}
	
	public static void insertSort(List<Edge> list) {
		
		// Insert sort: pick ele from unsorted part, and then insert it into sorted part
		for (int i = 1; i < list.size(); i ++) {
			
			int comparePos = i;
			
			for (int j = i - 1; j >= 0; j --) {
				
				int sorted = list.get(j).weight;
				int unsorted = list.get(comparePos).weight; 
				if (unsorted < sorted) {
					
					list.get(comparePos).weight = sorted;
					list.get(j).weight = unsorted;
					comparePos = j;
				}
			}
		}
	}
	
	public static void buildSortedEdgeList (int[][] metrix) {
		
		// init an Edge list
		List<Edge> list = initEdgeList(metrix);
		
		// sort the list
		insertSort(list);
		
		// test 
		System.out.println("length: " + list.size());
		
		for (Edge e: list) {
			
			System.out.println(e.begin + " " + e.end + " " + e.weight);
			System.out.println();
		}
	}
	
	public static void main(String[] args) {

		// test case, start from 0.
		int[][] metrix = {
			{0, 10, X, X, X, 11, X, X, X},
			{10, 0, 18, X, X, X, 16, X, 12},
			{X, 18, 0, 22, X, X, X, X, 8},
			{X, X, 22, 0, 20, X, 24, 16, 21},
			{X, X, X, 20, 0, 26, X, 7, X},
			{11, X, X, X, 26, 0, 17, X, X},
			{X, 16, X, X, X, 17, 0, 19, X},
			{X, X, X, 16, 7, X, 19, 0, X},
			{X, 12, 8, 21, X, X, X, X, 0},
		};
		
		initEdgeList(metrix);
		buildSortedEdgeList(metrix);
	}

}
