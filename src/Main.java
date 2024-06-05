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
  public static boolean checkPassword(String password) throws IOException {
    FileReader fr = new FileReader("src/SuperInfo");
    BufferedReader br = new BufferedReader(fr);
    return Values.convert(br.readLine()).equals(password);
  }

  // Nimay Desai
  public static void Register() throws IOException {
    Scanner input = new Scanner(System.in);
    FileWriter fw = new FileWriter("src/SuperInfo");
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

  // Nimay
  public static boolean Login() throws IOException {
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter a username ");
    String username = input.next();
    System.out.println("Please enter a password for security: ");
    String password = input.next();
    int t = 1;
    while (!checkPassword(password)) {
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

  public static boolean validDate(String date) {
    // Sachkeerat Brar
    int year, month, day;
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

      int currentAge = Values.getCurrentYear() - year;

      // See if they are a year younger
      if ((Values.getCurrentMonth() < month) || (Values.getCurrentMonth() == month && Values.getCurrentDay() < day))
        currentAge--;

      if(currentAge < 18) {
        System.out.println("You must be 18 years old to use this bank.");
        return false;
      }

    }
    catch (Exception _) {
      System.out.println("Invalid date format. Please input a date as yyyy/mm/dd.");
      return false;
    }

    return true;
  }
  
  // Nimay Desai
  public static void mainTesting(String[] args) throws IOException {
    FileReader fr = new FileReader("src/SuperInfo");
    BufferedReader br = new BufferedReader(fr);
    title();
    if (br.readLine() == null) {
      Register();
    } else {
      if (Login()) {
        System.out.println("WELCOME TO THE MAIN PROGRAM ");
      }
    }
  }
  public static void main(String[] args) throws IOException {
    Client a = new Client();
    Client b = new Client("Bob Doe", "2008/11/11", a);
    Client c = new Client(b);
    Client[] clients = {a, b, c};
    ClientList testing = new ClientList(clients);
    testing.addToList(b);
    testing.addToList(c);
    System.out.println(testing);

    //Client kushal=new Client("Kushal Prajapati", "2008/11/11");
    //Client.StoreClient(kushal);

  }
   
}
