import java.io.*;

// Nimay Desai
// This is the main linked list of the program which stores all the clients
public class ClientList {
  // Field
  private Node head; // The head node


  // The node class
  class Node {
    // Fields
    Client client; // This hold the client object
    Node link; // This holds the next object

    // Constructor
    public Node(Client c, Node l) {
      client = c;
      link = l;
    }
  }


  // Constructors
  public ClientList() {
    // Create a list
    head = null;
  }
  public ClientList(Client client) {
    // Create a list starting with a client
    head = new Node(client, null);
  }
  public ClientList(Client[] clients) {
    // This creates a client list using an array of clients
    head = new Node(clients[0], null); // Set the head
    Node current = head; // Hold the current node

    // Iterate through the array
    for (int i = 1; i < clients.length; i++) {
      // Create a new node and put it into the list
      current.link = new Node(clients[i], null);
      current = current.link;
    }
  }


  // Accessor method for the head
  public Node getHead() {
    return head;
  }


  // Mutator methods

  // Nimay Desai
  public void addToList(Client item) {
    // Adds a new item to the list
    // Takes in an item which represents the new client added
    // ALl elements are copied from item instead of ID which is retrieved

    if (head == null) {
      head = new Node(item, null);
      head.client.putID(1);
      return;
    }
    Node temp = head;
    while (temp.link != null) {
      temp = temp.link;
    }
    temp.link = new Node(item, null);
    temp.link.client.putID(temp.client.getID() + 1);
  }

  // Nimay Desai
  public void delete(Client item) {

    Node current = head;
    Node previous = null;
    boolean found = false;
    while (current != null) {
      if (item == current.client) {
        found = true;
        break;
      } else {
        previous = current;
        current = current.link;
      }
    }
    if (found) {
      if (current == head) {
        head = head.link;
      } else {
        previous.link = current.link;
        Node temp = current.link;
        while (temp != null) {
          temp.client.putID(temp.client.getID() - 1);
          temp = temp.link;
        }
      }
    }
  }


  // Searches

  // Sachkeerat Brar
  public int searchByID(int ID) {
    // This method searches the client list to find the index of the client who matches the given ID
    // Binary search

    int top = this.length() - 1; // Store the highest index of the sublist
    int bottom = 0; // Store the lowest index of the sublist

    // Return -1 if the ID given is invalid
    if ((ID < 1) || (ID > top))
      return -1;

    // Go through the list
    while (bottom <= top) {
      int mid = (bottom + top) / 2; // Store the middle index

      // If we have found the client, return its index
      if (((findNodeByIndex(mid).client).getID()) == ID)
        return mid;
      // Move the subarray up
      else if (bottom <= mid)
        bottom++;
      // Move the subarray down
      else
        top++;
    }

    // Return -1 if the client was not found
    return -1;
  }

  // Nimay Desai
  public int searchByName(String name) {
    int top = length() - 1; // The top of the subarray
    int bottom = 0; // The bottom of the subarray


    while (bottom <= top) {
      int mid = (bottom + top) / 2; // Calculate the middle value

      // If the client is found, return
      if (((findNodeByIndex(mid).client).getName()).equals(name)) {
        return mid;
      }
      // Move the subarray up
      else if (bottom <= mid) {
        bottom++;
      }
      // Otherwise, move the subarray down
      else {
        top++;
      }
    }

    // Return -1 if the name is not used by any user
    return -1;
  }

  // Sachkeerat Brar
  public Node findNodeByIndex(int i) {
    // This method finds the node which corresponds to the index given

    // If the index is invalid, a null Node is returned
    if ((i < 0) || (i >= length())) {
      System.out.println("Invalid Index: No node found.");
      return null;
    }

    Node node = head; // Store the Node

    // Go through the list until the index is found
    for (int c = 0; node != null && c <= i; c++)
      node = node.link; // Go to the next Node

    // Return the Node
    return node;
  }


  public static ClientList fromString(String data) {
    int prevIdx = 0; // Start from the beginning of the string
    int nextIdx = data.indexOf('(', prevIdx); // Find the index of the first '('
    String str;
    ClientList clients = new ClientList();
    while (nextIdx != -1) {
      prevIdx = nextIdx + 2; // Move to the first character after '('
      nextIdx = data.indexOf(')', prevIdx); // Find the index of the next ')'
      str = data.substring(prevIdx, nextIdx); // Extract the substring between '(' and ')'
      clients.addToList(Client.fromString(str));
      prevIdx = nextIdx + 1; // Move to the next character after ')'
      nextIdx = data.indexOf('(', prevIdx); // Find the index of the next '('
    }
    return clients;
  }


  // Instance methods
  // Nimay Desai
  public static ClientList loadClientList() throws IOException {
    FileReader fr = new FileReader(Values.getClientInfoLocation());
    BufferedReader br = new BufferedReader(fr);

    String data = br.readLine();

    if (data == null) {
      System.out.println("No data. Please make sure you have added a client list");
      return null;
    }
    return ClientList.fromString(data);
  }

  // Kushal Prajapati
  public void storeClientList() throws IOException {
      // This method writes the client list to the file
      // This method is called when the program is closed

      FileWriter fw = new FileWriter(Values.getClientInfoLocation());
      PrintWriter pw = new PrintWriter(fw);

      pw.println(Values.convert(this.toString()));

      // Flush the writer
      pw.flush();
  }

  // Sachkeerat Brar
  public int length() {
    // This method counts the amount of clients and returns it

    // Store the length
    int len = 0;

    // Go through and count each node
    for (Node temp = head; temp != null; temp = temp.link)
      len++;

    // Return the length
    return len;
  }

  // Nimay Desai
  public ClientList ageGreaterThan(int age) {
    // This method returns a client list which stores people with ages greater than the given age
    Node temp = head;
    ClientList clients = null;

    while (temp != null) {
      if (temp.client.getAge() > age) {
        if (clients == null)
          clients = new ClientList(temp.client);
        else
          clients.addToList(temp.client);
      }
      temp = temp.link;
    }
    return clients;
  }

  // Nimay Desai
  public ClientList ageLessThan(int age) {
    // This method returns a client list which stores people with ages less than the given age
    Node temp = head;
    ClientList clients = null;


    while (temp != null) {
      if (temp.client.getAge() < age) {
        if (clients == null) {
          clients = new ClientList(temp.client);
        } else {
          clients.addToList(temp.client);
        }
      }
      temp = temp.link;
    }
    return clients;
  }

  // Nimay Desai
  public ClientList ageEqualTo(int age) {
    // This method returns a client list which stores people with ages less than the given age

    Node temp = head;
    ClientList clients = null;


    while (temp != null) {
      if (temp.client.getAge() == age) {
        if (clients == null) {
          clients = new ClientList(temp.client);
        } else {
          clients.addToList(temp.client);
        }
      }
      temp = temp.link;
    }
    return clients;
  }

  public void printList() {
    // This method goes through the list and outputs each client
    Node temp = head;

    while (temp != null) {
      temp.client.printInfo();
      temp = temp.link;
    }
  }

  // Nimay Desai
  public String toString() {
    String currentStr = "{ ";
    Node temp = head;
    while (temp != null) {
      currentStr += temp.client.toString();

      temp = temp.link;

      if (temp != null) {
        currentStr += ", ";
      }
    }
    currentStr += " }";
    return currentStr;
  }

  // Nimay Desai
  public void writeToFile() throws IOException {
    FileWriter fw = new FileWriter(Values.getClientInfoLocation());
    PrintWriter pw = new PrintWriter(fw);

    pw.println(this);
    pw.flush();
  }

  // TODO: make a from string
}
