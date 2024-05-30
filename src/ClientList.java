// Nimay Desai
public class ClientList {
  private Node head;

  class Node {
    Client client;
    Node link;

    public Node (Client c, Node l) {
      client = c;
      link = l;
    }
  }

  // Converts an array of clients to a linked list of clients easily and effetively for multiple clients
  
  public ClientList (Client[] items) {
    head = new Node(items[0], null);
    Node curentItm = head;
    for (int i = 1; i < items.length; i++) {
      curentItm.link = new Node(items[i], null);
      curentItm = curentItm.link;
    }
  }
  
  public ClientList (Client item) {
    head = new Node(item, null);
  }
  
  public ClientList () {
    head = new Node(new Client(), null);
  }



  // Adds a new item to the list
  // Takes in an item which represents the new client added
  // ALl elements are copied from item instead of ID which is retrieved
  public void addToList (Client item) {
    if (head == null) {
      head = new Node(item, null);
      head.client.putID(1);
      return;
    }
    Node temp = head;
    while (temp.link != null ) {
      temp = temp.link;
    }
    temp.link = new Node(item, null);
    temp.link.client.putID(temp.client.getID());
  }

  public void delete (Client item) {
    Node current = head;
    Node previous = null;
    boolean found = false;
    while (current != null) {
      if (item == current.client) {
        found = true;
        break;
      }
      else {
        previous = current;
        current = current.link;
      }
    }
    if (found) {
      if (current == head) {
        head = head.link;
      }
      else {
        previous.link = current.link;
        Node temp = current.link;
        while (temp != null) {
          temp.client.putID(temp.client.getID()-1);
          temp = temp.link;
        }
      }
    }
  }
}
