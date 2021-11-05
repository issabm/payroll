package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.ManagementResourceProfile;
import com.issa.payroll.repository.ManagementResourceProfileRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ManagementResourceProfileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ManagementResourceProfileResourceIT {

    private static final String DEFAULT_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/management-resource-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManagementResourceProfileRepository managementResourceProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagementResourceProfileMockMvc;

    private ManagementResourceProfile managementResourceProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementResourceProfile createEntity(EntityManager em) {
        ManagementResourceProfile managementResourceProfile = new ManagementResourceProfile()
            .profile(DEFAULT_PROFILE)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return managementResourceProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementResourceProfile createUpdatedEntity(EntityManager em) {
        ManagementResourceProfile managementResourceProfile = new ManagementResourceProfile()
            .profile(UPDATED_PROFILE)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return managementResourceProfile;
    }

    @BeforeEach
    public void initTest() {
        managementResourceProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createManagementResourceProfile() throws Exception {
        int databaseSizeBeforeCreate = managementResourceProfileRepository.findAll().size();
        // Create the ManagementResourceProfile
        restManagementResourceProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isCreated());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ManagementResourceProfile testManagementResourceProfile = managementResourceProfileList.get(
            managementResourceProfileList.size() - 1
        );
        assertThat(testManagementResourceProfile.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testManagementResourceProfile.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testManagementResourceProfile.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testManagementResourceProfile.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testManagementResourceProfile.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testManagementResourceProfile.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testManagementResourceProfile.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testManagementResourceProfile.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testManagementResourceProfile.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createManagementResourceProfileWithExistingId() throws Exception {
        // Create the ManagementResourceProfile with an existing ID
        managementResourceProfile.setId(1L);

        int databaseSizeBeforeCreate = managementResourceProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagementResourceProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManagementResourceProfiles() throws Exception {
        // Initialize the database
        managementResourceProfileRepository.saveAndFlush(managementResourceProfile);

        // Get all the managementResourceProfileList
        restManagementResourceProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(managementResourceProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getManagementResourceProfile() throws Exception {
        // Initialize the database
        managementResourceProfileRepository.saveAndFlush(managementResourceProfile);

        // Get the managementResourceProfile
        restManagementResourceProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, managementResourceProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(managementResourceProfile.getId().intValue()))
            .andExpect(jsonPath("$.profile").value(DEFAULT_PROFILE))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingManagementResourceProfile() throws Exception {
        // Get the managementResourceProfile
        restManagementResourceProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewManagementResourceProfile() throws Exception {
        // Initialize the database
        managementResourceProfileRepository.saveAndFlush(managementResourceProfile);

        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();

        // Update the managementResourceProfile
        ManagementResourceProfile updatedManagementResourceProfile = managementResourceProfileRepository
            .findById(managementResourceProfile.getId())
            .get();
        // Disconnect from session so that the updates on updatedManagementResourceProfile are not directly saved in db
        em.detach(updatedManagementResourceProfile);
        updatedManagementResourceProfile
            .profile(UPDATED_PROFILE)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedManagementResourceProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedManagementResourceProfile))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
        ManagementResourceProfile testManagementResourceProfile = managementResourceProfileList.get(
            managementResourceProfileList.size() - 1
        );
        assertThat(testManagementResourceProfile.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testManagementResourceProfile.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResourceProfile.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResourceProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResourceProfile.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResourceProfile.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResourceProfile.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResourceProfile.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResourceProfile.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingManagementResourceProfile() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();
        managementResourceProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementResourceProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managementResourceProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManagementResourceProfile() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();
        managementResourceProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManagementResourceProfile() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();
        managementResourceProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceProfileMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManagementResourceProfileWithPatch() throws Exception {
        // Initialize the database
        managementResourceProfileRepository.saveAndFlush(managementResourceProfile);

        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();

        // Update the managementResourceProfile using partial update
        ManagementResourceProfile partialUpdatedManagementResourceProfile = new ManagementResourceProfile();
        partialUpdatedManagementResourceProfile.setId(managementResourceProfile.getId());

        partialUpdatedManagementResourceProfile
            .profile(UPDATED_PROFILE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED);

        restManagementResourceProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementResourceProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementResourceProfile))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
        ManagementResourceProfile testManagementResourceProfile = managementResourceProfileList.get(
            managementResourceProfileList.size() - 1
        );
        assertThat(testManagementResourceProfile.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testManagementResourceProfile.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testManagementResourceProfile.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResourceProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResourceProfile.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testManagementResourceProfile.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResourceProfile.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResourceProfile.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testManagementResourceProfile.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateManagementResourceProfileWithPatch() throws Exception {
        // Initialize the database
        managementResourceProfileRepository.saveAndFlush(managementResourceProfile);

        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();

        // Update the managementResourceProfile using partial update
        ManagementResourceProfile partialUpdatedManagementResourceProfile = new ManagementResourceProfile();
        partialUpdatedManagementResourceProfile.setId(managementResourceProfile.getId());

        partialUpdatedManagementResourceProfile
            .profile(UPDATED_PROFILE)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementResourceProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementResourceProfile))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
        ManagementResourceProfile testManagementResourceProfile = managementResourceProfileList.get(
            managementResourceProfileList.size() - 1
        );
        assertThat(testManagementResourceProfile.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testManagementResourceProfile.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResourceProfile.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResourceProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResourceProfile.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResourceProfile.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResourceProfile.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResourceProfile.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResourceProfile.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingManagementResourceProfile() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();
        managementResourceProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementResourceProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, managementResourceProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManagementResourceProfile() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();
        managementResourceProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManagementResourceProfile() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceProfileRepository.findAll().size();
        managementResourceProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementResourceProfile in the database
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManagementResourceProfile() throws Exception {
        // Initialize the database
        managementResourceProfileRepository.saveAndFlush(managementResourceProfile);

        int databaseSizeBeforeDelete = managementResourceProfileRepository.findAll().size();

        // Delete the managementResourceProfile
        restManagementResourceProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, managementResourceProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ManagementResourceProfile> managementResourceProfileList = managementResourceProfileRepository.findAll();
        assertThat(managementResourceProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
