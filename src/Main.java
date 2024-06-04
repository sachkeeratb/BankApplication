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
  public static String convert(String data) {
    /* This method acts as the encryption/decryption
     * This method will return a string where the original string's data is altered
     * A is flipped to Z, 0 is flipped to 9, and vice-versa
     * Example: password123 --> kzhhdliw876
     */
    String convertedData = ""; // Store the flipped data

    for(char c: data.toCharArray())
      // Check if the character is a number between 0 and 9
      if((c >= '0') && (c <= '9'))
        convertedData += (char)('0' + '9' - c);
        // Check if character is uppercase letter
      else if((c >= 'A') && (c <= 'Z'))
        convertedData += (char)('A' + 'Z' - c);
        // Check if character is lowercase letter
      else if ((c >= 'a') && (c <= 'z'))
        convertedData += (char)('a' + 'z' - c);
        // Else add just the .character
      else
        convertedData += c;

    return convertedData;
  }

  // Nimay Desai
  // Logins in returns whether it is valid
  public static boolean checkPassword(String password) throws IOException {
    FileReader fr = new FileReader("src/SuperInfo");
    BufferedReader br = new BufferedReader(fr);
    return convert(br.readLine()).equals(password);
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
    pw.println(convert(password));
    pw.close();
    fw.close();
  }

  // Nimay
  public static boolean Login () throws IOException {
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

  // Nimay Desai
  public static void main2(String[] args) throws IOException {
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
    ClientList clients = new ClientList(new Client[]{new Client(), new Client("Bob Doe", "2008/11/11"), new Client()});
    clients.ageGreaterThan(16).printlist();

  }

}
