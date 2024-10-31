package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.QuotesTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class QuotesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotes.class);
        Quotes quotes1 = getQuotesSample1();
        Quotes quotes2 = new Quotes();
        assertThat(quotes1).isNotEqualTo(quotes2);

        quotes2.setId(quotes1.getId());
        assertThat(quotes1).isEqualTo(quotes2);

        quotes2 = getQuotesSample2();
        assertThat(quotes1).isNotEqualTo(quotes2);
    }
}
