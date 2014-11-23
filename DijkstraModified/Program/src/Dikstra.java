import java.util.ArrayList;

public class Dikstra {
	// array indices represent the nodes (or vertices). // actually index 0 is the VERTEX # 1.
	private double[][] graph; // values represent edge weights.
	private boolean[] visited; // true - already visited, false - not yet visited.
	public double[] distance; 
	private int numNodes;
	public int[] predecessor; // used in printing out path.
	int source;
	ArrayList<Integer> Q;

	public Dikstra(double[][] graph) {
		this.graph = graph;
		this.numNodes = graph.length;
		distance = new double[numNodes];
		visited = new boolean[numNodes];
		predecessor = new int[numNodes];
		for(int i = 0; i < graph.length; i++) {
			distance[i] = 999999999; // let that define infinity for now..
			visited[i] = false;
			predecessor[i] = -1; // source de1`finately has no predecessor, and other vertices predecessors aren't known. (WE don't even know if there exists a connected path to a vertex v yet).
		}
	}
	// give the starting vertex.
	public void runAlgorithm(int source) {
		this.source = source;
		distance[source] = 0;
		Q = new ArrayList<Integer>();
		Q.add(source);

		Integer x;
		while(Q.size() != 0) {	
			x = nodeWithMinDist(); // get node in set Q with smallest distance.
			Q.remove(x); // remove from the Q.
			visited[x] = true; // add to visited set.
			//for every vertex y such that y is a neighbor of x and is unvisited.
			for(int y = 0; (y < numNodes); y++) {
				if((visited[y] == false) && (graph[x][y] != 0)) { // aside: if graph[x][y] = 0 , its distance will be infinity or (999999) forever.
					if (distance[x] + graph[x][y] < distance[y]) {
						distance[y] = distance[x] + graph[x][y];
						predecessor[y] = x;
					}
					if (!inQ(y))
						Q.add(y);
				}
			}
		}
	}

	private boolean inQ(int y) {
		for(Integer node: Q)
			if (node == y)
				return true;
		return false;
	}

	private int nodeWithMinDist() {
		int minNode = Q.get(0); // let minNode denote node with the minimum distance.
		for(int node: Q)
			if(distance[node] < distance[minNode])
				minNode = node;
		return minNode;
	}
	// print the shortest path from source vertix to vertex B - recursive method. 
	private void printPredecessors(int b) {
		if(predecessor[b] != -1) {
			printPredecessors(predecessor[b]); // call method recursively to print predecessors of the predccessor of b.
			System.out.print(" --> " + predecessor[b]); // I'll go ahead and print my predecessor out.
		}
	}
	public void printPath(int b) {
		if(visited[b]) {
			System.out.println("Shortest Path: from Node " + source + " to Node " + b + " is through: ");
			printPredecessors(b);
			System.out.println(" --> " + b);
			System.out.println("");
		} else System.out.println(" there is no such path");
	}
}
