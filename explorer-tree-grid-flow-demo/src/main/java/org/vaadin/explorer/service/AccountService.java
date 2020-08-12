package org.vaadin.explorer.service;

import org.vaadin.explorer.bean.Account;
import org.vaadin.explorer.bean.AccountData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountService {
    private AccountData accountData = new AccountData();
    private List<Account> accountList = accountData.getAccounts();

    public long getChildCount(Account parent) {
        return accountList.stream()
                .filter(account -> Objects.equals(parent, account.getParent()))
                .count();
    }

    public Boolean hasChildren(Account parent) {

        return accountList.stream()
                .anyMatch(account -> Objects.equals(parent, account.getParent()));
    }

    public List<Account> fetchChildren(Account parent) {

        return accountList.stream()
                .filter(account -> Objects.equals(parent, account.getParent())).collect(Collectors.toList());
    }

}