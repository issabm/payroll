package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RebriqueContratTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RebriqueContrat.class);
        RebriqueContrat rebriqueContrat1 = new RebriqueContrat();
        rebriqueContrat1.setId(1L);
        RebriqueContrat rebriqueContrat2 = new RebriqueContrat();
        rebriqueContrat2.setId(rebriqueContrat1.getId());
        assertThat(rebriqueContrat1).isEqualTo(rebriqueContrat2);
        rebriqueContrat2.setId(2L);
        assertThat(rebriqueContrat1).isNotEqualTo(rebriqueContrat2);
        rebriqueContrat1.setId(null);
        assertThat(rebriqueContrat1).isNotEqualTo(rebriqueContrat2);
    }
}
