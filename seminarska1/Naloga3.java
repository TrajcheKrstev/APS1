import java.io.*;

public class Naloga3 {
    public static void main(String[] args) throws Exception {
        //final long startTime = System.nanoTime();

        LinkedList seznam = new LinkedList();

        BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String st;
        st = br.readLine();
        String[] stevili = st.split(",");
        for (int i = 0; i < stevili.length; i++) {
            seznam.addLast(Integer.parseInt(stevili[i]));
        }
        st = br.readLine();
        int stOperacij = Integer.parseInt(st);
        for (int i = 0; i < stOperacij; i++) {
            st = br.readLine();
            String[] operacije = st.split(",");

            if (operacije[0].charAt(0) == 'o') {
                seznam.ohrani(operacije[1].charAt(0), Integer.parseInt(operacije[2]));
                out.write(seznam.izpis());
            } else if (operacije[0].charAt(0) == 'p') {
                seznam.preslikaj(operacije[1].charAt(0), Integer.parseInt(operacije[2]));
                out.write(seznam.izpis());
            } else {
                seznam.zdruzi(operacije[1].charAt(0));
                out.write(seznam.izpis());
            }
        }
        out.close();
        br.close();

        // final long duration = System.nanoTime() - startTime;
        // System.out.println(duration);
    }

}

class LinkedList {
    LinkedListElement first;
    LinkedListElement last;

    LinkedList() {
        makenull();
    }

    public void makenull() {
        first = new LinkedListElement(0, null);
        last = null;
    }

    public String izpis() {
        LinkedListElement temp = first.next;
        StringBuilder izpis = new StringBuilder();
        while (temp != null) {
            izpis.append(temp.element);
            if (temp.next != null)
                izpis.append(",");
            temp = temp.next;
        }
        izpis.append("\r\n");
        return izpis.toString();
    }

    public void addLast(int obj) {
        LinkedListElement nov = new LinkedListElement(obj, null);
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

    int lengthRek(LinkedListElement element) {
        if (element == null)
            return 0;
        return 1 + lengthRek(element.next);
    }

    public void preslikaj(char op, int val) {
        LinkedListElement temp = first.next;
        while (temp != null) {
            if (op == '+') {
                temp.element += val;
            } else if (op == '*') {
                temp.element *= val;
            }
            temp = temp.next;
        }
    }

    public void ohrani(char op, int val) {
        LinkedListElement temp = first;
        while (temp.next != null) {
            if (op == '>') {
                if (temp.next.element <= val) {
                    temp.next = temp.next.next;
                    temp = first;
                    continue;
                }
            } else if (op == '<') {
                if (temp.next.element >= val) {
                    temp.next = temp.next.next;
                    temp = first;
                    continue;
                }
            } else if (op == '=') {
                if (temp.next.element != val) {
                    temp.next = temp.next.next;
                    temp = first;
                    continue;
                }
            }

            temp = temp.next;
        }
    }

    public void zdruzi(char op) {
        int vrednost = first.next.element;
        LinkedListElement temp = first.next.next;
        while (temp != null) {
            if (op == '+') {
                vrednost += temp.element;
            } else if (op == '*') {
                vrednost *= temp.element;
            }
            temp = temp.next;
        }
        LinkedListElement el = new LinkedListElement(vrednost);
        first.next = el;
        last = el.next;
    }
}

class LinkedListElement {
    int element;
    LinkedListElement next;

    LinkedListElement(int obj) {
        element = obj;
        next = null;
    }

    LinkedListElement(int obj, LinkedListElement nxt) {
        element = obj;
        next = nxt;
    }
}