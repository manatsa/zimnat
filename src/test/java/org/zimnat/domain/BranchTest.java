package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.BranchTestSamples.*;
import static org.zimnat.domain.SBUTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class BranchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Branch.class);
        Branch branch1 = getBranchSample1();
        Branch branch2 = new Branch();
        assertThat(branch1).isNotEqualTo(branch2);

        branch2.setId(branch1.getId());
        assertThat(branch1).isEqualTo(branch2);

        branch2 = getBranchSample2();
        assertThat(branch1).isNotEqualTo(branch2);
    }

    @Test
    void familyCasketTest() {
        Branch branch = getBranchRandomSampleGenerator();
        SBU sBUBack = getSBURandomSampleGenerator();

        branch.setFamilyCasket(sBUBack);
        assertThat(branch.getFamilyCasket()).isEqualTo(sBUBack);

        branch.familyCasket(null);
        assertThat(branch.getFamilyCasket()).isNull();
    }
}
