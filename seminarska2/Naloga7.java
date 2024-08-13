import java.util.*;
import java.io.*;

public class Naloga7 {
    public static void main(String[] args) throws Exception {
        final long startTime = System.nanoTime();


        BufferedReader br = new BufferedReader(new FileReader(args[0]));
		BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
		String st = br.readLine();
		int N = Integer.parseInt(st);
        String[][] linije = new String[N][];
        for(int i = 0; i < N; i++){
            st = br.readLine();
            linije[i] = st.split(",");
        }
        st = br.readLine();
        String[] AB = st.split(",");
        DirectedGraph graf = new DirectedGraph();
        ArrayList<GraphVertex> nodes = new ArrayList<>();
        for(int i = 0; i < N; i++){
            for(int j = 0; j < linije[i].length; j++){
                GraphVertex node = new GraphVertex(linije[i][j]);
                graf.insertVertex(node);
                if(!nodes.contains(node)) nodes.add(node);
                if(i > 0){
                    graf.insertEdge(nodes.get(i - 1), node);
                }
                //System.out.print(linije[i][j]);
            }
            //System.out.println();
        }
        graf.print();

        br.close();
        out.close();

        final long duration = System.nanoTime() - startTime;
		System.out.println(duration);
    }
}

class GraphVertex
{
	Object value;
	GraphEdge firstEdge;
	GraphVertex nextVertex;
		
	public GraphVertex(Object val)
	{
		value = val;
		firstEdge = null;
		nextVertex = null;
	}
	
	public String toString()
	{
		return value.toString();
	}
}

class GraphEdge
{
	GraphVertex endVertex;
	GraphEdge nextEdge;
		
	public GraphEdge(GraphVertex eVertex, GraphEdge nEdge)
	{
		endVertex = eVertex;
		nextEdge = nEdge;
	}
}
	

class DirectedGraph {

	protected GraphVertex fVertex;
	
	public void makenull()
	{
		fVertex = null;
	}
	
	public void insertVertex(GraphVertex v)
	{
		v.nextVertex = fVertex;
		fVertex = v;
	}
	
	public void insertEdge(GraphVertex v1, GraphVertex v2)
	{
		GraphEdge newEdge = new GraphEdge(v2, v1.firstEdge);
		v1.firstEdge = newEdge;
	}
	
	public GraphVertex firstVertex()
	{
		return fVertex;
	}
	
	public GraphVertex nextVertex(GraphVertex v)
	{
		return v.nextVertex;
	}
	
	public GraphEdge firstEdge(GraphVertex v)
	{
		return v.firstEdge;
	}
	
	public GraphEdge nextEdge(GraphVertex v, GraphEdge e)
	{
		return e.nextEdge;
	}
	
	public GraphVertex endPoint(GraphEdge e)
	{
		return e.endVertex;
	}
	
	public void print()
	{
		for (GraphVertex v = firstVertex(); v != null; v = nextVertex(v)) 
		{
			System.out.print(v + ": ");
			for (GraphEdge e = firstEdge(v); e != null; e = nextEdge(v, e))
				System.out.print(endPoint(e) + ", ");
			System.out.println();
		}
	}
}
