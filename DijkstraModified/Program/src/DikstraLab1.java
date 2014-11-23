import java.util.ArrayList;
public class DikstraLab1 {

	// array indices represent the nodes (or vertices). // actually index 0 is the VERTEX # 1.
	private ArrayList<Double[]>[][] graph; // the ArrayList at each index represents the flight (may be multiple) information.
	private boolean[] visited; // true - city already visited, false - city not yet visited.(visited means that it is finalized and we have begun looking at it's neighours).
	public double[] arrival; 
	private int numCities;
	public int[] predecessor; // used in printing out path.
	int source; // starting city.
	ArrayList<Integer> Q; 

	// constructor should take in an 2D array of ArrayList<Double[]>, where each arraylist only consists of double arrays, and those double arrays must be length 2. (arrival and departure).
	public DikstraLab1(ArrayList<Double[]>[][] graph) {
		this.graph = graph;
		this.numCities = graph.length;
		arrival = new double[numCities];
		visited = new boolean[numCities];
		predecessor = new int[numCities];
		for(int i = 0; i < graph.length; i++) {
			arrival[i] = 999999999; // let that define infinity for now..
			visited[i] = false;
			predecessor[i] = -1; // source definately has no predecessor, and other vertices predecessors aren't known. (WE don't even know if there exists a connected path to a vertex v yet).
		}
	}
	// give the starting vertex
	public void runAlgorithm(int source) {
		this.source = source;
		arrival[source] = 0;
		Q = new ArrayList<Integer>();
		Q.add(source);

		Integer x; // or else it ArrayList.remove() method will think x is an index, not the object!.
		while(Q.size() != 0) {	
			x = cityWithEarliestArrival(); // get node in set Q with smallest arrival.
			Q.remove(x); // remove from the Q.
			visited[x] = true; // add to visited set.
			//for every vertex y such that y is a neighbor of x and is unvisited.
			for(int y = 0; y < numCities; y++) {
				if((visited[y] == false) && possibleFlightExists(x,y)) {
					// if the arrival at y through x is earlier than the currently known arrival to y, then update it.
					if (optimalArrivalTime(x, y) < arrival[y]) { // note, by optimal we mean optimal from X to Y (not necessarily the earliest overall time it takes to arrive at Y!).
						arrival[y] = optimalArrivalTime(x, y); // arrival (currently known)
						predecessor[y] = x;
					}
					if (!inQ(y))
						Q.add(y);
				}
			}
		} //************ could've done above method like arrival[x] + additionalTimeTakesToGetToY(x,y) < arrival[y] as well, where additionalTimeTakesToGetToY(x,y) would be slightly modified version of optimalArrivalTime(from x, to Y).
	}
	private boolean possibleFlightExists(int x, int y) {
		double arrivalX = arrival[x]; // we are at node X at time arrivalX.
		ArrayList<Double[]> flights = graph[x][y]; // all scheduled flights from X to Y.
		ArrayList<Double[]> possibleFlights = new ArrayList<Double[]>(); // excludes missed flights.
		for (Double[] flight: flights) {
			// if flight departure from X to Y is earlier than or equal to your arrival at X, you're too late to catch the flight.
			if(flight[0] <= arrivalX)
				continue;
			possibleFlights.add(flight);
		}
		if (possibleFlights.size() == 0)
			return false;
		return true;
	}
	// pass the departure city and the arrival city. (note, as a result of the Dijkstra;s algorithm, the departure city has 'its' earliest arrival time finalized.
	private double optimalArrivalTime(int x, int y) {
		double arrivalX = arrival[x]; // we are at node X at time arrivalX.
		ArrayList<Double[]> flights = graph[x][y]; // all scheduled flights from X to Y.
		ArrayList<Double[]> possibleFlights = new ArrayList<Double[]>(); // excludes missed flights.
		for (Double[] flight: flights) {
			// if flight departure from X to Y is earlier than or equal to your arrival at X, you're too late to catch the flight.
			if(flight[0] <= arrivalX)
				continue;
			possibleFlights.add(flight);
		}
		if (possibleFlights.size() == 0) 
			return 999999; // optimal arrival time is infinite, because there is no POSSIBLE path. (the edge acts as an open circuit)...
		double optimalArrival = possibleFlights.get(0)[1]; // set earliest arrival to something to begin with.
		for (Double[] flight: possibleFlights) {
			if (flight[1] < optimalArrival)
				optimalArrival = flight[1];
		}
		return optimalArrival; // this is the optimal POSSIBLE time to get from node X to node Y (out of the available flights), but doesn't have to be the best possible way(there could be other nodes through which there may be a faster path) to get to Y earliest.
	}
	
	private boolean inQ(int y) {
		for(Integer node: Q)
			if (node == y)
				return true;
		return false;
	}
	
	private int cityWithEarliestArrival() {
		int earliestCity = Q.get(0); // let minNode denote node with the minimum arrival.
		for(int city: Q)
			if(arrival[city] < arrival[earliestCity])
				earliestCity = city;
		return earliestCity;
	}
	// print the shortest path from source vertex to vertex B - recursive method. 
	private void printPredecessors(int b) {
		if(predecessor[b] != -1) {
			printPredecessors(predecessor[b]); // call method recursively to print predecessors of the predeccessor of b.
			System.out.print(" --> ");
			System.out.print(predecessor[b]); // I'll go ahead and print my predecessor out.
		}
	}
	public void printPath(int b) {
		if(visited[b]) {
			System.out.println("Fastest Path: from location " + source + " to location " + b + " is through: ");
			printPredecessors(b);
			System.out.println(" --> " + b);
			System.out.print("\n");
		} else
			System.out.println("There is no such path");
	}
} // can use an interface and anonymous instntiation of it to pass to the print method which decides how output will be printed (city names etc)
// the interface can be an inner class in this class since its only releevent to this class. note however though, the interface is still a top level class and can be used outside of this class independently. (note: being in this class adds some additional benefits like the generic stuff gets applied, (we wont be needing it here though)