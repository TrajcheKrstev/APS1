import java.io.*;;

public class Naloga4 {
    public static void main(String[] args) throws Exception {
        //final long startTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        String st = br.readLine();
        int T = Integer.parseInt(st);
        st = br.readLine();
        String[] Lv = st.split(",");
        st = br.readLine();
        String[] Lt = st.split(",");
        st = br.readLine();
        String[] Ls = st.split(",");
        st = br.readLine();
        String[] Lh = st.split(",");
        st = br.readLine();
        String[] Lg = st.split(",");
        Blagajna[] blagajne = new Blagajna[Lv.length];
        for (int i = 0; i < Lv.length; i++) {
            Blagajna blagajna = new Blagajna(i + 1, Integer.parseInt(Lv[i]), new Vrsta());
            blagajne[i] = blagajna;
        }
        int casNovegaKupca = Integer.parseInt(Lt[0]);
        int indexZaLt = 0;
        int indexZaLs = 0;
        int indexZaLh = 0;
        int indexZaLg = 0;
        int IDkupca = 1;
        Seznam kupciVTrgovini = new Seznam();
        Seznam[] uspesnoKoncani = new Seznam[Lv.length];
        for (int i = 0; i < Lv.length; i++) {
            uspesnoKoncani[i] = new Seznam();
        }
        Seznam neVstopile = new Seznam();
        // T korakov simulacije
        for (int i = 1; i <= T; i++) {
            // kupec zapusti blagajno
            for (int j = 0; j < blagajne.length; j++) {
                if (!blagajne[j].vrsta.isEmpty() && (blagajne[j].vrsta.front.element.zacelSkeniranje
                        + blagajne[j].vrsta.front.element.S * blagajne[j].V) == i) {
                    uspesnoKoncani[j].addLast(blagajne[j].vrsta.front.element);
                    blagajne[j].vrsta.remove();
                    if (!blagajne[j].vrsta.isEmpty())
                        blagajne[j].vrsta.front.element.zacelSkeniranje = i;
                }
            }
            // kupec se postavi v cakalano vrsto
            kupciVTrgovini.sortList();
            SeznamElement kupec = kupciVTrgovini.first.next;
            int j = 0;
            while (kupec != null) {
                if (kupec.element.zacelNabiranje + kupec.element.H * kupec.element.S == i) {
                    if (blagajne[najkrajsaVrsta(blagajne)].vrsta.length() == 0) {
                        kupec.element.zacelSkeniranje = i;
                    }
                    blagajne[najkrajsaVrsta(blagajne)].vrsta.add(kupec.element);
                    kupciVTrgovini.deleteNth(j);
                    j = 0;
                    kupec = kupciVTrgovini.first.next;
                    continue;
                }
                j++;
                kupec = kupec.next;
            }
            // kreiramo kupec
            if (i == casNovegaKupca) {
                Kupec novKupec = new Kupec(IDkupca, Integer.parseInt(Ls[indexZaLs]), Integer.parseInt(Lh[indexZaLh]),
                        Integer.parseInt(Lg[indexZaLg]));
                IDkupca++;
                indexZaLt = (indexZaLt + 1) % Lt.length;
                casNovegaKupca += Integer.parseInt(Lt[indexZaLt]);
                indexZaLs = (indexZaLs + 1) % Ls.length;
                indexZaLh = (indexZaLh + 1) % Lh.length;
                indexZaLg = (indexZaLg + 1) % Lg.length;
                if (blagajne[najkrajsaVrsta(blagajne)].vrsta.length() <= novKupec.G) {
                    novKupec.zacelNabiranje = i;
                    kupciVTrgovini.addLast(novKupec);
                } else {
                    neVstopile.addLast(novKupec);
                }
            }

        }
        StringBuilder izhod = new StringBuilder();
        for (int i = 0; i < uspesnoKoncani.length; i++) {
            izhod.append(uspesnoKoncani[i].izpis());
        }
        if (neVstopile.lengthRek() > 0) {
            izhod.append(neVstopile.izpis());
        } else
            izhod.append("0\r\n");
        out.write(izhod.toString());
        out.close();
        br.close();

        // final long duration = System.nanoTime() - startTime;
        // System.out.println(duration);
    }

    public static int najkrajsaVrsta(Blagajna[] blagajne) {
        int min = blagajne[0].vrsta.length();
        int minIndex = 0;
        for (int i = 1; i < blagajne.length; i++) {
            if (blagajne[i].vrsta.length() < min) {
                min = blagajne[i].vrsta.length();
                minIndex = i;
            }
        }
        return minIndex;
    }

}

class Kupec {
    int ID;
    int S;
    int H;
    int G;
    int zacelNabiranje;
    int zacelSkeniranje;

    Kupec(int ID, int S, int H, int G) {
        this.ID = ID;
        this.S = S;
        this.H = H;
        this.G = G;
        this.zacelSkeniranje = -1;
    }
}

class Blagajna {
    int index;
    int V;
    Vrsta vrsta;

    Blagajna(int index, int v, Vrsta vrsta) {
        this.index = index;
        this.V = v;
        this.vrsta = vrsta;
    }
}

class Vrsta {
    VrstaElement front;
    VrstaElement rear;

    Vrsta() {
        makenull();
    }

    void makenull() {
        front = null;
        rear = null;
    }

    int length() {
        VrstaElement temp = front;
        int counter = 0;
        while (temp != null) {
            counter++;
            temp = temp.next;
        }

        return counter;
    }

    boolean isEmpty() {
        if (front == null)
            return true;
        return false;
    }

    void add(Kupec novEl) {
        VrstaElement node = new VrstaElement(novEl);
        if (rear == null) {
            front = node;
            rear = front;
        } else {
            rear.next = node;
            rear = rear.next;
        }
    }

    Kupec remove() {
        Kupec element = null;
        if (front != null) {
            element = front.element;
            front = front.next;
        }
        if (front == null)
            rear = null;
        return element;
    }

    String izpis() {
        VrstaElement temp = front;
        StringBuilder izpis = new StringBuilder();
        while (temp != null) {
            izpis.append(temp.element.ID);
            temp = temp.next;
        }
        izpis.append("\r\n");
        return izpis.toString();
    }
}

class VrstaElement {
    Kupec element;
    VrstaElement next;

    VrstaElement(Kupec obj) {
        element = obj;
        next = null;
    }

    VrstaElement(Kupec obj, VrstaElement nxt) {
        element = obj;
        next = nxt;
    }
}

class Seznam {
    SeznamElement first;
    SeznamElement last;

    Seznam() {
        makenull();
    }

    public void makenull() {
        first = new SeznamElement(null, null);
        last = null;
    }

    public String izpis() {
        SeznamElement temp = first.next;
        StringBuilder izpis = new StringBuilder();
        while (temp != null) {
            izpis.append(temp.element.ID);
            if (temp.next != null)
                izpis.append(",");
            temp = temp.next;
        }
        izpis.append("\r\n");
        return izpis.toString();
    }

    public void addLast(Kupec obj) {
        SeznamElement nov = new SeznamElement(obj, null);
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

    void sortList() {
        SeznamElement current = first.next, index = null;
        Kupec temp;

        if (first.next == null) {
            return;
        } else {
            while (current != null) {
                index = current.next;
                while (index != null) {
                    if (current.element.ID > index.element.ID) {
                        temp = current.element;
                        current.element = index.element;
                        index.element = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
    }
}

class SeznamElement {
    Kupec element;
    SeznamElement next;

    SeznamElement(Kupec obj) {
        element = obj;
        next = null;
    }

    SeznamElement(Kupec obj, SeznamElement nxt) {
        element = obj;
        next = nxt;
    }
}