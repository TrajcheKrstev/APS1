import java.io.*;

public class Naloga2 {
    public static void main(String[] args) throws Exception {
        //final long startTime = System.nanoTime();

        File inp = new File(args[0]);
        FileWriter out = new FileWriter(args[1]);
        BufferedReader br = new BufferedReader(new FileReader(inp));
        String st;
        st = br.readLine();
        int n = Integer.parseInt(st);
        st = br.readLine();
        char[] s1 = st.toCharArray();
        int dolzina = s1.length;
        int preostanek = dolzina % 3;
        int trojke = dolzina / 3;
        int dolzinaV2 = trojke / 2 * 3;
        if (trojke % 2 != 0)
            dolzinaV2 += preostanek;
        char[] v2 = new char[dolzinaV2];
        int dolzinaS2 = 0;
        if(dolzina <= 3) dolzinaS2 = dolzina;
        else {
            dolzinaS2 = trojke / 2 * 3 ;
            if(trojke % 2 != 0) dolzinaS2 += 3;
            if(dolzina % 6 < 3) dolzinaS2 += dolzina % 6;
        }
        char[] s2 = new char[dolzinaS2];
        
        for (int iter = 0; iter < n; iter++) {
            //korak 4
            int indexV2 = 0;
            int indexS2 = s2.length - 1;
            for(int i = 0; i < s1.length; i++) {
                s2[indexS2] = s1[i];
                indexS2--;
                
                if (indexV2 < v2.length ) {
                    i++;
                    if(i < s1.length){
                        v2[indexV2] = s1[i];
                        indexV2++;
                    }
                }
            }
            
            //korak 3
            int indeksS2 = s2.length - 1;
            int indeksS1 = 0;
            int indeksV2 = v2.length - 1;
            
            if (s2.length % 6 > v2.length % 6 && s2.length % 3 != 0) {
                int limit = s2.length % 3;
                for (int i = 0; i < limit; i++) {
                    s1[indeksS1] = s2[indeksS2];
                    indeksS1++;
                    indeksS2--;
                }

                for(int k = 0; k < 3; k++){
                    if (indeksV2 >= 0){
                        s1[indeksS1] = v2[indeksV2];
                        indeksS1++;
                        indeksV2--;
                    }
                }
                
            } else if(s2.length == v2.length){
                for(int k = 0; k < 3; k++){
                    if (indeksV2 >= 0){
                        s1[indeksS1] = v2[indeksV2];
                        indeksS1++;
                        indeksV2--;
                    }
                }
            } else if (v2.length % 3 != 0) {
                int limit = v2.length % 3;
                for (int i = 0; i < limit; i++) {
                    s1[indeksS1] = v2[indeksV2];
                    indeksS1++;
                    indeksV2--;
                }
            }
            while(indeksS2 >= 0) {
                for(int k = 0; k < 3; k++){
                    if (indeksS2 >= 0){
                        s1[indeksS1] = s2[indeksS2];
                        indeksS1++;
                        indeksS2--;
                    }
                }
                for(int k = 0; k < 3; k++){
                    if (indeksV2 >= 0){
                        s1[indeksS1] = v2[indeksV2];
                        indeksS1++;
                        indeksV2--;
                    }
                }
            }          
            //korak 1 in 2
            if(s1.length % 2 != 0){
                for(int i = 1; i < s1.length / 2; i+=2){
                    char temp = s1[i];
                    s1[i] = s1[s1.length - i - 1];
                    s1[s1.length - i - 1] = temp;
                    
                }
            }else {
                for(int i = 1; i < s1.length / 2; i+=2){
                    char temp = s1[i];
                    s1[i] = s1[s1.length - i];
                    s1[s1.length - i] = temp;
                }
            }
        }
        
        out.write(s1);
        out.write("\r\n");
        out.close();
        br.close();

        // final long duration = System.nanoTime() - startTime;
        // System.out.println(duration);
    }
}


