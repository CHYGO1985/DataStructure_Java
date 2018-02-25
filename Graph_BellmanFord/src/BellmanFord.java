
/**
 * 
 * From https://www.geeksforgeeks.org/dynamic-programming-set-23-bellman-ford-algorithm/
 * test case:
 * 1) without negative cycle: 0-1:5, 0-2:2, 1-2:-10
 * 2) with negative cycle: 0-1:5, 2-0:2, 1-2:-10
 * 
 * @author jingjiejiang
 * @history
 * 1. Created on Feb 22, 2018
 *
 */
public class BellmanFord {
	
    // A class to represent a weighted edge in graph
    class Edge {
        int src, dest, weight;
        Edge() {
            src = dest = weight = 0;
        }
    };
 
    int V, E;
    Edge edge[];
 
    // Creates a graph with V vertices and E edges
    BellmanFord(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
 
    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm.  The
    // function also detects negative weight cycle
    public void bellmanFord(BellmanFord graph,int src)
    {
        int V = graph.V, E = graph.E;
        int dist[] = new int[V];
 
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i=0; i<V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;
 
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i=1; i<V; ++i)
        {
            for (int j=0; j<E; ++j)
            {
                int u = graph.edge[j].src;
                int v = graph.edge[j].dest;
                int weight = graph.edge[j].weight;
                if (dist[u]!=Integer.MAX_VALUE &&
                    dist[u]+weight<dist[v])
                    dist[v]=dist[u]+weight;
            }
        }
 
        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j=0; j<E; ++j)
        {
            int u = graph.edge[j].src;
            int v = graph.edge[j].dest;
            int weight = graph.edge[j].weight;
            if (dist[u] != Integer.MAX_VALUE &&
                dist[u]+weight < dist[v])
              System.out.println("Graph contains negative weight cycle");
        }
        
        printArr(dist, V);
    }
 
    // A utility function used to print the solution
    void printArr(int dist[], int V)
    {
        System.out.println("Vertex   Distance from Source");
        for (int i=0; i<V; ++i)
            System.out.println(i+"\t\t"+dist[i]);
    }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 int V = 3;  // Number of vertices in graph
	        int E = 3;  // Number of edges in graph
	 
	        BellmanFord graph = new BellmanFord(V, E);
	 
	        // add edge 0-1 (or A-B in above figure)
	        graph.edge[0].src = 0;
	        graph.edge[0].dest = 1;
	        graph.edge[0].weight = 5;
	 
	        // add edge 0-2 (or A-C in above figure)
	        graph.edge[1].src = 0;
	        graph.edge[1].dest = 2;
	        graph.edge[1].weight = 2;
	 
	        // add edge 1-2 (or B-C in above figure)
	        graph.edge[2].src = 1;
	        graph.edge[2].dest = 2;
	        graph.edge[2].weight = -10;
	 
	        graph.bellmanFord(graph, 0);

	}

}

