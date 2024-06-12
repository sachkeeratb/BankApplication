// Imported objects used

import java.io.*;
import java.util.Scanner;

/* Sachkeerat Brar
 * This class was made in order to store or modify values which would be accessed by multiple files.
 * This is so we can have cleaner code and a better development experience
 */
public class Values {
  // Fields

  // Dates
  private static int currentYear;
  private static int currentMonth;
  private static int currentDay;

  // Last logged in dates
  private static int previousYear;
  private static int previousMonth;
  private static int previousDay;

  // Interest
  private static double checkingInterestRate = 0.04; /// 4%
  private static double savingsInterestRate = 0.065; // 6.5%

  // Nimay Desai
  // Directory
  private static final String DIR = "src/";


  // Getting file locations
  public static String getClientInfoLocation() {
    return DIR + "ClientInfo";
  }
  public static String getSuperInfoLocation() {
    return DIR + "SuperInfo";
  }
  public static String getSuperInfoOldLocation() {
    return DIR + "SuperInfoOld";
  }
  public static String getInterestInfoLocation() {
    return DIR + "InterestInfo";
  }

  // Getting dates
  public static int getCurrentYear() {
    return currentYear;
  }
  public static int getCurrentMonth() {
    return currentMonth;
  }
  public static int getCurrentDay() {
    return currentDay;
  }
  public static int getPreviousYear() {
    return previousYear;
  }
  public static int getPreviousMonth() {
    return previousMonth;
  }
  public static int getPreviousDay() {
    return previousDay;
  }

  // Getting and updating the interest rates
  public static double getCheckingInterestRate() {
    return checkingInterestRate;
  }
  public static double getSavingsInterestRate() {
    return savingsInterestRate;
  }

  
  // Putting a constant rate for all savings accounts
  public static void putCheckingInterestRate(double rate) throws IOException {
    // If the rate is invalid if it is positive warn the users
    if (rate <= 0) {
      System.out.println("Invalid rate: Must be positive.");
      return;
    }
    // If the rate is greater than 10% revalidate the password
    if (rate >= 0.1) {
      System.out.println("The rate you have inputted is greater than 10%. Please input your password to confirm this change.");

      Scanner in = new Scanner(System.in);
      String input;

      byte tries = 5;
      do {
        input = in.next();
        if (!input.equals(convert(getPassword()))) {
          System.out.println("Invalid password. " + tries + " tries left.");
          tries--;
        } else
          break;
      } while (tries > 0);
      if (tries == 0) {
        System.out.println("You have run out of tries. Rate change denied.");
        return;
      }
    }

    // Update the Interest rate for checkings and write to file
    checkingInterestRate = rate;
    String data = getInterestInfo();
    String dataToPrint = convert(String.valueOf(checkingInterestRate)) + data.substring(data.indexOf(","));
    writeToInterest(dataToPrint);
  }
  public static void putSavingsInterestRate(double rate) throws IOException {
    // If the rate is invalid, warn the user
    if (rate <= 0) {
      System.out.println("Invalid rate: Must be positive.");
      return;
    }

    // If the rate is greater than 10%, make the user re-enter your password
    if (rate >= 0.1) {
      System.out.println("The rate you have inputted is greater than 10%. Please input your password to confirm this change.");

      Scanner in = new Scanner(System.in);
      String input;

      byte tries = 5;
      do {
        input = in.next();
        if (!input.equals(convert(getPassword()))) {
          System.out.println("Invalid password. " + tries + " tries left.");
          tries--;
        } else
          break;
      } while (tries > 0);
      if (tries == 0) {
        System.out.println("You have run out of tries. Rate change denied.");
        return;
      }
    }
    // update the interest rate of the savings account and write to file
    savingsInterestRate = rate;
    String data = getInterestInfo();
    String dataToPrint = data.substring(0, data.indexOf(",") + 1) + convert(String.valueOf(savingsInterestRate));
    writeToInterest(dataToPrint);
  }
  public static void updateInterestRate() throws IOException {
    String data = getInterestInfo();

    if (data == null) {
      checkingInterestRate = 0.04;
      savingsInterestRate = 0.065;
      writeToInterest(convert(checkingInterestRate + "," + savingsInterestRate));
    } else {
      checkingInterestRate = Double.parseDouble(data.substring(0, data.indexOf(",")));
      savingsInterestRate = Double.parseDouble(data.substring(data.indexOf(",") + 1));
    }
  }

  // Put the dates
  public static void putCurrentDate(String date) throws IOException {
    // Update the date provided by user input in format YYYY/MM/DD
    // each of the substrings of the date string will update the date in separate methods
    putCurrentYear(Integer.parseInt(date.substring(0, 4)));
    putCurrentMonth(Integer.parseInt(date.substring(5, date.indexOf("/", 5))));
    putCurrentDay(Integer.parseInt(date.substring(date.indexOf("/", 5) + 1)));
    updateDateValues();
  }
  public static void putCurrentYear(int year) throws IOException {
    // Update the year from the input
    if (year >= currentYear) {
      previousYear = currentYear;
      currentYear = year;
      updateDateValues();
    }
  }
  public static void putCurrentMonth(int month) throws IOException {
    // Update the month from the input
    if ((month >= 1) && (month <= 12)) {
      previousMonth = currentMonth;
      currentMonth = month;
      updateDateValues();
    }
  }
  public static void putCurrentDay(int day) throws IOException {
    // Update the current day from the input
    switch (currentMonth) {
      // If the month has 31 dates
      case 1, 3, 5, 7, 8, 10, 12:
        if ((day >= 1) && (day <= 31)) {
          previousDay = currentDay;
          currentDay = day;
          updateDateValues();
        }
        break;

      // If the month has 30 days  
      case 4, 6, 9, 11:
        if ((day >= 1) && (day <= 30)) {
          previousDay = currentDay;
          currentDay = day;
          updateDateValues();
        }
        break;

      // If the month is very special  
      case 2:
        if (isLeapYear(currentYear) && (day >= 1)) {
          if (day <= 29) {
            previousDay = currentDay;
            currentDay = day;
            updateDateValues();
          }
        } else {
          if (day <= 28) {
            previousDay = currentDay;
            currentDay = day;
            updateDateValues();
          }
        }
        break;

      default:
        System.out.println("Invalid day.");
        break;
    }
  }
  private static boolean isLeapYear(int year) {
    // Check if the year is a leap year (day validation for february)
    return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
  }

  // Update all the date values in the memory
  public static void loadDates() throws IOException {
    String newData = convert(getSuperInfo().substring(0, getSuperInfo().indexOf(".")));
    String oldData = convert(getSuperInfoOld().substring(0, getSuperInfoOld().indexOf(".")));

    currentYear = Integer.parseInt(newData.substring(0, newData.indexOf("/")));
    currentMonth = Integer.parseInt(newData.substring(newData.indexOf("/") + 1, newData.lastIndexOf("/")));
    currentDay = Integer.parseInt(newData.substring(newData.lastIndexOf("/") + 1));

    previousYear = Integer.parseInt(oldData.substring(0, oldData.indexOf("/")));
    previousMonth = Integer.parseInt(oldData.substring(oldData.indexOf("/") + 1, oldData.lastIndexOf("/")));
    previousDay = Integer.parseInt(oldData.substring(oldData.lastIndexOf("/") + 1));
  }

  // Update all the date values in the text files
  public static void updateDateValues() throws IOException {
    // Read from old and new Data
    String oldData = getSuperInfoOld();
    String newData = getSuperInfo();
    // If old date matches the new date, update the new date as the same data
    if (oldData.substring(0, oldData.indexOf(".")).equals(newData.substring(0, newData.indexOf("."))))
      updateDateNew(oldData);
      // If the old date does not match the new date, go to update both old and new data
    else
      updateDateBoth(newData, oldData);
  }
  private static void updateDateNew(String oldData) throws IOException {
    // Update the new date in superinfo from the old superinfo to the new superinfo

    String data = oldData.substring(oldData.indexOf("."));
    String date = convert(currentYear + "/" + currentMonth + "/" + currentDay);
    data = date + data;
    writeToSuperInfo(data);
  }
  private static void updateDateBoth(String oldData, String olderData) throws IOException {
    // Puts superinfo into superinfo old and put the new data into superinfo
    String newDate = currentYear + "/" + currentMonth + "/" + currentDay;
    String oldDate = previousYear + "/" + previousMonth + "/" + previousDay;

    // Take the old date, combine it with the older data substring, and write to the superinfold file
    String updatedOldData = convert(oldDate) + olderData.substring(olderData.indexOf("."));
    writeToSuperInfoOld(updatedOldData);

    // Take the new date, combine it with the old data and write to superinfo file
    String newData = convert(newDate) + oldData.substring(oldData.indexOf('.'));
    writeToSuperInfo(newData);
  }

  // Passwords
  private static String getPassword() throws IOException {
    // This method gives the encrypted password stored in the file
    String data = getSuperInfo(); // Store the data

    return data.substring(data.indexOf('.') + 1);
  }
  public static boolean comparePassword(String password) throws IOException {
    // This method compares the inputted password with the stored password
    return password.equals(convert(getPassword()));
  }
  public static boolean checkIfEmpty(String location) throws IOException {
    // This method checks if the file is empty
    BufferedReader br = new BufferedReader(new FileReader(location));
    String data = br.readLine();
    br.close();
    return data == null;
  }

  // Methods to read and write files in order to have no resource leaks & cleaner code
  public static void writeToSuperInfo(String data) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(getSuperInfoLocation()));
    pw.println(data);
    pw.close();
  }
  public static void writeToSuperInfoOld(String data) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(getSuperInfoOldLocation()));
    pw.println(data);
    pw.close();
  }
  public static void writeToClients(String data) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(getClientInfoLocation()));
    pw.println(data);
    pw.close();
  }
  public static void writeToInterest(String data) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(getInterestInfoLocation()));
    pw.println(data);
    pw.close();
  }
  public static String getSuperInfo() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(getSuperInfoLocation()));
    String data = br.readLine();

    br.close();

    return data;
  }
  public static String getSuperInfoOld() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(getSuperInfoOldLocation()));
    String data = br.readLine();
    br.close();
    return data;
  }
  public static String getClientInfo() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(getClientInfoLocation()));
    String data = br.readLine();
    br.close();
    return data;
  }
  public static String getInterestInfo() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(getInterestInfoLocation()));
    String data = br.readLine();
    br.close();
    return data;
  }


  // Nimay Desai
  public static String convert(String data) {
    /* This method acts as the main encryption/decryption of the entire program
     * This method will return a string where the original string's data is altered
     * A is flipped to Z, 0 is flipped to 9, and vice-versa
     * Example: password123 --> kzhhdliw876
     * Example: kzhhdliw876 --> password123
     */
    String convertedData = ""; // Store the flipped data

    for (char c : data.toCharArray())
      // Check if the character is a number between 0 and 9
      if ((c >= '0') && (c <= '9'))
        convertedData += (char) ('0' + '9' - c);
        // Check if character is uppercase letter
      else if ((c >= 'A') && (c <= 'Z'))
        convertedData += (char) ('A' + 'Z' - c);
        // Check if character is lowercase letter
      else if ((c >= 'a') && (c <= 'z'))
        convertedData += (char) ('a' + 'z' - c);
        // Else add the special character
      else
        convertedData += c;

    return convertedData;
  }
}
