import java.util.Scanner;
import java.io.*;

public class Client {

  private String name;
  private int ID;
  private final int age;
  private String dob;
  private final AccountList accounts;

  public Client() {
    ID = 1;
    name = "John Doe";
    dob = "2006/01/01";
    age = calculateAge("2006/01/01");
    accounts = new AccountList();
  }

  public Client(Client lastClient) {
    name = "John Doe";
    dob = "2006/01/01";
    age = calculateAge("2006/01/01");
    accounts = new AccountList();
    ID = lastClient.getID() + 1;
  }

  public Client(String n, String date) {
    name = n;
    dob = date;
    age = calculateAge(date);
    accounts = new AccountList();
    ID = 1;
  }

  public Client(String n, String date, Client lastClient) {
    name = n;
    dob = date;
    ID = lastClient.ID++;
    age = calculateAge(date);
    accounts = new AccountList();
  }

  private static int calculateAge(String date) {
    // Store the birth values as integers
    int birthYear = Integer.parseInt(date.substring(0, 4));
    int birthMonth = Integer.parseInt(date.substring(5, 6));
    int birthDay = Integer.parseInt(date.substring(8, 9));

    // Store the age
    int currentAge = Values.getCurrentYear() - birthYear;

    // See if they are a year younger
    if ((Values.getCurrentMonth() < birthMonth) || ((Values.getCurrentMonth() == birthMonth) && (Values.getCurrentDay() < birthDay)))
      currentAge--;

    // Return the age
    return currentAge;
  }

  public static void getClientInfo(Client c) {
    c.printInfo();
    c.accounts.printAccounts();
  }

  // method to store all information into a client using the FileWriter library
  public static void StoreClient(Client c) throws IOException {
    FileWriter fw = new FileWriter("src/ClientInfo", true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(c.name + "\n");
    bw.write(c.ID + "\n");
    bw.write(c.dob + "\n");
    bw.write(c.accounts.getNumAccounts() + "\n");
    for (int i = 0; i < c.accounts.getNumAccounts(); i++) {
      Account temp_account = c.accounts.getAccount(i);
      bw.write(temp_account.getAccountType() + "\n");
      bw.write(temp_account.getBalance() + "\n");
    }
    bw.close();
    fw.close();
  }

  // method to load all information into a client using the FileReader library
  public static void LoadClient(String s) throws IOException {

    FileReader fr = new FileReader("src/ClientInfo");
    BufferedReader br = new BufferedReader(fr);
    String line;
    while ((line = br.readLine()) != null) {
      if (line.equals(s)) {
        Client c = new Client();
        c.putName(line);
        c.putID(Integer.parseInt(br.readLine()));
        c.putDOB(br.readLine());
        int numAccounts = Integer.parseInt(br.readLine());
        for (int i = 0; i < numAccounts; i++) {
          char accountType = br.readLine().charAt(0);
          double balance = Double.parseDouble(br.readLine());
          c.accounts.addAccount(accountType, balance);
        }
        getClientInfo(c);
        break;
      }
    }
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

  public String getName() {
    return name;
  }

  public String getDOB() {
    return dob;
  }

  public int getAge() {
    return age;
  }

  public AccountList getAccounts() {
    return accounts;
  }

  public void addAccount(char type, double balance) {
    accounts.addAccount(type, balance);
  }

  public void printInfo() {
    System.out.println("Name: " + name);
    System.out.println("ID: " + ID);
    System.out.println("Age: " + calculateAge(dob));
    System.out.println("Date of Birth: " + dob);
    System.out.println("Accounts: ");
    for (int i = 0; i < accounts.getNumAccounts(); i++) {
      // PRINT ACCOUNT INFO FOR EACH ACCOUNT
      Account tempcount = accounts.getAccount(i);
      System.out.println(tempcount.accountInfo());
    }
  }
}