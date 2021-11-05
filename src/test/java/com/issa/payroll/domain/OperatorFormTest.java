package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperatorFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperatorForm.class);
        OperatorForm operatorForm1 = new OperatorForm();
        operatorForm1.setId(1L);
        OperatorForm operatorForm2 = new OperatorForm();
        operatorForm2.setId(operatorForm1.getId());
        assertThat(operatorForm1).isEqualTo(operatorForm2);
        operatorForm2.setId(2L);
        assertThat(operatorForm1).isNotEqualTo(operatorForm2);
        operatorForm1.setId(null);
        assertThat(operatorForm1).isNotEqualTo(operatorForm2);
    }
}
