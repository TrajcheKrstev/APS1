import java.io.*;

public class Naloga1 {

    static int visina, sirina;
    static int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws Exception {
        // time ran
        //final long startTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        String st = br.readLine();
        String[] velikost = st.split(",");
        visina = Integer.parseInt(velikost[0]);
        sirina = Integer.parseInt(velikost[1]);
        char[][] mreza = new char[visina][sirina];
        for (int i = 0; i < visina; i++) {
            st = br.readLine();
            String[] temp = st.split(",");
            for (int j = 0; j < sirina; j++) {
                mreza[i][j] = temp[j].charAt(0);
            }
        }
        int stBesed;
        st = br.readLine();
        stBesed = Integer.parseInt(st);
        String[] words = new String[stBesed];
        Seznam[] moznosti = new Seznam[words.length];
        for (int i = 0; i < stBesed; i++) {
            words[i] = br.readLine();
        }
        for (int i = 0; i < moznosti.length; i++) {
            Seznam seznam = new Seznam();
            moznosti[i] = seznam;
        }

        sort(words, words.length);

        resi(mreza, 0, words, moznosti);
        StringBuilder izpisi = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            izpisi.append(words[i]);
            izpisi.append(",");
            izpisi.append(moznosti[i].izpis());
        }
        out.write(izpisi.toString());

        out.close();
        br.close();

        // time ran
        // final long duration = System.nanoTime() - startTime;
        // System.out.println(duration);
    }

    public static boolean resi(char[][] mreza, int indexBesede, String[] words, Seznam[] moznosti) {
        if (indexBesede >= words.length)
            return true;
        if (moznosti[indexBesede].first.next == null)
            poisciMoznosti(mreza, words[indexBesede], indexBesede, moznosti);
        if (moznosti[indexBesede].first.next == null) {
            return false;
        }

        int vrstica = moznosti[indexBesede].first.next.y1;
        int stolpec = moznosti[indexBesede].first.next.x1;

        for (int i = 0; i < words[indexBesede].length(); i++) {
            if (mreza[vrstica][stolpec] == '0') {
                return false;
            }
            mreza[vrstica][stolpec] = '0';
            vrstica += x[moznosti[indexBesede].first.next.dir];
            stolpec += y[moznosti[indexBesede].first.next.dir];
        }
        if (resi(mreza, indexBesede + 1, words, moznosti))
            return true;
        else if (moznosti[indexBesede].lengthRek() == 1) {
            int dolzina = 0;
            vrstica = moznosti[indexBesede].first.next.y1;
            stolpec = moznosti[indexBesede].first.next.x1;
            for (int i = 0; i < words[indexBesede].length(); i++) {
                mreza[vrstica][stolpec] = words[indexBesede].charAt(dolzina);
                dolzina++;
                vrstica += x[moznosti[indexBesede].first.next.dir];
                stolpec += y[moznosti[indexBesede].first.next.dir];
            }
            moznosti[indexBesede].deleteNth(0);
            return false;
        } else {
            int dolzina = 0;
            vrstica = moznosti[indexBesede].first.next.y1;
            stolpec = moznosti[indexBesede].first.next.x1;
            for (int i = 0; i < words[indexBesede].length(); i++) {
                mreza[vrstica][stolpec] = words[indexBesede].charAt(dolzina);
                dolzina++;
                vrstica += x[moznosti[indexBesede].first.next.dir];
                stolpec += y[moznosti[indexBesede].first.next.dir];
            }
            moznosti[indexBesede].deleteNth(0);
            return resi(mreza, indexBesede, words, moznosti);
        }
    }

    public static void poisciMoznosti(char[][] mreza, String word, int index, Seznam[] moznosti) {
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                if (mreza[i][j] == word.charAt(0)) {
                    poisciBesedo(mreza, i, j, word, index, moznosti);
                }
            }
        }
    }

    public static void poisciBesedo(char[][] mreza, int vrstica, int stolpec, String word, int index,
            Seznam[] moznosti) {
        int len = word.length();
        for (int dir = 0; dir < 8; dir++) {
            int k, rd = vrstica + x[dir], cd = stolpec + y[dir];
            for (k = 1; k < len; k++) {
                if (rd >= visina || rd < 0 || cd >= sirina || cd < 0)
                    break;
                if (mreza[rd][cd] != word.charAt(k))
                    break;
                rd += x[dir];
                cd += y[dir];
            }
            if (k == len) {
                moznosti[index].addLast(stolpec, vrstica, cd - y[dir], rd - x[dir], dir);
            }
        }
    }

    public static void sort(String[] s, int n) {
        for (int i = 1; i < n; i++) {
            String temp = s[i];
            int j = i - 1;
            while (j >= 0 && temp.length() > s[j].length()) {
                s[j + 1] = s[j];
                j--;
            }
            s[j + 1] = temp;
        }
    }
}

class Seznam {
    SeznamElement first;
    SeznamElement last;

    Seznam() {
        makenull();
    }

    public void makenull() {
        first = new SeznamElement(-1, -1, -1, -1, -1);
        last = null;
    }

    public String izpis() {
        SeznamElement temp = first.next;
        StringBuilder izpis = new StringBuilder();

        izpis.append(temp.y1);
        izpis.append(",");
        izpis.append(temp.x1);
        izpis.append(",");
        izpis.append(temp.y2);
        izpis.append(",");
        izpis.append(temp.x2);
        izpis.append("\r\n");
        return izpis.toString();
    }

    public void addLast(int x1, int y1, int x2, int y2, int dir) {
        SeznamElement nov = new SeznamElement(x1, y1, x2, y2, dir);
        if (last == null) {
            first.next = nov;
            last = first;
        } else {
            last.next.next = nov;
            last = last.next;
        }
    }

    int lengthRek() {
        return lengthRek(first.next);
    }

    int lengthRek(SeznamElement element) {
        if (element == null)
            return 0;
        return 1 + lengthRek(element.next);
    }

    boolean deleteNth(int n) {
        SeznamElement el, prev_el;
        prev_el = null;
        el = first;
        for (int i = 0; i < n; i++) {
            prev_el = el;
            el = el.next;
            if (el == null)
                return false;
        }
        if (el.next != null) {
            if (last == el.next)
                last = el;
            else if (last == el)
                last = prev_el;

            el.next = el.next.next;

            return true;
        } else
            return false;
    }
}

class SeznamElement {
    int x1;
    int y1;
    int x2;
    int y2;
    int dir;
    SeznamElement next;

    SeznamElement(int x1, int y1, int x2, int y2, int dir) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dir = dir;
        next = null;
    }

    SeznamElement(int x1, int y1, int x2, int y2, int dir, SeznamElement nxt) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dir = dir;
        next = nxt;
    }
}