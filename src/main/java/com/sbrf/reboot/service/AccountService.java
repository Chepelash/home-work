package com.sbrf.reboot.service;

import java.util.Set;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public boolean isAccountExist(long id, Account account) {
        Set<Account> accounts = this.accountRepository.getAllAccountsByClientId(id);
        return accounts.contains(account);
    }
}
