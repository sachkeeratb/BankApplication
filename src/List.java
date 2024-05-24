import java.util.Date;

// Nimasy Desai
public class List {
  // Fields
  private Node head;

  // Node class
  class Node {
    // Fields
    private User client;
    Node link;

    // Constructor
    public Node(User i, Node l) {
      client = i;
      link = l;
    }
  }
}
