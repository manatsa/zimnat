package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.FailureResponseTestSamples.*;
import static org.zimnat.domain.ResponsesTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class ResponsesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responses.class);
        Responses responses1 = getResponsesSample1();
        Responses responses2 = new Responses();
        assertThat(responses1).isNotEqualTo(responses2);

        responses2.setId(responses1.getId());
        assertThat(responses1).isEqualTo(responses2);

        responses2 = getResponsesSample2();
        assertThat(responses1).isNotEqualTo(responses2);
    }

    @Test
    void failureResponseTest() {
        Responses responses = getResponsesRandomSampleGenerator();
        FailureResponse failureResponseBack = getFailureResponseRandomSampleGenerator();

        responses.setFailureResponse(failureResponseBack);
        assertThat(responses.getFailureResponse()).isEqualTo(failureResponseBack);
        assertThat(failureResponseBack.getResponse()).isEqualTo(responses);

        responses.failureResponse(null);
        assertThat(responses.getFailureResponse()).isNull();
        assertThat(failureResponseBack.getResponse()).isNull();
    }
}
