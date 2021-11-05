package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.FormPaieLigneReb;
import com.issa.payroll.repository.FormPaieLigneRebRepository;
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
 * Integration tests for the {@link FormPaieLigneRebResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormPaieLigneRebResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final Double DEFAULT_VAL_ORIGIN = 1D;
    private static final Double UPDATED_VAL_ORIGIN = 2D;

    private static final Double DEFAULT_VAL_CALCUL = 1D;
    private static final Double UPDATED_VAL_CALCUL = 2D;

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

    private static final String ENTITY_API_URL = "/api/form-paie-ligne-rebs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormPaieLigneRebRepository formPaieLigneRebRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormPaieLigneRebMockMvc;

    private FormPaieLigneReb formPaieLigneReb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormPaieLigneReb createEntity(EntityManager em) {
        FormPaieLigneReb formPaieLigneReb = new FormPaieLigneReb()
            .priorite(DEFAULT_PRIORITE)
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .valOrigin(DEFAULT_VAL_ORIGIN)
            .valCalcul(DEFAULT_VAL_CALCUL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return formPaieLigneReb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormPaieLigneReb createUpdatedEntity(EntityManager em) {
        FormPaieLigneReb formPaieLigneReb = new FormPaieLigneReb()
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .valOrigin(UPDATED_VAL_ORIGIN)
            .valCalcul(UPDATED_VAL_CALCUL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return formPaieLigneReb;
    }

    @BeforeEach
    public void initTest() {
        formPaieLigneReb = createEntity(em);
    }

    @Test
    @Transactional
    void createFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeCreate = formPaieLigneRebRepository.findAll().size();
        // Create the FormPaieLigneReb
        restFormPaieLigneRebMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isCreated());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeCreate + 1);
        FormPaieLigneReb testFormPaieLigneReb = formPaieLigneRebList.get(formPaieLigneRebList.size() - 1);
        assertThat(testFormPaieLigneReb.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testFormPaieLigneReb.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFormPaieLigneReb.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFormPaieLigneReb.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFormPaieLigneReb.getValOrigin()).isEqualTo(DEFAULT_VAL_ORIGIN);
        assertThat(testFormPaieLigneReb.getValCalcul()).isEqualTo(DEFAULT_VAL_CALCUL);
        assertThat(testFormPaieLigneReb.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFormPaieLigneReb.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFormPaieLigneReb.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFormPaieLigneReb.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFormPaieLigneReb.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFormPaieLigneReb.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testFormPaieLigneReb.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFormPaieLigneReb.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFormPaieLigneRebWithExistingId() throws Exception {
        // Create the FormPaieLigneReb with an existing ID
        formPaieLigneReb.setId(1L);

        int databaseSizeBeforeCreate = formPaieLigneRebRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormPaieLigneRebMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormPaieLigneRebs() throws Exception {
        // Initialize the database
        formPaieLigneRebRepository.saveAndFlush(formPaieLigneReb);

        // Get all the formPaieLigneRebList
        restFormPaieLigneRebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formPaieLigneReb.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].valOrigin").value(hasItem(DEFAULT_VAL_ORIGIN.doubleValue())))
            .andExpect(jsonPath("$.[*].valCalcul").value(hasItem(DEFAULT_VAL_CALCUL.doubleValue())))
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
    void getFormPaieLigneReb() throws Exception {
        // Initialize the database
        formPaieLigneRebRepository.saveAndFlush(formPaieLigneReb);

        // Get the formPaieLigneReb
        restFormPaieLigneRebMockMvc
            .perform(get(ENTITY_API_URL_ID, formPaieLigneReb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formPaieLigneReb.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.valOrigin").value(DEFAULT_VAL_ORIGIN.doubleValue()))
            .andExpect(jsonPath("$.valCalcul").value(DEFAULT_VAL_CALCUL.doubleValue()))
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
    void getNonExistingFormPaieLigneReb() throws Exception {
        // Get the formPaieLigneReb
        restFormPaieLigneRebMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormPaieLigneReb() throws Exception {
        // Initialize the database
        formPaieLigneRebRepository.saveAndFlush(formPaieLigneReb);

        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();

        // Update the formPaieLigneReb
        FormPaieLigneReb updatedFormPaieLigneReb = formPaieLigneRebRepository.findById(formPaieLigneReb.getId()).get();
        // Disconnect from session so that the updates on updatedFormPaieLigneReb are not directly saved in db
        em.detach(updatedFormPaieLigneReb);
        updatedFormPaieLigneReb
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .valOrigin(UPDATED_VAL_ORIGIN)
            .valCalcul(UPDATED_VAL_CALCUL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFormPaieLigneRebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormPaieLigneReb.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormPaieLigneReb))
            )
            .andExpect(status().isOk());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
        FormPaieLigneReb testFormPaieLigneReb = formPaieLigneRebList.get(formPaieLigneRebList.size() - 1);
        assertThat(testFormPaieLigneReb.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testFormPaieLigneReb.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaieLigneReb.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFormPaieLigneReb.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaieLigneReb.getValOrigin()).isEqualTo(UPDATED_VAL_ORIGIN);
        assertThat(testFormPaieLigneReb.getValCalcul()).isEqualTo(UPDATED_VAL_CALCUL);
        assertThat(testFormPaieLigneReb.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaieLigneReb.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaieLigneReb.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaieLigneReb.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaieLigneReb.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaieLigneReb.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaieLigneReb.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaieLigneReb.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();
        formPaieLigneReb.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormPaieLigneRebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formPaieLigneReb.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();
        formPaieLigneReb.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneRebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();
        formPaieLigneReb.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneRebMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormPaieLigneRebWithPatch() throws Exception {
        // Initialize the database
        formPaieLigneRebRepository.saveAndFlush(formPaieLigneReb);

        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();

        // Update the formPaieLigneReb using partial update
        FormPaieLigneReb partialUpdatedFormPaieLigneReb = new FormPaieLigneReb();
        partialUpdatedFormPaieLigneReb.setId(formPaieLigneReb.getId());

        partialUpdatedFormPaieLigneReb
            .code(UPDATED_CODE)
            .valCalcul(UPDATED_VAL_CALCUL)
            .dateop(UPDATED_DATEOP)
            .isDeleted(UPDATED_IS_DELETED);

        restFormPaieLigneRebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormPaieLigneReb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormPaieLigneReb))
            )
            .andExpect(status().isOk());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
        FormPaieLigneReb testFormPaieLigneReb = formPaieLigneRebList.get(formPaieLigneRebList.size() - 1);
        assertThat(testFormPaieLigneReb.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testFormPaieLigneReb.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaieLigneReb.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFormPaieLigneReb.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFormPaieLigneReb.getValOrigin()).isEqualTo(DEFAULT_VAL_ORIGIN);
        assertThat(testFormPaieLigneReb.getValCalcul()).isEqualTo(UPDATED_VAL_CALCUL);
        assertThat(testFormPaieLigneReb.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaieLigneReb.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFormPaieLigneReb.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFormPaieLigneReb.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFormPaieLigneReb.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFormPaieLigneReb.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaieLigneReb.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFormPaieLigneReb.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFormPaieLigneRebWithPatch() throws Exception {
        // Initialize the database
        formPaieLigneRebRepository.saveAndFlush(formPaieLigneReb);

        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();

        // Update the formPaieLigneReb using partial update
        FormPaieLigneReb partialUpdatedFormPaieLigneReb = new FormPaieLigneReb();
        partialUpdatedFormPaieLigneReb.setId(formPaieLigneReb.getId());

        partialUpdatedFormPaieLigneReb
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .valOrigin(UPDATED_VAL_ORIGIN)
            .valCalcul(UPDATED_VAL_CALCUL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFormPaieLigneRebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormPaieLigneReb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormPaieLigneReb))
            )
            .andExpect(status().isOk());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
        FormPaieLigneReb testFormPaieLigneReb = formPaieLigneRebList.get(formPaieLigneRebList.size() - 1);
        assertThat(testFormPaieLigneReb.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testFormPaieLigneReb.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaieLigneReb.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFormPaieLigneReb.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaieLigneReb.getValOrigin()).isEqualTo(UPDATED_VAL_ORIGIN);
        assertThat(testFormPaieLigneReb.getValCalcul()).isEqualTo(UPDATED_VAL_CALCUL);
        assertThat(testFormPaieLigneReb.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaieLigneReb.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaieLigneReb.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaieLigneReb.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaieLigneReb.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaieLigneReb.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaieLigneReb.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaieLigneReb.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();
        formPaieLigneReb.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormPaieLigneRebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formPaieLigneReb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();
        formPaieLigneReb.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneRebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormPaieLigneReb() throws Exception {
        int databaseSizeBeforeUpdate = formPaieLigneRebRepository.findAll().size();
        formPaieLigneReb.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieLigneRebMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaieLigneReb))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormPaieLigneReb in the database
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormPaieLigneReb() throws Exception {
        // Initialize the database
        formPaieLigneRebRepository.saveAndFlush(formPaieLigneReb);

        int databaseSizeBeforeDelete = formPaieLigneRebRepository.findAll().size();

        // Delete the formPaieLigneReb
        restFormPaieLigneRebMockMvc
            .perform(delete(ENTITY_API_URL_ID, formPaieLigneReb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormPaieLigneReb> formPaieLigneRebList = formPaieLigneRebRepository.findAll();
        assertThat(formPaieLigneRebList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
