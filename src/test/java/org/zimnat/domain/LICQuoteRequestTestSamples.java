package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LICQuoteRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LICQuoteRequest getLICQuoteRequestSample1() {
        return new LICQuoteRequest().id(1L).theFunction("theFunction1");
    }

    public static LICQuoteRequest getLICQuoteRequestSample2() {
        return new LICQuoteRequest().id(2L).theFunction("theFunction2");
    }

    public static LICQuoteRequest getLICQuoteRequestRandomSampleGenerator() {
        return new LICQuoteRequest().id(longCount.incrementAndGet()).theFunction(UUID.randomUUID().toString());
    }
}
