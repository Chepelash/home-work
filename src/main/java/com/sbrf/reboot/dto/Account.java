package com.sbrf.reboot.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class Account {
    private String number;
    private long clientId;
    private long id;
    private LocalDate createDate;
    private BigDecimal balance;

    public Account(String number){
        this.number = number;
    }
}
