// Sachkeerat Brar
public class Account {
  private AccountType type;
  private TransactionHistory history;
  private double balance;

  public Account(AccountType t) {
    type = t;
    history = new TransactionHistory();
    balance = 0;
  }

  public void deposit(double amount) {
    if(amount <= 0) {
      System.out.println("Invalid deposit value.");
      return;
    }
    
    history.newTransaction(amount);
    balance += amount;
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
}
