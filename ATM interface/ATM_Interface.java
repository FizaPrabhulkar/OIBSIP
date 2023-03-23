//Oasis Infobyte Task 3

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Account {
    protected String user_Id;
    protected String name;
    protected int pin;
    protected String acc_num;
    protected int balance = 0;
    protected final List<String> transactionHistory = new ArrayList<>();
}
class ATM_Functions {

    String check_userId = null;

    private  Scanner sc = new Scanner(System.in);
    private final HashMap<String, Account> acc = new HashMap<>();
    private final HashMap<String, String> details = new HashMap<>();

    public void register() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            Account account = new Account();
            System.out.println("\n\t************ Registration *************");
            System.out.println("\nPlease enter your name : ");
            account.name = sc.nextLine();

            while (true) {
                System.out.println("Create a UserId : ");
                account.user_Id = sc.nextLine();

                if (!acc.containsKey(account.user_Id)) {
                    break;
                } else {
                    System.out.println("Enter a valid userId");
                }
            }

            while (true) {
                System.out.println("Create a Account Number : ");
                account.acc_num = sc.nextLine();

                if (!details.containsKey(account.acc_num)) {
                    break;
                } else {
                    System.out.print("Enter valid Account Number!");
                }               
            }
            
            while (true) {
                System.out.println("Please create pin : ");
                account.pin = sc.nextInt();
                if (account.pin >= 1000) {
                    break;
                } else {
                    System.out.println("Your Pin must be of 4 digits!");
                }
            }

            acc.put(account.user_Id, account);
            details.put(account.acc_num, account.user_Id);
            sc.nextLine();

            System.out.println("\nRegister Successful..!");
            sc.nextLine();
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public Account logIn() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        boolean exit = true;
        int attempts = 0;
        String uId = null;
        Account account = null;
        System.out.println("\n\t************ LogIn ************");
        while (exit) {
            System.out.println("\nPlease enter your UserId : ");
            uId = sc.nextLine();
            attempts++;

            if (!acc.containsKey(uId)) {
                if (attempts == 3) {
                    System.out.println("Too Many Attempts!");
                    return null;
                }
                System.out.println("Enter a valid UserId!");
            } else {
                exit = false;
            }
            account = acc.get(uId);
        }

        check_userId = uId;

        exit = true;
        attempts = 0;

        while (exit) {
            System.out.println("Please enter your pin : ");
            int pin = sc.nextInt();
            attempts++;

            if (pin != account.pin) {
                if (attempts == 3) {
                    System.out.println("Too Many attempts!");
                    return null;
                }
                System.out.println("Enter a Valid Pin!");
            } else {
                exit = false;
                System.out.println("Hello, " + account.name + " ..");
                sc.nextLine();
                sc.nextLine();
            }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return account;
    }

    public void withdraw(Account account) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        LocalDateTime DateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = DateObj.format(myFormatObj);

        System.out.println("\n\t************ Withdraw ************");
        System.out.println("\nPlease enter the amount you want to withdraw:");
        int amount = sc.nextInt();

        if (acc.get(check_userId) != null && amount <= account.balance) {
            account.balance -= amount;

            String his = formattedDate + "\t\t withdrawn from your account \t\t\t\t " + amount + "\t\t\t" + account.balance;

            account.transactionHistory.add(his);
            System.out.println(amount + "  successfully withdrawn from your acccount..");
            sc.nextLine();
        } else {
            System.out.println("Balance is insufficient!");
            sc.nextLine();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void deposit(Account account) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        LocalDateTime DateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = DateObj.format(myFormatObj);

        System.out.println("\n\t************ Deposit ************");
        System.out.println("\nPlease enter amount you want to deposit:");
        int amount = sc.nextInt();
        account.balance += amount;
        String his = formattedDate + "\t\t deposited to your account \t\t\t\t " + amount + "\t\t\t" + account.balance;


        account.transactionHistory.add(his);

        System.out.println(amount + "  successfully deposited to your account..");
        sc.nextLine();
        sc.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void transfer(Account account) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        LocalDateTime DateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = DateObj.format(myFormatObj);

        System.out.println("\n\t************ Transfer money ************");
        System.out.println("Please enter the account number you want to transfer the amount :");
        String acc_num = sc.next();

        if (!details.containsKey(acc_num)) {
            System.out.println("Account Does not Exist!!\n Retry");
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            return;
        }
        System.out.println("Please enter the amount :");
        int transferAmount = sc.nextInt();

        if (transferAmount <= account.balance && account.balance != 0) {
            account.balance -= transferAmount;
            String his = formattedDate + "\t\ttransferred from your account\t\t\t\t" + transferAmount + "\t\t\t" + account.balance;

            account.transactionHistory.add(his);

            System.out.println(transferAmount + "  successfully transferred from your account");
            sc.nextLine();
        } else {
            System.out.println("Balance is insufficient!");
            sc.nextLine();
        }

        String id = details.get(acc_num);

        if (acc.get(id) != null) {
            acc.get(id).balance += transferAmount;
        } else {
            return;
        }


        String his = formattedDate + "\t\treceived by " + account.name + "\t\t\t\t" + transferAmount + "\t\t\t" + acc.get(id).balance;


        acc.get(id).transactionHistory.add(his);
        sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void transac_history(Account account) {
        System.out.println("\n\t************ Transaction History ************\n");
        System.out.println("Date\t\tTime\t\t\t\tAction\t\t\t\t\tAmount\t\tRemaining amount\n");
        System.out.println("____\t\t_____\t\t\t\t______\t\t\t\t\t______\t\t________________\n");
        for (String his : account.transactionHistory) {
            System.out.println(his);
        }
        sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

public class ATM_Interface{
    public static void main(String[] args) {

        ATM_Functions atm_functions = new ATM_Functions();
        try (Scanner sc = new Scanner(System.in)) {
            int choice;

            while (true) {
                System.out.println("\n\n\t************ Welcome to ATM System ************");
                System.out.println("\nEnter 1 to Register your account");
                System.out.println("Enter 2 to Log In to your existing account");
                System.out.println("Enter 0 to exit from the system");
               
                choice = sc.nextInt();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                if (choice == 1) {
                    atm_functions.register();
                } else if (choice == 2) {
                    Account account = atm_functions.logIn();
                    if (account == null) {
                        System.out.println("Failed to login");
                    } else {

                        boolean exit = true;
                        while (exit) {
                           
                            System.out.println("                                              "+account.name);
                            System.out.println("============================================================================================");

                            System.out.println("\n1.Transaction History");
                            System.out.println("2.Withdraw");
                            System.out.println("3.Deposit");
                            System.out.println("4.Transfer");
                            System.out.println("5.Quit\n");
                            System.out.println("=================");
                            System.out.println("Enter your Option");
                            System.out.println("==================");

                            int ch = sc.nextInt();

                            switch (ch) {
                                case 1:
                                    atm_functions.transac_history(account);
                                    break;
                                case 2:
                                    atm_functions.withdraw(account);
                                    break;
                                case 3:
                                    atm_functions.deposit(account);
                                    break;
                                case 4:
                                    atm_functions.transfer(account);
                                    break;
                                default:
                                    exit = false;
                                    System.out.println("Thank you for visiting..!");
                                    sc.nextLine();
                                    System.out.print("\033[H\033[2J");
                                    System.out.flush();
                                    break;
                            }
                        }
                    }
                } else {
                    System.out.println("Thank you for visiting..!");
                    break;
                }

               
            }
        }


    }
}