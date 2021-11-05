package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssieteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assiete.class);
        Assiete assiete1 = new Assiete();
        assiete1.setId(1L);
        Assiete assiete2 = new Assiete();
        assiete2.setId(assiete1.getId());
        assertThat(assiete1).isEqualTo(assiete2);
        assiete2.setId(2L);
        assertThat(assiete1).isNotEqualTo(assiete2);
        assiete1.setId(null);
        assertThat(assiete1).isNotEqualTo(assiete2);
    }
}
