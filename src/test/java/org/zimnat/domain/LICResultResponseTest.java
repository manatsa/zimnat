package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICConfirmationResponseTestSamples.*;
import static org.zimnat.domain.LICResultResponseTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICResultResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICResultResponse.class);
        LICResultResponse lICResultResponse1 = getLICResultResponseSample1();
        LICResultResponse lICResultResponse2 = new LICResultResponse();
        assertThat(lICResultResponse1).isNotEqualTo(lICResultResponse2);

        lICResultResponse2.setId(lICResultResponse1.getId());
        assertThat(lICResultResponse1).isEqualTo(lICResultResponse2);

        lICResultResponse2 = getLICResultResponseSample2();
        assertThat(lICResultResponse1).isNotEqualTo(lICResultResponse2);
    }

    @Test
    void responseTest() {
        LICResultResponse lICResultResponse = getLICResultResponseRandomSampleGenerator();
        LICConfirmationResponse lICConfirmationResponseBack = getLICConfirmationResponseRandomSampleGenerator();

        lICResultResponse.setResponse(lICConfirmationResponseBack);
        assertThat(lICResultResponse.getResponse()).isEqualTo(lICConfirmationResponseBack);

        lICResultResponse.response(null);
        assertThat(lICResultResponse.getResponse()).isNull();
    }
}
