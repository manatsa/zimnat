package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.LICQuoteRequestTestSamples.*;
import static org.zimnat.domain.LICQuoteTestSamples.*;
import static org.zimnat.domain.VehicleTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class LICQuoteRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LICQuoteRequest.class);
        LICQuoteRequest lICQuoteRequest1 = getLICQuoteRequestSample1();
        LICQuoteRequest lICQuoteRequest2 = new LICQuoteRequest();
        assertThat(lICQuoteRequest1).isNotEqualTo(lICQuoteRequest2);

        lICQuoteRequest2.setId(lICQuoteRequest1.getId());
        assertThat(lICQuoteRequest1).isEqualTo(lICQuoteRequest2);

        lICQuoteRequest2 = getLICQuoteRequestSample2();
        assertThat(lICQuoteRequest1).isNotEqualTo(lICQuoteRequest2);
    }

    @Test
    void vehiclesTest() {
        LICQuoteRequest lICQuoteRequest = getLICQuoteRequestRandomSampleGenerator();
        Vehicle vehicleBack = getVehicleRandomSampleGenerator();

        lICQuoteRequest.setVehicles(vehicleBack);
        assertThat(lICQuoteRequest.getVehicles()).isEqualTo(vehicleBack);

        lICQuoteRequest.vehicles(null);
        assertThat(lICQuoteRequest.getVehicles()).isNull();
    }

    @Test
    void lICQuoteTest() {
        LICQuoteRequest lICQuoteRequest = getLICQuoteRequestRandomSampleGenerator();
        LICQuote lICQuoteBack = getLICQuoteRandomSampleGenerator();

        lICQuoteRequest.setLICQuote(lICQuoteBack);
        assertThat(lICQuoteRequest.getLICQuote()).isEqualTo(lICQuoteBack);
        assertThat(lICQuoteBack.getRequest()).isEqualTo(lICQuoteRequest);

        lICQuoteRequest.lICQuote(null);
        assertThat(lICQuoteRequest.getLICQuote()).isNull();
        assertThat(lICQuoteBack.getRequest()).isNull();
    }
}
