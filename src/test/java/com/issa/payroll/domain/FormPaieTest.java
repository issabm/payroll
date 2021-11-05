package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormPaieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormPaie.class);
        FormPaie formPaie1 = new FormPaie();
        formPaie1.setId(1L);
        FormPaie formPaie2 = new FormPaie();
        formPaie2.setId(formPaie1.getId());
        assertThat(formPaie1).isEqualTo(formPaie2);
        formPaie2.setId(2L);
        assertThat(formPaie1).isNotEqualTo(formPaie2);
        formPaie1.setId(null);
        assertThat(formPaie1).isNotEqualTo(formPaie2);
    }
}
