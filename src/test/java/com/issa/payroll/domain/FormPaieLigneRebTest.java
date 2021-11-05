package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormPaieLigneRebTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormPaieLigneReb.class);
        FormPaieLigneReb formPaieLigneReb1 = new FormPaieLigneReb();
        formPaieLigneReb1.setId(1L);
        FormPaieLigneReb formPaieLigneReb2 = new FormPaieLigneReb();
        formPaieLigneReb2.setId(formPaieLigneReb1.getId());
        assertThat(formPaieLigneReb1).isEqualTo(formPaieLigneReb2);
        formPaieLigneReb2.setId(2L);
        assertThat(formPaieLigneReb1).isNotEqualTo(formPaieLigneReb2);
        formPaieLigneReb1.setId(null);
        assertThat(formPaieLigneReb1).isNotEqualTo(formPaieLigneReb2);
    }
}
