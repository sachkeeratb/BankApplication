// Sachkeerat Brar

// This object was made to shape a client's account
public class Account {
  // Fields
  private byte n; // This represents the compounding period in A = P(1 + r/n)^(nt)
  private char type; // This represents if the account is a savings account or checking account
  private TransactionHistory history = new TransactionHistory(); // This stores the transaction history of the account
  private double balance; // This stores the client's balance in their account


  // Constructors
  public Account() {
    type = 'c'; // Set the type to checking
    history = new TransactionHistory(); // Make a new transaction history list
    balance = 0; // Set the balance to 0
    n = 1; // Make it compound yearly
  }
  public Account(char t) {
    switch (t) {
      // A case where the type is valid
      case 's', 'c':
        type = t;
        break;

      // If the type is invalid, set it automatically to checking
      default:
        System.out.println("A account type must be either 's' or 'c' for a savings or checking accounts, respectfully.\nAccount created as a checking account.");
        type = 'c';
        break;
    }

    history = new TransactionHistory(); // Make a new transaction history list
    balance = 0; // Set the balance to 0
    n = 1; // Make it compound yearly
  }
  public Account(char t, byte compounding) {
    switch (t) {
      // A case where the type is valid
      case 's', 'c':
        type = t;
        break;

      // If the type is invalid, set it automatically to checking
      default:
        System.out.println("A account type must be either 's' or 'c' for a savings or checking accounts, respectfully.\nAccount created as a checking account.");
        type = 'c';
        break;
    }

    history = new TransactionHistory(); // Make a new transaction history list
    balance = 0; // Store the balance as 0

    switch (compounding) {
      // If it compounds either annually, semi-annually, quarterly, or monthly
      case 1, 2, 4, 12:
        n = compounding;
        break;

      // If it doesn't, inform the user and compound annually
      default:
        System.out.println("Invalid compounding rate. Setting to compound yearly.");
        n = 1;
        break;
    }
  }
  public Account(char t, byte compounding, TransactionHistory transactions) {
    switch (t) {
      // A case where the type is valid
      case 's', 'c':
        type = t;
        break;

      // If the type is invalid, set it automatically to checking
      default:
        System.out.println("A account type must be either 's' or 'c' for a savings or checking accounts, respectfully.\nAccount created as a checking account.");
        type = 'c';
        break;
    }

    history = transactions; // Set the history to the given transactions
    balance = TransactionHistory.calculateBalance(history); // Recalibrate the balance

    switch (compounding) {
      // If it compounds either annually, semi-annually, quarterly, or monthly
      case 1, 2, 4, 12:
        n = compounding;
        break;

      // If it doesn't, inform the user and compound annually
      default:
        System.out.println("Invalid compounding rate. Setting to compound yearly.");
        n = 1;
        break;
    }
  }
  public Account(char t, byte compounding, double[] transactions) {
    switch (t) {
      // A case where the type is valid
      case 's', 'c':
        type = t;
        break;

      // If the type is invalid, set it automatically to checking
      default:
        System.out.println("A account type must be either 's' or 'c' for a savings or checking accounts, respectfully.\nAccount created as a checking account.");
        type = 'c';
        break;
    }

    history = new TransactionHistory(); // Make a new transaction history list
    history.arrayToList(transactions); // Put the array of transactions into the linked list
    balance = TransactionHistory.calculateBalance(history); // Recalibrate the balance

    switch (compounding) {
      // If it compounds either annually, semi-annually, quarterly, or monthly
      case 1, 2, 4, 12:
        n = compounding;
        break;

      // If it doesn't, inform the user and compound annually
      default:
        System.out.println("Invalid compounding rate. Setting to compound yearly.");
        n = 1;
        break;
    }
  }


  // Accessors
  public char getAccountType() {
    return type;
  }
  public double getBalance() {
    return balance;
  }
  public TransactionHistory getHistory() {
    return history;
  }


  // Mutators
  public void putN(byte compounding) {
    // If the new n value compounds annually, semi-annually, quarterly, or monthly, you can change it
    switch (compounding) {
      case 1, 2, 4, 12:
        n = compounding;
        break;

      // Otherwise, inform the user
      default:
        System.out.println("Invalid compounding periods.");
        break;
    }
  }
  public void changeType() {
    // If the type is a checking account, flip it
    if (type == 'c')
      type = 's';
      // If the type is savings, flip it
    else
      type = 'c';
  }
  public void withdraw(double amount) {
    // This method withdraws money from the account
    // It updates the balance and transaction history as well

    // If the amount to withdraw is more than the current balance
    if (amount > balance)
      System.out.println("Invalid withdrawal: Inadequate Funds!");

      // If the amount to withdraw is negative
    else if (amount <= 0)
      System.out.println("Invalid withdrawal: Cannot withdraw negative values!");

      // Otherwise, withdraw
    else {
      history.newTransaction(-1 * amount);
      balance -= amount;
    }
  }
  public void deposit(double amount) {
    // This method withdraws money from the account
    // It updates the balance and transaction history as well

    // If the deposit is negative, inform the user
    if (amount <= 0)
      System.out.println("Invalid deposit: Cannot deposit negative values!");

      // Otherwise, deposit
    else {
      history.newTransaction(amount);
      balance += amount;
    }
  }
  public void addInterest() {
    // This method calculates the interest earnt based on compounding periods

    // Get and store the interest rate
    double interestRate = type == 's' ? Values.getSavingsInterestRate() : Values.getCheckingInterestRate();

    // Go through each case of the compounding periods
    switch (n) {
      // Annually
      case 1: {
        // If the current year isn't greater than the previous year, exit
        if (Values.getCurrentYear() <= Values.getPreviousYear())
          return;


        // Calculate the year(s) passed and get the interest rate
        int time = Values.getCurrentYear() - Values.getPreviousYear();

        // Calculate the new balance and interest
        double newBalance = compoundInterest(balance, interestRate, time);
        double interest = newBalance - balance;

        // Store the interest gained and set the new balance
        history.newTransaction(interest);
        balance = newBalance;

        break;
      }

      // Semi-annually
      case 2: {
        // If the current month is not at least 6 months ahead of the last month (within the same year)
        if ((Values.getPreviousMonth() + 6 < Values.getCurrentMonth()) && (Values.getCurrentYear() == Values.getPreviousYear()))
          return;

        // Calculate the distance of the months and get the interest rate
        double time = Math.abs(((double) Values.getCurrentMonth() - Values.getPreviousMonth()) / 12);

        // Calculate the new balance and interest
        double newBalance = compoundInterest(balance, interestRate, time);
        double interest = newBalance - balance;

        // Store the interest gained and set the new balance
        history.newTransaction(interest);
        balance = newBalance;
        break;
      }

      // Quarterly
      case 4: {
        // If at least 3 months haven't passed
        if (Values.getPreviousMonth() + 3 < Values.getCurrentMonth())
          return;

        // Calculate the distance of the months and get the interest rate
        double time = Math.abs(((double) Values.getCurrentMonth() - Values.getPreviousMonth()) / 12);

        // Calculate the new balance and interest
        double newBalance = compoundInterest(balance, interestRate, time);
        double interest = newBalance - balance;

        // Store the interest gained and set the new balance
        history.newTransaction(interest);
        balance = newBalance;

        break;
      }

      // Monthly
      case 12: {
        // If at least a month hasn't passed, leave
        if ((Values.getCurrentMonth() <= Values.getPreviousMonth()))
          return;

        // Calculate the distance of the months and get the interest rate
        double time = Math.abs(((double) Values.getCurrentMonth() - Values.getPreviousMonth()) / 12);

        // Calculate the new balance and interest
        double newBalance = compoundInterest(balance, interestRate, time);
        double interest = newBalance - balance;

        // Store the interest gained and set the new balance
        history.newTransaction(interest);
        balance = newBalance;

        break;
      }
    }
  }


  // Instance methods

  // Sachkeerat Brar
  private String compoundedByN() {
    // This method returns a string for the account info method for how the account compounds
    return switch (n) {
      case 1 -> "annually";
      case 2 -> "semi-annually";
      case 4 -> "quarterly";
      case 12 -> "monthly";
      default -> "";
    };
  }
  // Nimay Desai and Sachkeerat Brar
  private double compoundInterest(double P, double r, double t) {
    // This method calculated compound interest to add interest to the account
    return Math.pow(P * (1 + r / (double) n), (double) n * t);
  }
  // Sachkeerat Brar
  public void display() {
    // This method outputs the account's info in a readable way
    System.out.println("Account Type: " + (type == 's' ? "savings" : "checking") + "\nBalance: $" + balance + "\nCompounded: " + compoundedByN() + "\nTransaction History: ");
    history.display();
  }
  // Sachkeerat Brar
  public void copy(Account other) {
    // This method returns an account which has a copy of another account's values
    n = other.n;
    type = other.type;
    history = new TransactionHistory();
    history.copy(other.history);
    balance = other.balance;
  }
  // Nimay Desai
  public String toString() {
    // This method turns the account into a string
    return "@ " + type + ", " + n + ", " + history.toString() + " #";
  }

  
  // Static methods

  // Sachkeerat Brar & Kushal Prajapati
  public static Account fromString(String data) {
    // This method creates an account from a string of data
    char accType = data.charAt(4);
    byte accCompounding = Byte.parseByte(data.substring(7, data.indexOf(",", 7)));
    String accHistory = data.substring(data.indexOf(">"), data.indexOf("<") + 1);
    TransactionHistory newHistory = TransactionHistory.fromString(accHistory);
  
    Account account = new Account(accType, accCompounding, newHistory);
  
    return account;
  }
  // Sachkeerat Brar
  private static int calculateAge(String date) {
    // This method calculates an age from a date formatted as "yyyy/mm/dd"

    // Store the birth values as integers
    int birthYear = Integer.parseInt(date.substring(0, 4));
    int birthMonth = Integer.parseInt(date.substring(5, date.indexOf("/", 5)));
    int birthDay = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1));
    System.out.println(birthYear + "/" + birthMonth + "/" + birthDay);

    // Store the age
    int currentAge = Values.getCurrentYear() - birthYear;
    System.out.println(Values.getCurrentYear());
    System.out.println(currentAge);

    // See if they are a year younger
    if ((Values.getCurrentMonth() < birthMonth) || ((Values.getCurrentMonth() == birthMonth) && (Values.getCurrentDay() < birthDay)))
      currentAge--;

    // Return the age
    return currentAge;
  }
}
