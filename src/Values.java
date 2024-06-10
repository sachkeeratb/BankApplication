import java.io.*;
import java.util.Scanner;

/* Sachkeerat Brar
 * This class was made in order to store or modify values which would be accessed by multiple files.
 * This is so we can have cleaner code and a better development experience
 */
public class Values {
  // Dates
  private static int currentYear = 2024;
  private static int currentMonth = 6;
  private static int currentDay = 10;

  // Last logged in dates
  private static final int previousYear = 2024;
  private static int previousMonth = 6;
  private static int previousDay = 6;

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
  public static void putCheckingInterestRate(double rate) throws IOException {
    if (rate <= 0) {
      System.out.println("Invalid rate: Must be positive.");
      return;
    }

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

    checkingInterestRate = rate;
  }
  public static void putSavingsInterestRate(double rate) throws IOException {
    if (rate <= 0) {
      System.out.println("Invalid rate: Must be positive.");
      return;
    }

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

    savingsInterestRate = rate;
  }

  // Update the date
  public static void putCurrentYear(int year) throws IOException {
    if (year > currentYear) {
      previousDay = currentYear;
      currentYear = year;
      updateDateValues();
    }
  }
  public static void putCurrentMonth(int month) throws IOException {
    if ((month >= 1) && (month <= 12)) {
      previousMonth = currentMonth;
      currentMonth = month;
      updateDateValues();
    }
  }
  public static void putCurrentDay(int day) throws IOException {
    switch (currentMonth) {
      case 1, 3, 5, 7, 8, 10, 12:
        if ((day >= 1) && (day <= 31)) {
          previousDay = currentDay;
          currentDay = day;
          updateDateValues();
        }
        break;

      case 4, 6, 9, 11:
        if ((day >= 1) && (day <= 30)) {
          previousDay = currentDay;
          currentDay = day;
          updateDateValues();
        }
        break;

      case 2:
        if (isLeapYear(currentYear) && (day >= 1)) {
          if (day <= 29) {
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

  public static void updateDateValues() throws IOException {
    BufferedReader brOld = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));
    BufferedReader brNew = new BufferedReader(new FileReader(Values.getSuperInfoLocation()));

    if ((brOld.readLine()).substring(0, 9).equals((brNew.readLine()).substring(0, 9)))
      updateDateNew();
    else
      updateDateBoth();
  }
  public static void updateDateNew() throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(Values.getSuperInfoLocation()));
    BufferedReader br = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));

    String data = (br.readLine()).substring(10);
    String date = currentYear + "/" + currentMonth + "/" + currentDay;
    pw.println(convert(date) + "." + data);

    pw.flush();
  }
  public static void updateDateBoth() throws IOException {
    PrintWriter pwOld = new PrintWriter(new FileWriter(Values.getSuperInfoOldLocation()));
    PrintWriter pwNew = new PrintWriter(new FileWriter(Values.getSuperInfoLocation()));
    BufferedReader brOld = new BufferedReader(new FileReader(Values.getSuperInfoOldLocation()));
    BufferedReader brNew = new BufferedReader(new FileReader(Values.getSuperInfoLocation()));

    String oldData = brNew.readLine();
    String olderData = brOld.readLine();

    String newDate = currentYear + "/" + currentMonth + "/" + currentDay;
    String oldDate = previousYear + "/" + previousMonth + "/" + previousDay;

    String updatedOldData = convert(oldDate) + olderData.substring(olderData.substring(8).indexOf('.'));
    pwOld.println(updatedOldData);

    String newData = convert(newDate) + oldData.substring(olderData.substring(8).indexOf('.'));
    pwNew.println(newData);

    pwOld.flush();
    pwNew.flush();
  }

  private static boolean isLeapYear(int year) {
    // Check if the year is a leap year (day validation for february)
    return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
  }

  private static String getPassword() throws IOException {
    // This method gives the encrypted password stored in the file
    BufferedReader br = new BufferedReader(new FileReader(getSuperInfoLocation()));

    String data = br.readLine(); // Store the data

    String password = data.substring(data.substring(8).indexOf('>')); // The encrypted password is after the . after the date

    return password;
  }

  public static boolean comparePassword(String password) throws IOException {
    // This method compares the inputted password with the stored password
    return password.equals(convert(getPassword()));
  }

  public static boolean checkIfEmpty(String location) throws IOException {
    // This method checks if the file is empty
    BufferedReader br = new BufferedReader(new FileReader(location));
    return br.readLine() == null;
  }

  // Nimay Desai
  public static String convert(String data) {
    /* This method acts as the encryption/decryption
     * This method will return a string where the original string's data is altered
     * A is flipped to Z, 0 is flipped to 9, and vice-versa
     * Example: password123 --> kzhhdliw876
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
