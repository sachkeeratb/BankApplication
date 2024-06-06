import java.io.*;

// Kushal Prajapati
public class AccountList {
  private Account[] accounts;
  private int numAccounts;

  public AccountList() {
    accounts = new Account[5];
    numAccounts = 0;
  }

  public void addAccount(char t) {
    if (numAccounts==accounts.length) {
      System.out.println("You have too many accounts. Please delete one of your accounts before adding a new one");
    } else {
      Account a = new Account(t);
      accounts[numAccounts] = a;
      numAccounts++;
    }
  }

  public void addAccount(Account a) {
    if (numAccounts==accounts.length) {
      System.out.println("You have too many accounts. Please delete one of your accounts before adding a new one");
    }
    else{
      accounts[numAccounts] = a;
      numAccounts++;
    }
  }



  public Account getAccount(int index) {
    return accounts[index];
  }

  public int getNumAccounts() {
    return numAccounts;
  }

  public void removeAccount(int index) {
    for (int i = index; i < numAccounts - 1; i++) {
      accounts[i] = accounts[i + 1];
      numAccounts--;
    }
  }
  // Kushal Prajapati and Nimay Desai
  public void printAccounts () {
    System.out.print("[");
    for (int i = 0; i < numAccounts; i++) {
      System.out.print(accounts[i]);
      if (accounts[i+1] != null) {
        System.out.print(",");
      }
      System.out.println("]");
    }
  }

  // Nimay Desai
  public String toString() {
    String currentStr = "[";
    for (int i = 0; i < numAccounts; i++) {
      currentStr += accounts[i].toString();
      if (accounts[i+1] != null) {
        currentStr += ",";
      }
    }


    currentStr += "]";
    return currentStr;
  }

  public void addAccount(char accountType, double balance) {
    Account a = new Account(accountType, balance);
    if (numAccounts == accounts.length) {
      Account[] temp = new Account[accounts.length * 2];
      for (int i = 0; i < accounts.length; i++)
        temp[i] = accounts[i];
      accounts = temp;
    }
    accounts[numAccounts++] = a;
  }
}

