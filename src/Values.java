// Sachkeerat Brar
public class Values {
  /* This class was made in order to store or modify values which would be accessed by multiple files.
     This is so we can have cleaner code and a better development experience
  */

  // Dates
  private static int currentYear = 2024;
  private static int currentMonth = 6;
  private static int currentDay = 6;

  // Interest
  private static double checkingInterestRate = 0.04; /// 4%
  private static double savingsInterestRate = 0.065; // 6.5%

  // Directory
  // Nimay Desai
  private static final String DIR = "src/";

  // Getting file locations
  public static String getClientInfoLocation() {
    return DIR + "ClientInfo";
  }
  public static String getClientInfoOldLocation() {
    return DIR + "ClientInfoOld";
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

  // Getting and updating the interest rates
  public static double getCheckingInterestRate() {
    return checkingInterestRate;
  }
  public static double getSavingsInterestRate() {
    return savingsInterestRate;
  }
  public static void putCheckingInterestRate(double rate) {
    if(rate <= 0) {
      System.out.println("Invalid rate: Must be positive.");
      return;
    }
    // TODO: read & use password to change this
    // if(rate >= 0.1)

    checkingInterestRate = rate;
  }
  public static void putSavingsInterestRate(double rate) {
    if(rate <= 0) {
      System.out.println("Invalid rate: Must be positive.");
      return;
    }
    // TODO: read & use password to change this
    // if(rate >= 0.1)

    savingsInterestRate = rate;
  }

  // Update the date
  public static void putCurrentYear(int year) {
    if(year > currentYear)
      currentYear = year;
  }
  public static void putCurrentMonth(int month) {
    if((month >= 1) && (month <= 12))
      currentMonth = month;
  }
  public void putCurrentDay(int day) {
    switch(currentMonth) {
      case 1, 3, 5, 7, 8, 10, 12:
        if((day >= 1) && (day <= 31))
          currentDay = day;
        break;

      case 4, 6, 9, 11:
        if((day >= 1) && (day <= 30))
          currentDay = day;
        break;

      case 2:
        if(isLeapYear(currentYear) && (day >= 1)) {
          if(day <= 29)
            currentDay = day;
        }
        else
          if(day <= 28)
            currentDay = day;
        break;

      default:
        System.out.println("Invalid day.");
        break;
    }
  }

  // Check if the year is a leap year (day validation for february)
  private static boolean isLeapYear(int year) {
    if((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))
      return true;
    return false;
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
        convertedData += (char) ('0' + '9' - c);
        // Check if character is uppercase letter
      else if((c >= 'A') && (c <= 'Z'))
        convertedData += (char) ('A' + 'Z' - c);
        // Check if character is lowercase letter
      else if ((c >= 'a') && (c <= 'z'))
        convertedData += (char) ('a' + 'z' - c);
        // Else add just the .character
      else
        convertedData += c;

    return convertedData;
  }
}
