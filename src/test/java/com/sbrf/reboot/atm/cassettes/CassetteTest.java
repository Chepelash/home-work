package com.sbrf.reboot.atm.cassettes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

class CassetteTest {

    static class OneHundred extends Banknote {
    }

    static class OneThousand extends Banknote {
    }

    static Stream<ArrayList<? extends Banknote>> banknotesProvider(){
        return Stream.of(
            new ArrayList<OneHundred>(){{
                add(new OneHundred());
                add(new OneHundred());
                add(new OneHundred());
            }},
            new ArrayList<OneThousand>(){{
                add(new OneThousand());
                add(new OneThousand());
            }}
        );
    }

    @ParameterizedTest
    @MethodSource("banknotesProvider")
    void getCountBanknotes(ArrayList<? extends Banknote> banknotes) {
        Cassette<? extends Banknote> cassette = new Cassette<>(banknotes);
        Assertions.assertEquals(banknotes.size(), cassette.getCountBanknotes());
    }
}
