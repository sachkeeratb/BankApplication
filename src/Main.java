// Imported objects used

import java.io.IOException;
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
    // This method exits the program and writes the data to the file
    // This method takes in a ClientList clients which represents the current list of clients
    // This method does not return anything as it directly calls System.exit();

    // Update the interest rate and their values
    Values.updateInterestRate();

    // Write the client list to the files
    clients.storeClientList();

    // Thanks the user and exits the program with no errors
    System.out.println("Thank you for using this program.");
    System.exit(0);
  }

  // Nimay Desai
  public static void register() throws IOException {
    // Registers a new user to the program
    // It does not take in any argument as all data is retrieved from user in
    // It does not return anything as everything is written to a file

    Scanner in = new Scanner(System.in); // Creates a new scanner

    // Create writers to write to the files
    // Creates a PrintWriter for SuperInfoOld

    // Inform the user about their registration
    System.out.println("The program has detected that this is the first time you have opened this application.\nAccount creation will begin.");

    String date; // Create a date
    do {
      // Ask the user for the date
      System.out.println("Enter the date as yyyy/mm/dd: ");
      // Takes in the data
      date = in.next();
    } while (!validDate(date)); // Runs the loop until the data is valid

    // Prompts the user for a password
    System.out.println("Please enter a password for security: ");
    // Gets the password from the user
    String password = in.next();

    System.out.println("You have successfully registered.");

    // Add the encrypted password and the current date to SuperInfo
    // Add the encrypted password and the current date to SuperInfoOld
    Values.writeToSuperInfo(Values.convert(date + "." + password));
    Values.writeToSuperInfoOld(Values.convert(date + "." + password));

    Values.loadDates();
  }

  // Nimay Desai
  public static void login(ClientList clients) throws IOException {
    // Logins into the application
    // Takes in a ClientList clients which represent the list of clients passed down into the exit

    // If password is invalid
    if (!verifyPassword()) // Exit the program
      exit(clients);

    // Gets the date from the database
    String date = getDate();
    // Changes the date to the new date and puts it into memory

    Values.loadDates();
    Values.putCurrentDate(date);

    // Updates the interest for the clients
    Values.updateInterestRate();
    updateInterest(clients);

    // Sucessful Login
    System.out.println("You have successfully logged in!");
  }

  // Nimay Desai and Kushal Prajapati
  public static void mainMenu(ClientList clients) throws IOException {
    // This is the main menu of the program that lets you pick between the 4 options
    System.out.println("Welcome to the Bank!"); // Prompt the use for the option
    System.out.println("What would you like to do?");

    System.out.println("1 --> Handle Clients");
    System.out.println("2 --> Modify Bank");
    System.out.println("3 --> Exit program");

    Scanner in = new Scanner(System.in); // Create a scanner
    int opt; // Create the option which represents the option
    do {
      System.out.println("Enter your option between 1 to 3: ");
      opt = in.nextInt();
    } while ((opt < 1) || (opt > 3)); // Until valid option is entered
    switch (opt) {
      case 1 -> handleClients(clients); // Handle Clients
      case 2 -> modifyBank(clients); // Modify Bank
      case 3 -> exit(clients); // Exit the program
    }
  }

  // Nimay Desai
  public static boolean verifyPassword() throws IOException {
    // Verifies the current password with the password stored in the file
    // It does not take in anything as all data is directly from user in
    // Returns a boolean on whether the password is valid
    // This is secure as it calls a public method to compare passwords, while getting the password itself is private and encrypted

    // Create a scanner
    Scanner in = new Scanner(System.in);

    // Prompts the user for the password
    System.out.println("Please enter your password to verify: ");

    // Stores the password in a variable
    String password = in.next();

    // Represents the number of attempts
    int numberOfAttempts = 1;

    // Asks the user 5 times for the password
    while (!Values.comparePassword(password)) {
      // If more than five attempts exit the login menu
      if (numberOfAttempts >= 5)
        return false;

      // Say how many attempts they have left based on the number of attempts they have already done
      System.out.println("Wrong password entered, please try again. Program will exit in " + (5 - numberOfAttempts) + " failed " + (numberOfAttempts == 4 ? "attempt" : "attempts") + ".");
      password = in.next(); // Stores the password
      numberOfAttempts++; // Increase the number of attempts by one
    }

    // Sucessful login
    return true;
  }

  // Sachkeerat Brar
  public static String getDate() {
    // Gets the current date
    // Does not take in any argument as the data is retrieved from the user
    // Returns a String representing the current date

    // Create a Scanner
    Scanner in = new Scanner(System.in);
    String date;
    do {
      // Prompts user for the current date
      System.out.println("Enter the current date as yyyy/mm/dd: ");
      // Stores date in a variable
      date = in.next();
    } while (!validDate(date)); // Until the date is valid

    return date; // Returns the date
  }

  // Sachkeerat Brar
  public static boolean validDate(String date) {
    // This method checks if the new date is valid

    int year, month, day; // Store the year month and day

    try { // Try to parse the date
      year = Integer.parseInt(date.substring(0, 4));
      month = Integer.parseInt(date.substring(5, date.indexOf("/", 5)));
      day = Integer.parseInt(date.substring(date.indexOf("/", 5) + 1));

      // If the date is in the past, the date is not valid
      if (year < Values.getPreviousYear()) {
        System.out.println("Do not lie about the date.");
        return false;
      }
      if ((month < Values.getPreviousMonth()) && (year == Values.getPreviousYear())) {
        System.out.println("Do not lie about the date.");
        return false;
      }
      if ((day < Values.getPreviousDay()) && (month == Values.getPreviousMonth()) && (year == Values.getPreviousYear())) {
        System.out.println("Do not lie about the date.");
        return false;
      }

      if ((year < Values.getCurrentYear()) || (month < 1) || (month > 12) || (day < 1) || (day > 31)) {
        System.out.println("Please input a valid date.");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Invalid date format. Please in a date as yyyy/mm/dd.");
      return false;
    }

    return true;
  }

  // Sachkeerat Brar
  public static boolean validAge(String date) {
    // This function checks if the age of the user is valid
    // It takes in a String date which represents the date entered by the user
    // If the year is above 120, then say invalid date or if the year is below 18
    
    int year, month, day; // Create variables which represents the year month and day

    year = Integer.parseInt(date.substring(0, 4)); // Get the year
    month = Integer.parseInt(date.substring(5, date.indexOf("/", 5))); // Get the month
    day = Integer.parseInt(date.substring(date.indexOf("/", 5) + 1)); // Get the day

    if (year <= 1904) { // Older than 120 (No one is above 120 currently)
      System.out.println("Do not lie about your age."); // Exit the program
      System.exit(0);
    }

    int currentAge = Values.getCurrentYear() - year; // Create a vvariable which represents the users age

    // See if they are a year younger
    if ((Values.getCurrentMonth() < month) || (Values.getCurrentMonth() == month && Values.getCurrentDay() < day))
      currentAge--;

    // A client of the bank must at least be 18 years old
    if (currentAge < 18) { // Too young (Less than 18)
      System.out.println("You must be 18 years old to use this bank.");
      return false; // Return false (Invalid)
    }

    return true; // Else return true (Valid age)
  }

  // Sachkeerat Brar
  public static void updateInterest(ClientList clients) {
    // This method goes through the clients and their accounts in order to add interest to the accounts
    // This function takes in a ClientList clients which represents the list of clients
    // This function does not return anything as everything is modified
    for (ClientList.Node temp = clients.getHead(); temp != null; temp = temp.link) // Goes through clients
      for (int i = 0; i < temp.client.getAccounts().getNumAccounts(); i++) // Goes through accounts
        if (temp.client.getAccounts().getAccount(i) != null) // Valid account
          temp.client.getAccounts().getAccount(i).addInterest(); // Add interest
  }

  // Kushal Prajapati & Sachkeerat Brar
  public static void modifyBank(ClientList clients) throws IOException {
    // This method allows the user to modify the bank's information stored
    // This function takes in a ClientList clients which represents the list of clients
    // This function does not return anything as everything is modified
    System.out.println("Select your option: ");
    System.out.println("1 --> Change Password");
    System.out.println("2 --> Change Checking Interest");
    System.out.println("3 --> Change Savings Interest");
    System.out.println("4 --> Go back");
    System.out.println("More coming soon!");
    Scanner in = new Scanner(System.in); // Create a scanner
    int opt = in.nextInt(); // Take in user input

    // Redirects to each of the methods to modify the bank information
    switch (opt) {
      case 1:
        changePassword(clients); // User wants to change password
        break;
      case 2: {
        System.out.println("Enter a new interest rate for checking accounts as a percentage: "); // User wants to change the checking interest rate
        // Convert percent to decimal
        double rate = in.nextDouble() / 100.0;
        // Modify the interest rate
        Values.putCheckingInterestRate(rate);
        break;
      }
      case 3: {
        System.out.println("Enter a new interest rate for savings accounts as a percentage: "); // User wants to cahnge saving interest rate
        double rate = in.nextDouble() / 100.0; // Convert percent to decimal
        Values.putSavingsInterestRate(rate); // Modify the interest rate
        break;
      }
      case 4:
        mainMenu(clients); // Go back (mainMenu)
        break;

      default: // Invalid option
        System.out.println("Invalid option.");
        break;
    }
  }

  // Kushal Prajapati & Sachkeerat Brar
  public static void changePassword(ClientList clients) throws IOException {
    // This method allows the user to change the password of the bank
    Scanner in = new Scanner(System.in);

    // Have readers and writers to update the files

    // Verify is the user knows their password to change the password
    if (!verifyPassword()) {
      exit(clients);
      return;
    }

    // Get the user's new password
    System.out.println("Enter your new password: ");
    String newPassword = in.next();
    System.out.println(newPassword);

    // Get the data of each file
    String data = Values.getSuperInfo();
    String dataOld = Values.getSuperInfoOld();

    // Gets the info from the data (password is only after the period)
    String newInfoOld = dataOld.substring(0, dataOld.indexOf(".") + 1) + Values.convert(newPassword);
    String newInfo = data.substring(0, data.indexOf(".") + 1) + Values.convert(newPassword);

    // Change the date in the file (SuperInfo and SuperInfoOld)
    Values.writeToSuperInfo(newInfo);
    Values.writeToSuperInfoOld(newInfoOld);
  }

  // Kushal & Nimay Desai
  public static void handleClients(ClientList clients) throws IOException {
    // deals with withdrawing and handling the clients such as withdraw and depositing money
    // This function takes in list of clients which represents the list of clients
    // This function does not return anything as everything is modified

    Scanner in = new Scanner(System.in);
    System.out.println("1 --> Create Client"); // Create clients for the list
    System.out.println("2 --> Create Account"); // Create accounts for the list
    System.out.println("3 --> Delete Client"); // Delete clients from the list
    System.out.println("4 --> Delete Account"); // Delete accounts from the list
    System.out.println("5 --> Withdraw or Deposit Money"); // withdraw or deposit money
    System.out.println("6 --> Transfer Money"); // transfer money from one user to another user
    System.out.println("7 --> View Balance"); // View a client's balance
    System.out.println("8 --> View Clients"); // Change info
    System.out.println("9 --> Filter Clients"); // Filter by age
    System.out.println("10 --> Back"); // Go back

    int opt;
    do { // Prompt user for option
      System.out.println("Enter your option: ");
      opt = in.nextInt();
    } while ((opt < 1) || (opt > 10)); // Until user enters valid option

    switch (opt) {
      case 1 -> createClient(clients); // Create a client
      case 2 -> createAccount(clients); // Create an account for the client
      case 3 -> deleteClient(clients); // Create an account for the client
      case 4 -> deleteAccount(clients); // Create an account for the client
      case 5 -> withDepo(clients); // Withdraw and Deposit Money
      case 6 -> transfer(clients); // Transfer Money Between Two Users
      case 7 -> viewBal(clients); //  View Balance of the Client
      case 8 -> viewInfo(clients); // Change name or account details
      case 9 -> filterClients(clients); // View Clients By Age
      case 10 -> mainMenu(clients); // Go back to the main menu
    }
  }

  // Kushal Prajapati and Nimay Desai
  public static void deleteClient(ClientList clients) throws IOException {
    // Deletes a client from the list
    // Takes in a ClientList clients
    // Does not return anything as all data is deleted from the list

    System.out.println("Enter the ID of the client you would like to delete:");
    // Prompts the use for the ID
    Scanner in = new Scanner(System.in); // Create a Scanner
    int ID = in.nextInt(); // Prompt the user for an ID
    Client client = clients.searchByID(ID); // Search for the client by its ID
    if (client == null) { // Invalid ID
      System.out.println("Invalid ID.");
      return;
    }
    // Delete the client from the list
    clients.delete(client);

    // Stores the client list
    clients.storeClientList();
  }

  // Sachkeerat Brar
  public static void deleteAccount(ClientList clients) throws IOException {
    System.out.println("Enter the ID of the client whose account would like to delete:");
    Scanner in = new Scanner(System.in);
    int ID = in.nextInt();
    Client client = clients.searchByID(ID);
    if (client == null) {
      System.out.println("Invalid ID.");
      return;
    }

    int accountIdx;
    do {
      System.out.println("Enter the number of the account you would like to remove (1 - 5): ");
      accountIdx = in.nextInt() - 1;
    } while ((accountIdx < 0) || (accountIdx > 4));

    if (client.getAccounts().getAccount(accountIdx) == null)
      System.out.println("Invalid account.");
    else {
      client.getAccounts().removeAccount(accountIdx);
      clients.storeClientList();
    }
  }

  // Nimay Desai & Sachkeerat Brar
  public static void createClient(ClientList clients) throws IOException {
    // This function creates a client and adds it to the client list
    // This function takes in a ClinetList clients which represents the list of clients
    // This function does not return anything as everything is modified
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
    if (clients.getHead() == null) // Empty list
      client = new Client(name, dob);
    else // Non empty
      client = new Client(clients.lastNode().client, name, dob);

    clients.addToList(client); // Add the client to the list

    clients.storeClientList(); // Modify the list

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
    } while (client == null);

    if (client.getAccounts().getNumAccounts() == 5) {
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
      type = choice == 2 ? 's' : 'c';
    } while ((choice != 1) && (choice != 2));

    // Get the compounding periods of the account
    byte compounding;
    do {
      System.out.println("Enter the period of compounding you would like for this account: ");
      System.out.println("1 --> Annually");
      System.out.println("2 --> Semi-Annually");
      System.out.println("4 --> Quarterly");
      System.out.println("12 --> Monthly");
      compounding = input.nextByte();
    } while ((compounding != 1) && (compounding != 2) && (compounding != 4) && (compounding != 12));

    // Create the account and give it to the user
    Account a = new Account(type, compounding);
    client.getAccounts().addAccount(a);

    clients.storeClientList();
  }

  // Kushal Prajapati
  public static void filterClients(ClientList clients) {
    // Filters Clients based on parameters
    // Takes in a ClientList clients which represents the list of clients
    // Does not return anything as the clients are output to the console
    
    Scanner in = new Scanner(System.in); // Create a scanner
    // filter by the age of the client
    byte age; // Create the age
    // Prompt the user for their age
    do { // Run until a valid age is entered
      System.out.println("Enter the age of the clients you would like to view (18 - 120): ");
      age = in.nextByte();
    } while ((age < 18) || (age > 120));
    // Filter and print out the clients that are greater, equal, or less than the age
    int filterChoice;
    do {
      System.out.println("Do you want to filter by people that are greater, equal, or less than your selected age: ");
      System.out.println("1 --> Greater");
      System.out.println("2 --> Equal");
      System.out.println("3 --> Less");
      filterChoice = in.nextInt(); // Until correct option is entered
    } while ((filterChoice < 1) || (filterChoice > 3));

    ClientList temp = null; // Create client lsit

    switch (filterChoice) { // Make temp the filtered list
      case 1 -> temp = clients.ageGreaterThan(age);
      case 2 -> temp = clients.ageEqualTo(age);
      case 3 -> temp = clients.ageLessThan(age);
    }

    if (temp != null) // If temp
      temp.display(); // Display it
    else // Else display nothing
      System.out.println("None.");
  }

  // Nimay Desai
  public static void withDepo(ClientList clients) throws IOException {
    // This method displays a menu to withdraw and deposit
    // It takes in a ClientList clients which represents the list of clients down to other methods

    Scanner input = new Scanner(System.in); // Create a scanner
    int opt; // Create a variable representing the user option
    do { // Until correct option is entered
      System.out.println("Press 1 to withdraw: "); // Prompt the user to withdraw or deposit
      System.out.println("Press 2 to deposit: ");
      opt = input.nextInt();
    } while ((opt != 1) && (opt != 2));
    switch (opt) {
      case 1 -> withdraw(clients); // Withdraw
      case 2 -> deposit(clients); // Deposit
    }
  }

  // Nimay Desai
  public static void withdraw(ClientList clients) throws IOException {

    // This function does not return anything as everything is modified
    // This method withdraws money from the account
    // It takes in a client list clients which represents the list of clients

    Scanner input = new Scanner(System.in); // Create a scanner
    System.out.println("Enter the ID of the client"); // Prompt the user for the ID
    int ID = input.nextInt(); // Get the ID

    Client client = clients.searchByID(ID); // Get a client based on the enetered id

    if (client == null) { // Make sure client is valid
      System.out.println("Invalid client.");
      return;
    }

    int accountIdx;
    do { // Until valid account is entered
      System.out.println("Enter the number of the account you would like to withdraw from (1 - 5): ");
      accountIdx = input.nextInt() - 1;
    } while ((accountIdx < 0) || (accountIdx > 4));
    Account account = client.getAccounts().getAccount(accountIdx); // Gets the account based on the index

    if (account == null) {
      System.out.println("Invalid account.");
      return;
    }

    System.out.println("Enter how much money you would like to withdraw");
    double val = input.nextDouble();
    account.withdraw(val);

    clients.storeClientList();

    mainMenu(clients);
  }
  // Nimay Desai
  public static void deposit(ClientList clients) throws IOException {
    // This function deposits moeny into the acconunt using user input
    // This functoin takes in a lLientList clients which represents the list of clients
    // This function does not return anything as everything is modified
    Scanner input = new Scanner(System.in); // Create a scanner
    System.out.println("Enter the ID of the client"); // Prompt for ID
    int ID = input.nextInt(); // Take in ID

    Client client = clients.searchByID(ID); // Search by ID

    if (client == null) { // No client with that ID
      System.out.println("Invalid client.");
      return;
    }

    int accountIdx; // Create account index
    do {
      System.out.println("Enter the number of the account you would like to deposit to (1 - 5): "); // Prompt user for account
      accountIdx = input.nextInt() - 1;
    } while ((accountIdx < 0) || (accountIdx > 4)); // Until valid option is entered
    Account account = client.getAccounts().getAccount(accountIdx); // Get account

    if (account == null) { // Invalid account
      System.out.println("Invalid account. ");
      return;
    }

    System.out.println("Enter how much money you would like to deposit"); // Prompt user for money to deposit
    double val = input.nextDouble(); // Get the value
    account.deposit(val); // Deposit

    clients.storeClientList();

    mainMenu(clients);
  }

  // Sachkeerat Brar
  public static void transfer(ClientList clients) throws IOException {
    // Transfers money between two accounts

    Scanner in = new Scanner(System.in);
    System.out.println("Enter the ID of the client: ");
    Client client = clients.searchByID(in.nextInt());

    if (client == null) {
      System.out.println("Invalid client.");
      return;
    }

    int accountIdx;
    do {
      System.out.println("Enter the number of the account you would like to withdraw from (1 - 5): ");
      accountIdx = in.nextInt() - 1;
    } while ((accountIdx < 0) || (accountIdx > 4));
    Account account = client.getAccounts().getAccount(accountIdx);

    if (account == null) {
      System.out.println("Invalid account.");
      return;
    }

    System.out.println("Enter the ID of the client you would like to transfer to: ");
    Client client2 = clients.searchByID(in.nextInt());

    if (client2 == null) {
      System.out.println("Invalid client.");
      return;
    }

    int accountIdx2;
    do {
      System.out.println("Enter the number of the account you would like to deposit to (1 - 5): ");
      accountIdx2 = in.nextInt() - 1;
    } while ((accountIdx2 < 0) || (accountIdx2 > 4));
    Account account2 = client2.getAccounts().getAccount(accountIdx2);

    if (account2 == null) {
      System.out.println("Invalid account.");
      return;
    }

    double val;
    do {
      System.out.println("Enter the amount of money you would like to transfer: ");
      val = in.nextDouble();
    } while (val <= 0);

    if (val <= account.getBalance()) {
      account.withdraw(val);
      account2.deposit(val);
    }
    clients.storeClientList();

    mainMenu(clients);
  }

  // Kushal Prajapati
  public static void viewBal(ClientList clients) {
    // Views the balance of the chosen clients
    // Takes in a ClientList clients which represents the list of clients
    // Does not return anything as the balance is shown to the console

    Scanner in = new Scanner(System.in); // Create a scanner
    System.out.println("Enter the ID of the client you would like to see: "); // Prompts the user for the ID
    int ID = in.nextInt();
    Client client = clients.searchByID(ID);
    if (client == null) {
      System.out.println("Invalid client.");
      return;
    }

    int accountIdx;
    do {
      System.out.println("Enter the number of the account you would like to see: ");
      accountIdx = in.nextInt() - 1;
    } while ((accountIdx < 0) || (accountIdx >= client.getAccounts().getNumAccounts()));
    Account account = client.getAccounts().getAccount(accountIdx);
    System.out.println("$" + account.getBalance());
  }

  // Nimay Desai
  public static void viewInfo(ClientList clients) {
    Scanner in = new Scanner(System.in);
    Client currentClient;
    System.out.println("How would you like to search the user?");
    System.out.println("Enter 1 for the name:\nEnter 2 for ID: ");

    int opt = in.nextInt();
    switch (opt) {
      case 1:
        System.out.println("Enter the name of the client: ");
        String name = in.next();
        currentClient = clients.searchByName(name);

        if (currentClient != null)
          currentClient.display();
        else
          System.out.println("None.");
        break;

      case 2:
        System.out.println("Enter the ID of the client (above 0): ");
        int id = in.nextInt();
        currentClient = clients.searchByID(id);

        if (currentClient != null)
          currentClient.display();
        else
          System.out.println("None.");

        break;

      default:
        System.out.println("Invalid option selected.");
        break;
    }
  }

  // Nimay Desai
  public static void changeData(Client currentClient, ClientList clients) {
    Scanner in = new Scanner(System.in);
    // This function takes in a Client currentClient which represents the currentCLinet
    // and a ClientList clients which is passed down to inner funciton
    // This function changes clients data
    // This function does not return anything as everything is modified
    // Get the user's choice
    int opt;
    do {
      System.out.println("What would you like to change");
      System.out.println("1 --> Change the Name");
      System.out.println("2 --> Modify Accounts");
      System.out.println("3 --> Go back");
      opt = in.nextInt();
    } while ((opt < 1) || (opt > 3));

    // Go through their choices
    switch (opt) {
      case 1:
        System.out.println("Please enter the new name: ");
        String name = in.next();
        currentClient.putName(name);
        break;
      case 2:
        modifyAccounts(currentClient, clients);
        break;
      case 3:
        viewInfo(clients);
        break;
    }
  }

  // Nimay Desai
  public static void modifyAccounts(Client currentClient, ClientList clients) {
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
  public static void changeCompounding(Account currentAccount) {
    // Changes the compounding period of the current account
    // Takes in an Account currenAccount which represents the current account
    // It does not return anything as everything is modified

    Scanner input = new Scanner(System.in); // Create Scanner
    System.out.println("Enter the compounding period value: "); // Prompt the user for the compounding period
    System.out.println("1 --> Annually\n2 --> Semi-Annually\n 4 --> Quarterly\n or 12 --> Monthly)");

    // Get the compounding period and set the account to it
    byte n = input.nextByte();
    currentAccount.putN(n);
  }

  // The main program starts by printing the title, gets the information, and then logs in or registers respectively
  public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(System.in);
    title();

    if (Values.checkIfEmpty(Values.getSuperInfoLocation()))
      register();

    ClientList clients = new ClientList();
    login(clients);

    if (!Values.checkIfEmpty(Values.getClientInfoLocation()))
      clients = ClientList.loadClientList();

    boolean choice;
    do {
      mainMenu(clients);
      System.out.println("Enter 1 to continue: ");
      choice = in.nextInt() == 1;
    } while (choice);

    exit(clients);
  }
}
