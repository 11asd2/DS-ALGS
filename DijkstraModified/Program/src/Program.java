import java.util.ArrayList;

public class Program {
	public static void main(String[] args) {
		// adjacency matrix of 4 cities.
		ArrayList<Double[]>[][] graph = new ArrayList[4][4];
		for(int i = 0; i < graph.length; i++)
			for(int j= 0; j < graph.length; j++)
				graph[i][j] = new ArrayList<Double[]>(2);
		 // note index 0 is city 1!
		graph[0][1].add(new Double[]{1.0, 2.0}); // departure 2: arrival 6: from location 0(ie city 1) to location 1 (ie city 2).
		graph[0][1].add(new Double[]{3.0, 6.0});
		graph[0][2].add(new Double[]{2.0, 8.0});
		graph[0][3].add(new Double[]{4.0, 8.0});
		graph[1][2].add(new Double[]{7.0, 9.0});
		graph[1][3].add(new Double[]{3.0, 4.0});
		graph[2][0].add(new Double[]{1.0, 2.0});
		graph[2][1].add(new Double[]{2.0, 4.0});
		graph[2][3].add(new Double[]{1.0, 4.0});
		graph[2][3].add(new Double[]{7.0, 8.0});
		graph[3][0].add(new Double[]{1.0, 3.0});
		graph[3][0].add(new Double[]{6.0, 8.0});
		graph[3][1].add(new Double[]{2.0, 4.0});
		graph[3][2].add(new Double[]{5.0, 6.0});
	
		DikstraLab1 dikstra = new DikstraLab1(graph);
		//System.out.println(dikstra.predecessor[7]);
		dikstra.runAlgorithm(0);
		//System.out.println(dikstra.predecessor[7]);
		dikstra.printPath(2);
		System.out.println(dikstra.arrival[2]);
	}
}