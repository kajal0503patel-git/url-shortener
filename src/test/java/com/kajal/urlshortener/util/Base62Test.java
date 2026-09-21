package com.kajal.urlshortener.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class Base62Test {

    @Test
    void encodesZeroAsZero() {
        assertEquals("0", Base62.encode(0));
    }

    @Test
    void encodesOneAsOne() {
        assertEquals("1", Base62.encode(1));
    }

    @Test
    void encodesLastSingleCharacterAsUppercaseZ() {
        assertEquals("Z", Base62.encode(61));
    }

    @Test
    void encodesSixtyTwoAsOneZero() {
        assertEquals("10", Base62.encode(62));
    }

    @Test
    void encodesOneHundredTwentyFiveAsTwoOne() {
        assertEquals("21", Base62.encode(125));
    }

    @Test
    void encodesLastTwoCharacterValueAsZZ() {
        assertEquals("ZZ", Base62.encode(3843)); // 62^2 - 1
    }

    @Test
    void encodesFirstThreeCharacterValueAsOneZeroZero() {
        assertEquals("100", Base62.encode(3844)); // 62^2
    }

    @Test
    void negativeIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Base62.encode(-1));
    }

    @Test
    void encodesLongMaxValueWithoutError() {
        // 62^10 < Long.MAX_VALUE < 62^11, so the result has 11 characters
        assertEquals(11, Base62.encode(Long.MAX_VALUE).length());
    }
}
