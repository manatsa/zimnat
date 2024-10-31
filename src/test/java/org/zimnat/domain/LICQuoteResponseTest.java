package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICQuoteResponseTestSamples.*;
import static org.zimnat.domain.QuoteResponseTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICQuoteResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICQuoteResponse.class);
        LICQuoteResponse lICQuoteResponse1 = getLICQuoteResponseSample1();
        LICQuoteResponse lICQuoteResponse2 = new LICQuoteResponse();
        assertThat(lICQuoteResponse1).isNotEqualTo(lICQuoteResponse2);

        lICQuoteResponse2.setId(lICQuoteResponse1.getId());
        assertThat(lICQuoteResponse1).isEqualTo(lICQuoteResponse2);

        lICQuoteResponse2 = getLICQuoteResponseSample2();
        assertThat(lICQuoteResponse1).isNotEqualTo(lICQuoteResponse2);
    }

    @Test
    void responseTest() {
        LICQuoteResponse lICQuoteResponse = getLICQuoteResponseRandomSampleGenerator();
        QuoteResponse quoteResponseBack = getQuoteResponseRandomSampleGenerator();

        lICQuoteResponse.setResponse(quoteResponseBack);
        assertThat(lICQuoteResponse.getResponse()).isEqualTo(quoteResponseBack);

        lICQuoteResponse.response(null);
        assertThat(lICQuoteResponse.getResponse()).isNull();
    }
}
