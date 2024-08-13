import java.util.*;
import java.io.*;

public class Naloga9 {
	static int index = 1;
	public static void main(String[] args) throws Exception {
		//final long startTime = System.nanoTime();

		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
		String st = br.readLine();
		int n = Integer.parseInt(st);
		// branje vhoda
		String[][] mreza = new String[n][n];
		for (int i = 0; i < n; i++) {
            st = br.readLine();
            String[] temp = st.split(",");
            for (int j = 0; j < n; j++) {
                mreza[i][j] = temp[j];
            }
        }
		// koren drevesa
		Drvo drevo = new Drvo(0, min(mreza), max(mreza), 0);
		//dodaj sinove
		dodaj(mreza, drevo, 0, 1);
		st = br.readLine();
		// preveri za vsako gladino
		int gladine = Integer.parseInt(st);
		for(int i = 0; i < gladine; i++){
			st = br.readLine();
			int gladina = Integer.parseInt(st);
			out.write(drevo.print(gladina, n));
		}
		out.close();
		br.close();

		//final long duration = System.nanoTime() - startTime;
		//System.out.println(duration);
	}

	// rekurzivna metoda za dodajanje
	public static void dodaj(String[][] mreza, Drvo drevo, int oce, int level){
		// ce mrezo ne moremo vec deliti smo prisli do konca
		if(mreza.length == 1) return;
		int min = 0;
		int max = 0;
		int iZac = 0;
		int jZac = 0;
		int size = mreza.length / 2;
		// za vsak sin naredimo novo mrezo
		String[][] prvaMreza = new String[size][size];
		String[][] drugaMreza = new String[size][size];
		String[][] tretjaMreza = new String[size][size];
		String[][] cetrtaMreza = new String[size][size];
		// prvi sin
		// kopiramo elemente v novo mrezo
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				prvaMreza[i][j] = mreza[i + iZac][j + jZac];
			}
		}
		min = min(prvaMreza);
		max = max(prvaMreza);
		drevo.dodajSina(oce, index, min, max, level);
		index++;
		if(min != max) dodaj(prvaMreza, drevo, index - 1, level + 1);
		
		//drugi sin		
		jZac = size;
		// kopiramo elemente v novo mrezo
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				drugaMreza[i][j] = mreza[i + iZac][j + jZac];
			}
		}
		min = min(drugaMreza);
		max = max(drugaMreza);
		drevo.dodajSina(oce, index, min, max, level);
		index++;
		if(min != max) dodaj(drugaMreza, drevo, index - 1, level + 1);
		
		//tretji sin
		jZac = 0;
		iZac = size;
		// kopiramo elemente v novo mrezo		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				tretjaMreza[i][j] = mreza[i + iZac][j + jZac];
			}
		}
		min = min(tretjaMreza);
		max = max(tretjaMreza);
		drevo.dodajSina(oce, index, min, max, level);
		index++;
		if(min != max) dodaj(tretjaMreza, drevo, index - 1, level + 1);
		
		//cetrti sin		
		jZac = size;
		iZac = size;
		// kopiramo elemente v novo mrezo
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				cetrtaMreza[i][j] = mreza[i + iZac][j + jZac];
			}
		}
		min = min(cetrtaMreza);
		max = max(cetrtaMreza);
		drevo.dodajSina(oce, index, min, max, level);
		index++;
		if(min != max) dodaj(cetrtaMreza, drevo, index - 1, level + 1);
	}
	
	public static int max(String[][] mreza){
		int max = 0;
		for(int i = 0; i < mreza.length; i++){
			for(int j = 0; j < mreza.length; j++){
				if(Integer.parseInt(mreza[i][j]) > max) max = Integer.parseInt(mreza[i][j]);
			}
		}
		return max;
	}

	public static int min(String[][] mreza){
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < mreza.length; i++){
			for(int j = 0; j < mreza.length; j++){
				if(Integer.parseInt(mreza[i][j]) < min) min = Integer.parseInt(mreza[i][j]);
			}
		}
		return min;
	}
}

class Node 
{
    int id;
	int min;
    int max;
	int level;

    Node oce;
	Node brat;
	Node sin;
	
	public Node(int id, int min, int max, Node oce, int level) 
	{
        this.id = id;
		this.min = min;
		this.max = max;
        this.oce = oce;
		this.level = level;
	}
}

class Drvo 
{
	Node koren;
	
	public Drvo(int id, int min, int max, int level) 
	{
		koren = new Node(id, min, max, null, level);
	}

	String print(int gladina, int size)
    {
		StringBuilder izpis = new StringBuilder();
		int nodesVisited = 0;
		int points = 0;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(koren);

		// BFS
        while (!queue.isEmpty()) {
			Node tempNode = queue.poll();
			nodesVisited++;
			if(tempNode.min > gladina){ // ce so vsi tocki nad mejo, nobena ni potopljena
				continue;
			}else if(tempNode.max <= gladina){ // ce so vsi tocki pod mejo, potopljeni so vsi
				int depth = tempNode.level;
				if(depth == 0){ // koren -> vse tocke potopljeni
					points += size * size;
				}else { // st. tock v tem segmentu izracunamo po spodnji formuli:
					points += (size / (2 << depth - 1)) * (size / (2 << depth - 1));
				}
				continue;
			}else {
				Node sin = tempNode.sin;
				//dodaj vsi sinovi v queue
				while (sin != null) 
				{
					queue.add(sin);
					sin = sin.brat;
				}
				
			}
        }
		izpis.append(points);
		izpis.append(",");
		izpis.append(nodesVisited);
		izpis.append("\r\n");
		return izpis.toString();
    }
	
	//Pomozna metoda: vrne kazalec na vozlisce, ki vsebuje vozlisce s podanim id-jem in 
	//se nahaja v poddrevesu s korenom 'v'
	private Node poisci(int id, Node v) {
		if (v.id == id) return v;
		else {
			Node sin = v.sin;
			Node r;
			
			while (sin != null) {
				r = poisci(id, sin);
				
				if (r != null)
					return r;
				else
					sin = sin.brat;
			}
				
			return null;
		}
	}
	
	//Metoda doda sina podanemu ocetu
	public boolean dodajSina(int oce, int sin, int min, int max, int level) 
	{
		Node v = poisci(oce, koren);
		
		if (v != null)
		{
			Node s = new Node(sin, min, max, v, level);
			s.brat = v.sin;
			v.sin = s;
			
			return true;
		}
		else
			return false;
	}
}
