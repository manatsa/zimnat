package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICQuoteUpdateTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICQuoteUpdateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICQuoteUpdate.class);
        LICQuoteUpdate lICQuoteUpdate1 = getLICQuoteUpdateSample1();
        LICQuoteUpdate lICQuoteUpdate2 = new LICQuoteUpdate();
        assertThat(lICQuoteUpdate1).isNotEqualTo(lICQuoteUpdate2);

        lICQuoteUpdate2.setId(lICQuoteUpdate1.getId());
        assertThat(lICQuoteUpdate1).isEqualTo(lICQuoteUpdate2);

        lICQuoteUpdate2 = getLICQuoteUpdateSample2();
        assertThat(lICQuoteUpdate1).isNotEqualTo(lICQuoteUpdate2);
    }
}
