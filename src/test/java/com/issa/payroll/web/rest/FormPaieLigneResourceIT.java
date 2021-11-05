package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.FormPaieLigne;
import com.issa.payroll.repository.FormPaieLigneRepository;
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
 * Integration tests for the {@link FormPaieLigneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormPaieLigneResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

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

    private static final String ENTITY_API_URL = "/api/form-paie-lignes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormPaieLigneRepository formPaieLigneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormPaieLigneMockMvc;

    private FormPaieLigne formPaieLigne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormPaieLigne createEntity(EntityManager em) {
        FormPaieLigne formPaieLigne = new FormPaieLigne()
            .priorite(DEFAULT_PRIORITE)
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
        return formPaieLigne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormPaieLigne createUpdatedEntity(EntityManager em) {
        FormPaieLigne formPaieLigne = new FormPaieLigne()
            .priorite(UPDATED_PRIORITE)
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
        return formPaieLigne;
    }

    @BeforeEach
    public void initTest() {
        formPaieLigne = createEntity(em);
    }

    @Test
    @Transactional
    void createFormPaieLigne() throws Exception {
        int databaseSizeBeforeCreate = formPaieLigneRepository.findAll().size();
        // Create the FormPaieLigne
        restFormPaieLigneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaieLigne)))
            .andExpect(status().isCreated());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeCreate + 1);
        FormPaieLigne testFormPaieLigne = formPaieLigneList.get(formPaieLigneList.size() - 1);
        assertThat(testFormPaieLigne.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testFormPaieLigne.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFormPaieLigne.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFormPaieLigne.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFormPaieLigne.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFormPaieLigne.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFormPaieLigne.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFormPaieLigne.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFormPaieLigne.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFormPaieLigne.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testFormPaieLigne.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFormPaieLigne.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFormPaieLigneWithExistingId() throws Exception {
        // Create the FormPaieLigne with an existing ID
        formPaieLigne.setId(1L);

        int databaseSizeBeforeCreate = formPaieLigneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormPaieLigneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaieLigne)))
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormPaieLignes() throws Exception {
        // Initialize the database
        formPaieLigneRepository.saveAndFlush(formPaieLigne);

        // Get all the formPaieLigneList
        restFormPaieLigneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formPaieLigne.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
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
    void getFormPaieLigne() throws Exception {
        // Initialize the database
        formPaieLigneRepository.saveAndFlush(formPaieLigne);

        // Get the formPaieLigne
        restFormPaieLigneMockMvc
            .perform(get(ENTITY_API_URL_ID, formPaieLigne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formPaieLigne.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
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
    void getNonExistingFormPaieLigne() throws Exception {
        // Get the formPaieLigne
        restFormPaieLigneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormPaieLigne() throws Exception {
        // Initialize the database
        formPaieLigneRepository.saveAndFlush(formPaieLigne);

        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();

        // Update the formPaieLigne
        FormPaieLigne updatedFormPaieLigne = formPaieLigneRepository.findById(formPaieLigne.getId()).get();
        // Disconnect from session so that the updates on updatedFormPaieLigne are not directly saved in db
        em.detach(updatedFormPaieLigne);
        updatedFormPaieLigne
            .priorite(UPDATED_PRIORITE)
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

        restFormPaieLigneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormPaieLigne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormPaieLigne))
            )
            .andExpect(status().isOk());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
        FormPaieLigne testFormPaieLigne = formPaieLigneList.get(formPaieLigneList.size() - 1);
        assertThat(testFormPaieLigne.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testFormPaieLigne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaieLigne.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFormPaieLigne.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaieLigne.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaieLigne.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaieLigne.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaieLigne.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaieLigne.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaieLigne.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaieLigne.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaieLigne.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFormPaieLigne() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();
        formPaieLigne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormPaieLigneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formPaieLigne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigne))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormPaieLigne() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();
        formPaieLigne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigne))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormPaieLigne() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();
        formPaieLigne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaieLigne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormPaieLigneWithPatch() throws Exception {
        // Initialize the database
        formPaieLigneRepository.saveAndFlush(formPaieLigne);

        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();

        // Update the formPaieLigne using partial update
        FormPaieLigne partialUpdatedFormPaieLigne = new FormPaieLigne();
        partialUpdatedFormPaieLigne.setId(formPaieLigne.getId());

        partialUpdatedFormPaieLigne
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFormPaieLigneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormPaieLigne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormPaieLigne))
            )
            .andExpect(status().isOk());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
        FormPaieLigne testFormPaieLigne = formPaieLigneList.get(formPaieLigneList.size() - 1);
        assertThat(testFormPaieLigne.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testFormPaieLigne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaieLigne.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFormPaieLigne.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFormPaieLigne.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFormPaieLigne.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaieLigne.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaieLigne.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFormPaieLigne.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFormPaieLigne.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaieLigne.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaieLigne.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFormPaieLigneWithPatch() throws Exception {
        // Initialize the database
        formPaieLigneRepository.saveAndFlush(formPaieLigne);

        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();

        // Update the formPaieLigne using partial update
        FormPaieLigne partialUpdatedFormPaieLigne = new FormPaieLigne();
        partialUpdatedFormPaieLigne.setId(formPaieLigne.getId());

        partialUpdatedFormPaieLigne
            .priorite(UPDATED_PRIORITE)
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

        restFormPaieLigneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormPaieLigne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormPaieLigne))
            )
            .andExpect(status().isOk());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
        FormPaieLigne testFormPaieLigne = formPaieLigneList.get(formPaieLigneList.size() - 1);
        assertThat(testFormPaieLigne.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testFormPaieLigne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaieLigne.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFormPaieLigne.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaieLigne.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaieLigne.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaieLigne.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaieLigne.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaieLigne.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaieLigne.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaieLigne.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaieLigne.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFormPaieLigne() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();
        formPaieLigne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormPaieLigneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formPaieLigne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigne))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormPaieLigne() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();
        formPaieLigne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigne))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormPaieLigne() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRepository.findAll().size();
        formPaieLigne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formPaieLigne))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormPaieLigne in the database
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormPaieLigne() throws Exception {
        // Initialize the database
        formPaieLigneRepository.saveAndFlush(formPaieLigne);

        int databaseSizeBeforeDelete = formPaieLigneRepository.findAll().size();

        // Delete the formPaieLigne
        restFormPaieLigneMockMvc
            .perform(delete(ENTITY_API_URL_ID, formPaieLigne.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormPaieLigne> formPaieLigneList = formPaieLigneRepository.findAll();
        assertThat(formPaieLigneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
