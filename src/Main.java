// Imported objects used
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// This is the main file of the program
public class Main {
  // Sachkeerat Brar
  public static void title() {
    // This method outputs the title of the application and the creators of it
    System.out.println("""
        ___            _       _             _ _         _   _
       | _ ) __ _ _ _ | |__   /_\\  _ __ _ __| (_)__ __ _| |_(_)___ _ _
       | _ \\/ _` | ' \\| / /  / _ \\| '_ \\ '_ \\ | / _/ _` |  _| / _ \\ ' \\
       |___/\\__,_|_||_|_\\_\\ /_/ \\_\\ .__/ .__/_|_\\__\\__,_|\\__|_\\___/_||_|
                                  |_|  |_|

      By: Sachkeerat Brar, Nimay Desai, and Kushal Prajapati
      """);
  }

  // Sachkeerat Brar
  public static void exit(ClientList clients) throws IOException {
    // This function exits the program and writes the data to the file
    // This function takes in a ClientList clients which represents the current list of clients
    // This function does not return anything as it directly calls System.exit();

    // Stores the client list to ClientInfo
    clients.storeClientList();
    System.out.println("Thank you for using this program.");
    // Exits the program with no errors
    System.exit(0);
  }

  // Nimay Desai
  public static void register() throws IOException {
    // Registers a new user to the program
    // It does not take in any argument as all data is retrieved from user in
    // It does not return anything as everything is written to a file
    Scanner in = new Scanner(System.in); // Creates a new scanner
    FileWriter fw = new FileWriter(Values.getSuperInfoLocation()); // Creates a FileWrite of SuperInfo
    FileWriter fwOld = new FileWriter(Values.getSuperInfoOldLocation()); // Creates a FileWriter of SuperInfoOld
    PrintWriter pw = new PrintWriter(fw); // Creates a PrintWriter of SuperInfo
    PrintWriter pwOld = new PrintWriter(fwOld); // Creates a PrintWriter for SuperInfoOld
    System.out.println("The program has detected that this is the first time you have opened this application.\nAccount creation will begin.");

    String date; // Create a data
    do {
      // Ask the user for the date
      System.out.println("Enter the date as yyyy/mm/dd: ");
      // Takes in the data
      date = in.next();
    } while(!validDate(date)); // Runs the loop until the data is valid

    // Prompts the user for a password
    System.out.println("Please enter a password for security: ");
    // Gets the password from the user
    String password = in.next();

    System.out.println("You have successfully registered.");
    // Add the encrypted password and the current date to SuperInfo
    pw.println(Values.convert(date + "." + password));
    // Add the encrypted password and the current date to SuperInfoOld
    pwOld.println(Values.convert(date + "." + password));

    // Flush all PrintWriters and FileWriters so all data is written
    fw.flush();
    fwOld.flush();
    pw.flush();
    pwOld.flush();
  }

  // Nimay Desai
  // Verifies the current password with the password stored in the file
  // It does not take in anything as all data is directly from user in
  // Returns a boolean on whether the password is valid
  public static boolean verifyPassword() throws IOException {
    // Create a scanner
    Scanner in = new Scanner(System.in);

    // Prompts the user for the password
    System.out.println("Please enter your password to verify: ");
    // Stores the password in a variable
    String password = in.next();
    // Represents the number of attempts
    int numberOfAttempts = 1;
    // Asks the user 5 times for the password
    while(!Values.comparePassword(password)) {
      if(numberOfAttempts >= 5) { // If more then five attempts exit the login menu
        return false;
      }

      // Say how many attempts they have left based on the number of attemps they have already done
      System.out.println("Wrong password entered, please try again. Program will exit in " + (5 - numberOfAttempts) + " failed " + (numberOfAttempts == 4 ? "attempt" : "attempts") + ".");
      password = in.next(); // Stores the password
      numberOfAttempts++; // Increase the number of attempts by one
    }

    // Sucessful Login
    return true;
  }

  // Nimay Desai
  // Logins into the application
  // Takes in a ClientList clients which represent the list of clients passed down into the exit
  // This function returns a boolean on whether the login was valid or not
  public static boolean login(ClientList clients) throws IOException {
    // If password is invalid
    if(!verifyPassword()) {
      // Exit the program
      exit(clients);
      return false;
    }

    // Gets the date from the database
    String date = getDate();
    // Changes the date to the new date
    Values.putCurrentDate(date);
    // Updates the interest for the clients
    updateInterest(clients);

    // Sucessful Login
    System.out.println("You have successfully logged in!");
    return true;
  }

  // Sachkeerat Brar
  // Gets the current date
  // Does not take in any argument as the data is retrieved from the user
  // Returns a String representing the current date
  public static String getDate() {
    // Create a Scanner
    Scanner in = new Scanner(System.in);
    String date;
    do {
      // Prompts user for the current date
      System.out.println("Enter the current date as yyyy/mm/dd: ");
      // Stores date in a variable
      date = in.next();
    } while(!validDate(date)); // Until the date is valid

    return date; // Returns the date
  }

  // Sachkeerat Brar
  public static boolean validDate(String date) {
    int year, month, day;

    try {
      year = Integer.parseInt(date.substring(0, 4));
      month = Integer.parseInt(date.substring(5, date.indexOf("/", 5)));
      day = Integer.parseInt(date.substring(date.indexOf("/", 5) + 1));

      if(year < Values.getPreviousYear()) {
        System.out.println("Do not lie about the date.");
        return false;
      }
      if((month < Values.getPreviousMonth()) && (year == Values.getPreviousYear())) {
        System.out.println("Do not lie about the date.");
        return false;
      }
      if((day < Values.getPreviousDay()) && (month == Values.getPreviousMonth())) {
        System.out.println("Do not lie about the date.");
        return false;
      }

      if((year > Values.getCurrentYear()) || (month < 1) || (month > 12) || (day < 1) || (day > 31)) {
        System.out.println("Please in a valid date.");
        return false;
      }
    }
    catch(Exception _) {
      System.out.println("Invalid date format. Please in a date as yyyy/mm/dd.");
      return false;
    }

    return true;
  }

  // Sachkeerat Brar
  public static boolean validAge(String date) {
    int year, month, day;

    year = Integer.parseInt(date.substring(0, 4));
    month = Integer.parseInt(date.substring(5, date.indexOf("/", 5)));
    day = Integer.parseInt(date.substring(date.indexOf("/", 5) + 1));

    if(year <= 1904) {
      System.out.println("Do not lie about your age.");
      System.exit(0);
    }

    int currentAge = Values.getCurrentYear() - year;

    // See if they are a year younger
    if((Values.getCurrentMonth() < month) || (Values.getCurrentMonth() == month && Values.getCurrentDay() < day))
      currentAge--;

    if(currentAge < 18) {
      System.out.println("You must be 18 years old to use this bank.");
      return false;
    }

    return true;
  }

  // Sachkeerat Brar
  public static void updateInterest(ClientList clients) {
    // This method goes through the clients and their accounts in order to add interest to the accounts
    for(ClientList.Node temp = clients.getHead(); temp != null; temp = temp.link)
      for(int i = 0; i < temp.client.getAccounts().getNumAccounts(); i++)
        if(temp.client.getAccounts().getAccount(i) != null)
          temp.client.getAccounts().getAccount(i).addInterest();
  }

  // Kushal Prajapati
  // This is the main menu of the program that lets you pick between the 4 options
  public static void mainMenu(ClientList clients) throws IOException {
    System.out.println("Welcome to the Bank!");
    System.out.println("What would you like to do?");

    System.out.println("1 --> Handle Clients");
    System.out.println("2 --> Modify Bank");
    System.out.println("3 --> Exit program");

    Scanner in = new Scanner(System.in);
    int opt;
    do {
      System.out.println("Enter your option between 1 to 3: ");
      opt = in.nextInt();
    } while((opt < 1) || (opt > 3));
    switch(opt) {
      case 1 -> handleClients(clients);
      case 2 -> modifyBank(clients);
      case 3 -> exit(clients);
    }
  }

  // Kushal Prajapati & Sachkeerat Brar
  public static void modifyBank(ClientList clients) throws IOException{
    // This method allows the user to modify the bank's information stored
    System.out.println("Select your option: ");
    System.out.println("1 --> Change Password");
    System.out.println("2 --> Change Checking Interest");
    System.out.println("3 --> Change Savings Interest");
    System.out.println("4 --> Go back");
    System.out.println("More coming soon!");
    Scanner in = new Scanner(System.in);
    int opt = in.nextInt();

    switch(opt) {
      case 1:
        changePassword(clients);
        break;
      case 2: {
        System.out.println("Enter a new interest rate for checking accounts: ");
        double rate = in.nextDouble();
        Values.putCheckingInterestRate(rate);
      }
      case 3: {
        System.out.println("Enter a new interest rate for checking accounts: ");
        double rate = in.nextDouble();
        Values.putSavingsInterestRate(rate);
      }
      case 4:
        mainMenu(clients);
        break;

      default:
        System.out.println("Invalid option.");
        break;
    }
  }

  // Kushal Prajapati & Sachkeerat Brar
  public static void changePassword(ClientList clients) throws IOException {
    // This method allows the user to change the password of the bank
    Scanner in = new Scanner(System.in);

    verifyPassword();

    System.out.println("Enter your new password: ");
    String newPassword = in.next();

    PrintWriter pwOld = new PrintWriter(new FileWriter(Values.getSuperInfoOldLocation()));
    PrintWriter pw = new PrintWriter(new FileWriter(Values.getSuperInfoLocation()));
    BufferedReader br = new BufferedReader(new FileReader(Values.getSuperInfoLocation()));
    BufferedReader brOld = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));

    String dataOld = brOld.readLine();
    String data = br.readLine();
    data = data.substring(0, data.indexOf(".") + 1) + Values.convert(newPassword);
    dataOld = dataOld.substring(0, data.indexOf(".") + 1) + Values.convert(newPassword);

    pwOld.println(dataOld);
    pw.println(data);

    pwOld.flush();
    pw.flush();
  }


  // Kushal & Nimay Desai
  // deals with withdrawing and handling the clients such as withdraw and depositing money
  public static void handleClients(ClientList clients) throws IOException {
    Scanner in = new Scanner(System.in);
    System.out.println("1 --> Create Client"); // Create clients for the list
    System.out.println("2 --> Create Account"); // Create accounts for the list
    System.out.println("3 --> Withdraw or Deposit Money"); // withdraw or deposit money
    System.out.println("4 --> Transfer Money"); // transfer money from one user to another user
    System.out.println("5 --> View Balance"); // View a client's balance
    System.out.println("6 --> Change Clients Information"); // Change info
    System.out.println("7 --> Filter Clients"); // Filter by age
    System.out.println("8 --> Back"); // Go back

    int opt;
    do {
      System.out.println("Enter your option: ");
      opt = in.nextInt();
    } while((opt < 1) || (opt > 8));

    switch(opt) {
      case 1 -> createClient(clients); // Create a client
      case 2 -> createAccount(clients); // Create an account for the client
      case 3 -> withDepo(clients); // Withdraw and Deposit Money
      case 4 -> transfer(clients); // Transfer Money Between Two Users
      case 5 -> viewBal(clients); //  View Balance of the Client
      case 6 -> changeInfo(clients); // Change name or account details
      case 7 -> filterClients(clients); // View Clients By Age
      case 8 -> mainMenu(clients); // Go back to the main menu
    }
  }

  // Nimay Desai & Sachkeerat
  public static void createClient(ClientList clients) throws IOException {
    Scanner input = new Scanner(System.in); // Create client
    String name; // Stores the name
    String dob; // Stores the date of birth
    do { // Runs until a valid date and age is entered
      System.out.println("Enter the name of the client"); // Prompts user for the name
      name = input.next(); // Stores the name of client
      System.out.println("Enter the date of birth"); // Prompts user for date of birth
      dob = input.next(); // Stores the date of birth
    } while (!validAge(dob));



    // Creates a client with the name and date of birth supplied w/ ID
    Client client;
    if(clients.getHead() == null)
      client = new Client(name, dob);
    else
      client = new Client(name, dob, clients.lastNode().client);

    clients.addToList(client);

    clients.storeClientList();

    mainMenu(clients);
  }

  // Kushal Prajapati & Sachkeerat Brar
  public static void createAccount(ClientList clients) throws IOException {
    // This method allows the user to create an account for a client
    Scanner input = new Scanner(System.in);

    // Get the client
    Client client;
    do {
      System.out.println("Enter the ID of the client: ");
      int ID = input.nextInt();
      client = clients.findNodeByIndex(ID - 1).client;

      if (client == null) {
        System.out.println("Invalid ID. Please try again.");
      }
    } while(client == null);

    if(client.getAccounts().getNumAccounts() == 5) {
      System.out.println("This client has reached the maximum accounts available.");
      return;
    }

    // Get the type of the account
    int choice;
    char type;
    do {
      System.out.println("Enter the type of account you would like to create: ");
      System.out.println("1 --> Checking");
      System.out.println("2 --> Savings");
      choice = input.nextInt();
      type = choice == 2
        ? 's'
        : 'c';
    } while((choice != 1) && (choice != 2));

    // Get the compounding periods of the account
    byte compounding;
    do {
      System.out.println("Enter the period of compounding you would like for this account: ");
      System.out.println("1 --> Annually");
      System.out.println("2 --> Semi-Annually");
      System.out.println("4 --> Quarterly");
      System.out.println("12 --> Monthly");
      compounding = input.nextByte();
    } while((compounding != 1) && (compounding != 2) && (compounding != 4) && (compounding != 12));

    // Create the account and give it to the user
    Account a = new Account(type, compounding);
    client.getAccounts().addAccount(a);

    clients.storeClientList();
  }

  // Kushal Prajapati
  // filter students
  public static void filterClients(ClientList clients) {
    Scanner in = new Scanner(System.in);
    // filter by the age of the client
    byte age;
    // Prompt the user for their age
    do {
      System.out.println("Enter the age of the clients you would like to view (18 - 120): ");
      age = in.nextByte();
    } while ((age < 18) || (age > 120));
    // Filter and print out the clients that are greater, equal, or less than the age
    int filterChoice;
    do {
      System.out.println("Do you want to filter by people that are greater, equal, or less than your selected age:");
      System.out.println("1. Greater");
      System.out.println("2. Equal");
      System.out.println("3. Less");
      filterChoice = in.nextInt();
    } while ((filterChoice < 1) || (filterChoice > 3));

    switch (filterChoice) {
      case 1: {
        ClientList temp = clients.ageGreaterThan(age);
        temp.display();
        break;
      }
      case 2: {
        ClientList temp = clients.ageEqualTo(age);
        temp.display();
        break;
      }
      case 3: {
        ClientList temp = clients.ageLessThan(age);
        temp.display();
        break;
      }
    }
  }

  // Nimay Desai
  public static void withDepo(ClientList clients) throws IOException {
    Scanner input = new Scanner(System.in);
    int opt;
    do {
      System.out.println("Press 1 to withdraw");
      System.out.println("Press 2 to deposit");
      opt = input.nextInt();
    } while ((opt != 1) && (opt != 2));
    switch (opt) {
      case 1 -> withdraw(clients);
      case 2 -> deposit(clients);
    }

  }

  public static void withdraw (ClientList clients) throws IOException {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the ID of the client");
    int ID = input.nextInt();

    Client client= clients.searchByID(ID);

    System.out.println("Enter the index of the account you would like to withdraw");
    int accountIdx = input.nextInt();
    Account account = client.getAccounts().getAccount(accountIdx);

    System.out.println("Enter how much money you would like to withdraw");
    int val = input.nextInt();
    account.withdraw(val);

    clients.storeClientList();

    mainMenu(clients);
 }

  public static void deposit (ClientList clients) throws IOException {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the ID of the client");
    int ID = input.nextInt();

    Client client = clients.searchByID(ID);

    System.out.println("Enter the number of the account you would like to deposit");
    int accountIdx = input.nextInt()-1;
    Account account = client.getAccounts().getAccount(accountIdx);

    System.out.println("Enter how much money you would like to deposit");
    int val = input.nextInt();
    account.deposit(val);

    clients.storeClientList();

    mainMenu(clients);
  }

  // Kushal Prajapati
  // Transfers money between two accounts
  public static void transfer(ClientList clients) throws IOException {

    Scanner in = new Scanner(System.in);
    System.out.println("Enter the ID of the client: ");
    int ID = in.nextInt();
    Client client = clients.searchByID(ID);

    System.out.println("Enter the number of the account you would like to transfer: ");
    int accountIdx = in.nextInt()-1;
    Account account = client.getAccounts().getAccount(accountIdx);

    System.out.println("Enter how much money you would like to transfer: ");
    int val = in.nextInt();
    account.withdraw(val);

    System.out.println("Enter the ID of the client you would like to transfer to: ");
    int ID2=in.nextInt()-1;
    Client client2= clients.searchByID(ID2);

    System.out.println("Enter the number of the account of the second client: ");
    int accountIdx2= in.nextInt()-1;
    Account account2 = client2.getAccounts().getAccount(accountIdx2);
    account2.deposit(val);
    mainMenu(clients);

  }

  // Kushal
  // Views the balance of the chosen clients
  // Takes in a ClientList clients which represnts the list of clients
  // Does not return anything as the balance is shown to the terminal
  public static void viewBal(ClientList clients) {
    Scanner in= new Scanner(System.in);
    System.out.println("Enter the ID of the client you would like to see: ");
    int ID = in.nextInt();
    Client client= clients.searchByID(ID);

    int accountIdx;
    do {
      System.out.println("Enter the number of the account you would like to transfer: ");
      accountIdx = in.nextInt() - 1;
    } while((accountIdx < 0) || (accountIdx >= client.getAccounts().getNumAccounts()));
    Account account = client.getAccounts().getAccount(accountIdx);
    System.out.println(account.getBalance());
  }

  // Nimay Desai
  public static void changeInfo(ClientList clients) {
    Scanner in = new Scanner(System.in);
    Client currentClient;
    System.out.println("How would you like to search the user");
    System.out.println("Enter 1 for name and Enter 2 for ID");

    int opt = in.nextInt();
    switch (opt) {
      case 1:
        System.out.println("Enter the name of the customer");
        String name = in.next();
        currentClient = clients.findNodeByIndex(clients.searchByName(name)).client;
        currentClient.display();
        break;

      case 2:
        System.out.println("Enter the ID of the client (above 0): ");
        int id = in.nextInt();
        currentClient = clients.searchByID(id);
        currentClient.display();
        break;

      default:
        System.out.println("Invalid option selected.");
        break;
    }
  }

  // Nimay Desai
  public static void changeData(Client currentClient, ClientList clients) {
    Scanner in = new Scanner(System.in);

    // Get the user's choice
    int opt;
    do {
      System.out.println("What would you like to change");
      System.out.println("1 --> Change the Name");
      System.out.println("2 --> Modify Accounts");
      System.out.println("3 --> Go back");
      opt = in.nextInt();
    } while((opt < 1) || (opt > 3));

    // Go through their choices
    switch (opt) {
      case 1:
        System.out.println("Please enter the new name");
        String name = in.next();
        currentClient.putName(name);
        break;
      case 2:
        modifyAccounts(currentClient, clients);
        break;
      case 3:
        changeInfo(clients);
        break;
    }
  }

  // Nimay Desai
  public static void modifyAccounts (Client currentClient, ClientList clients) {
    // Displays menu of options to modify a user accounts and modifies them
    // Takes in a Client currentClient which represents the current client to modify and a ClientList clients which is passed on to changeData
    // Does not return anything as everything is modified
    Scanner in = new Scanner(System.in); // Create Scanner
    System.out.println("Enter index of account"); // Prompts the user for the index of the account
    int idx = in.nextInt(); // Gets the index of the account
    Account currentAccount = currentClient.getAccounts().getAccount(idx); // Gets the account of that index
    System.out.println("What would you like to do with your account"); // Prompts the user what to do with that account
    System.out.println("1 --> Change The Type");
    System.out.println("2 --> Change The Compounding Period");
    System.out.println("3 --> Go Back");
    int opt = in.nextInt(); // Gets the user input
    switch (opt) {
      case 1 -> currentAccount.changeType(); // Change the type of the account
      case 2 -> changeCompounding(currentAccount); // Change the compounding period of the account
      case 3 -> changeData(currentClient, clients); // Goes back to the previous menu
    }
  }

  // Nimay Desai
  // Changes the compounding period of the current account
  // Takes in an Account currenAccount which represents the current account
  // It does not return anything as everything is modified
  public static void changeCompounding (Account currentAccount) {
    Scanner input = new Scanner(System.in); // Create Scanner
    System.out.println("Enter the compounding period value: "); // Prompt the user for the compounding period
    System.out.println("1 --> Annually\n2 -> Semi-Annually\n 4 -> Quarterly\n or 12 -> Monthly)");

    // Get the compounding period and set the account to it
    byte n = input.nextByte();
    currentAccount.putN(n);
  }


  // Nimay Desai & Sachkeerat Brar
  public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(System.in);
    
    title();

    if(Values.checkIfEmpty(Values.getSuperInfoLocation()))
      register();

    ClientList clients = new ClientList();
    if(!Values.checkIfEmpty(Values.getClientInfoLocation()))
      clients = ClientList.loadClientList();
    else
      clients.storeClientList();

    login(clients);

    boolean choice;
    do {
      mainMenu(clients);
      System.out.println("Enter 1 to continue: ");
      choice = in.nextInt() == 1;
    } while(choice);

    exit(clients);
  }
}
