// Kushal Prajapati
// This object was made in order to have a better way to store the user's 5 accounts
// Easier validation, storage, checking, etc.
public class AccountList {
  // Fields
  private Account[] accounts = new Account[5]; // This holds an array of the user's 5 accounts
  private int numAccounts; // This holds the amount of non-empty accounts in the array


  // Constructor
  public AccountList() {
    numAccounts = 0;
  }


  // Accessors
  public Account getAccount(int index) {
    // This method returns an account based on the index.

    // If the index is invalid, inform the user
    if ((index < 0) || (index > 4)) {
      System.out.println("Invalid index. An index is between 0 to 4 (inclusive).");
      return null;
    }

    // Otherwise, return the account
    return accounts[index];
  }
  public int getNumAccounts() {
    return numAccounts;
  }

  // Mutators
  public void addAccount(Account a) {
    // This method takes in an account to add

    // If there are already 5 accounts, inform the user
    if (numAccounts == accounts.length)
      System.out.println("You have too many accounts. Please delete one of your accounts before adding a new one");

      // Otherwise, add the user and increment the amount of non-empty accounts in the array
    else {
      accounts[numAccounts] = a;
      numAccounts++;
    }
  }
  public void removeAccount(int index) {
    // This method is a type of sort which takes in an index and goes through the list until that account is found
    // Then, it sets it to null and moves the accounts remaining left to fill the space

    // Inform the user if the index is invalid
    if ((index < 0) || (index >= numAccounts)) {
      System.out.println("Invalid index. An index must be between 0 to the number of accounts - 1 (inclusive).");
      return;
    }

    // Remove the account
    accounts[index] = null;
    numAccounts--;

    // Go through the list and push the accounts left
    boolean valid = false; // Check if there are any extra empty values
    for (int i = 0; i < accounts.length - 1 && !valid; i++) {
      // Go through the list and move empty elements to the end by moving non-empty elements left
      if ((accounts[i] == null) && (accounts[i + 1] != null)) {
        accounts[i] = new Account();
        accounts[i].copy(accounts[i + 1]);
        accounts[i + 1] = null;
        valid = true; // Set valid to true
      }

      // Go through the array again to verify the list is set
      for (int j = 0; i < numAccounts; j++)
        if (accounts[j] == null) {
          valid = false;
          break;
        }
    }
  }
  
  
  // Instance methods
  
  // Converts an account list into a string
  public String toString() {
    // This method makes the account list into a string

    // If there are no accounts, return a string which represents emptiness
    if (numAccounts == 0)
      return "[ ]";

    // If there is only one account, return its string value surrounded by square brackets
    if (numAccounts == 1)
      return "[ " + accounts[0].toString() + " ]";

    // Otherwise, make a string which resembles an array and return it
    String currentStr = "[ ";
    for (int i = 0; i < numAccounts - 1; i++) {
      currentStr += accounts[i].toString() + ", ";
    }
    currentStr += accounts[numAccounts - 1] + " ]";

    return currentStr;
  }
  // Sachkeerat Brar
  public void display() {
    // This method displays the accounts in the list in a formatted manner
    for (int i = 0; i < numAccounts; i++) {
      System.out.println("Account " + (i + 1) + ": ");
      accounts[i].display();
      System.out.println();
    }
  }


  // Static methods

  // Sachkeerat Brar w/ Nimay Desai & Kushal
  public static AccountList fromString(String data) {
    // A method that takes data in string format into an account list

    // If the data is empty, return null
    if (data.equals("[ ]"))
      return null;

    // Count the accounts
    int count = 1;
    for (int i = 1; i < data.length(); i++)
      if (data.charAt(i) == '[')
        count++;

    // Create the list
    AccountList accList = new AccountList();

    // Get the first account
    String firstData = data.substring(data.indexOf("["), data.indexOf("]") + 1);
    Account firstAcc = Account.fromString(firstData);
    accList.addAccount(firstAcc);

    // Get the rest of the accounts
    if (count > 1) {
      String accData = data.substring(data.indexOf("[", data.indexOf("[") + 1), data.indexOf("]", data.indexOf("]") + 1) + 1);
      Account acc = Account.fromString(accData);
      accList.addAccount(acc);
    }
    if (count > 2) {
      String accData = data.substring(data.indexOf("[" + 2, data.indexOf("[", data.indexOf("[") + 1)), data.indexOf("]", data.indexOf("]", data.indexOf("]") + 1) + 1) + 1);
      Account acc = Account.fromString(accData);
      accList.addAccount(acc);
    }
    if (count > 3) {
      String accData = data.substring(data.indexOf("[" + 2, data.indexOf("[", data.indexOf("[", data.indexOf("[") + 1))), data.indexOf("]", data.indexOf("]", data.indexOf("]", data.indexOf("]") + 1) + 1) + 1) + 1);
      Account acc = Account.fromString(accData);
      accList.addAccount(acc);
    }
    if (count > 4) {
      Account finalAcc = Account.fromString(data.substring(data.lastIndexOf("[") + 2, data.lastIndexOf("]") - 2));
      accList.addAccount(finalAcc);
    }

    return accList;
  }
}
