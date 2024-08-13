import java.util.*;
import java.io.*;

public class Naloga10 {
    public static void main(String[] args) throws Exception {
        final long startTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
		BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
		String st = br.readLine();
		int n = Integer.parseInt(st);
        String[][] vzorec = new String[n][];
        for(int i = 0; i < n; i++){ 
            st = br.readLine();
            String[] vrstica = st.split(",");
            vzorec[i] = vrstica;
        }
        st = br.readLine();
        int m = Integer.parseInt(st);
        String[][] cilj = new String[m][];
        for(int i = 0; i < m; i++){
            st = br.readLine();
            String[] vrstica = st.split(",");
            cilj[i] = vrstica;
        }
        int stevec = 0;

        for(int i = 0; i < m; i++){
            if(resi(vzorec, cilj, i, 0)) stevec++;
        }
        out.write(String.valueOf(stevec));
        out.write("\r\n");
        out.close();
        br.close();
        final long duration = System.nanoTime() - startTime;
		System.out.println(duration);
    }

    public static boolean resi(String[][] vzorec, String[][] cilj, int indeksCilj, int indeksVzorec) {
        // ce oznaka vozlisca ni ista return false
        if(cilj[indeksCilj][1].charAt(0) != vzorec[indeksVzorec][1].charAt(0)) return false;      
        // ce trenutno vozlisce vzorca nima sinov, in imata isto oznaka return true  
        if(vzorec[indeksVzorec].length == 2) return true;
        // ce imata razlicno stevilo sinov return false
        if(cilj[indeksCilj].length != vzorec[indeksVzorec].length) return false;
        int ujemanja = 0;
        // preveri za vsak sin ce se ujemata
        for(int i = 2; i < vzorec[indeksVzorec].length; i++){
            if(resi(vzorec, cilj, Integer.parseInt(cilj[indeksCilj][i]) - 1, Integer.parseInt(vzorec[indeksVzorec][i]) - 1)) ujemanja++;
        }
        // ce so vsi sinovi isti return true, sicer false
        return ujemanja == vzorec[indeksVzorec].length - 2;
    }
}
