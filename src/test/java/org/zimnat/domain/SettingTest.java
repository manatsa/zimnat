package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.SettingTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class SettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Setting.class);
        Setting setting1 = getSettingSample1();
        Setting setting2 = new Setting();
        assertThat(setting1).isNotEqualTo(setting2);

        setting2.setId(setting1.getId());
        assertThat(setting1).isEqualTo(setting2);

        setting2 = getSettingSample2();
        assertThat(setting1).isNotEqualTo(setting2);
    }
}
