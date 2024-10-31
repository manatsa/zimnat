package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICQuoteRequestTestSamples.*;
import static org.zimnat.domain.LICQuoteTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICQuoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICQuote.class);
        LICQuote lICQuote1 = getLICQuoteSample1();
        LICQuote lICQuote2 = new LICQuote();
        assertThat(lICQuote1).isNotEqualTo(lICQuote2);

        lICQuote2.setId(lICQuote1.getId());
        assertThat(lICQuote1).isEqualTo(lICQuote2);

        lICQuote2 = getLICQuoteSample2();
        assertThat(lICQuote1).isNotEqualTo(lICQuote2);
    }

    @Test
    void requestTest() {
        LICQuote lICQuote = getLICQuoteRandomSampleGenerator();
        LICQuoteRequest lICQuoteRequestBack = getLICQuoteRequestRandomSampleGenerator();

        lICQuote.setRequest(lICQuoteRequestBack);
        assertThat(lICQuote.getRequest()).isEqualTo(lICQuoteRequestBack);

        lICQuote.request(null);
        assertThat(lICQuote.getRequest()).isNull();
    }
}
