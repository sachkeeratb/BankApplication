// Sachkeerat Brar
public class Account {
  private char type;
  private TransactionHistory history;
  private double balance;

  public Account(char t) {
    switch(t) {
      case 's':
      case 'c':
        type = t;
        break;
      default:
        System.out.println("A account type must be either 's' or 'c' for a savings or checking accounts, respectfully.\nAccount created as a checking account.");
        type = 'c';
        break;
    }
    history = new TransactionHistory();
    balance = 0;
  }
  public Account(char t, double[] transactions) {
    switch(t) {
      case 's':
      case 'c':
        type = t;
        break;
      default:
        System.out.println("A account type must be either 's' or 'c' for a savings or checking accounts, respectfully.\nAccount created as a checking account.");
        type = 'c';
        break;
    }
    history = new TransactionHistory();
    history.arrayToList(transactions);
    balance = history.calculateBalance();
  }

  public Account(char t, double b) {
    type = t;
    history = new TransactionHistory();
    balance = b;
  }

  public char getAccountType(){
    return type;
  }

  public double getBalance(){
    return balance;
  }

  public TransactionHistory getHistory(){
    return history;
  }

  public void withdraw(double amount) {
    if(amount > balance) {
      System.out.println("Invalid withdrawal: Inadequate Funds!");
      return;
    }
    if (amount <= 0) {
      System.out.println("Invalid withdrawal: Cannot withdraw negative values!");
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

  public void addInterest(int oldYear) {
    if(oldYear >= Values.getCurrentYear())
      return;

    int time = Values.getCurrentYear() - oldYear;
    double interestRate = type == 's' ? Values.getSavingsInterestRate() : Values.getCheckingInterestRate();

    balance = compoundInterest(balance, interestRate, 1, time);
  }

  public static double compoundInterest(double P, double r, int n, int t) {
    // Nimay Desai and Sachkeerat Brar
    return Math.pow(P * (1 + r / n), t * n);
  }

  // Nimay Desai
  public String toString () {
    String currentStr = "";
    currentStr += type;
    currentStr += ".";
    currentStr += balance;
    currentStr += history.toString();
    return currentStr;
  }

  // Sachkeerat Brar
  public String AccountInfo() {
    return "Account Type: " + type + "\nBalance: $" + balance + "\nTransaction History: " + history;
  }
}
