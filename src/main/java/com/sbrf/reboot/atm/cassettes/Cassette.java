package com.sbrf.reboot.atm.cassettes;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class Cassette<T extends Banknote> {
    private ArrayList<T> banknotes;
    public int getCountBanknotes() {
        return banknotes.size();
    }
}
