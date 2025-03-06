/*
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class User {
    String name;
    int userId;

    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }f
}

class Customer extends User {
    private List<BankAccount> accounts;

    public Customer(String name, int userId, BankAccount account) {
        super(name, userId);
        this.accounts = new ArrayList<>();
        this.accounts.add(account);
        System.out.println("Account " + account.getAccountNumber() + " created for " + name);
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
        System.out.println("Additional Account " + account.getAccountNumber() + " added for " + name);
    }

    public void deposit(int accNum, double amount) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accNum) {
                account.deposit(amount);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void withdraw(int accNum, double amount) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accNum) {
                account.withdraw(amount);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for " + name);
        } else {
            System.out.println("Accounts for " + name + ":");
            for (BankAccount account : accounts) {
                System.out.println("Account Number: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
            }
        }
    }
}

class Manager extends User {
    private List<Customer> customersList;

    public Manager(String name, int userId) {
        super(name, userId);
        this.customersList = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customersList.add(customer);
        System.out.println("Customer " + customer.name + " added.");
    }

    public Customer findCustomer(int userId) {
        for (Customer c : customersList) {
            if (c.userId == userId) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> getCustomersList() {
        return customersList;
    }
}

abstract class BankAccount {
    protected int accountNumber;
    protected double balance;

    public BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public abstract void withdraw(double amount);
}

class SavingsAccount extends BankAccount {
    public SavingsAccount(int accountNumber) {
        super(accountNumber);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ". New balance: " + balance);
        }
    }
}

class CurrentAccount extends BankAccount {
    private final int overdraftLimit = -2000;

    public CurrentAccount(int accountNumber) {
        super(accountNumber);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < overdraftLimit) {
            System.out.println("Not sufficient funds to withdraw.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ". New balance: " + balance);
        }
    }
}

public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager("Admin", 1001);

        System.out.println("Welcome to the Bank Application");

        while (true) {
            System.out.println("\n1. Create Customer (With Account)\n2. Deposit Money\n3. Withdraw Money\n4. View Customers\n5. View Customer Accounts\n6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter user ID (6 digits): ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    if (String.valueOf(userId).length() != 6) {
                        System.out.println("User ID must be exactly 6 digits.");
                        break;
                    }

                    System.out.print("Enter account number (4 digits): ");
                    int accNum = scanner.nextInt();
                    scanner.nextLine();

                    if (String.valueOf(accNum).length() != 4) {
                        System.out.println("Account number must be exactly 4 digits.");
                        break;
                    }

                    System.out.print("Enter account type (Savings/Current): ");
                    String accType = scanner.nextLine();

                    BankAccount account;
                    if (accType.equalsIgnoreCase("Savings")) {
                        account = new SavingsAccount(accNum);
                    } else if (accType.equalsIgnoreCase("Current")) {
                        account = new CurrentAccount(accNum);
                    } else {
                        System.out.println("Invalid account type. Please choose Savings or Current.");
                        break;
                    }

                    Customer customer = new Customer(name, userId, account);
                    manager.addCustomer(customer);
                    break;

                case 2:
                    System.out.print("Enter customer user ID: ");
                    int custIdDeposit = scanner.nextInt();
                    scanner.nextLine();

                    Customer custDeposit = manager.findCustomer(custIdDeposit);
                    if (custDeposit != null) {
                        System.out.print("Enter account number: ");
                        int accNumDeposit = scanner.nextInt();
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        custDeposit.deposit(accNumDeposit, depositAmount);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter customer user ID: ");
                    int custIdWithdraw = scanner.nextInt();
                    scanner.nextLine();

                    Customer custWithdraw = manager.findCustomer(custIdWithdraw);
                    if (custWithdraw != null) {
                        System.out.print("Enter account number: ");
                        int accNumWithdraw = scanner.nextInt();
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        custWithdraw.withdraw(accNumWithdraw, withdrawAmount);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 4:
                    for (Customer customerEntry : manager.getCustomersList()) {
                        System.out.println("Customer: " + customerEntry.name + ", User ID: " + customerEntry.userId);
                    }
                    break;

                case 5:
                    System.out.print("Enter customer user ID: ");
                    int custIdView = scanner.nextInt();
                    scanner.nextLine();
                    Customer custView = manager.findCustomer(custIdView);
                    if (custView != null) {
                        custView.viewAccounts();
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting application...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}*/
