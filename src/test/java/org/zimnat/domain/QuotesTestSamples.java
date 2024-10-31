package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class QuotesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Quotes getQuotesSample1() {
        return new Quotes()
            .id(1L)
            .vRN("vRN1")
            .licenceID("licenceID1")
            .result(1)
            .message("message1")
            .iDNumber("iDNumber1")
            .clientIDType("clientIDType1")
            .firstName("firstName1")
            .lastName("lastName1")
            .address1("address11")
            .address2("address21")
            .suburbID("suburbID1")
            .licFrequency("licFrequency1")
            .radioTVUsage("radioTVUsage1")
            .radioTVFrequency("radioTVFrequency1")
            .taxClass("taxClass1")
            .nettMass("nettMass1")
            .licExpiryDate("licExpiryDate1")
            .transactionAmt("transactionAmt1")
            .arrearsAmt("arrearsAmt1")
            .penaltiesAmt("penaltiesAmt1")
            .administrationAmt("administrationAmt1")
            .totalLicAmt("totalLicAmt1")
            .radioTVAmt("radioTVAmt1")
            .radioTVArrearsAmt("radioTVArrearsAmt1")
            .totalRadioTVAmt("totalRadioTVAmt1")
            .totalAmount("totalAmount1");
    }

    public static Quotes getQuotesSample2() {
        return new Quotes()
            .id(2L)
            .vRN("vRN2")
            .licenceID("licenceID2")
            .result(2)
            .message("message2")
            .iDNumber("iDNumber2")
            .clientIDType("clientIDType2")
            .firstName("firstName2")
            .lastName("lastName2")
            .address1("address12")
            .address2("address22")
            .suburbID("suburbID2")
            .licFrequency("licFrequency2")
            .radioTVUsage("radioTVUsage2")
            .radioTVFrequency("radioTVFrequency2")
            .taxClass("taxClass2")
            .nettMass("nettMass2")
            .licExpiryDate("licExpiryDate2")
            .transactionAmt("transactionAmt2")
            .arrearsAmt("arrearsAmt2")
            .penaltiesAmt("penaltiesAmt2")
            .administrationAmt("administrationAmt2")
            .totalLicAmt("totalLicAmt2")
            .radioTVAmt("radioTVAmt2")
            .radioTVArrearsAmt("radioTVArrearsAmt2")
            .totalRadioTVAmt("totalRadioTVAmt2")
            .totalAmount("totalAmount2");
    }

    public static Quotes getQuotesRandomSampleGenerator() {
        return new Quotes()
            .id(longCount.incrementAndGet())
            .vRN(UUID.randomUUID().toString())
            .licenceID(UUID.randomUUID().toString())
            .result(intCount.incrementAndGet())
            .message(UUID.randomUUID().toString())
            .iDNumber(UUID.randomUUID().toString())
            .clientIDType(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .address1(UUID.randomUUID().toString())
            .address2(UUID.randomUUID().toString())
            .suburbID(UUID.randomUUID().toString())
            .licFrequency(UUID.randomUUID().toString())
            .radioTVUsage(UUID.randomUUID().toString())
            .radioTVFrequency(UUID.randomUUID().toString())
            .taxClass(UUID.randomUUID().toString())
            .nettMass(UUID.randomUUID().toString())
            .licExpiryDate(UUID.randomUUID().toString())
            .transactionAmt(UUID.randomUUID().toString())
            .arrearsAmt(UUID.randomUUID().toString())
            .penaltiesAmt(UUID.randomUUID().toString())
            .administrationAmt(UUID.randomUUID().toString())
            .totalLicAmt(UUID.randomUUID().toString())
            .radioTVAmt(UUID.randomUUID().toString())
            .radioTVArrearsAmt(UUID.randomUUID().toString())
            .totalRadioTVAmt(UUID.randomUUID().toString())
            .totalAmount(UUID.randomUUID().toString());
    }
}
