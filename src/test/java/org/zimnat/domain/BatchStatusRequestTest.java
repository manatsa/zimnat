package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.BatchStatusRequestTestSamples.*;
import static org.zimnat.domain.BatchStatusTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class BatchStatusRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchStatusRequest.class);
        BatchStatusRequest batchStatusRequest1 = getBatchStatusRequestSample1();
        BatchStatusRequest batchStatusRequest2 = new BatchStatusRequest();
        assertThat(batchStatusRequest1).isNotEqualTo(batchStatusRequest2);

        batchStatusRequest2.setId(batchStatusRequest1.getId());
        assertThat(batchStatusRequest1).isEqualTo(batchStatusRequest2);

        batchStatusRequest2 = getBatchStatusRequestSample2();
        assertThat(batchStatusRequest1).isNotEqualTo(batchStatusRequest2);
    }

    @Test
    void batchStatusTest() {
        BatchStatusRequest batchStatusRequest = getBatchStatusRequestRandomSampleGenerator();
        BatchStatus batchStatusBack = getBatchStatusRandomSampleGenerator();

        batchStatusRequest.setBatchStatus(batchStatusBack);
        assertThat(batchStatusRequest.getBatchStatus()).isEqualTo(batchStatusBack);
        assertThat(batchStatusBack.getRequest()).isEqualTo(batchStatusRequest);

        batchStatusRequest.batchStatus(null);
        assertThat(batchStatusRequest.getBatchStatus()).isNull();
        assertThat(batchStatusBack.getRequest()).isNull();
    }
}
