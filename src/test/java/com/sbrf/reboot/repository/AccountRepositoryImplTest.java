package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryImplTest {

    AccountRepositoryImpl accountRepository;


    @Test
    void onlyPersonalAccounts() throws FileNotFoundException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<Account> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1);
        ArrayList<String> strings = new ArrayList<String>() {{
            add("2-ACCNUM");
            add("1-ACCNUM");
            add("4-ACC1NUM");
        }};

        allAccountsByClientId.forEach(e -> assertTrue(strings.contains(e.getNumber())));
    }

    @Test
    void successGetAllAccountsByClientId() throws FileNotFoundException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<Account> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1);

        assertEquals(1, (int) allAccountsByClientId.stream().filter(e -> e.getNumber().equals("4-ACC1NUM")).count());
    }

    @Test
    void failGetAllAccountsByClientId() {
        accountRepository = new AccountRepositoryImpl("somePath");
        assertThrows(FileNotFoundException.class, () -> {
            accountRepository.getAllAccountsByClientId(1L);
        });
    }

    @Test
    void successUpdateValue() throws IOException, NoSuchFieldException {
        // copy test file
        Path copied = Paths.get("src/main/resources/Accounts_copy.txt");
        Path originalPath = Paths.get("src/main/resources/Accounts.txt");
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        accountRepository = new AccountRepositoryImpl(copied.toString());
        accountRepository.updateClientNumber(3L, "3-ACCNUM", "5-ACCNUM");
        Set<Account> allAccountsByClientId = accountRepository.getAllAccountsByClientId(3L);
        assertEquals(1, (int) allAccountsByClientId.stream().filter(e -> e.getNumber().equals("5-ACCNUM")).count());
    }

    @Test
    void fileNotExistsUpdateValue() {
        accountRepository = new AccountRepositoryImpl("somePath");
        assertThrows(FileNotFoundException.class, () -> {
            accountRepository.updateClientNumber(1L, "3-ACCNUM", "5-ACCNUM");
        });
    }

    @Test
    void idNotExistsUpdateValue() {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        assertThrows(NoSuchFieldException.class, () -> {
            accountRepository.updateClientNumber(4L, "", "");
        });
    }

    @Test
    void numberNotExistsUpdateValue() {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        assertThrows(NoSuchFieldException.class, () -> {
            accountRepository.updateClientNumber(1L, "213123", "5-ACCNUM");
        });
    }
}