import java.util.*;
import java.io.*;

public class Naloga8 {
	public static void main(String[] args) throws Exception {
		//final long startTime = System.nanoTime();

		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
		String st = br.readLine();
		int n = Integer.parseInt(st);
		Graph g = new Graph(n * 2);
		StringBuilder izpisi = new StringBuilder();
		for(int i = 0; i < n; i++){
			String[] edge = br.readLine().split(",");
			int u = Integer.parseInt(edge[0]);
			int v = Integer.parseInt(edge[1]);
			if(!g.isReachable(u, v)){
				g.addEdge(u, v);
			} else {
				izpisi.append(u);
				izpisi.append(",");
				izpisi.append(v);
				izpisi.append("\r\n");
			}
		}
		out.write(izpisi.toString());
		out.close();
		br.close();
		//final long duration = System.nanoTime() - startTime;
		//System.out.println(duration);
	}
}

class Graph {
    int V;
    ArrayList<ArrayList<Integer>> adj;

    public Graph(int V){
        this.V = V;
        adj = new ArrayList<>();
        for(int i=0;i<V;i++)
            adj.add(new ArrayList<>());
    }

    void addEdge(int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v);
    }

   
    boolean isReachable(int s, int d) {
        if (s == d)
            return true;
        // nobeno vozlisce ni obiskano
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
        Queue<Integer> queue = new LinkedList<>();
        // trenutno vozlisce dodamo v vrsto
        visited[s] = true;
        queue.add(s);
        // dokler vrsta ni prazna
        // za prvi element v vrsto preveri vse njegove sosede
        // ce niso ze obiskani dodaj v vrsto 
        while (!queue.isEmpty()) {
            s = queue.remove();
            for (int i=0; i< adj.get(s).size(); i++) {
                if (adj.get(s).get(i) == d) return true;

                if (!visited[adj.get(s).get(i)]) {
                    visited[adj.get(s).get(i)] = true;
                    queue.add(adj.get(s).get(i));
                }
            }
        }
        return false;
    }
}