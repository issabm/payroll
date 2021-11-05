package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagementResourceProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagementResourceProfile.class);
        ManagementResourceProfile managementResourceProfile1 = new ManagementResourceProfile();
        managementResourceProfile1.setId(1L);
        ManagementResourceProfile managementResourceProfile2 = new ManagementResourceProfile();
        managementResourceProfile2.setId(managementResourceProfile1.getId());
        assertThat(managementResourceProfile1).isEqualTo(managementResourceProfile2);
        managementResourceProfile2.setId(2L);
        assertThat(managementResourceProfile1).isNotEqualTo(managementResourceProfile2);
        managementResourceProfile1.setId(null);
        assertThat(managementResourceProfile1).isNotEqualTo(managementResourceProfile2);
    }
}
