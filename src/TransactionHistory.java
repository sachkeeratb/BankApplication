// Sachkeerat Brar
// Every bank should know what their clients have done, which is why we have a transaction history
public class TransactionHistory {
  // Field
  Node head; // The head of the list


  // The node object
  public class Node {
    Node link;
    private double transaction; // The transaction as a double

    // Constructor
    public Node(double newTransaction, Node newLink) {
      transaction = newTransaction;
      link = newLink;
    }

    
    // Accessor method
    public double getTransaction() {
      return transaction;
    }


    // Instance method
    public String toString() {
      return "$" + transaction;
    }
  }


  // Mutators
  public void newTransaction(double newTransaction) {
    // Round the transaction to two decimal places
    newTransaction = (double) Math.round(newTransaction * 100.0) / 100.0;

    // Create a node and put it at the front of the list
    Node newNode = new Node(newTransaction, null);
    newNode.link = head;
    head = newNode;
  }


  // Instance methods

  public void arrayToList(double[] values) {
    // This method turns an array of transactions into a list
    Node temp = head;

    // If the head is null, create a new node
    if (temp == null)
      temp = new Node(0, null);

    temp.transaction = values[0];

    // Go through the array
    for (int i = 0; i < values.length - 1; i++) {
      // If the next node is null, create a node
      if (temp.link == null)
        temp.link = new Node(0, null);

      // Put in the value
      temp.transaction = values[i];

      // Go to the next node
      temp = temp.link;
    }

    // Set the last value so that there are no empty transaction values
    temp.transaction = values[values.length - 1];
  }
  public void copy(TransactionHistory other) {
    // This method takes another history object and copies it's values into this history object
    Node otherCurrent = other.head; // The other's head
    Node newHead = new Node(otherCurrent.transaction, null); // The new head
    Node newCurrent = newHead; // The new current for the new head

    // Go through the other list
    while (otherCurrent.link != null) {
      // Create a new node with the value copied and move to the next node
      newCurrent.link = new Node(otherCurrent.transaction, null);
      newCurrent = newCurrent.link;
      otherCurrent = otherCurrent.link;
    }

    // Set the head to the new head
    head = newHead;
  }
  public void display() {
    // This method outputs the transaction history in a clean way

    int count = 1;
    for (Node temp = head; temp != null; temp = temp.link) {
      System.out.println("Transaction " + count + ": ");
      if (temp.transaction > 0)
        System.out.println("$" + temp.transaction);
      else if (temp.transaction < 0)
        System.out.println("-$" + Math.abs(temp.transaction));

      count++;
    }
  }
  // Nimay Desai
  public String toString() {
    // This method was created in order to have the transaction history in a simple and concise way

    // If there are no transactions
    if (head == null) {
      return "> <";
    }
    // If there is only one transaction
    if (head.link == null) {
      return "> " + head.transaction + " <";
    }

    // Build a string of all the values of the transactions from most recent to oldest
    String info = "> ";
    Node temp;
    for (temp = head; temp.link != null; temp = temp.link)
      info += temp.transaction + ", ";
    info += temp.transaction + " <";

    return info; // Return the list as a string
  }


  // Static methods

  // Sachkeerat Brar
  public static double calculateBalance(TransactionHistory history) {
    // This method calculates the balance of the history
    double sum = 0; // Store the sum

    if (history == null) {
      history = new TransactionHistory();
      return 0;
    }

    // Go through the list, collecting the transactions
    for (Node temp = history.head; temp != null; temp = temp.link)
      sum += temp.transaction;

    // Return the balance
    return sum;
  }
  // Nimay Desai
  public static TransactionHistory fromString(String data) {
    // This method takes in a string and converts it into a list

    TransactionHistory newHistory = new TransactionHistory();

    if (data.equals("> <"))
      return newHistory;
    if (!data.contains(",")) {
      newHistory.newTransaction(Double.parseDouble(data.substring(2, data.indexOf("<") - 1)));
      return newHistory;
    }

    newHistory.newTransaction(Double.parseDouble(data.substring(2, data.indexOf(","))));

    for (int i = data.indexOf(",") + 2; i < data.lastIndexOf(","); i = data.indexOf(",", i + 1) + 2) {
      newHistory.newTransaction(Double.parseDouble(data.substring(i, data.indexOf(",", i))));
    }
    newHistory.newTransaction(Double.parseDouble(data.substring(data.lastIndexOf(",") + 2, data.indexOf("<") - 1)));
    return newHistory;
  }
}
