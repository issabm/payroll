package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.OperatorForm;
import com.issa.payroll.repository.OperatorFormRepository;
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
 * Integration tests for the {@link OperatorFormResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OperatorFormResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/operator-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperatorFormRepository operatorFormRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperatorFormMockMvc;

    private OperatorForm operatorForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperatorForm createEntity(EntityManager em) {
        OperatorForm operatorForm = new OperatorForm()
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return operatorForm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperatorForm createUpdatedEntity(EntityManager em) {
        OperatorForm operatorForm = new OperatorForm()
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return operatorForm;
    }

    @BeforeEach
    public void initTest() {
        operatorForm = createEntity(em);
    }

    @Test
    @Transactional
    void createOperatorForm() throws Exception {
        int databaseSizeBeforeCreate = operatorFormRepository.findAll().size();
        // Create the OperatorForm
        restOperatorFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operatorForm)))
            .andExpect(status().isCreated());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeCreate + 1);
        OperatorForm testOperatorForm = operatorFormList.get(operatorFormList.size() - 1);
        assertThat(testOperatorForm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperatorForm.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testOperatorForm.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testOperatorForm.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testOperatorForm.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testOperatorForm.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOperatorForm.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testOperatorForm.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testOperatorForm.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testOperatorForm.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOperatorForm.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createOperatorFormWithExistingId() throws Exception {
        // Create the OperatorForm with an existing ID
        operatorForm.setId(1L);

        int databaseSizeBeforeCreate = operatorFormRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operatorForm)))
            .andExpect(status().isBadRequest());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperatorForms() throws Exception {
        // Initialize the database
        operatorFormRepository.saveAndFlush(operatorForm);

        // Get all the operatorFormList
        restOperatorFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operatorForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
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
    void getOperatorForm() throws Exception {
        // Initialize the database
        operatorFormRepository.saveAndFlush(operatorForm);

        // Get the operatorForm
        restOperatorFormMockMvc
            .perform(get(ENTITY_API_URL_ID, operatorForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operatorForm.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
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
    void getNonExistingOperatorForm() throws Exception {
        // Get the operatorForm
        restOperatorFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOperatorForm() throws Exception {
        // Initialize the database
        operatorFormRepository.saveAndFlush(operatorForm);

        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();

        // Update the operatorForm
        OperatorForm updatedOperatorForm = operatorFormRepository.findById(operatorForm.getId()).get();
        // Disconnect from session so that the updates on updatedOperatorForm are not directly saved in db
        em.detach(updatedOperatorForm);
        updatedOperatorForm
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restOperatorFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOperatorForm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOperatorForm))
            )
            .andExpect(status().isOk());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
        OperatorForm testOperatorForm = operatorFormList.get(operatorFormList.size() - 1);
        assertThat(testOperatorForm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperatorForm.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testOperatorForm.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testOperatorForm.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testOperatorForm.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testOperatorForm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOperatorForm.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testOperatorForm.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testOperatorForm.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOperatorForm.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOperatorForm.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOperatorForm() throws Exception {
        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();
        operatorForm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorForm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operatorForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperatorForm() throws Exception {
        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();
        operatorForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operatorForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperatorForm() throws Exception {
        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();
        operatorForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorFormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operatorForm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperatorFormWithPatch() throws Exception {
        // Initialize the database
        operatorFormRepository.saveAndFlush(operatorForm);

        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();

        // Update the operatorForm using partial update
        OperatorForm partialUpdatedOperatorForm = new OperatorForm();
        partialUpdatedOperatorForm.setId(operatorForm.getId());

        partialUpdatedOperatorForm
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restOperatorFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperatorForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperatorForm))
            )
            .andExpect(status().isOk());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
        OperatorForm testOperatorForm = operatorFormList.get(operatorFormList.size() - 1);
        assertThat(testOperatorForm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperatorForm.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testOperatorForm.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testOperatorForm.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testOperatorForm.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testOperatorForm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOperatorForm.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testOperatorForm.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testOperatorForm.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOperatorForm.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOperatorForm.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOperatorFormWithPatch() throws Exception {
        // Initialize the database
        operatorFormRepository.saveAndFlush(operatorForm);

        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();

        // Update the operatorForm using partial update
        OperatorForm partialUpdatedOperatorForm = new OperatorForm();
        partialUpdatedOperatorForm.setId(operatorForm.getId());

        partialUpdatedOperatorForm
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restOperatorFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperatorForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperatorForm))
            )
            .andExpect(status().isOk());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
        OperatorForm testOperatorForm = operatorFormList.get(operatorFormList.size() - 1);
        assertThat(testOperatorForm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperatorForm.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testOperatorForm.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testOperatorForm.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testOperatorForm.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testOperatorForm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOperatorForm.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testOperatorForm.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testOperatorForm.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOperatorForm.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOperatorForm.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOperatorForm() throws Exception {
        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();
        operatorForm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operatorForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operatorForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperatorForm() throws Exception {
        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();
        operatorForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operatorForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperatorForm() throws Exception {
        int databaseSizeBeforeUpdate = operatorFormRepository.findAll().size();
        operatorForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorFormMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(operatorForm))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperatorForm in the database
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperatorForm() throws Exception {
        // Initialize the database
        operatorFormRepository.saveAndFlush(operatorForm);

        int databaseSizeBeforeDelete = operatorFormRepository.findAll().size();

        // Delete the operatorForm
        restOperatorFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, operatorForm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperatorForm> operatorFormList = operatorFormRepository.findAll();
        assertThat(operatorFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
