package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICQuoteResponseTestSamples.*;
import static org.zimnat.domain.QuoteResponseTestSamples.*;
import static org.zimnat.domain.QuotesTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class QuoteResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuoteResponse.class);
        QuoteResponse quoteResponse1 = getQuoteResponseSample1();
        QuoteResponse quoteResponse2 = new QuoteResponse();
        assertThat(quoteResponse1).isNotEqualTo(quoteResponse2);

        quoteResponse2.setId(quoteResponse1.getId());
        assertThat(quoteResponse1).isEqualTo(quoteResponse2);

        quoteResponse2 = getQuoteResponseSample2();
        assertThat(quoteResponse1).isNotEqualTo(quoteResponse2);
    }

    @Test
    void quotesTest() {
        QuoteResponse quoteResponse = getQuoteResponseRandomSampleGenerator();
        Quotes quotesBack = getQuotesRandomSampleGenerator();

        quoteResponse.setQuotes(quotesBack);
        assertThat(quoteResponse.getQuotes()).isEqualTo(quotesBack);

        quoteResponse.quotes(null);
        assertThat(quoteResponse.getQuotes()).isNull();
    }

    @Test
    void lICQuoteResponseTest() {
        QuoteResponse quoteResponse = getQuoteResponseRandomSampleGenerator();
        LICQuoteResponse lICQuoteResponseBack = getLICQuoteResponseRandomSampleGenerator();

        quoteResponse.setLICQuoteResponse(lICQuoteResponseBack);
        assertThat(quoteResponse.getLICQuoteResponse()).isEqualTo(lICQuoteResponseBack);
        assertThat(lICQuoteResponseBack.getResponse()).isEqualTo(quoteResponse);

        quoteResponse.lICQuoteResponse(null);
        assertThat(quoteResponse.getLICQuoteResponse()).isNull();
        assertThat(lICQuoteResponseBack.getResponse()).isNull();
    }
}
