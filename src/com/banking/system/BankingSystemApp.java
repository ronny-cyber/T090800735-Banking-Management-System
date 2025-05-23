package com.banking.system;

import java.util.List;
import java.util.Scanner;

public class BankingSystemApp {

    private static final BankingService service = new BankingServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

 
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    
    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getUserChoice();
            handleUserChoice(choice);
        }
    }

    private static void printMenu() {
    	System.out.println("\n" + CYAN + "==========================================");
        System.out.println("🌟 WELCOME TO BANKING MANAGEMENT SYSTEM 🌟");
        System.out.println("==========================================" + RESET);
        System.out.println(GREEN + "1️   ➤ Add Customer");
        System.out.println("2️   ➤ Add Account");
        System.out.println("3️   ➤ Add Transaction");
        System.out.println("4️   ➤ Add Beneficiary");
        System.out.println("5️   ➤ View Transactions by Account ID");
        System.out.println("6️   ➤ View Beneficiaries by Customer ID");
        System.out.println("7️   ➤ Find Customer by ID");
        System.out.println("8️   ➤ Find Account by ID");
        System.out.println("9️   ➤ List All Customers");
        System.out.println("🔟  ➤ List All Accounts");
        System.out.println("🚪  ➤ Exit" + RESET);
        System.out.println(CYAN + "==========================================" + RESET);
        System.out.print("👉 Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("RED + \"❌ Invalid input! Please enter a valid number.\" + RESET");
            return -1; // Return invalid choice
        }
    }

    private static void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> addCustomer();
            case 2 -> addAccount();
            case 3 -> addTransaction();
            case 4 -> addBeneficiary();
            case 5 -> viewTransactionsByAccountId();
            case 6 -> viewBeneficiariesByCustomerId();
            case 7 -> findCustomerById();
            case 8 -> findAccountById();
            case 9 -> listAllCustomers();
            case 10 -> listAllAccounts();
            case 11 -> exitSystem();
            default -> System.out.println("RED + \"❌ Invalid choice! Please select a valid option from the menu.\" + RESET");
        }
    }

    private static void addCustomer() {
    	System.out.println("\n" + YELLOW + "🆕 Adding a New Customer" + RESET);
        System.out.print("👤 Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        System.out.print("📛 Name: ");
        String name = scanner.nextLine();
        System.out.print("🏡 Address: ");
        String address = scanner.nextLine();
        System.out.print("📞 Contact: ");
        String contact = scanner.nextLine();

        Customer customer = new Customer(customerId, name, address, contact);
        service.addCustomer(customer);
        System.out.println(GREEN + "✅ Customer added successfully!" + RESET);
    }

    private static void addAccount() {
    	System.out.println("\n" + YELLOW + "💰 Adding a New Account" + RESET);
        System.out.print("🏦 Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.print("👤 Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        System.out.print("💼 Account Type (Saving/Current): ");
        String type = scanner.nextLine();
        System.out.print("💲 Initial Balance: ");
        double balance = Double.parseDouble(scanner.nextLine());

        Account account = new Account(accountId, customerId, type, balance);
        service.addAccount(account);
        System.out.println(GREEN + "✅ Account added successfully!" + RESET);
    }

    private static void addTransaction() {
    	System.out.println("\n" + YELLOW + "🔄 Adding a New Transaction" + RESET);
        System.out.print("🏦 Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.print("💳 Transaction Type (Deposit/Withdrawal): ");
        String type = scanner.nextLine();
        System.out.print("💰 Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction transaction = new Transaction(0, accountId, type, amount, null);
        service.addTransaction(transaction);
        System.out.println(GREEN + "✅ Transaction added successfully!" + RESET);
    }

    private static void addBeneficiary() {
    	System.out.println("\n" + YELLOW + "👥 Adding a New Beneficiary" + RESET);
        System.out.print("🆔 Beneficiary ID: ");
        int beneficiaryId = Integer.parseInt(scanner.nextLine());
        System.out.print("👤 Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        System.out.print("📛 Name: ");
        String name = scanner.nextLine();
        System.out.print("🏦 Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("🏛 Bank Details: ");
        String bankDetails = scanner.nextLine();

        Beneficiary beneficiary = new Beneficiary(beneficiaryId, customerId, name, accountNumber, bankDetails);
        service.addBeneficiary(beneficiary);
        System.out.println(GREEN + "✅ Beneficiary added successfully!" + RESET);
    }

    private static void viewTransactionsByAccountId() {
    	System.out.print("\n" + YELLOW + "🏦 Enter Account ID: " + RESET);
        int accountId = Integer.parseInt(scanner.nextLine());
        List<Transaction> transactions = service.getTransactionsByAccountId(accountId);

        if (transactions.isEmpty()) {
            System.out.println(RED + "❌ No transactions found for Account ID: " + accountId + RESET);
        } else {
            System.out.println(CYAN + "\n📜 Transactions for Account ID " + accountId + ":" + RESET);
            transactions.forEach(transaction -> System.out.println(GREEN + "✅ " + transaction + RESET));
        }
    }

    private static void viewBeneficiariesByCustomerId() {
    	System.out.print("\n" + YELLOW + "👤 Enter Customer ID: " + RESET);
        int customerId = Integer.parseInt(scanner.nextLine());
        List<Beneficiary> beneficiaries = service.getBeneficiariesByCustomerId(customerId);

        if (beneficiaries.isEmpty()) {
            System.out.println(RED + "❌ No beneficiaries found for Customer ID: " + customerId + RESET);
        } else {
            System.out.println(CYAN + "\n🤝 Beneficiaries for Customer ID " + customerId + ":" + RESET);
            beneficiaries.forEach(beneficiary -> System.out.println(GREEN + "✅ " + beneficiary + RESET));
        }
    }

    private static void findCustomerById() {
    	System.out.print("\n" + YELLOW + "🔍 Enter Customer ID: " + RESET);
        int customerId = Integer.parseInt(scanner.nextLine());
        Customer customer = service.findCustomerById(customerId);

        if (customer == null) {
            System.out.println(RED + "❌ Customer not found." + RESET);
        } else {
            System.out.println(GREEN + "✅ Customer Details: " + customer + RESET);
        }
    }

    private static void findAccountById() {
    	System.out.print("\n" + YELLOW + "🔍 Enter Account ID: " + RESET);
        int accountId = Integer.parseInt(scanner.nextLine());
        Account account = service.findAccountById(accountId);

        if (account == null) {
            System.out.println(RED + "❌ Account not found." + RESET);
        } else {
            System.out.println(GREEN + "✅ Account Details: " + account + RESET);
        }
    }

    private static void listAllCustomers() {
    	List<Customer> customers = service.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println(RED + "❌ No customers found." + RESET);
        } else {
            System.out.println(CYAN + "\n📋 All Customers:" + RESET);
            customers.forEach(customer -> System.out.println(GREEN + "✅ " + customer + RESET));
        }
    }

    private static void listAllAccounts() {
    	List<Account> accounts = service.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println(RED + "❌ No accounts found." + RESET);
        } else {
            System.out.println(CYAN + "\n🏦 All Accounts:" + RESET);
            accounts.forEach(account -> System.out.println(GREEN + "✅ " + account + RESET));
        }
    }

    private static void exitSystem() {
    	System.out.println("\n" + CYAN + "==========================================");
        System.out.println("🙏 Thank you for using the Banking System! 🙏");
        System.out.println("🚀 Have a great day ahead! 🚀");
        System.out.println("==========================================" + RESET);
        scanner.close();
        System.exit(0);
    }
}
