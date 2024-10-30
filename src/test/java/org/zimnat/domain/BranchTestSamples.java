package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BranchTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Branch getBranchSample1() {
        return new Branch().id(1L).name("name1").code("code1").address("address1");
    }

    public static Branch getBranchSample2() {
        return new Branch().id(2L).name("name2").code("code2").address("address2");
    }

    public static Branch getBranchRandomSampleGenerator() {
        return new Branch()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}
