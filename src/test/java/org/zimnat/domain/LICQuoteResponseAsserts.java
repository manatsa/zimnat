package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LICQuoteResponseAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLICQuoteResponseAllPropertiesEquals(LICQuoteResponse expected, LICQuoteResponse actual) {
        assertLICQuoteResponseAutoGeneratedPropertiesEquals(expected, actual);
        assertLICQuoteResponseAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLICQuoteResponseAllUpdatablePropertiesEquals(LICQuoteResponse expected, LICQuoteResponse actual) {
        assertLICQuoteResponseUpdatableFieldsEquals(expected, actual);
        assertLICQuoteResponseUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLICQuoteResponseAutoGeneratedPropertiesEquals(LICQuoteResponse expected, LICQuoteResponse actual) {
        assertThat(expected)
            .as("Verify LICQuoteResponse auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLICQuoteResponseUpdatableFieldsEquals(LICQuoteResponse expected, LICQuoteResponse actual) {
        assertThat(expected)
            .as("Verify LICQuoteResponse relevant properties")
            .satisfies(e -> assertThat(e.getPartnerReference()).as("check partnerReference").isEqualTo(actual.getPartnerReference()))
            .satisfies(e -> assertThat(e.getTheDate()).as("check theDate").isEqualTo(actual.getTheDate()))
            .satisfies(e -> assertThat(e.getTheVersion()).as("check theVersion").isEqualTo(actual.getTheVersion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLICQuoteResponseUpdatableRelationshipsEquals(LICQuoteResponse expected, LICQuoteResponse actual) {
        assertThat(expected)
            .as("Verify LICQuoteResponse relationships")
            .satisfies(e -> assertThat(e.getResponse()).as("check response").isEqualTo(actual.getResponse()));
    }
}
