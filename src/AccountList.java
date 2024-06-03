// Kushal Prajapati
public class AccountList {

    private Account[] accounts;
    private int numAccounts;

    public AccountList() {
        accounts = new Account[5];
        numAccounts = 0;
    }

    public void addAccount(AccountType t) {
        Account a = new Account(t);
        if (numAccounts == accounts.length) {
            Account[] temp = new Account[accounts.length * 2];
            for (int i = 0; i < accounts.length; i++)
                temp[i] = accounts[i];
            accounts = temp;
        }
        accounts[numAccounts++] = a;
    }

    public Account getAccount(int index) {
        return accounts[index];
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    public void removeAccount(int index) {
        for (int i = index; i < numAccounts - 1; i++) {
            accounts[i] = accounts[i + 1];
            numAccounts--;
        }
    }
    public void printAccounts () {
        for (int i = 0; i < numAccounts; i++) {
            System.out.println(accounts[i]);
        }
    }
}