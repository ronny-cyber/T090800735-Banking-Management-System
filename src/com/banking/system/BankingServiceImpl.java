package com.banking.system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankingServiceImpl implements BankingService {
	
	@Override
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO Customer (customerID, name, address, contact) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getContact());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAccount(Account account) {
        String query = "INSERT INTO Account (accountID, customerID, type, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, account.getAccountID());
            stmt.setInt(2, account.getCustomerID());
            stmt.setString(3, account.getType());
            stmt.setDouble(4, account.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO Transaction (accountID, type, amount) VALUES (?, ?, ?)";
        String updateBalance = "UPDATE Account SET balance = balance + ? WHERE accountID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement updateStmt = conn.prepareStatement(updateBalance)) {

            // Insert transaction
            stmt.setInt(1, transaction.getAccountID());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.executeUpdate();

            // Update account balance
            double balanceChange = transaction.getType().equalsIgnoreCase("Deposit") ? transaction.getAmount() : -transaction.getAmount();
            updateStmt.setDouble(1, balanceChange);
            updateStmt.setInt(2, transaction.getAccountID());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBeneficiary(Beneficiary beneficiary) {
        String query = "INSERT INTO Beneficiary (beneficiaryID, customerID, name, accountNumber, bankDetails) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, beneficiary.getBeneficiaryID());
            stmt.setInt(2, beneficiary.getCustomerID());
            stmt.setString(3, beneficiary.getName());
            stmt.setString(4, beneficiary.getAccountNumber());
            stmt.setString(5, beneficiary.getBankDetails());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findCustomerById(int id) {
        String query = "SELECT * FROM Customer WHERE customerID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("customerID"), rs.getString("name"), rs.getString("address"), rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findAccountById(int id) {
        String query = "SELECT * FROM Account WHERE accountID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("accountID"), rs.getInt("customerID"), rs.getString("type"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transaction WHERE accountID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transactionID"),
                        rs.getInt("accountID"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Beneficiary> getBeneficiariesByCustomerId(int customerId) {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        String query = "SELECT * FROM Beneficiary WHERE customerID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Beneficiary beneficiary = new Beneficiary(
                        rs.getInt("beneficiaryID"),
                        rs.getInt("customerID"),
                        rs.getString("name"),
                        rs.getString("accountNumber"),
                        rs.getString("bankDetails")
                );
                beneficiaries.add(beneficiary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beneficiaries;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("customerID"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("accountID"),
                        rs.getInt("customerID"),
                        rs.getString("type"),
                        rs.getDouble("balance")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }


}
