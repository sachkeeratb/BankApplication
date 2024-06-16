// Kushal Prajapati

// Utilizes  overloading for different ways to construct a client
// This is the client object for the clients associated with the bank
public class Client {  // Fields
  private int ID; // Client ID (starts at 1)
  private String name; // Full name
  private String dob; // Date
  private int age; // Age (calculated from date of birth)
  private AccountList accounts; // Accounts the user holds


  // Constructors
  public Client() {
    // The default user
    ID = 1;
    name = "John Doe";
    dob = "2006/01/01";
    age = calculateAge(dob);
    accounts = new AccountList();
  }
  public Client(Client lastClient) {
    // This constructor makes a default client with the last client's ID incremented
    // This ensures that the no other client doesn have te same ID
    ID = lastClient.ID + 1;
    name = "John Doe";
    dob = "2006/01/01";
    age = calculateAge(dob);
    accounts = new AccountList();
  }
  public Client(String n, String date) {
    // Create a client with the inputted name and date
    ID = 1;
    name = n;
    dob = date;
    age = calculateAge(dob);
    accounts = new AccountList();
  }
  public Client(String n, String date, AccountList accs) {
    // Create a client with the name, date, and the last client's ID incremented
    ID = 1; // Set 
    name = n;
    dob = date;
    age = calculateAge(dob);

    if (accs == null)
      accounts = new AccountList();
    else
      accounts = accs;
  }
  public Client(Client lastClient, String n, String date) {
    // Create a client with the name, date, and the last client's ID incremented
    ID = lastClient.ID + 1;
    name = n;
    dob = date;
    age = calculateAge(dob);
    accounts = new AccountList();
  }


  // Kushal Prajapati and Nimay Desai
  // Accessors
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


  // Mutators
  public void putID(int newID) {
    ID = newID;
  }
  public void putName(String newName) {
    name = newName;
  }


  // Instance methods

  public void display() {
    // This function outputs client info
    // This function does not ake in anything as all data is retrieved from the class itself
    // This function does not return anytbing as it outputted

    System.out.println("ID: " + ID + "\nName: " + name + "\nDate of Birth: " + dob + "\nAge: " + age + "\n\nAccounts: ");
    accounts.display(); // Display accounts
  }
  public String toString() {
    // This method turns the object into a string when used to print to the console
    return "( " + ID + ", " + name + ", " + dob + ", " + accounts.toString() + " )";
  }


  // Static methods
    
  // Sachkeerat Brar & Kushal Prajapati
  public static Client fromString(String data) {
    // Loads current state
    // This method creates a client out of a string

    int clientID = Integer.parseInt(data.substring(0, data.indexOf(",")));
    String clientName = data.substring(data.indexOf(",") + 2, data.indexOf(",", data.indexOf(",") + 1));
    String clientDOB = data.substring(data.indexOf(",", data.indexOf(",") + 1) + 2, data.indexOf(",", data.indexOf(",", data.indexOf(",") + 1) + 1));
    String clientAccs = data.substring(data.indexOf("[", 1), data.indexOf("]") + 1);
    AccountList accounts = AccountList.fromString(clientAccs);

    Client client = new Client(clientName, clientDOB, accounts);
    client.putID(clientID);

    return client;
  }

  // Sachkeerat Brar
  private static int calculateAge(String date) {
    // This method calculates an age from a date formatted as "yyyy/mm/dd"

    // Store the birth values as integers
    int birthYear = Integer.parseInt(date.substring(0, 4));
    int birthMonth = Integer.parseInt(date.substring(5, date.indexOf("/", 5)));
    int birthDay = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1));

    // Store the age
    int currentAge = Values.getCurrentYear() - birthYear;

    // See if they are a year younger
    if ((Values.getCurrentMonth() < birthMonth) || ((Values.getCurrentMonth() == birthMonth) && (Values.getCurrentDay() < birthDay)))
      currentAge--;

    // Return the age
    return currentAge;
  }
}