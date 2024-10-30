package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BranchAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBranchAllPropertiesEquals(Branch expected, Branch actual) {
        assertBranchAutoGeneratedPropertiesEquals(expected, actual);
        assertBranchAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBranchAllUpdatablePropertiesEquals(Branch expected, Branch actual) {
        assertBranchUpdatableFieldsEquals(expected, actual);
        assertBranchUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBranchAutoGeneratedPropertiesEquals(Branch expected, Branch actual) {
        assertThat(expected)
            .as("Verify Branch auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBranchUpdatableFieldsEquals(Branch expected, Branch actual) {
        assertThat(expected)
            .as("Verify Branch relevant properties")
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
    public static void assertBranchUpdatableRelationshipsEquals(Branch expected, Branch actual) {
        assertThat(expected)
            .as("Verify Branch relationships")
            .satisfies(e -> assertThat(e.getFamilyCasket()).as("check familyCasket").isEqualTo(actual.getFamilyCasket()));
    }
}
