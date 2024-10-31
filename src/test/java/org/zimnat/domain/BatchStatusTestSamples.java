package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BatchStatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BatchStatus getBatchStatusSample1() {
        return new BatchStatus()
            .id(1L)
            .partnerReference("partnerReference1")
            .theDate("theDate1")
            .theVersion("theVersion1")
            .partnerToken("partnerToken1");
    }

    public static BatchStatus getBatchStatusSample2() {
        return new BatchStatus()
            .id(2L)
            .partnerReference("partnerReference2")
            .theDate("theDate2")
            .theVersion("theVersion2")
            .partnerToken("partnerToken2");
    }

    public static BatchStatus getBatchStatusRandomSampleGenerator() {
        return new BatchStatus()
            .id(longCount.incrementAndGet())
            .partnerReference(UUID.randomUUID().toString())
            .theDate(UUID.randomUUID().toString())
            .theVersion(UUID.randomUUID().toString())
            .partnerToken(UUID.randomUUID().toString());
    }
}
