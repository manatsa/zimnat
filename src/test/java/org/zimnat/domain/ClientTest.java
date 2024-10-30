package org.zimnat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zimnat.domain.AddressTestSamples.*;
import static org.zimnat.domain.ClientTestSamples.*;

import org.junit.jupiter.api.Test;
import org.zimnat.web.rest.TestUtil;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void addressTest() {
        Client client = getClientRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        client.setAddress(addressBack);
        assertThat(client.getAddress()).isEqualTo(addressBack);

        client.address(null);
        assertThat(client.getAddress()).isNull();
    }
}
