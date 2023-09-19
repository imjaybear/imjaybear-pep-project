package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.ArrayList;

/*
 * DONE
 */
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public ArrayList<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account getAccount(Account account) {
        ArrayList<Account> accounts = new ArrayList<>();
        Account foundAcc = new Account();
        accounts.addAll(accountDAO.getAllAccounts());
        boolean isFound = accounts.contains(account);
        
        if (isFound == false && account.username == "" && account.password.length() < 4) {
            return null;
        } else 
            foundAcc = (AccountDAO.getAccount(account));
        
        return foundAcc;
    }

    public Account addAccount(Account account) {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts = accountDAO.getAllAccounts();
        boolean isFound = accounts.contains(account);
        
        if (isFound || account.username == "" || account.password.length() < 4) {
            return null;
        } else {
            Account name = AccountDAO.insertAccount(account);
            return name;
        }
    }

    public Account retrieveMessage(int id) {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts = accountDAO.getAllAccounts();
        Account account = new Account();
        
        for (Account acc : accounts) {
            if (acc.account_id == id) {
                account = accountDAO.getAccountById(id);
            } else
                return null;

        }
        return account;
    }
}