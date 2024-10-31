package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.FailureResponseTestSamples.*;
import static org.zimnat.domain.ResponsesTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class FailureResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FailureResponse.class);
        FailureResponse failureResponse1 = getFailureResponseSample1();
        FailureResponse failureResponse2 = new FailureResponse();
        assertThat(failureResponse1).isNotEqualTo(failureResponse2);

        failureResponse2.setId(failureResponse1.getId());
        assertThat(failureResponse1).isEqualTo(failureResponse2);

        failureResponse2 = getFailureResponseSample2();
        assertThat(failureResponse1).isNotEqualTo(failureResponse2);
    }

    @Test
    void responseTest() {
        FailureResponse failureResponse = getFailureResponseRandomSampleGenerator();
        Responses responsesBack = getResponsesRandomSampleGenerator();

        failureResponse.setResponse(responsesBack);
        assertThat(failureResponse.getResponse()).isEqualTo(responsesBack);

        failureResponse.response(null);
        assertThat(failureResponse.getResponse()).isNull();
    }
}
