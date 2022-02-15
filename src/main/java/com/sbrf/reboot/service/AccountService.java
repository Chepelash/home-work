package com.sbrf.reboot.service;

import com.sbrf.reboot.dto.Account;
import com.sbrf.reboot.repository.AccountRepository;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Account getMaxAccountBalance(long clientId) throws FileNotFoundException, NoSuchFieldException {
        Set<Account> accounts = accountRepository.getAllAccountsByClientId(clientId);
        if(accounts == null)
            throw new NoSuchFieldException("No accounts found");
        return accounts.stream().max(Comparator.comparing(Account::getBalance))
                .orElse(Account.builder().clientId(0L).id(0L).balance(BigDecimal.ZERO).build());
    }

    public Set getAllAccountsByDateMoreThen(long clientId, LocalDate minusDays) throws FileNotFoundException, NoSuchFieldException {
        Set<Account> accounts = accountRepository.getAllAccountsByClientId(clientId);
        if(accounts == null)
            throw new NoSuchFieldException("No accounts found");
        return accounts.stream()
                .filter(account -> account.getCreateDate().
                        isAfter(minusDays) || account.getCreateDate().isEqual(minusDays))
                .collect(Collectors.toSet());
    }
}
