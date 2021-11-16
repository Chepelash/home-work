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
        OneHundred oneHundred = new OneHundred();

        Cassette<OneHundred> cassette = new Cassette<>(new ArrayList<OneHundred>() {{
            add(oneHundred);
//            add(new OneThousand()); //it will not compile
//            add(new Banknote()); //it will not compile
        }});

        Assertions.assertEquals(1, cassette.getCountBanknotes());
    }

    @Test
    void getCountThreeBanknotes() {
        OneThousand oneThousand = new OneThousand();

        Cassette<OneThousand> cassette = new Cassette<>(new ArrayList<OneThousand>() {{
            add(oneThousand);
            add(oneThousand);
            add(oneThousand);
//            add(new OneHundred()); //it will not compile
//            add(new Banknote()); //it will not compile
        }});

        Assertions.assertEquals(3, cassette.getCountBanknotes());
    }
}