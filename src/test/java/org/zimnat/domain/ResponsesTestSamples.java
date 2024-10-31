package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ResponsesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Responses getResponsesSample1() {
        return new Responses().id(1L).result("result1").message("message1");
    }

    public static Responses getResponsesSample2() {
        return new Responses().id(2L).result("result2").message("message2");
    }

    public static Responses getResponsesRandomSampleGenerator() {
        return new Responses().id(longCount.incrementAndGet()).result(UUID.randomUUID().toString()).message(UUID.randomUUID().toString());
    }
}
