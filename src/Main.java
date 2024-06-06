import java.io.*;
import java.util.Scanner;

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

  // Nimay Desai
  public void instructions() throws IOException {
    Scanner in = new Scanner(System.in);
    System.out.println("Welcome to the Bank!");
    System.out.println("Enter Your Name: ");
    String name = in.nextLine();

    System.out.println("Please enter the Bank password.\nIf first use, please enter a new password.");

  }

  // Nimay Desai
  // Logins in returns whether it is valid
  public static boolean checkPassword(String password, String location) throws IOException {
    FileReader fr = new FileReader(location);
    BufferedReader br = new BufferedReader(fr);
    return Values.convert(br.readLine()).equals(password);
  }

  // Nimay Desai
  public static void Register(String location) throws IOException {
    Scanner input = new Scanner(System.in);
    FileWriter fw = new FileWriter(location);
    PrintWriter pw = new PrintWriter(fw);
    System.out.println("The program has detected that this is the first time you have opened this application.\nAccount creation will begin.");

    String password = null;
    String username = null;
    while (password == null || username == null) {
      System.out.println("Please enter a username ");
      username = input.next();
      System.out.println("Please enter a password for security: ");
      password = input.next();
    }
    System.out.println("You have successfully registered. Please open the program again to login with the new password");
    pw.println(username);
    pw.println(Values.convert(password));
    pw.close();
    fw.close();
  }

  // Nimay Desai
  public static boolean Login() throws IOException {
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter a username ");
    String username = input.next();
    System.out.println("Please enter a password for security: ");
    String password = input.next();
    int t = 1;
    while (!checkPassword(password, Values.getSuperInfoLocation())) {
      if (t >= 5) {
        System.out.println("Wrong password entered 5 times. Exiting...");
        return false;
      }
      System.out.println("Wrong password entered, please try again. Program will exit in " + (5-t) + " failed " + (t == 4 ? "attempt":"attempts")+".");
      password = input.next();
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
  public static void updateDate(String date) throws IOException {
    BufferedReader brOld = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));
    BufferedReader brNew = new BufferedReader(new FileReader(Values.getSuperInfoLocation()));

    if((brOld.readLine()).substring(0, 9).equals((brNew.readLine()).substring(0, 9)))
      updateDateNew(date);
    else
      updateDateBoth(date);
  }

  // Sachkeerat Brar
  public static void updateDateNew(String date) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(Values.getSuperInfoLocation()));
    BufferedReader br = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));

    String data = (br.readLine()).substring(10);
    pw.println(Values.convert(date) + "." + data);
  }

  // Sachkeerat Brar
  public static void updateDateBoth(String date) throws IOException {
    PrintWriter pwOld = new PrintWriter(new FileWriter(Values.getSuperInfoOldLocation()));
    PrintWriter pwNew = new PrintWriter(new FileWriter(Values.getSuperInfoLocation()));
    BufferedReader brOld = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));
    BufferedReader brNew = new BufferedReader(new FileReader(Values.getSuperInfoLocation()));

    String oldData = brNew.readLine();
    String olderData = brOld.readLine();

    String updatedOldData = oldData.substring(0, 9) + olderData.substring(10);
    pwOld.println(updatedOldData);

    String newData = Values.convert(date) + oldData.substring(10);
    pwNew.println(newData);
  }

  // Sachkeerat Brar
  public static boolean validDate(String date) {
    // Sachkeerat Brar
    int year,  month, day;
    try {
      year = Integer.parseInt(date.substring(0, 4));
      if(year <= 1904) {
        System.out.println("Do not lie about your age.");
        return false;
      }

      month = Integer.parseInt(date.substring(5, 7));
      day = Integer.parseInt(date.substring(8, 10));

      if((year > Values.getCurrentYear()) || (month < 1) || (month > 12) || (day < 1) || (day > 31)) {
        System.out.println("Please input a valid date.");
        return false;
      }
    }
    catch (Exception _) {
      System.out.println("Invalid date format. Please input a date as yyyy/mm/dd.");
      return false;
    }

    return true;
  }

  // Sachkeerat Brar
  public static boolean validAge(String date) {
    // Sachkeerat Brar
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
    if ((Values.getCurrentMonth() < month) || (Values.getCurrentMonth() == month && Values.getCurrentDay() < day))
      currentAge--;

    if(currentAge < 18) {
      System.out.println("You must be 18 years old to use this bank.");
      return false;
    }

    return true;
  }


  public void MainMenu() throws IOException {
    System.out.println("Welcome to the Bank!");
    System.out.println("What would you like to do?");

    System.out.println("1 --> Handle Clients");
    System.out.println("2 --> Modify Bank");
    System.out.println("3 --> Log Out");
    System.out.println("4 -> Exit program");

    Scanner in = new Scanner(System.in);
    System.out.println("Enter your option: ");
    int opt = in.nextInt();
    switch (opt) {
      case 1 -> HandleClients();
      case 2 -> ModifyBank();
      case 3 -> Login();
    }

  }

  public void ModifyBank() {
    System.out.println("Select your option ");
    System.out.println("1 -->  Change Password");
    System.out.println("2 -->  Change Password");
  }

  public void HandleClients() throws IOException {
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
    } while ((opt < 1) || (opt > 5));


    switch(opt){
      case 1 -> WithDepo();
      case 2 -> Transfer();
      case 3 -> ViewBal();
      case 4 -> ChangeInfo();
      case 5 -> MainMenu();
    }
  }

  public void WithDepo(){}

  public void Transfer(){}

  public void ViewBal(){}

  public void ChangeInfo(){}

  public static void main(String[] args) throws IOException {
    Client a = new Client();
    Client b = new Client("Bob Doe", "2008/11/11", a);
    Client c = new Client(b);
    Client[] clients = {a, b, c};
    ClientList testing = new ClientList(clients);
    testing.addToList(b);
    testing.addToList(c);
    System.out.println(testing);


    Client Sach = new Client("Sachkeerat Brar", "2003/11/11", a);
    Sach.addAccount('b', 100);
    Sach.addAccount('c', 200);
    Sach.addAccount('s', 300);
    Client.StoreClient(Sach);
    Client.LoadClient("Sachkeerat Brar");
  }
   
}
