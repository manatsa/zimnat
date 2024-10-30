package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SettingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSettingAllPropertiesEquals(Setting expected, Setting actual) {
        assertSettingAutoGeneratedPropertiesEquals(expected, actual);
        assertSettingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSettingAllUpdatablePropertiesEquals(Setting expected, Setting actual) {
        assertSettingUpdatableFieldsEquals(expected, actual);
        assertSettingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSettingAutoGeneratedPropertiesEquals(Setting expected, Setting actual) {
        assertThat(expected)
            .as("Verify Setting auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSettingUpdatableFieldsEquals(Setting expected, Setting actual) {
        assertThat(expected)
            .as("Verify Setting relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getValue()).as("check value").isEqualTo(actual.getValue()))
            .satisfies(e -> assertThat(e.getEffectiveDate()).as("check effectiveDate").isEqualTo(actual.getEffectiveDate()))
            .satisfies(e -> assertThat(e.getLastDate()).as("check lastDate").isEqualTo(actual.getLastDate()))
            .satisfies(e -> assertThat(e.getAdminAccess()).as("check adminAccess").isEqualTo(actual.getAdminAccess()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSettingUpdatableRelationshipsEquals(Setting expected, Setting actual) {}
}