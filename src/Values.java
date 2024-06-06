// Sachkeerat Brar
public class Values {
  private static int currentYear = 2024;
  private static int currentMonth = 6;
  private static int currentDay = 6;

  private static double checkingInterestRate = 0.04; /// 4%
  private static double savingsInterestRate = 0.065; // 6.5%
  private static String dir = "src/";
  
  public static String getClientInfoLocation() {
    return dir + "ClientInfo";
  }
  public static String getClientInfoOldLocation() {
    return dir + "ClientInfoOld";
  }
  public static String getSuperInfoLocation() {
    return dir + "SuperInfo";
  }
  public static String getSuperInfoOldLocation() {
    return dir + "SuperInfoOld";
  }

  public static int getCurrentYear() {
    return currentYear;
  }
  public static int getCurrentMonth() {
    return currentMonth;
  }
  public static int getCurrentDay() {
    return currentDay;
  }

  public static double getCheckingInterestRate() {
    return checkingInterestRate;
  }
  public static double getSavingsInterestRate() {
    return savingsInterestRate;
  }

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
}
