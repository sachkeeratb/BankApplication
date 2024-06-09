// Sachkeerat Brar
public class Account {
  int n;
  private final char type;
  private final TransactionHistory history;
  private double balance;

  public Account(char t) {
    switch (t) {
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
    n = 1;
  }

  public Account(char t, double[] transactions) {
    switch (t) {
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
    n = 1;
  }

  public Account(char t, double b) {
    type = t;
    history = new TransactionHistory();
    balance = b;
  }

  public char getAccountType() {
    return type;
  }

  public double getBalance() {
    return balance;
  }

  public TransactionHistory getHistory() {
    return history;
  }

  public void withdraw(double amount) {
    if (amount > balance) {
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
    if (amount > balance) {
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

  public void addInterest() {
    switch (n) {
      case 1: {
        if (Values.getCurrentYear() >= Values.getPreviousYear())
          return;

        int time = Values.getCurrentYear() - Values.getPreviousYear();
        double interestRate = type == 's' ? Values.getSavingsInterestRate() : Values.getCheckingInterestRate();

        balance = compoundInterest(balance, interestRate, time);
        break;
      }

      case 2: {
        if (Values.getCurrentYear() + 6 >= Values.getPreviousYear())
          return;

        double time = (double) (Values.getCurrentMonth() - Values.getPreviousMonth()) / 12;
        double interestRate = type == 's' ? Values.getSavingsInterestRate() : Values.getCheckingInterestRate();

        balance = compoundInterest(balance, interestRate, time);
        break;
      }

      case 4: {
        if (Values.getPreviousMonth() + 3 >= Values.getPreviousMonth())
          return;

        double time = (double) (Values.getCurrentMonth() - Values.getPreviousMonth()) / 12;
        double interestRate = type == 's' ? Values.getSavingsInterestRate() : Values.getCheckingInterestRate();

        balance = compoundInterest(balance, interestRate, time);
        break;
      }

      case 12: {
        if (Values.getPreviousMonth() >= Values.getPreviousMonth())
          return;

        double time = (double) (Values.getCurrentMonth() - Values.getPreviousMonth()) / 12;
        double interestRate = type == 's' ? Values.getSavingsInterestRate() : Values.getCheckingInterestRate();

        balance = compoundInterest(balance, interestRate, time);
        break;
      }
    }
  }

  private double compoundInterest(double P, double r, double t) {
    // Nimay Desai and Sachkeerat Brar
    return Math.pow(P * (1 + r / (double) n), (double) n * t);
  }

  // Nimay Desai
  public String toString() {
    return type + "." + balance + "." + n + "." + history.toString();
  }

  private String compoundedByN() {
    return switch (n) {
      case 1 -> "annually";
      case 2 -> "semi-annually";
      case 4 -> "quarterly";
      case 12 -> "monthly";
      default -> "";
    };
  }

  // Sachkeerat Brar
  public String accountInfo() {
    return "Account Type: " + type + "\nBalance: $" + balance + "\nCompounded: " + compoundedByN() + "\nTransaction History: " + history;
  }
}
