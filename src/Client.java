// Kushal Prajapati
import java.util.Scanner;
import java.io.*;

public class Client {

  private String name;
  private int ID;
  private int age;
  private String dob;
  private AccountList accounts = new AccountList();


  public Client() {
    String name = "John Doe";
    dob = "2006/01/01";
    age = calculateAge("2006/01/01");
    accounts = new AccountList();

  }

  public Client(String n, String date) {
    name = n;
    dob = date;
    age = calculateAge(date);
    accounts = new AccountList();
  }

  public Client(String n, String date, Client lastClient) {
    name = n;
    dob = date;
    ID = lastClient.ID++;
    age = calculateAge(date);
    accounts = new AccountList();
  }


  public void putID(int newID) {
    ID = newID;
  }

  public void putName(String newName) {
    name = newName;
  }

  public void putDOB(String newDOB) {
    dob = newDOB;
  }

  public int getID() {
    return ID;
  }

  public int getAge () {
    return age;
  }


  public String getName() {
    // Nimay Desai
    return name;
  }

  private static int calculateAge(String date) {
    // Store the current values
    final int CURRENT_YEAR = 2024;
    final int CURRENT_MONTH = 6;
    final int CURRENT_DAY = 1;

    // Store the birth values as integers
    int birthYear = Integer.parseInt(date.substring(0, 4));
    int birthMonth = Integer.parseInt(date.substring(5, 7));
    int birthDay = Integer.parseInt(date.substring(8, 10));

    // Store the age
    int currentAge = CURRENT_YEAR - birthYear;

    // See if they are a year younger
    if (CURRENT_MONTH < birthMonth) {
      currentAge--;
    } else if (CURRENT_MONTH == birthMonth && CURRENT_DAY < birthDay) {
      currentAge--;
    }

    // Return the age
    return currentAge;
  }

  //store accounts all into one string

  public String storeAccounts() {
    String accountString = "";
    // store balance and type of account
    return "";
  }

  // TODO: after finishing account (sachkeerat)
  /*
  //load accounts from string
  public void loadAccounts(String accountString) {
    String[] accountArray = accountString.split("\n");
    for (int i = 0; i < accountArray.length; i++) {
      AccountType t = new AccountType();
      if (accountArray[i].equals("Checking")) {
        t.checking = true;
      } else if (accountArray[i].equals("Savings")) {
        t.savings = true;
      } else if (accountArray[i].equals("Investment")) {
        t.investment = true;
      }
      accounts.addAccount(t);
    }
  }

  public void saveClient() throws IOException {

    FileWriter fw = new FileWriter("src/ClientInfo", true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(name + "\n" + ID + "\n" + dob + "\n" + storeAccounts() + "\n");
    bw.close();
  }

  public void loadClient(int ID) throws IOException {

    FileReader fr = new FileReader("src/ClientInfo");
    BufferedReader br = new BufferedReader(fr);
    String line = br.readLine();
    while (line != null) {
      if (Integer.parseInt(line) == ID) {
        name = br.readLine();
        dob = br.readLine();
        loadAccounts(br.readLine());
        break;
      }
      line = br.readLine();
    }
    br.close();

  }
  */

  public void printInfo() {
    System.out.println("Name: " + name);
    System.out.println("ID: " + ID);
    System.out.println("Age: " + age);
    System.out.println("Date of Birth: " + dob);
  }

  public void newAccount(char t) {
    accounts.addAccount(t);
  }

  public void deleteAccount(char t) {
    accounts.removeAccount(t);
  }

  public void deposit(double amount, int num) {
    accounts.getAccount(num).deposit(amount);
  }

  public void withdraw(double amount, int num) {
    accounts.getAccount(num).withdraw(amount);
  }

  public static void transfer(double amount, int num1, int num2, Client c1, Client c2) {
    c1.accounts.getAccount(num1).transfer(amount, c2.accounts.getAccount(num2));
  }
}
