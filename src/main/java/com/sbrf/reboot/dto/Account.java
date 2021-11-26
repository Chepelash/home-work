package com.sbrf.reboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    private long id;
    private LocalDate createDate;
    private BigDecimal balance;
}
