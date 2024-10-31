package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class QuoteResponseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static QuoteResponse getQuoteResponseSample1() {
        return new QuoteResponse().id(1L).result("result1").message("message1");
    }

    public static QuoteResponse getQuoteResponseSample2() {
        return new QuoteResponse().id(2L).result("result2").message("message2");
    }

    public static QuoteResponse getQuoteResponseRandomSampleGenerator() {
        return new QuoteResponse()
            .id(longCount.incrementAndGet())
            .result(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString());
    }
}
