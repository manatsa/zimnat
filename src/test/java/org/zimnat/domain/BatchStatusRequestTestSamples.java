package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BatchStatusRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BatchStatusRequest getBatchStatusRequestSample1() {
        return new BatchStatusRequest().id(1L).theFunction("theFunction1").insuranceIDBatch("insuranceIDBatch1");
    }

    public static BatchStatusRequest getBatchStatusRequestSample2() {
        return new BatchStatusRequest().id(2L).theFunction("theFunction2").insuranceIDBatch("insuranceIDBatch2");
    }

    public static BatchStatusRequest getBatchStatusRequestRandomSampleGenerator() {
        return new BatchStatusRequest()
            .id(longCount.incrementAndGet())
            .theFunction(UUID.randomUUID().toString())
            .insuranceIDBatch(UUID.randomUUID().toString());
    }
}
