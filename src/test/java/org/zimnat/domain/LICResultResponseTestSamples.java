package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LICResultResponseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LICResultResponse getLICResultResponseSample1() {
        return new LICResultResponse().id(1L).partnerReference("partnerReference1").theDate("theDate1").version("version1");
    }

    public static LICResultResponse getLICResultResponseSample2() {
        return new LICResultResponse().id(2L).partnerReference("partnerReference2").theDate("theDate2").version("version2");
    }

    public static LICResultResponse getLICResultResponseRandomSampleGenerator() {
        return new LICResultResponse()
            .id(longCount.incrementAndGet())
            .partnerReference(UUID.randomUUID().toString())
            .theDate(UUID.randomUUID().toString())
            .version(UUID.randomUUID().toString());
    }
}
