package org.zimnat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LICConfirmationResponseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LICConfirmationResponse getLICConfirmationResponseSample1() {
        return new LICConfirmationResponse()
            .id(1L)
            .result("result1")
            .message("message1")
            .licenceID("licenceID1")
            .receiptID("receiptID1")
            .vRN("vRN1")
            .status("status1")
            .loadedBy("loadedBy1")
            .loadedDate("loadedDate1")
            .approvedBy("approvedBy1")
            .approvedDate("approvedDate1")
            .iDNumber("iDNumber1")
            .firstName("firstName1")
            .lastName("lastName1")
            .address1("address11")
            .address2("address21")
            .suburbID("suburbID1")
            .transactionAmt("transactionAmt1")
            .arrearsAmt("arrearsAmt1")
            .penaltiesAmt("penaltiesAmt1")
            .administrationAmt("administrationAmt1")
            .totalLicAmt("totalLicAmt1")
            .radioTVAmt("radioTVAmt1")
            .radioTVArrearsAmt("radioTVArrearsAmt1")
            .totalRadioTVAmt("totalRadioTVAmt1")
            .totalAmount("totalAmount1")
            .taxClass("taxClass1")
            .nettMass("nettMass1")
            .licExpiryDate("licExpiryDate1")
            .radioTVExpiryDate("radioTVExpiryDate1");
    }

    public static LICConfirmationResponse getLICConfirmationResponseSample2() {
        return new LICConfirmationResponse()
            .id(2L)
            .result("result2")
            .message("message2")
            .licenceID("licenceID2")
            .receiptID("receiptID2")
            .vRN("vRN2")
            .status("status2")
            .loadedBy("loadedBy2")
            .loadedDate("loadedDate2")
            .approvedBy("approvedBy2")
            .approvedDate("approvedDate2")
            .iDNumber("iDNumber2")
            .firstName("firstName2")
            .lastName("lastName2")
            .address1("address12")
            .address2("address22")
            .suburbID("suburbID2")
            .transactionAmt("transactionAmt2")
            .arrearsAmt("arrearsAmt2")
            .penaltiesAmt("penaltiesAmt2")
            .administrationAmt("administrationAmt2")
            .totalLicAmt("totalLicAmt2")
            .radioTVAmt("radioTVAmt2")
            .radioTVArrearsAmt("radioTVArrearsAmt2")
            .totalRadioTVAmt("totalRadioTVAmt2")
            .totalAmount("totalAmount2")
            .taxClass("taxClass2")
            .nettMass("nettMass2")
            .licExpiryDate("licExpiryDate2")
            .radioTVExpiryDate("radioTVExpiryDate2");
    }

    public static LICConfirmationResponse getLICConfirmationResponseRandomSampleGenerator() {
        return new LICConfirmationResponse()
            .id(longCount.incrementAndGet())
            .result(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString())
            .licenceID(UUID.randomUUID().toString())
            .receiptID(UUID.randomUUID().toString())
            .vRN(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .loadedBy(UUID.randomUUID().toString())
            .loadedDate(UUID.randomUUID().toString())
            .approvedBy(UUID.randomUUID().toString())
            .approvedDate(UUID.randomUUID().toString())
            .iDNumber(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .address1(UUID.randomUUID().toString())
            .address2(UUID.randomUUID().toString())
            .suburbID(UUID.randomUUID().toString())
            .transactionAmt(UUID.randomUUID().toString())
            .arrearsAmt(UUID.randomUUID().toString())
            .penaltiesAmt(UUID.randomUUID().toString())
            .administrationAmt(UUID.randomUUID().toString())
            .totalLicAmt(UUID.randomUUID().toString())
            .radioTVAmt(UUID.randomUUID().toString())
            .radioTVArrearsAmt(UUID.randomUUID().toString())
            .totalRadioTVAmt(UUID.randomUUID().toString())
            .totalAmount(UUID.randomUUID().toString())
            .taxClass(UUID.randomUUID().toString())
            .nettMass(UUID.randomUUID().toString())
            .licExpiryDate(UUID.randomUUID().toString())
            .radioTVExpiryDate(UUID.randomUUID().toString());
    }
}
