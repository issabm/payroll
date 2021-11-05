package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatricePaieEmpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatricePaieEmp.class);
        MatricePaieEmp matricePaieEmp1 = new MatricePaieEmp();
        matricePaieEmp1.setId(1L);
        MatricePaieEmp matricePaieEmp2 = new MatricePaieEmp();
        matricePaieEmp2.setId(matricePaieEmp1.getId());
        assertThat(matricePaieEmp1).isEqualTo(matricePaieEmp2);
        matricePaieEmp2.setId(2L);
        assertThat(matricePaieEmp1).isNotEqualTo(matricePaieEmp2);
        matricePaieEmp1.setId(null);
        assertThat(matricePaieEmp1).isNotEqualTo(matricePaieEmp2);
    }
}
