// Sachkeerat Brar
public class TransactionHistory {
  Node head; // The head of the list

  public class Node {
    Node link;
    private double transaction; // The transaction as a double

    public Node(double newTransaction, Node newLink) {
      transaction = newTransaction;
      link = newLink;
    }

    public Node(Node newLink){
      transaction = 0;
      link = newLink;
    }

    public double getTransaction() {
      return transaction;
    }
    public String toString() {
      return "$" + transaction;
    }
  }

  // Store a new transaction
  public void newTransaction(double newTransaction) {
    // Round the transaction to two decimal places
    newTransaction = (double) Math.round(newTransaction * 100) / 100;

    // Create a node and put it at the front of the list
    Node newNode = new Node(newTransaction, null);
    newNode.link = head;
    head = newNode;
  }

  public double calculateBalance() {
    double sum = 0;
    for(Node temp = head; temp != null; temp = temp.link)
      sum += temp.transaction;
    return sum;
  }

  public void arrayToList(double[] values) {
    Node temp = head;
    if(temp == null)
      temp = new Node(null);
    temp.transaction = values[0];
    for(int i = 0; i < values.length; i++) {
      if(temp.link == null)
        temp.link = new Node(null);
      temp.transaction = values[i];
      temp = temp.link;
    }
  }

  // Nimay Desai
  public String toString() {
    // This method was created in order to have the transaction history in a simple and concise way

    // If there are no transactions
    if(head == null) {
      return "[ ]";
    }
    // If there is only one transaction
    if(head.link == null) {
      return "[ $" + head.transaction + " ]";
    }

    // Build a string of all the values of the transactions from most recent to oldest
    String info = "[ ";
    Node temp;
    for(temp = head; temp.link != null; temp = temp.link)
      info += "$" + temp.transaction + ", ";
    info += "$" + temp.transaction + " ]";

    return info; // Return the list as a string
  }
}
