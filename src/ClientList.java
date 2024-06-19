// Imported objects used
import java.io.IOException;

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
    // All elements are copied from item instead of ID which is retrieved

    if (head == null) { // IF h
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
    // This method removes a client from the list and shifts the IDs of the clients after it
    // Takes in a client called item which represents the client the user wants to delete
    Node current = head; // Sets the current node equal to the head
    Node previous = null; // Sets the previous value equal to null at the start
    boolean found = false; // Represents if the item is found

    while (current != null && !found) { // Are we not at the end of list
      if (item == current.client) // If the item is equal to the current value
        found = true; // We have found the object

      else { // Else shift previous and current by one
        previous = current;
        current = current.link;
      }
    }

    // If the item is found
    if (found) {
      // If the current is the first element
      if (current == head) {
        head = head.link; // Bypass the first element
        Node temp = head;
        while (temp != null) {
          temp.client.putID(temp.client.getID() - 1);
          temp = temp.link;
        }
      } else {
        // Else bypass previous
        previous.link = current.link;
        Node temp = previous.link;
        // Go through each value and make sure the ID is correct
        while (temp != null) {
          temp.client.putID(temp.client.getID() - 1);
          temp = temp.link;
        }
      }
    }
  }


  // Searches
  
  // Sachkeerat Brar
  public Client searchByID(int ID) {
    // This method searches the client list to find the client who matches the given ID
    // Takes in an integer caleld ID which represents the ID the user wants to search
    // Binary search

    int top = this.length(); // Store the highest index of the sublist
    int bottom = 1; // Store the lowest index of the sublist

    // Return null if the ID given is invalid
    if ((ID < 1) || (ID > top))
      return null;

    // Go through the list
    while (bottom <= top) {
      int mid = (bottom + top) / 2; // Store the middle index

      // If we have found the client, return its index
      if (((findNodeByIndex(mid - 1).client.getID())) == ID)
        return findNodeByIndex(mid - 1).client;
        // Move the subarray up
      else if (bottom < ID)
        bottom++;
        // Move the subarray down
      else
        top--;
    }

    // Return null if the client was not found
    return null;
  }
  // Nimay Desai
  public Client searchByName(String name) {
    // This function searches the client list by the naem
    // It takes in a String name which represents the name the user wants to search for
    // This function returns a Client which is the client the user's name == the given name
    // If no client return null
    int top = length(); // The top of the subarray
    int bottom = 1; // The bottom of the subarray

    while (bottom <= top) {
      int mid = (bottom + top) / 2; // Calculate the middle value

      // If the client is found, return
      if (((findNodeByIndex(mid - 1).client).getName()).equals(name))
        return findNodeByIndex(mid - 1).client;
        // Move the subarray up
      else if (bottom < mid)
        bottom++;
        // Otherwise, move the subarray down
      else
        top--;
    }

    // Return null if the name is not used by any user
    return null;
  }
  // Sachkeerat Brar
  public Node findNodeByIndex(int i) {
    // This method finds the node which corresponds to the index given
    // This function takes an integer i which represents the index the user wants
    // This function returns a Node which represents the Node found

    // If the index is invalid, a null Node is returned
    if ((i < 0) || (i >= this.length())) {
      System.out.println("Invalid Index: No node found.");
      return null;
    }

    Node temp; // Store the Node
    int c = 0;

    // Go through the list until the index is found
    for (temp = head; temp.link != null && c < i; c++)
      temp = temp.link; // Go to the next Node

    // Return the Node
    return temp;
  }


  // Instance methods

  /// Sachkeerat Brar
  public void storeClientList() throws IOException {
    // This method writes the client list to the file
    // This method is called when the program is closed
    // This function does not take anything as everything is retrieved form this
    // This function does not return anything as everything is written to the fil
    Values.writeToClients(Values.convert(this.toString()));
  }
  // Sachkeerat Brar
  public int length() {
    // This function returns an integer length which represents the length of the string
    // It does not take in anything as it retrieved from the function the method is called on
    // This method counts the amount of clients and returns it

    // Store the length
    int len = 0;

    // Go through and count each node
    for (Node temp = head; temp != null; temp = temp.link)
      len++;

    // Return the length
    return len;
  }
  // Sachkeerat Brar
  public Node lastNode() {
    // This method returns the last node in the list
    Node temp;

    // Go through the list until the last node is found
    for (temp = head; temp != null; temp = temp.link)
      if (temp.link == null)
        return temp;

    // Otherwise return null
    return null;
  }
  // Nimay Desai
  public ClientList ageGreaterThan(int age) {
    // This method returns a client list which stores people with ages greater than the given age
    // This function takes in an integer age which represents the age of the user
    // THis functoin returns all the clients greater than that age
    Node temp = head;
    ClientList clients = null;

    // Until the last element
    while (temp != null) {
      if (temp.client.getAge() > age) { // Age is greater than the age provided
        if (clients == null) // If no client list
          clients = new ClientList(temp.client); // Create a new clientlist
        else
          clients.addToList(temp.client); // Else add it to the list
      }
      temp = temp.link;
    }
    return clients; // Return the ist
  }
  // Nimay Desai
  public ClientList ageLessThan(int age) {
    // This method returns a client list which stores people with ages less than the given age
    // This function returns all the clients less than the age 
    // THis function takes in an integer age which represents the age the user wants
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
    // This function returns all the clients which the age equal to the give age
    // This function takes in an integer age which represents the age the user wants

    Node temp = head; // Create temp at first element
    ClientList clients = null; // Create list of filtered clients


    while (temp != null) { // Go through until the end of the list
      if (temp.client.getAge() == age) { // If the clients age is equal to the age
        if (clients == null) { // If no client
          clients = new ClientList(temp.client); // Create a new client list
        } else {
          clients.addToList(temp.client);
        }
      }
      temp = temp.link;
    }
    return clients; // Return the l;ist of clients
  }
  // Nimay Desai
  public void display() {
    // This method goes through the list and outputs each client
    Node temp = head;

    while (temp != null) {
      temp.client.display();
      System.out.println();
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


  // Static methods

  // Nimay Desai
  public static ClientList fromString(String data) {
    // This method converts a client list from a string into a client list
    // This method takes in a string data which represents the original string
    // This method returns a ClientList which represents the converted clientlist
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
  // Kushal Prajapati
  public static ClientList loadClientList() throws IOException {
    // Load the Clientlist from the location and if there is no client list return null
    String data = Values.getClientInfo();

    if (data == null) {
      System.out.println("No data. Please make sure you have added a client list");
      return null;
    }

    data = Values.convert(data);
    return ClientList.fromString(data);
  }
}
