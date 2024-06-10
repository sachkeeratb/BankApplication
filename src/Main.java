import java.io.*;
import java.util.Scanner;

public class Main {

  // Sachkeerat Brar
  public static void title() throws IOException {
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

  // Nimay Desai
  public static void instructions() throws IOException {
    Scanner in = new Scanner(System.in);
    System.out.println("Welcome to the Bank!");
    System.out.println("What would you like to do?");
    System.out.println("1 --> Login");
    System.out.println("2 --> Register");
    System.out.println("3 --> Exit");
    int opt = in.nextInt();
    switch (opt) {
      case 1 -> login();
      case 2 -> register();
      case 3 -> System.exit(0);
    }
  }

  // Nimay Desai
  public static void register() throws IOException {
    Scanner in = new Scanner(System.in);
    FileWriter fw = new FileWriter(Values.getClientInfoLocation());
    PrintWriter pw = new PrintWriter(fw);
    System.out.println("The program has detected that this is the first time you have opened this application.\nAccount creation will begin.");

    String date;
    do {
      System.out.println("Enter the date as yyyy/mm/dd: ");
      date = in.next();
    } while(!validDate(date));

    String password = null;
    while(password == null) {
      System.out.println("Please enter a password for security: ");
      password = in.next();
    }
    System.out.println("You have successfully registered. Please open the program again to login with the new password");
    pw.println(Values.convert(date + "." + password));
    pw.flush();
    fw.flush();
  }

  // Nimay Desai
  public static boolean login() throws IOException {
    Scanner in = new Scanner(System.in);

    System.out.println("Please enter a password for security: ");
    String password = in.next();
    int t = 1;

    while(!Values.comparePassword(password)) {
      if(t >= 5) {
        System.out.println("Wrong password entered 5 times. Exiting...");
        return false;
      }

      System.out.println("Wrong password entered, please try again. Program will exit in " + (5 - t) + " failed " + (t == 4 ? "attempt" : "attempts") + ".");
      password = in.next();
      t++;
    }

    System.out.println("You have successfully logged in!");
    return true;
  }

  // Sachkeerat Brar
  public static String getDate() {
    Scanner in = new Scanner(System.in);
    String date;
    do {
      System.out.println("Enter the current date as yyyy/mm/dd: ");
      date = in.next();
    } while(!validDate(date) || !validAge(date));

    return date;
  }

  // Sachkeerat Brar
  public static boolean validDate(String date) {
    int year, month, day;

    try {
      year = Integer.parseInt(date.substring(0, 4));
      month = Integer.parseInt(date.substring(5, date.indexOf("/", 5)));
      day = Integer.parseInt(date.substring(date.indexOf("/", 5) + 1));
      System.out.println(year + "/" + month + "/" + day);

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
        System.out.println("Please input a valid date.");
        return false;
      }
    }
    catch(Exception _) {
      System.out.println("Invalid date format. Please input a date as yyyy/mm/dd.");
      return false;
    }

    return true;
  }

  // Sachkeerat Brar
  public static boolean validAge(String date) {
    int year, month, day;

    year = Integer.parseInt(date.substring(0, 4));

    if(year <= 1904) {
      System.out.println("Do not lie about your age.");
      System.exit(0);
    }

    month = Integer.parseInt(date.substring(5, 7));
    day = Integer.parseInt(date.substring(8, 10));

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
  public static void updateBalance(ClientList clients) {
    for(ClientList.Node temp = clients.getHead(); temp != null; temp = temp.link)
      for(int i = 0; i < 5; i++)
        if(temp.client.getAccounts().getAccount(i) != null)
          temp.client.getAccounts().getAccount(i).addInterest();

  }


  public static void MainMenu() throws IOException {
    System.out.println("Welcome to the Bank!");
    System.out.println("What would you like to do?");

    System.out.println("1 --> Handle Clients");
    System.out.println("2 --> Modify Bank");
    System.out.println("3 --> Log Out");
    System.out.println("4 -> Exit program");

    Scanner in = new Scanner(System.in);
    System.out.println("Enter your option: ");
    int opt = in.nextInt();
    switch(opt) {
      case 1 -> HandleClients();
      case 2 -> ModifyBank();
      case 3 -> login();
    }

  }

  public static void ModifyBank() {
    System.out.println("Select your option ");
    System.out.println("1 -->  Change Password");
    System.out.println("2 -->  Change Password");
  }

  public static void HandleClients() throws IOException {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter your option: ");
    int opt;
    do {
      System.out.println("1 --> Withdraw or Deposit Money");
      System.out.println("2 --> Transfer Money");
      System.out.println("3 --> View Balance");
      System.out.println("4 --> Change User Information");
      System.out.println("5 --> Back");
      opt = in.nextInt();
    } while((opt < 1) || (opt > 5));


    switch(opt) {
      case 1 -> WithDepo();
      case 2 -> Transfer();
      case 3 -> ViewBal();
      case 4 -> ChangeInfo();
      case 5 -> MainMenu();
    }
  }

  public static void Transfer() throws IOException {
    ClientList clients = ClientList.fromString("tempdata");
    Scanner in = new Scanner(System.in);
    System.out.println("Enter your name: ");
    String name1 = in.nextLine();
    ClientList.Node temp1 = clients.getHead();
    while (temp1 != null && !temp1.client.getName().equals(name1)) {
      temp1 = temp1.link;
    }
    System.out.println("How much would you like to transfer: ");
    double amount = in.nextDouble();
    System.out.println("What account would you like to transfer from:");
//    temp1.client.getAccounts().printAccounts();
    char accountType1 = in.next().charAt(0);
    System.out.println("Enter the user you would like to transfer to: ");
    String name2 = in.nextLine();
    ClientList.Node temp2 = clients.getHead();
    while (temp2 != null && !temp2.client.getName().equals(name2)) {
      temp2 = temp2.link;
    }
    System.out.println("What account would you like to transfer to:");
//    temp2.client.getAccounts().printAccounts();
    char accountType2 = in.next().charAt(0);
    if (temp2.client.getAccounts().getAccount(0).getBalance() < amount) {
      System.out.println("You do not have enough balance to transfer this amount.");
      return;
    } else {
      temp2.client.getAccounts().getAccount(accountType2).deposit(amount);
      temp1.client.getAccounts().getAccount(accountType1).withdraw(amount);
      System.out.println("Transfer successful.");
//      Client.StoreClient(temp1.client);
//      Client.StoreClient(temp2.client);
    }
  }
  public static void ViewBal() throws IOException {
    ClientList clients = ClientList.fromString("temp");
    Scanner in = new Scanner(System.in);
    System.out.println("Enter your name: ");
    String name = in.nextLine();
    ClientList.Node temp = clients.getHead();
    while(temp != null && !temp.client.getName().equals(name)){
      temp = temp.link;
    }
    System.out.println("What account would you like to view the balance of:");
    temp.client.getAccounts().printAccounts();
    char accountType = in.next().charAt(0);
    System.out.println("The balance of your account is: " + temp.client.getAccounts().getAccount(accountType).getBalance());
  }
  public static void WithDepo() throws IOException {
    ClientList clients = ClientList.fromString("temp");
    Scanner in = new Scanner(System.in);
    System.out.println("Enter your name: ");
    String name = in.nextLine();
    ClientList.Node temp = clients.getHead();
    while(temp != null && !temp.client.getName().equals(name)){
      temp = temp.link;
    }
    System.out.println("How much would you like to withdraw or deposit: ");
    double amount = in.nextDouble();
    System.out.println("What account would you like to withdraw or deposit from:");
    temp.client.getAccounts().printAccounts();
    char accountType = in.next().charAt(0);
    System.out.println("Would you like to withdraw or deposit?");
    System.out.println("1 --> Withdraw");
    System.out.println("2 --> Deposit");
    int opt = in.nextInt();
    if(opt == 1){
      if(temp.client.getAccounts().getAccount(0).getBalance() < amount){
        System.out.println("You do not have enough balance to withdraw this amount.");
        return;
      }
      else{
        temp.client.getAccounts().getAccount(accountType).withdraw(amount);
        System.out.println("Withdrawal successful.");
//        Client.StoreClient(temp.client);
      }
    }
    else{
      temp.client.getAccounts().getAccount(accountType).deposit(amount);
      System.out.println("Deposit successful.");
//      Client.StoreClient(temp.client);
    }
  }

  public static void ChangeInfo() {
    ClientList clients = ClientList.fromString("temp");
    System.out.println("What Information Would You Like To Change:");

  }

  public static void main(String[] args) throws IOException {
    title();
    if(Values.checkIfEmpty(Values.getSuperInfoLocation()))
      register();
    login();
  }
}

