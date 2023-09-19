package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * DONE
 * 
 */
public class AccountDAO {
    public ArrayList<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Account> accounts = new ArrayList<>();
        try {

            String sql = "Select * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public static Account getAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        Account result = new Account();
        try {
            String sql = "select * from Account where username=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            int generatedkey = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {

                generatedkey = rs.getInt(1);
                account.setAccount_id(generatedkey);
            }
            return account;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        Account result = new Account();
        String sql = "SELECT * FROM account WHERE account_id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));

            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}