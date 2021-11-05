package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoldeAbsenceContratTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoldeAbsenceContrat.class);
        SoldeAbsenceContrat soldeAbsenceContrat1 = new SoldeAbsenceContrat();
        soldeAbsenceContrat1.setId(1L);
        SoldeAbsenceContrat soldeAbsenceContrat2 = new SoldeAbsenceContrat();
        soldeAbsenceContrat2.setId(soldeAbsenceContrat1.getId());
        assertThat(soldeAbsenceContrat1).isEqualTo(soldeAbsenceContrat2);
        soldeAbsenceContrat2.setId(2L);
        assertThat(soldeAbsenceContrat1).isNotEqualTo(soldeAbsenceContrat2);
        soldeAbsenceContrat1.setId(null);
        assertThat(soldeAbsenceContrat1).isNotEqualTo(soldeAbsenceContrat2);
    }
}
