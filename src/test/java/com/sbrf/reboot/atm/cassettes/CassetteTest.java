package com.sbrf.reboot.atm.cassettes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CassetteTest {

    class OneHundred extends Banknote {
    }

    class OneThousand extends Banknote {
    }

    @Test
    void getCountBanknotes() {
        Cassette<Banknote> cassette = new Cassette<>(new ArrayList<Banknote>() {{
            add(new OneHundred());
            add(new OneThousand());
            add(new Banknote());
        }});

        Assertions.assertEquals(3, cassette.getCountBanknotes());
    }

}
