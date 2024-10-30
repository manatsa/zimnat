package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.BranchTestSamples.*;
import static org.zimnat.domain.ClientTestSamples.*;
import static org.zimnat.domain.ProductTestSamples.*;
import static org.zimnat.domain.TransDetailsTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class TransDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransDetails.class);
        TransDetails transDetails1 = getTransDetailsSample1();
        TransDetails transDetails2 = new TransDetails();
        assertThat(transDetails1).isNotEqualTo(transDetails2);

        transDetails2.setId(transDetails1.getId());
        assertThat(transDetails1).isEqualTo(transDetails2);

        transDetails2 = getTransDetailsSample2();
        assertThat(transDetails1).isNotEqualTo(transDetails2);
    }

    @Test
    void productTest() {
        TransDetails transDetails = getTransDetailsRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        transDetails.setProduct(productBack);
        assertThat(transDetails.getProduct()).isEqualTo(productBack);

        transDetails.product(null);
        assertThat(transDetails.getProduct()).isNull();
    }

    @Test
    void clientTest() {
        TransDetails transDetails = getTransDetailsRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        transDetails.setClient(clientBack);
        assertThat(transDetails.getClient()).isEqualTo(clientBack);

        transDetails.client(null);
        assertThat(transDetails.getClient()).isNull();
    }

    @Test
    void branchTest() {
        TransDetails transDetails = getTransDetailsRandomSampleGenerator();
        Branch branchBack = getBranchRandomSampleGenerator();

        transDetails.setBranch(branchBack);
        assertThat(transDetails.getBranch()).isEqualTo(branchBack);

        transDetails.branch(null);
        assertThat(transDetails.getBranch()).isNull();
    }
}
