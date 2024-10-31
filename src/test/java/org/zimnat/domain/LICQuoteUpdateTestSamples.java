package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LICQuoteUpdateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LICQuoteUpdate getLICQuoteUpdateSample1() {
        return new LICQuoteUpdate()
            .id(1L)
            .partnerReference("partnerReference1")
            .theDate("theDate1")
            .theVersion("theVersion1")
            .partnerToken("partnerToken1")
            .licenceID("licenceID1");
    }

    public static LICQuoteUpdate getLICQuoteUpdateSample2() {
        return new LICQuoteUpdate()
            .id(2L)
            .partnerReference("partnerReference2")
            .theDate("theDate2")
            .theVersion("theVersion2")
            .partnerToken("partnerToken2")
            .licenceID("licenceID2");
    }

    public static LICQuoteUpdate getLICQuoteUpdateRandomSampleGenerator() {
        return new LICQuoteUpdate()
            .id(longCount.incrementAndGet())
            .partnerReference(UUID.randomUUID().toString())
            .theDate(UUID.randomUUID().toString())
            .theVersion(UUID.randomUUID().toString())
            .partnerToken(UUID.randomUUID().toString())
            .licenceID(UUID.randomUUID().toString());
    }
}
