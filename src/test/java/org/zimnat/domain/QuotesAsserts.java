package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class QuotesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertQuotesAllPropertiesEquals(Quotes expected, Quotes actual) {
        assertQuotesAutoGeneratedPropertiesEquals(expected, actual);
        assertQuotesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertQuotesAllUpdatablePropertiesEquals(Quotes expected, Quotes actual) {
        assertQuotesUpdatableFieldsEquals(expected, actual);
        assertQuotesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertQuotesAutoGeneratedPropertiesEquals(Quotes expected, Quotes actual) {
        assertThat(expected)
            .as("Verify Quotes auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertQuotesUpdatableFieldsEquals(Quotes expected, Quotes actual) {
        assertThat(expected)
            .as("Verify Quotes relevant properties")
            .satisfies(e -> assertThat(e.getvRN()).as("check vRN").isEqualTo(actual.getvRN()))
            .satisfies(e -> assertThat(e.getLicenceID()).as("check licenceID").isEqualTo(actual.getLicenceID()))
            .satisfies(e -> assertThat(e.getResult()).as("check result").isEqualTo(actual.getResult()))
            .satisfies(e -> assertThat(e.getMessage()).as("check message").isEqualTo(actual.getMessage()))
            .satisfies(e -> assertThat(e.getiDNumber()).as("check iDNumber").isEqualTo(actual.getiDNumber()))
            .satisfies(e -> assertThat(e.getClientIDType()).as("check clientIDType").isEqualTo(actual.getClientIDType()))
            .satisfies(e -> assertThat(e.getFirstName()).as("check firstName").isEqualTo(actual.getFirstName()))
            .satisfies(e -> assertThat(e.getLastName()).as("check lastName").isEqualTo(actual.getLastName()))
            .satisfies(e -> assertThat(e.getAddress1()).as("check address1").isEqualTo(actual.getAddress1()))
            .satisfies(e -> assertThat(e.getAddress2()).as("check address2").isEqualTo(actual.getAddress2()))
            .satisfies(e -> assertThat(e.getSuburbID()).as("check suburbID").isEqualTo(actual.getSuburbID()))
            .satisfies(e -> assertThat(e.getLicFrequency()).as("check licFrequency").isEqualTo(actual.getLicFrequency()))
            .satisfies(e -> assertThat(e.getRadioTVUsage()).as("check radioTVUsage").isEqualTo(actual.getRadioTVUsage()))
            .satisfies(e -> assertThat(e.getRadioTVFrequency()).as("check radioTVFrequency").isEqualTo(actual.getRadioTVFrequency()))
            .satisfies(e -> assertThat(e.getTaxClass()).as("check taxClass").isEqualTo(actual.getTaxClass()))
            .satisfies(e -> assertThat(e.getNettMass()).as("check nettMass").isEqualTo(actual.getNettMass()))
            .satisfies(e -> assertThat(e.getLicExpiryDate()).as("check licExpiryDate").isEqualTo(actual.getLicExpiryDate()))
            .satisfies(e -> assertThat(e.getTransactionAmt()).as("check transactionAmt").isEqualTo(actual.getTransactionAmt()))
            .satisfies(e -> assertThat(e.getArrearsAmt()).as("check arrearsAmt").isEqualTo(actual.getArrearsAmt()))
            .satisfies(e -> assertThat(e.getPenaltiesAmt()).as("check penaltiesAmt").isEqualTo(actual.getPenaltiesAmt()))
            .satisfies(e -> assertThat(e.getAdministrationAmt()).as("check administrationAmt").isEqualTo(actual.getAdministrationAmt()))
            .satisfies(e -> assertThat(e.getTotalLicAmt()).as("check totalLicAmt").isEqualTo(actual.getTotalLicAmt()))
            .satisfies(e -> assertThat(e.getRadioTVAmt()).as("check radioTVAmt").isEqualTo(actual.getRadioTVAmt()))
            .satisfies(e -> assertThat(e.getRadioTVArrearsAmt()).as("check radioTVArrearsAmt").isEqualTo(actual.getRadioTVArrearsAmt()))
            .satisfies(e -> assertThat(e.getTotalRadioTVAmt()).as("check totalRadioTVAmt").isEqualTo(actual.getTotalRadioTVAmt()))
            .satisfies(e -> assertThat(e.getTotalAmount()).as("check totalAmount").isEqualTo(actual.getTotalAmount()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertQuotesUpdatableRelationshipsEquals(Quotes expected, Quotes actual) {}
}