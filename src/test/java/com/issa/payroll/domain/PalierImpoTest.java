package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PalierImpoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PalierImpo.class);
        PalierImpo palierImpo1 = new PalierImpo();
        palierImpo1.setId(1L);
        PalierImpo palierImpo2 = new PalierImpo();
        palierImpo2.setId(palierImpo1.getId());
        assertThat(palierImpo1).isEqualTo(palierImpo2);
        palierImpo2.setId(2L);
        assertThat(palierImpo1).isNotEqualTo(palierImpo2);
        palierImpo1.setId(null);
        assertThat(palierImpo1).isNotEqualTo(palierImpo2);
    }
}
