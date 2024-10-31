package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICResultTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICResult.class);
        LICResult lICResult1 = getLICResultSample1();
        LICResult lICResult2 = new LICResult();
        assertThat(lICResult1).isNotEqualTo(lICResult2);

        lICResult2.setId(lICResult1.getId());
        assertThat(lICResult1).isEqualTo(lICResult2);

        lICResult2 = getLICResultSample2();
        assertThat(lICResult1).isNotEqualTo(lICResult2);
    }
}
