package com.sbrf.reboot.service;

import com.sbrf.reboot.dto.Account;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public boolean isAccountExist(long id, Account account) {
        Set<Account> accounts = this.accountRepository.getAllAccountsByClientId(id);
        return accounts.contains(account);
    }
}
