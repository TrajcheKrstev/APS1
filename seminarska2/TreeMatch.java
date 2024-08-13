import java.util.*;
import java.io.*;

public class TreeMatch {
  // A tree node
  static class Node {
    char data;
    List<Node> children;


    Node(char data) {
      this.data = data;
      children = new ArrayList<>();
    }
  }

 // Returns true if tree A is present in tree B, false otherwise
static boolean isSubtree(Node A, Node B) {
    // Base case: if tree A is null, it is always a subtree of tree B
    System.out.printf("%c\n", B.data);
    if (A == null) {
      return true;
    }
  
    // Base case: if tree B is null, tree A cannot be a subtree of tree B
    if (B == null) {
      return false;
    }
  
    // If the data of the root nodes of A and B match, check if the rest of the trees match
    if (A.data == B.data && matchTree(A, B)) {
      return true;
    }
  
    // If the data of the root nodes do not match, check if A is a subtree of any of the children of B
    for (Node child : B.children) {
      if (isSubtree(A, child)) {
        return true;
      }
    }
  
    return false;
  }
  
  // Returns true if tree A and tree B are identical, false otherwise
  static boolean matchTree(Node A, Node B) {
    // Base case: if both trees are null, they are considered to be a match
    if (A == null && B == null) {
      return true;
    }
  
    // Base case: if one of the trees is null, they cannot be a match
    if (A == null || B == null) {
      return false;
    }
  
    // If the data of the root nodes do not match, the trees cannot be a match
    if (A.data != B.data) {
      return false;
    }
  
    // If the data of the root nodes match, check if the children match
    if (A.children.size() != B.children.size()) {
      return false;
    }
    for (int i = 0; i < A.children.size(); i++) {
      if (!matchTree(A.children.get(i), B.children.get(i))) {
        return false;
      }
    }
  
    return true;
  }
  
// Returns the number of times tree A appears in tree B
static int countMatches(Node A, Node B) {
    // Initialize the count to 0
    int count = 0;
  
    // Use a queue to perform a breadth-first search of tree B
    Queue<Node> queue = new LinkedList<>();
    queue.add(B);
  
    while (!queue.isEmpty()) {
      Node current = queue.poll();
  
      // If tree A is a subtree of the current node, increment the count
      if (isSubtree(A, current)) {
        count++;
      }
  
      // Add the children of the current node to the queue
      queue.addAll(current.children);
    }
  
    return count;
  }

  public static void main(String[] args) throws Exception {
    //final long startTime = System.nanoTime();

    // BufferedReader br = new BufferedReader(new FileReader(args[0]));
    // BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
    // String st = br.readLine();
    // int n = Integer.parseInt(st);
    // for(int i = 0; i < n; i++){

    // }
    

    // out.close();
    // br.close();

    // final long duration = System.nanoTime() - startTime;
    // System.out.println(duration);






     // Create tree A
    Node A = new Node('a');
    // A.children.add(new Node('b'));
    // A.children.add(new Node('a'));

    // Create tree B
    Node B = new Node('a');
    B.children.add(new Node('b'));
    B.children.add(new Node('b'));
    B.children.get(0).children.add(new Node('a'));
    B.children.get(0).children.add(new Node('a'));
    B.children.get(1).children.add(new Node('a'));
    B.children.get(1).children.add(new Node('a'));

    // Find the number of times tree A appears in tree B
    int count = countMatches(A, B);
    System.out.println("Number of matches: " + count);
  }
}
