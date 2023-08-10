import java.util.*;

class User {
    //class to store the data of user
    private String accountNo;
    private String password;
    private String phoneNo;
    private String email;
    private double accountBalance;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    boolean checkValid(String s) {
        //a method to check if given string contains all integers or not
        if (s.length() == 0) {
            return false;
        }
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (c[i] < '0' || c[i] > '9') {
                return false;
            }
        }
        return true;
    }
}

class Admin {
    //a class for admin...
    //admin has the access to see the balance available in ATM...
    //admin can also add money to ATM every day...
    Scanner sc = new Scanner(System.in);

    public String getUserId() {
        //userId of admin
        return "Admin";
    }

    public String getPassword() {
        //password of admin
        return "admin@123";
    }

    public int[] addMoney() {
        //method which allows Admin to add money into the ATM
        int[] arr = new int[10];
        System.out.println("Enter the denomination you want to deposit-- ");
        System.out.println("1.1 Re Coins\n2.2 Rs Coins\n3.5 Rs Coins\n4.10 Rs Notes\n5.20 Rs Notes");
        System.out.println("6.50 Rs Notes\n7.100 Rs Notes\n8.200 Rs Notes\n9.500 Rs Notes\n10.2000 Rs Notes");
        int freq;
        int in = sc.nextInt();
        System.out.println("Enter the number of Notes - ");
        freq = sc.nextInt();
        while (in != -1) {
            if (in == 1 || in == 2 || in == 3 || in == 4 || in == 5 || in == 6 || in == 7 || in == 8 || in == 9 || in == 10) {
                arr[in - 1] += freq;
            } else System.out.println("Incorrect Input.. Try Again...");
            System.out.println("1.1 Re Coins\n2.2 Rs Coins\n3.5 Rs Coins\n4.10 Rs Notes\n5.20 Rs Notes");
            System.out.println("6.50 Rs Notes\n7.100 Rs Notes\n8.200 Rs Notes\n9.500 Rs Notes\n10.2000 Rs Notes");
            System.out.println("Enter -1 when over..");
            in = sc.nextInt();
            if (in != -1) {
                System.out.println("Enter the number of Notes - ");
                freq = sc.nextInt();
            }
        }
        return arr;
    }
}

class Account {
    //this class contains all functionalities related to user
    Scanner sc = new Scanner(System.in);
    Vector<User> user = new Vector<>();
    //a vector of objects of class "User" to store the data dynamically
    private int idx; //to determine the index of required user from the vector
    boolean signedIn = true;

    void signUp() {
        //a method used to create account for new users
        User u = new User();
        System.out.println("\n__________Welcome__________\n");
        String s1;
        boolean valid;
        do {
            valid = true;
            System.out.println("\nThe account number must be a 5-digit number...\n");
            System.out.print("Create your Account Number- ");
            s1 = sc.next();
            if (!u.checkValid(s1) || s1.length() != 5) {
                System.out.println("\nInvalid number... Please try again...\n");
                valid = false;
            }
            for (User value : user) {
                if (s1.equals(value.getAccountNo())) {
                    System.out.println("\nThe account number already exists..\n");
                    System.out.println("Please try any other account number..");
                    valid = false;
                }
            }
        } while (((!u.checkValid(s1) || s1.length() != 5)) || !valid);
        //checks if the provided account number is a 5-digit number and if it already exists in database or not
        u.setAccountNo(s1);

        do {
            valid = true;
            System.out.println("\nThe PIN must be a number...");
            System.out.print("Create your Account PIN- ");
            s1 = sc.next();
            if (!u.checkValid(s1)) {
                System.out.println("\nInvalid PIN... Please try again...");
                valid = false;
            } else {
                System.out.print("Re-enter the PIN- ");
                String s2 = sc.next();
                if (!s1.equals(s2)) {
                    System.out.println("PIN didn't match.. Try Again..\n");
                    valid = false;
                }
            }
        } while (!valid);
        //checks if the provided PIN is an integer and if re-entered PIN matches wth current PIN or not
        u.setPassword(s1);

        do {
            System.out.print("Enter Mobile number- +91 ");
            s1 = sc.next();
            if (!u.checkValid(s1) || s1.length() != 10) {
                System.out.println("\nInvalid  mobile number... Please try again...\n");
            }
        } while (!u.checkValid(s1) || s1.length() != 10);
        //checks if the mobile number is a 10-digit number or not
        u.setPhoneNo(s1);

        System.out.println("Enter email address- ");
        u.setEmail(sc.next());
        u.setAccountBalance(0);
        user.add(u); //the user details gets added into the vector
        System.out.println("\nAccount successfully created...\n\n");
    }

    void signIn() {
        //a method for old users' login
        String s3;
        do {
            idx = -1;
            System.out.print("Enter UserId- ");
            s3 = sc.next();
            for (int i = 0; i < user.size(); i++) {
                if (Objects.equals(user.get(i).getAccountNo(), s3)) {
                    idx = i;
                    break;
                }
            }
            if (idx == -1) {
                System.out.println("\nInvalid Account Number..\nPlease try again..\n");
            }
        } while (idx == -1);
        //checks if the given account number is valid or not

        int count = 2; //"count" is number of attempts left to enter wrong PIN
        do {
            System.out.print("Enter PIN- ");
            s3 = sc.next();
            if (!Objects.equals(s3, user.get(idx).getPassword())) {
                System.out.println("Invalid PIN..\n You have " + count + " attempts");
                count--;
            }
            if (count == -1) {
                //maximum three chances to enter wrong pin
                signedIn = false;
                break;
            }
        } while (!Objects.equals(s3, user.get(idx).getPassword()));
    }

    int getIdx() {
        return idx;
    }

    void resetPin(int idx) {
        //a method to reset PIN
        System.out.print("Enter your mobile number--");
        String s = sc.next();
        //PIN is reset if user enters correct mobile number
        if (Objects.equals(s, user.get(idx).getPhoneNo())) {
            System.out.println("__Reset PIN__");
            boolean valid;
            String s1;
            do {
                valid = true;
                System.out.println("\nThe PIN must be a number...\n");
                System.out.print("Create your Account PIN- ");
                s1 = sc.next();
                if (!user.get(idx).checkValid(s1)) {
                    System.out.println("\nInvalid PIN... Please try again...\n");
                    valid = false;
                } else {
                    System.out.print("Re-enter the PIN- ");
                    String s2 = sc.next();
                    if (!s1.equals(s2)) {
                        System.out.println("PIN didn't match.. Try Again..\n");
                        valid = false;
                    }
                }
            } while (!valid);
            user.get(idx).setPassword(s1);
            signedIn = true;
        } else System.out.println("Incorrect Mobile Number.. Try Again");
    }

    void resetPhoneNo(int i) {
        System.out.print("Enter the old mobile number-- ");
        String temp4 = sc.next();
        if (Objects.equals(temp4, user.get(i).getPhoneNo())) {
            //checks if old mobile number matches
            System.out.print("Enter new mobile number-- ");
            temp4 = sc.next();
            user.get(i).setPhoneNo(temp4);
            System.out.println("Mobile number updated successfully...");
        } else System.out.println("Incorrect mobile number... Try Again..");
    }

    void resetEmail(int i) {
        System.out.print("Enter the old email Id-- ");
        String temp4 = sc.next();
        if (Objects.equals(temp4, user.get(i).getEmail())) {
            //checks if old e-mail id matches
            System.out.print("Enter new EmailId-- ");
            temp4 = sc.next();
            user.get(i).setEmail(temp4);
            System.out.println("EmailId  updated successfully...");
        } else System.out.println("Incorrect EmailId... Try Again..");
    }
}

abstract class CashDispenser {
    //an abstract class to keep track of number of notes available in the ATM
    int[] cash = new int[10];
    static int[] denomination = {1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
    //0-1 1-2 2-5 3-10 4-20 5-50 6-100 7-200 8-500 9-2000

}

interface Functionality {
    //an interface which contains all functionalities used
    public void depositMoney(int[] arr);

    public double depositMoney();

    public boolean cashAvailability(int reqAmount);

    public int getDenomination(int i);

}


class MoneyTransaction extends CashDispenser implements Functionality {
    Scanner sc = new Scanner(System.in);

    public int getDenomination(int i) {
        return denomination[i];
    }

    public boolean cashAvailability(int reqAmount) {
        //a method to check if the required amount is available in ATM or not
        for (int i = 9; i >= 0; i--) {
            if (reqAmount / denomination[i] <= cash[i]) {
                if (reqAmount % denomination[i] == 0) {
                    return true;
                } else {
                    int temp = reqAmount / denomination[i];
                    reqAmount -= temp * denomination[i];
                }
            } else {
                reqAmount -= cash[i] * denomination[i];
            }
        }
        return false;
    }

    public double depositMoney() {
        //a method to let user deposit the amount in his/her bank account
        int[] arr = new int[10];
        System.out.println("Enter the denomination you want to deposit-- ");
        System.out.println("1.1 Re Coins\n2.2 Rs Coins\n3.5 Rs Coins\n4.10 Rs Notes\n5.20 Rs Notes");
        System.out.println("6.50 Rs Notes\n7.100 Rs Notes\n8.200 Rs Notes\n9.500 Rs Notes\n10.2000 Rs Notes");
        double money = 0;
        int freq;
        int in = sc.nextInt();
        System.out.println("Enter the number of Notes - ");
        freq = sc.nextInt();
        while (in != -1) {
            if (in == 1 || in == 2 || in == 3 || in == 4 || in == 5 || in == 6 || in == 7 || in == 8 || in == 9 || in == 10) {
                arr[in - 1] += freq;
                money += getDenomination(in - 1) * freq;
            } else System.out.println("Incorrect Input.. Try Again...");
            System.out.println("1.1 Re Coins\n2.2 Rs Coins\n3.5 Rs Coins\n4.10 Rs Notes\n5.20 Rs Notes");
            System.out.println("6.50 Rs Notes\n7.100 Rs Notes\n8.200 Rs Notes\n9.500 Rs Notes\n10.2000 Rs Notes");
            System.out.println("Enter -1 when over..");
            in = sc.nextInt();
            if (in != -1) {
                System.out.println("Enter the number of Notes - ");
                freq = sc.nextInt();
            }
        }
        depositMoney(arr);
        return money;
    }

    //polymorphism
    public void depositMoney(int[] arr) {
        //a method to add the money deposited by user in the ATM's balance
        for (int i = 0; i < 10; i++) {
            cash[i] += arr[i];
        }
    }

    public void withdrawMoney(int reqAmount) {
        //a method to withdraw money from ATM
        for (int i = 9; i >= 0; i--) {
            if (reqAmount / denomination[i] <= cash[i]) {
                if (reqAmount % denomination[i] == 0) {
                    cash[i] -= (reqAmount / denomination[i]);
                    return;
                } else {
                    int temp = reqAmount / denomination[i];
                    reqAmount -= temp * denomination[i];
                    cash[i] -= temp;
                }
            } else {
                reqAmount -= cash[i] * denomination[i];
                cash[i] = 0;
            }
        }
        System.out.println("Please collect the cash from Cash Dispenser...");
    }

    public void showAvailableBalance() {
        //method to show available balance in ATM to the ADMIN
        for (int i = 0; i < 10; i++) {
            System.out.println(denomination[i] + "rupee notes -- " + cash[i]);
        }
    }
}


public class ATM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account account = new Account();
        Admin admin = new Admin();
        MoneyTransaction mt = new MoneyTransaction();
        System.out.println("__________Welcome________");

        while (true) {
            int t;
            do {
                System.out.println("Select an option-\n1.User\n2.Admin");
                t = sc.nextInt();
                if (t != 1 && t != 2) {
                    System.out.println("\nInvalid Input...\nPlease enter a valid input\n");
                }
            } while (t != 1 && t != 2);
            if (t == 1) {
                //for users
                int n1 = 0;
                do {
                    System.out.println("Select the option- \n1.Sign Up\n2.Login");
                    n1 = sc.nextInt();
                    if (n1 != 1 && n1 != 2) {
                        System.out.println("\nInvalid Input...\nPlease enter a valid input\n");
                    }
                } while (n1 != 1 && n1 != 2);
                if (n1 == 1) {
                    //signing up
                    account.signUp();
                }
                if (n1 == 2) {
                    //signing in
                    account.signIn();
                    if (!account.signedIn) {
                        //option to reset the PIN if the user enters incorrect PIN for 3 times
                        System.out.println("Select an option- \n1.Reset PIN\n2.Exit");
                        {
                            if (sc.nextInt() == 1) {
                                account.resetPin(account.getIdx());
                            }
                        }
                    } else {
                        //i is the index of the signed-in user in the vector
                        int i = account.getIdx();
                        System.out.println("Select an option-\n1.View Balance\n2.Withdraw Money\n3.Deposit Money");
                        System.out.println("4.Change Mobile Number\n5.Change e-mail Id\n6.Logout");
                        int temp3 = sc.nextInt();
                        while (temp3 != 6) {
                            if (temp3 == 1) {
                                //show available balance
                                System.out.println("Available Balance= " + account.user.get(i).getAccountBalance() + "\n\n");
                            } else if (temp3 == 2) {
                                System.out.print("Enter the amount you want to withdraw-- ");
                                int temp4 = sc.nextInt();
                                if (account.user.get(i).getAccountBalance() < temp4) {
                                    //checks if required amount is available in account's balance
                                    System.out.println("Insufficient Balance...\n");
                                } else if (!mt.cashAvailability(temp4)) {
                                    //checks if required amount is available in ATM
                                    System.out.println("Amount not available in ATM...");
                                } else {
                                    mt.withdrawMoney(temp4);
                                    double oldMoney = account.user.get(i).getAccountBalance();
                                    account.user.get(i).setAccountBalance(oldMoney - temp4);
                                    System.out.println("Available Balance- " + account.user.get(i).getAccountBalance());
                                }
                            } else if (temp3 == 3) {
                                //withdrawal of money
                                double money = mt.depositMoney();
                                double oldMoney = account.user.get(i).getAccountBalance();
                                account.user.get(i).setAccountBalance(oldMoney + money);
                                System.out.println("Available Balance- " + account.user.get(i).getAccountBalance());
                            } else if (temp3 == 4) {
                                //reset mobile number
                                account.resetPhoneNo(i);
                            } else if (temp3 == 5) {
                                //reset e-mail address
                                account.resetEmail(i);
                            }
                            System.out.println("Select an option-\n1.View Balance\n2.Withdraw Money\n3.Deposit Money");
                            System.out.println("4.Change Mobile Number\n5.Change e-mail Id\n6.Logout");
                            temp3 = sc.nextInt();
                        }
                    }
                }
            } else {
                //for admin
                String us, p;
                System.out.print("Enter userId- ");
                us = sc.next();
                System.out.print("Enter password- ");
                p = sc.next();
                if (Objects.equals(us, admin.getUserId()) && Objects.equals(p, admin.getPassword())) {
                    int temp5;
                    do {
                        System.out.println("Select an option-\n1.Deposit Money\n2.View Available Balance\n3.Logout");
                        temp5 = sc.nextInt();
                        if (temp5 != 1 && temp5 != 2 && temp5 != 3) {
                            System.out.println("\nInvalid Input...\nPlease enter a valid input\n");
                        } else if (temp5 == 1) {
                            //deposit money in ATM
                            int[] arr = admin.addMoney();
                            mt.depositMoney(arr);
                        } else if (temp5 == 2) {
                            //show available balance in ATM
                            mt.showAvailableBalance();
                        }
                    } while (temp5 != 3);
                } else System.out.println("Invalid credentials.. Try again..");
            }
        }
    }
}