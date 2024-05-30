// Kushal Prajapati
import java.util.Scanner;
import java.io.*;

public class Client {

  private String name;
  private int ID;
  private int age;
  private String dob;
  private AccountList accounts = new AccountList();


  public Client (){
    String name = "John Doe";
    dob = "2006/01/01";
    int age = calculateAge("2006/01/01");
  }
  public Client(String n, String date){
    name = n;
    dob = date;
    age = calculateAge(date);
  }

  public Client(String n,String date, Client lastClient){
    name = n;
    dob = date;
    ID = lastClient.ID++;
    age=calculateAge(date);
  }

  public void putID(int newID) {
    ID = newID;
  }

  public int getID(){
    return ID;
  }

  // Sachkeerat Brar
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
    if(((double) birthMonth + ((double) birthDay / 30)) > (double) (CURRENT_MONTH - (CURRENT_DAY / 30)))
      currentAge--;

    // Return the age
    return currentAge;
  }

  // Nimay Desai
//  public void createAccount(AccountType t) {
//    for (int i = 0; i < accounts.length; i++) {
//      if (accounts[i] == null) {
//        accounts[i] = new Account(t);
//        return;
//      }
//    }
//    System.out.println("ACCOUNT LIMIT EXCEEDED");
//  }


  public void SaveState() throws IOException{
  FileWriter fw = new FileWriter("C:\\Users\\Owner\\Desktop\\Coding\\School\\Bank Application\\src\\Client.java");
  PrintWriter pw=new PrintWriter(fw);

  }

  public void LoadState(){

  }


//  public static void Deposit(){
//    Scanner sc = new Scanner(System.in);
//    System.out.println("Hello,"+name +", how much money would you like to deposit");
//
//  }
//  public
}
