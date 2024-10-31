package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.TPILICQuoteTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class TPILICQuoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPILICQuote.class);
        TPILICQuote tPILICQuote1 = getTPILICQuoteSample1();
        TPILICQuote tPILICQuote2 = new TPILICQuote();
        assertThat(tPILICQuote1).isNotEqualTo(tPILICQuote2);

        tPILICQuote2.setId(tPILICQuote1.getId());
        assertThat(tPILICQuote1).isEqualTo(tPILICQuote2);

        tPILICQuote2 = getTPILICQuoteSample2();
        assertThat(tPILICQuote1).isNotEqualTo(tPILICQuote2);
    }
}
