package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TransDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TransDetails getTransDetailsSample1() {
        return new TransDetails().id(1L).policyRef("policyRef1");
    }

    public static TransDetails getTransDetailsSample2() {
        return new TransDetails().id(2L).policyRef("policyRef2");
    }

    public static TransDetails getTransDetailsRandomSampleGenerator() {
        return new TransDetails().id(longCount.incrementAndGet()).policyRef(UUID.randomUUID().toString());
    }
}
