import java.util.Objects;

public class Main {
  public static void title() {
    // This method outputs the title of the application and the creators of it
    System.out.println("""
        ___            _       _             _ _         _   _         
       | _ ) __ _ _ _ | |__   /_\\  _ __ _ __| (_)__ __ _| |_(_)___ _ _ 
       | _ \\/ _` | ' \\| / /  / _ \\| '_ \\ '_ \\ | / _/ _` |  _| / _ \\ ' \\
       |___/\\__,_|_||_|_\\_\\ /_/ \\_\\ .__/ .__/_|_\\__\\__,_|\\__|_\\___/_||_|
                                  |_|  |_|                             
      
      By: Sachkeerat Brar, Nimay Desai, and Kushal Prajapati
      """);
  }

  // Nimay Desai
  public static String convert(String data) {
    /* This method acts as the encryption/decryption
     * This method will return a string where the original string's data is altered
     * A is flipped to Z, 0 is flipped to 9, and vice-versa
     * Example: password123 --> kzhhdliw876
    */
    String convertedData = ""; // Store the flipped data
    for(char c: data.toCharArray()) {
      // Check if the character is a number between 0 and 9
      if((c >= '0') && (c <= '9')) {
        convertedData += (char)('0' + '9' - c);
      } else if((c >= 'A') && (c <= 'Z')) {
        convertedData += (char)('A' + 'Z' - c);
      } else if ((c >= 'a') && (c <= 'z')) {
        convertedData += (char)('a' + 'z' - c);
      }
    }
    return convertedData;
  }

  public static void main(String[] args) {
    title();
  }
}
