package com.sbrf.reboot.service;

import java.util.Set;

public interface AccountRepository {
    public Set<Account> getAllAccountsByClientId(long id);
}
