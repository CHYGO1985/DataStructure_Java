import java.util.LinkedList;
import java.util.List;


public class KruskalAlgorithm {
	
	public static final int VER_NUM = 9;
	public static final int X = 65535;

	public static class Edge {
		
		int begin;
		int end;
		int weitht;

		public Edge (int begin, int end, int weight) {
			
			this.begin = begin;
			this.end = end;
			this.weitht = weight;
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
	
	public static void InsertSort(List<Edge> list) {
		
		// Insert sort: pick ele from unsorted part, and then insert it into sorted part
		for (int i = 1; i < list.size(); i ++) {
			
			int comparePos = i;
			
			for (int j = i - 1; j >= 0; j --) {
				
				int sorted = list.get(j).weitht;
				int unsorted = list.get(comparePos).weitht; 
				if (unsorted < sorted) {
					
					list.get(comparePos).weitht = sorted;
					list.get(j).weitht = unsorted;
					comparePos = j;
				}
			}
		}
	}
	
	public static List<Edge> buildSortedEdgeList (int[][] metrix) {
		
		// init an Edge list
		List<Edge> list = initEdgeList(metrix);
		
		// sort the list
		InsertSort(list);
		
		/*
		// test 
		System.out.println("length: " + list.size());
		
		for (Edge e: list) {
			
			System.out.println(e.begin + " " + e.end + " " + e.weitht);
			System.out.println();
		}
		*/
		
		return list;
	}
	
	public static int findAvailSlot(int[] parent, int vertex) {
		
		while (parent[vertex] > 0) {
			vertex = parent[vertex];
		}
		
		return vertex;
	}
	
	public static void KruskalSpanTree(List<Edge> list) {
		
		// The array is used to check whether there is a closed cycle in the sub tree.
	    // *** this array is the most tricky part, the use of parent array avoid many
		// trouble of dividing sub trees and finding closed cycle in a sub tree.
		// *** the principle is that: the parent array itself presents sub trees, so if begin and end
		// finds the same slot, that means they are in the same sub tree and there is a closed cycle.
		// We do not need to worry about the vertex in another subtree, as that is available.
		int[] parent = new int[VER_NUM];
		
		for (int i = 0; i < VER_NUM; i ++) {
			
			parent[i] = 0;
		}
		
		for (int i = 0; i < list.size(); i ++) {
			
			int oldBegin = list.get(i).begin;
			int oldEnd = list.get(i).end; 
			int newBegin = findAvailSlot(parent, oldBegin); 
			int newEnd = findAvailSlot(parent, oldEnd);
			
			// begin != end, means there is not any closed cycle in the sub tree.
			if (newBegin != newEnd) {
				
				parent[newBegin] = newEnd;
				System.out.println( oldBegin + " " + oldEnd + " "
						+ list.get(i).weitht);
			}
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
		
		List<Edge> list = buildSortedEdgeList(metrix);
		KruskalSpanTree(list);

	}

}
