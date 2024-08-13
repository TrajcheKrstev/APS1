import java.util.*;
import java.io.*;

public class Naloga6 {

    public static void main(String[] args) throws Exception {
        final long startTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        String st = br.readLine();
        String[] uspesnosti = st.split(",");
        st = br.readLine();
        String[] nm = st.split(",");
        // st. vozlisc
        int v = uspesnosti.length + 1;
 
        // Adjacency list za povezave
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<Integer>());
        }
        // dodamo povezave
        for(int i = 0; i < Integer.parseInt(nm[0]); i++){
            st = br.readLine();
            String[] vozlisca = st.split(",");
            addEdge(adj, Integer.parseInt(vozlisca[0]), Integer.parseInt(vozlisca[1]));
        }
        int[] turisti = new int[v];
        double[] sells = new double[v];
        
        for(int i = 0; i < Integer.parseInt(nm[1]); i++){
            st = br.readLine();
            String[] abc = st.split(",");
            int prvoVozlisce = Integer.parseInt(abc[0]) - 1;
            int drugoVozlisce = Integer.parseInt(abc[1]) - 1;
            turisti = resi(adj, v, prvoVozlisce + 1, drugoVozlisce + 1, Integer.parseInt(abc[2]), turisti);
        }
        // izracunamo koliko cajev je vsaka lokacija prodala
        for(int i = 0; i < uspesnosti.length; i++){
            sells[i + 1] = ((double)turisti[i + 1] * Double.parseDouble(uspesnosti[i]));
        }
        int maxVisitors = 0;
        int maxVisitorsId = 0;
        double maxSold = 0;
        int maxSoldId = 0;
        // poiscemo max sold in max visited vozlisca
        for(int i = 1; i < v; i++){
            if(turisti[i] > maxVisitors){
                maxVisitors = turisti[i];
                maxVisitorsId = i;
            }
            if(sells[i] > maxSold){
                maxSold = sells[i];
                maxSoldId = i;
            }
        }
        StringBuilder izpis = new StringBuilder();
        izpis.append(maxSoldId);
        izpis.append(",");
        izpis.append(maxVisitorsId);
        izpis.append("\r\n");
        out.write(izpis.toString());
        br.close();
        out.close();
        
        final long duration = System.nanoTime() - startTime;
        System.out.println(duration);
    }

    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }

    // poisci vse poti in shrani v paths
    static void poisci(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> parent, int n, int u) {
        // prisli smo do zacetno vozlisce
        if (u == -1) {
            paths.add(new ArrayList<>(path));
            return;
        }
 
        for (int par : parent.get(u)) {
            path.add(u);
            poisci(paths, path, parent, n, par);
            path.remove(path.size()-1);
        }
    }
 
    static void bfs(ArrayList<ArrayList<Integer>> adj, ArrayList<ArrayList<Integer>> parent,
             int n, int start) {
       
        // v dist hranimo najkrajsa pot od zacetka do vseh vozliscah 
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
 
        Queue<Integer> q = new LinkedList<>();
 
        // dodamo zacetno vozlisce v vrsto
        // parent -1 in distance 0
        q.offer(start);
           
        parent.get(start).clear();
        parent.get(start).add(-1);
       
        dist[start] = 0;
 
        // BFS
        while (!q.isEmpty()) {
            int u = q.poll();
            
            for (int v : adj.get(u)) {
                if (dist[v] > dist[u] + 1) {
                    // smo nasli krajso pot
                    // pobrisi parent in dodaj novo vozlisce
                    dist[v] = dist[u] + 1;
                    q.offer(v);
                    parent.get(v).clear();
                    parent.get(v).add(u);
                }
                else if (dist[v] == dist[u] + 1) {
                    // drugi kandidat za najkrajso pot
                    parent.get(v).add(u);
                }
            }
        }
    }
 
    // metoda ki poisce ustrezna pot
    static int[] resi(ArrayList<ArrayList<Integer>> adj, int n, int start, int end, int visitors, int[] turisti){
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<ArrayList<Integer>> parent = new ArrayList<>();
         
        for(int i = 0; i < n; i++){
            parent.add(new ArrayList<>());
        }
       
        bfs(adj, parent, n, start); 
        poisci(paths, path, parent, n, end);
        for (ArrayList<Integer> v : paths) {
            // poti v paths so v obratni vrstni red, zato jih obrnemo
            Collections.reverse(v);
        }
        // za vsako vozlisce v najkrajso pot dodamo stevilo turistov
        ArrayList<Integer> najkrajsoPot = paths.get(sort(paths));
        for (int i = najkrajsoPot.size() - 1; i >= 0; i--) {
            turisti[najkrajsoPot.get(i)] += visitors;
        }
        return turisti;
    }

    // metoda sortira vse najkrajse poti in vraca indeks ki ustreza navodilah
    public static int sort(ArrayList<ArrayList<Integer>> lists) {
        int maxIndex = 0;
        int currentIndex = 0;
        for (int i = 0; i < lists.size(); i++) {
            currentIndex = 0;
            ArrayList<Integer> currentList = lists.get(i);
            boolean isCurrentListGreater = true;
            for (int j = 0; j < currentList.size(); j++) {
                int currentMax = lists.get(maxIndex).get(currentIndex);
                int currentVal = currentList.get(currentIndex);
                if (currentVal < currentMax) {
                    isCurrentListGreater = false;
                    break;
                } else if (currentVal > currentMax) {
                    break;
                }
                currentIndex++;
            }
            if (isCurrentListGreater) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
