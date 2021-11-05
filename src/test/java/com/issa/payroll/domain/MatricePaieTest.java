package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatricePaieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatricePaie.class);
        MatricePaie matricePaie1 = new MatricePaie();
        matricePaie1.setId(1L);
        MatricePaie matricePaie2 = new MatricePaie();
        matricePaie2.setId(matricePaie1.getId());
        assertThat(matricePaie1).isEqualTo(matricePaie2);
        matricePaie2.setId(2L);
        assertThat(matricePaie1).isNotEqualTo(matricePaie2);
        matricePaie1.setId(null);
        assertThat(matricePaie1).isNotEqualTo(matricePaie2);
    }
}
