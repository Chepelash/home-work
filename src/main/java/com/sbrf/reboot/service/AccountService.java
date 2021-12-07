package com.sbrf.reboot.service;

import com.sbrf.reboot.dto.Account;
import com.sbrf.reboot.repository.AccountRepository;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public boolean isAccountExist(long id, Account account) {
        Set<Account> accounts;
        try {
            accounts = accountRepository.getAllAccountsByClientId(id);
        } catch (FileNotFoundException e){
            return false;
        }
        return accounts.contains(account);
    }

    public Account getMaxAccountBalance(long l) {
        return null;
    }

    public Set getAllAccountsByDateMoreThen(long l, LocalDate minusDays) {
        return null;
    }
}
