package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .name("name1")
            .description("description1")
            .code("code1")
            .riskCode("riskCode1")
            .screenCode("screenCode1")
            .dataModelCode("dataModelCode1");
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .name("name2")
            .description("description2")
            .code("code2")
            .riskCode("riskCode2")
            .screenCode("screenCode2")
            .dataModelCode("dataModelCode2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .riskCode(UUID.randomUUID().toString())
            .screenCode(UUID.randomUUID().toString())
            .dataModelCode(UUID.randomUUID().toString());
    }
}
