package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Account;

import java.util.Set;

public interface AccountRepository {
    Set<Account> getAllAccountsByClientId(long id);
}
