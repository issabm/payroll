package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssieteInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssieteInfo.class);
        AssieteInfo assieteInfo1 = new AssieteInfo();
        assieteInfo1.setId(1L);
        AssieteInfo assieteInfo2 = new AssieteInfo();
        assieteInfo2.setId(assieteInfo1.getId());
        assertThat(assieteInfo1).isEqualTo(assieteInfo2);
        assieteInfo2.setId(2L);
        assertThat(assieteInfo1).isNotEqualTo(assieteInfo2);
        assieteInfo1.setId(null);
        assertThat(assieteInfo1).isNotEqualTo(assieteInfo2);
    }
}
