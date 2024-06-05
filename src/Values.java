// Sachkeerat Brar
public class Values {
  public static int CURRENT_YEAR = 2024;
  public static int CURRENT_MONTH = 6;
  public static int CURRENT_DAY = 5;

  public static double CHECKING_INTEREST_RATE = 0.05; /// 4%
  public static double SAVINGS_INTEREST_RATE = 0.065; // 6.5%

  public static int getCurrentYear() {
    return CURRENT_YEAR;
  }
  public static int getCurrentMonth() {
    return CURRENT_MONTH;
  }
  public static int getCurrentDay() {
    return CURRENT_DAY;
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
