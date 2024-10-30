package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SettingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Setting getSettingSample1() {
        return new Setting().id(1L).name("name1").description("description1").value("value1");
    }

    public static Setting getSettingSample2() {
        return new Setting().id(2L).name("name2").description("description2").value("value2");
    }

    public static Setting getSettingRandomSampleGenerator() {
        return new Setting()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .value(UUID.randomUUID().toString());
    }
}
