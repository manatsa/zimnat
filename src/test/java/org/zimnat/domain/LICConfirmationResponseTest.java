package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICConfirmationResponseTestSamples.*;
import static org.zimnat.domain.LICResultResponseTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICConfirmationResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICConfirmationResponse.class);
        LICConfirmationResponse lICConfirmationResponse1 = getLICConfirmationResponseSample1();
        LICConfirmationResponse lICConfirmationResponse2 = new LICConfirmationResponse();
        assertThat(lICConfirmationResponse1).isNotEqualTo(lICConfirmationResponse2);

        lICConfirmationResponse2.setId(lICConfirmationResponse1.getId());
        assertThat(lICConfirmationResponse1).isEqualTo(lICConfirmationResponse2);

        lICConfirmationResponse2 = getLICConfirmationResponseSample2();
        assertThat(lICConfirmationResponse1).isNotEqualTo(lICConfirmationResponse2);
    }

    @Test
    void lICResultResponseTest() {
        LICConfirmationResponse lICConfirmationResponse = getLICConfirmationResponseRandomSampleGenerator();
        LICResultResponse lICResultResponseBack = getLICResultResponseRandomSampleGenerator();

        lICConfirmationResponse.setLICResultResponse(lICResultResponseBack);
        assertThat(lICConfirmationResponse.getLICResultResponse()).isEqualTo(lICResultResponseBack);
        assertThat(lICResultResponseBack.getResponse()).isEqualTo(lICConfirmationResponse);

        lICConfirmationResponse.lICResultResponse(null);
        assertThat(lICConfirmationResponse.getLICResultResponse()).isNull();
        assertThat(lICResultResponseBack.getResponse()).isNull();
    }
}
