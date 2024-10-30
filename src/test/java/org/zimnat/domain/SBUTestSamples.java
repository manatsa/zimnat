package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SBUTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SBU getSBUSample1() {
        return new SBU().id(1L).name("name1").code("code1").address("address1");
    }

    public static SBU getSBUSample2() {
        return new SBU().id(2L).name("name2").code("code2").address("address2");
    }

    public static SBU getSBURandomSampleGenerator() {
        return new SBU()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}
