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
    int age = calculateAge("2006/01/01");
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

  public void newAccount(AccountType t) {
    accounts.addAccount(t);
  }

  public void deleteAccount(AccountType t) {
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

  public static void getClientInfo(Client c) {
    c.printInfo();
    c.accounts.printAccounts();
  }

}
