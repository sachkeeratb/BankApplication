import java.io.*;
import java.util.Scanner;

public class Main {

  // Sachkeerat Brar
  // title screen  of the program
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
    clients.storeClientList();
    System.out.println("Thank you for using this program.");
    System.exit(0);
  }

  // Nimay Desai
  public static void register() throws IOException {
    Scanner in = new Scanner(System.in);
    FileWriter fw = new FileWriter(Values.getSuperInfoLocation());
    PrintWriter pw = new PrintWriter(fw);
    PrintWriter pwOld = new PrintWriter(new FileWriter(Values.getSuperInfoOldLocation()));
    System.out.println("The program has detected that this is the first time you have opened this application.\nAccount creation will begin.");

    String date;
    do {
      System.out.println("Enter the date as yyyy/mm/dd: ");
      date = in.next();
    } while(!validDate(date));

    System.out.println("Please enter a password for security: ");
    String password = in.next();

    System.out.println("You have successfully registered.");
    pw.println(Values.convert(date + "." + password));
    pwOld.println(Values.convert(date + "." + password));

    pw.flush();
    pwOld.flush();
    fw.flush();
  }

  // Nimay Desai
  public static boolean login(ClientList clients) throws IOException {
    Scanner in = new Scanner(System.in);

    System.out.println("Please enter your password to login: ");
    String password = in.next();
    int t = 1;

    String date = getDate();
    Values.putCurrentDate(date);

    for (ClientList.Node temp = clients.getHead(); temp != null; temp = temp.link) {
      for(int i = 0; i < 5; i++) {
        if(temp.client.getAccounts().getAccount(i) != null)
          temp.client.getAccounts().getAccount(i).addInterest();
      }
    }

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
    } while(!validDate(date));

    return date;
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

// Kushal Prajapati
  // This is the main menu of the program that lets you pick between the 4 options
  public static void MainMenu(ClientList clients) throws IOException {
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
      case 1 -> HandleClients(clients);
      case 2 -> ModifyBank();
      case 3 -> login(clients);
      case 4 -> exit(clients);
    }

  }

  // Kushal Prajapati
  public static void ModifyBank() throws IOException{
    System.out.println("Select your option ");
    System.out.println("1 -->  Change Password");
    System.out.println("2 -->  Go back");
    System.out.println("More coming soon!");
    Scanner sc=new Scanner(System.in);
    int opt=sc.nextInt();
    switch(opt){
      case 1 -> ChangePassword();
    }
  }

  // Kushal Prajapati

  public static void ChangePassword() throws IOException {

    Scanner in = new Scanner(System.in);

    System.out.println("Enter your current password: ");
    String oldpassword = in.next();
    System.out.println("Enter your new password: ");
    String password = in.next();

    FileWriter fw = new FileWriter(Values.getSuperInfoLocation());
    PrintWriter pw = new PrintWriter(fw);
    BufferedReader br = new BufferedReader(new FileReader(Values.getSuperInfoLocation()));


    while (br.readLine() != null) {
      if (br.readLine().contains(oldpassword)) {
        String data = br.readLine();
        data = data.replace(oldpassword, Values.convert(password));
        pw.println(data);
      }
    }
  }



  // Nimay Desai
  public static void HandleClients(ClientList clients) throws IOException {
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
      case 5 -> MainMenu(clients);
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

  // Nimay Desai
  public static void ChangeInfo() {
    Scanner input = new Scanner(System.in);
    ClientList clients = ClientList.fromString("temp");
    ClientList.Node currentClient;
    System.out.println("How would you like to search the user");
    System.out.println("Enter 1 for name and Enter 2 for ID");
    int opt = input.nextInt();
    switch (opt) {
      case 1:
        System.out.println("Enter the name of the customer");
        String name = input.next();
        currentClient = clients.findNodeByIndex(clients.searchByName(name));
        break;
      case 2:
        System.out.println("Enter the ID of the customer (above 0)");
        int id = input.nextInt();
        currentClient = clients.findNodeByIndex(clients.searchByID(id));
        break;
    }

  }

  public static void ChangeData(Client currentClient) {
    Scanner input = new Scanner(System.in);
    System.out.println("What would you like to change");
    System.out.println("Enter 1 to change the Name");
    System.out.println("Enter 2 to Modify Accounts");
    System.out.println("Enter 3 To go back");
    int opt = input.nextInt();
    switch (opt) {
      case 1:
        System.out.println("Please enter the new name");
        String name = input.next();
        currentClient.putName(name);
        break;
      case 2:
        ModifyAccounts(currentClient);
        break;
      case 3:
        ChangeInfo();
        break;
    }
  }
  
  public static void ModifyAccounts (Client currentClient) {

  }


  public static void main(String[] args) throws IOException {
    title();
    if(Values.checkIfEmpty(Values.getSuperInfoLocation()))
      register();

    ClientList clients = new ClientList();
    if(!Values.checkIfEmpty(Values.getClientInfoLocation()))
      clients = ClientList.loadClientList();

    boolean loggedIn;
    do {
      loggedIn = login(clients);
    } while(!loggedIn);


  }
}
