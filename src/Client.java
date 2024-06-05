import java.util.Scanner;
import java.io.*;

public class Client {

  private String name;
  private int ID;
  private int age;
  private String dob;
  private AccountList accounts = new AccountList();

  public Client() {
    ID = 0;
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
    ID=0;
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

  public String getName() {
    return name;
  }

  public String getDOB() {
    return dob;
  }

  public int getAge() {
    return age;
  }

  private static int calculateAge(String date) {
    // Store the current values
    final int CURRENT_YEAR = 2024;
    final int CURRENT_MONTH = 6;
    final int CURRENT_DAY = 1;

    // Store the birth values as integers
    int birthYear = Integer.parseInt(date.substring(0, 3));
    int birthMonth = Integer.parseInt(date.substring(5, 6));
    int birthDay = Integer.parseInt(date.substring(8, 9));

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

  public void printInfo() {
    System.out.println("Name: " + name);
    System.out.println("ID: " + ID);
    System.out.println("Age: " + age);
    System.out.println("Date of Birth: " + dob);
    System.out.println("Accounts:");
    for (int i = 0; i < accounts.getNumAccounts(); i++) {
      // PRINT ACCOUNT INFO FOR EACH ACCOUNT
      Account tempcount=accounts.getAccount(i);
      System.out.println("Account Type: " + tempcount.AccountInfo());
    }
  }



  public static void getClientInfo(Client c) {
    c.printInfo();
    c.accounts.printAccounts();
  }

  public static void StoreClient(Client c) throws IOException {
    FileWriter fw = new FileWriter("src/ClientInfo", true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(c.name + "\n");
    bw.write(c.ID + "\n");
    bw.write(c.age + "\n");
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

  /*
  public static void LoadClient(Client c) throws IOException{
    FileReader fr = new FileReader("src/ClientInfo");
    BufferedReader br = new BufferedReader(fr);

    c.name = br.readLine();
    c.ID = Integer.parseInt(br.readLine());
    c.age = Integer.parseInt(br.readLine());
    c.dob = br.readLine();
    c.accounts = new AccountList();
    int numAccounts = Integer.parseInt(br.readLine());
    for (int i = 0; i < numAccounts; i++) {
      AccountType t = AccountType.valueOf(br.readLine());
      double balance = Double.parseDouble(br.readLine());
      TransactionHistory h = new TransactionHistory();
      String[] transactions = br.readLine().split(" ");
      for (int j = 0; j < transactions.length; j++) {
        h.newTransaction(Double.parseDouble(transactions[j]));
      }
      Account a = new Account(t, balance, h);
      c.accounts.addAccount(a);
    }
  }
  */

}