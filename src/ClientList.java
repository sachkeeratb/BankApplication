import java.io.*;

// Nimay Desai
public class ClientList {
  private Node head;

  class Node {
    Client client;
    Node link;

    public Node(Client c, Node l) {
      client = c;
      link = l;
    }
  }

  public ClientList(Client[] items) {
    // Nimay Desai
    head = new Node(items[0], null);
    Node curentItm = head;
    for (int i = 1; i < items.length; i++) {
      curentItm.link = new Node(items[i], null);
      curentItm = curentItm.link;
    }
  }

  public ClientList(Client item) {
    // Nimay Desai
    head = new Node(item, null);
  }

  // Converts an array of clients to a linked list of clients easily and effectively for multiple clients

  public ClientList() {
    // Nimay Desai
    head = new Node(new Client(), null);
  }

  public static ClientList toClientList() throws IOException {
    FileReader fr = new FileReader(Values.getClientInfoLocation());
    BufferedReader br = new BufferedReader(fr);

    String data = br.readLine();
    if (data == null) {
      System.out.println("No data. Please make sure you have added a client list");
      return null;
    }
    System.out.println(data);
    ClientList clients = new ClientList();
    String str = data.substring(data.indexOf('[') + 2, data.indexOf(','));

    System.out.println(str);
    while (str != null) {

    }
    return null;

  }

  public Node getHead() {
    return head;
  }

  // Adds a new item to the list
  // Takes in an item which represents the new client added
  // ALl elements are copied from item instead of ID which is retrieved
  public void addToList(Client item) {
    // Nimay Desai
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

  public void delete(Client item) {
    // Nimay Desai
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

  public int length() {
    // Sachkeerat Brar
    // This method counts the amount of clients and returns it

    // Store the length
    int len = 0;

    // Go through and count each node
    for (Node temp = head; temp != null; temp = temp.link)
      len++;

    // Return the length
    return len;
  }

  public Node findNodeByIndex(int i) {
    // Sachkeerat Brar
    // This method finds the node which corresponds to the index given

    // If the index is invalid, a null Node is returned
    if ((i < 0) || (i >= this.length()))
      return null;

    Node node = head; // Store the Node

    // Go through the list until the index is found
    for (int c = 0; node != null && c <= i; c++)
      node = node.link; // Go to the next Node

    // Return the Node
    return node;
  }

  // Searches
  public int searchByName(String name) {
    // Nimay Desai
    int top = length() - 1;
    int bottom = 0;
    while (bottom <= top) {
      int mid = (bottom + top) / 2;
      if (((findNodeByIndex(mid).client).getName()).equals(name)) {
        return mid;
      } else if (bottom <= mid) {
        bottom++;
      } else {
        top++;
      }
    }

    return -1;
  }

  public int searchByID(int ID) {
    // Sachkeerat Brar
    // This method searches the client list to find the index of the client who matches the given ID

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

  public ClientList ageGreaterThan(int age) {
    // Nimay Desai
    Node temp = head;
    ClientList clients = null;


    while (temp != null) {
      if (temp.client.getAge() > age) {
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

  public ClientList ageLessThan(int age) {
    // Nimay Desai
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

  public ClientList ageEqualTo(int age) {
    // Nimay Desai
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

  public void printlist() {
    Node temp = head;
    while (temp != null) {
      temp.client.printInfo();
      temp = temp.link;
    }
  }

  public String toString() {
    // Nimay Desai
    String currentStr = "[";
    Node temp = head;
    while (temp != null) {
      currentStr += temp.client.getID() + "." + temp.client.getDOB() + "." + temp.client.getAge() + ".";
      // currentStr += temp.client.getAccounts().toString();
      temp = temp.link;
      if (temp != null) {
        currentStr += ",";
      }
    }
    currentStr += "]";
    return currentStr;
  }

  public void WriteToFile(String location) throws IOException {
    // Nimay Desai
    FileWriter fw = new FileWriter(location);
    PrintWriter pw = new PrintWriter(fw);
    pw.println(this);
    pw.flush();
  }
}
