package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FailureResponseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FailureResponse getFailureResponseSample1() {
        return new FailureResponse().id(1L).partnerReference("partnerReference1").date("date1").version("version1");
    }

    public static FailureResponse getFailureResponseSample2() {
        return new FailureResponse().id(2L).partnerReference("partnerReference2").date("date2").version("version2");
    }

    public static FailureResponse getFailureResponseRandomSampleGenerator() {
        return new FailureResponse()
            .id(longCount.incrementAndGet())
            .partnerReference(UUID.randomUUID().toString())
            .date(UUID.randomUUID().toString())
            .version(UUID.randomUUID().toString());
    }
}
