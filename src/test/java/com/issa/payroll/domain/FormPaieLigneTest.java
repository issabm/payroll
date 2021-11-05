package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormPaieLigneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormPaieLigne.class);
        FormPaieLigne formPaieLigne1 = new FormPaieLigne();
        formPaieLigne1.setId(1L);
        FormPaieLigne formPaieLigne2 = new FormPaieLigne();
        formPaieLigne2.setId(formPaieLigne1.getId());
        assertThat(formPaieLigne1).isEqualTo(formPaieLigne2);
        formPaieLigne2.setId(2L);
        assertThat(formPaieLigne1).isNotEqualTo(formPaieLigne2);
        formPaieLigne1.setId(null);
        assertThat(formPaieLigne1).isNotEqualTo(formPaieLigne2);
    }
}
