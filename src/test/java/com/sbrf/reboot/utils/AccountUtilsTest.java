package com.sbrf.reboot.utils;

import com.sbrf.reboot.dto.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class AccountUtilsTest {

    @Test
    void sortedById() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id(3L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(1L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(2L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedById(accounts);

        Assertions.assertEquals(1L, accounts.get(0).getId());
        Assertions.assertEquals(2L, accounts.get(1).getId());
        Assertions.assertEquals(3L, accounts.get(2).getId());
        Assertions.assertEquals(3L, accounts.get(3).getId());

    }

    @Test
    void sortedByIdDate() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id(1L).createDate(LocalDate.now().minusDays(4)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now().minusDays(3)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now().minusDays(1)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(2L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedByIdDate(accounts);

        Assertions.assertEquals(1L, accounts.get(0).getId());
        Assertions.assertEquals(2L, accounts.get(1).getId());
        Assertions.assertEquals(LocalDate.now().minusDays(3), accounts.get(2).getCreateDate());
        Assertions.assertEquals(LocalDate.now().minusDays(1), accounts.get(3).getCreateDate());
    }

    @Test
    void sortedByIdDateBalance() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id(1L).createDate(LocalDate.now().minusDays(4)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(2L).createDate(LocalDate.now()).balance(BigDecimal.valueOf(6)).build());
            add(Account.builder().id(2L).createDate(LocalDate.now()).balance(BigDecimal.valueOf(7)).build());
            add(Account.builder().id(2L).createDate(LocalDate.now().minusDays(1)).balance(BigDecimal.valueOf(6)).build());
        }};

        AccountUtils.sortedByIdDateBalance(accounts);

        Assertions.assertEquals(1L, accounts.get(0).getId());
        Assertions.assertEquals(2L, accounts.get(1).getId());
        Assertions.assertEquals(2L, accounts.get(2).getId());
        Assertions.assertEquals(2L, accounts.get(3).getId());

        Assertions.assertEquals(LocalDate.now().minusDays(4), accounts.get(0).getCreateDate());
        Assertions.assertEquals(LocalDate.now().minusDays(1), accounts.get(1).getCreateDate());
        Assertions.assertEquals(LocalDate.now(), accounts.get(2).getCreateDate());
        Assertions.assertEquals(LocalDate.now(), accounts.get(3).getCreateDate());

        Assertions.assertEquals(BigDecimal.TEN, accounts.get(0).getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(6), accounts.get(1).getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(6), accounts.get(2).getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(7), accounts.get(3).getBalance());
    }
}
