package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Client getClientSample1() {
        return new Client().id(1L).firstName("firstName1").lastName("lastName1").idNumber("idNumber1").phoneNumber("phoneNumber1");
    }

    public static Client getClientSample2() {
        return new Client().id(2L).firstName("firstName2").lastName("lastName2").idNumber("idNumber2").phoneNumber("phoneNumber2");
    }

    public static Client getClientRandomSampleGenerator() {
        return new Client()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .idNumber(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString());
    }
}
