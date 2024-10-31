package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.BatchStatusRequestTestSamples.*;
import static org.zimnat.domain.BatchStatusTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class BatchStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchStatus.class);
        BatchStatus batchStatus1 = getBatchStatusSample1();
        BatchStatus batchStatus2 = new BatchStatus();
        assertThat(batchStatus1).isNotEqualTo(batchStatus2);

        batchStatus2.setId(batchStatus1.getId());
        assertThat(batchStatus1).isEqualTo(batchStatus2);

        batchStatus2 = getBatchStatusSample2();
        assertThat(batchStatus1).isNotEqualTo(batchStatus2);
    }

    @Test
    void requestTest() {
        BatchStatus batchStatus = getBatchStatusRandomSampleGenerator();
        BatchStatusRequest batchStatusRequestBack = getBatchStatusRequestRandomSampleGenerator();

        batchStatus.setRequest(batchStatusRequestBack);
        assertThat(batchStatus.getRequest()).isEqualTo(batchStatusRequestBack);

        batchStatus.request(null);
        assertThat(batchStatus.getRequest()).isNull();
    }
}
