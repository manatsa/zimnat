package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LICQuoteResponseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LICQuoteResponse getLICQuoteResponseSample1() {
        return new LICQuoteResponse().id(1L).partnerReference("partnerReference1").theDate("theDate1").theVersion("theVersion1");
    }

    public static LICQuoteResponse getLICQuoteResponseSample2() {
        return new LICQuoteResponse().id(2L).partnerReference("partnerReference2").theDate("theDate2").theVersion("theVersion2");
    }

    public static LICQuoteResponse getLICQuoteResponseRandomSampleGenerator() {
        return new LICQuoteResponse()
            .id(longCount.incrementAndGet())
            .partnerReference(UUID.randomUUID().toString())
            .theDate(UUID.randomUUID().toString())
            .theVersion(UUID.randomUUID().toString());
    }
}
