package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TPILICQuoteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TPILICQuote getTPILICQuoteSample1() {
        return new TPILICQuote()
            .id(1L)
            .partnerReference(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .theDate("theDate1")
            .theVersion("theVersion1")
            .partnerToken("partnerToken1")
            .function("function1");
    }

    public static TPILICQuote getTPILICQuoteSample2() {
        return new TPILICQuote()
            .id(2L)
            .partnerReference(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .theDate("theDate2")
            .theVersion("theVersion2")
            .partnerToken("partnerToken2")
            .function("function2");
    }

    public static TPILICQuote getTPILICQuoteRandomSampleGenerator() {
        return new TPILICQuote()
            .id(longCount.incrementAndGet())
            .partnerReference(UUID.randomUUID())
            .theDate(UUID.randomUUID().toString())
            .theVersion(UUID.randomUUID().toString())
            .partnerToken(UUID.randomUUID().toString())
            .function(UUID.randomUUID().toString());
    }
}
