package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SBUAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSBUAllPropertiesEquals(SBU expected, SBU actual) {
        assertSBUAutoGeneratedPropertiesEquals(expected, actual);
        assertSBUAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSBUAllUpdatablePropertiesEquals(SBU expected, SBU actual) {
        assertSBUUpdatableFieldsEquals(expected, actual);
        assertSBUUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSBUAutoGeneratedPropertiesEquals(SBU expected, SBU actual) {
        assertThat(expected)
            .as("Verify SBU auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSBUUpdatableFieldsEquals(SBU expected, SBU actual) {
        assertThat(expected)
            .as("Verify SBU relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getAddress()).as("check address").isEqualTo(actual.getAddress()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSBUUpdatableRelationshipsEquals(SBU expected, SBU actual) {}
}
