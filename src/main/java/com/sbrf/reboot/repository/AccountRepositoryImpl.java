package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Account;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private String fpath;
    @Override
    public Set<Account> getAllAccountsByClientId(long id) {
        return null;
    }
}
