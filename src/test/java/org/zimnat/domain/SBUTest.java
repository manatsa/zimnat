package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.SBUTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class SBUTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SBU.class);
        SBU sBU1 = getSBUSample1();
        SBU sBU2 = new SBU();
        assertThat(sBU1).isNotEqualTo(sBU2);

        sBU2.setId(sBU1.getId());
        assertThat(sBU1).isEqualTo(sBU2);

        sBU2 = getSBUSample2();
        assertThat(sBU1).isNotEqualTo(sBU2);
    }
}
