package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureAbsenceRebriqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureAbsenceRebrique.class);
        NatureAbsenceRebrique natureAbsenceRebrique1 = new NatureAbsenceRebrique();
        natureAbsenceRebrique1.setId(1L);
        NatureAbsenceRebrique natureAbsenceRebrique2 = new NatureAbsenceRebrique();
        natureAbsenceRebrique2.setId(natureAbsenceRebrique1.getId());
        assertThat(natureAbsenceRebrique1).isEqualTo(natureAbsenceRebrique2);
        natureAbsenceRebrique2.setId(2L);
        assertThat(natureAbsenceRebrique1).isNotEqualTo(natureAbsenceRebrique2);
        natureAbsenceRebrique1.setId(null);
        assertThat(natureAbsenceRebrique1).isNotEqualTo(natureAbsenceRebrique2);
    }
}
