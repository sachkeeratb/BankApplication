// Sachkeerat Brar
public class Account {
  private char type;
  private TransactionHistory history;
  private double balance;

  public Account(char t) {
    type = t;
    history = new TransactionHistory();
    balance = 0;
  }

  public Account() {
    type = 'c';
    history = new TransactionHistory();
    balance = 0;
  }

  public void withdraw(double amount) {
    if(amount > balance) {
      System.out.println("Invalid withdrawl: Inadequate Funds!");
      return;
    }
    if (amount <= 0) {
      System.out.println("Invalid withdraw. Cannot withdraw negative values!");
      return;
    }

    history.newTransaction(-amount);
    balance -= amount;
  }

  public void transfer(double amount, Account other) {
    if(amount > balance) {
      System.out.println("Invalid withdrawal. Inadequate Funds.");
      return;
    }
    if (amount <= 0) {
      System.out.println("Invalid withdrawal. Cannot withdraw negative values!");
      return;
    }

    history.newTransaction(-amount);
    other.history.newTransaction(amount);
    other.balance += amount;
    balance -= amount;
  }

  public void deposit(double amount) {
    if (amount <= 0) {
      System.out.println("Invalid deposit. Cannot deposit negative values!");
      return;
    }

    history.newTransaction(amount);
    balance += amount;
  }

  public String AccountInfo() {
    return "Account Type: " + type + "\nBalance: $" + balance + "\nTransaction History: " + history;
  }
  public AccountType getAccountType(){
    return type;
  }
}
