package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.FormPaie;
import com.issa.payroll.repository.FormPaieRepository;
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
 * Integration tests for the {@link FormPaieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormPaieResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNE_DEBUT = 1;
    private static final Integer UPDATED_ANNE_DEBUT = 2;

    private static final Integer DEFAULT_ANNEE_FIN = 1;
    private static final Integer UPDATED_ANNEE_FIN = 2;

    private static final Integer DEFAULT_MOIS_DEBUT = 1;
    private static final Integer UPDATED_MOIS_DEBUT = 2;

    private static final Integer DEFAULT_MOIS_FIN = 1;
    private static final Integer UPDATED_MOIS_FIN = 2;

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/form-paies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormPaieRepository formPaieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormPaieMockMvc;

    private FormPaie formPaie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormPaie createEntity(EntityManager em) {
        FormPaie formPaie = new FormPaie()
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .anneDebut(DEFAULT_ANNE_DEBUT)
            .anneeFin(DEFAULT_ANNEE_FIN)
            .moisDebut(DEFAULT_MOIS_DEBUT)
            .moisFin(DEFAULT_MOIS_FIN)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .util(DEFAULT_UTIL)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return formPaie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormPaie createUpdatedEntity(EntityManager em) {
        FormPaie formPaie = new FormPaie()
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .anneDebut(UPDATED_ANNE_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .moisFin(UPDATED_MOIS_FIN)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return formPaie;
    }

    @BeforeEach
    public void initTest() {
        formPaie = createEntity(em);
    }

    @Test
    @Transactional
    void createFormPaie() throws Exception {
        int databaseSizeBeforeCreate = formPaieRepository.findAll().size();
        // Create the FormPaie
        restFormPaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaie)))
            .andExpect(status().isCreated());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeCreate + 1);
        FormPaie testFormPaie = formPaieList.get(formPaieList.size() - 1);
        assertThat(testFormPaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFormPaie.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFormPaie.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFormPaie.getAnneDebut()).isEqualTo(DEFAULT_ANNE_DEBUT);
        assertThat(testFormPaie.getAnneeFin()).isEqualTo(DEFAULT_ANNEE_FIN);
        assertThat(testFormPaie.getMoisDebut()).isEqualTo(DEFAULT_MOIS_DEBUT);
        assertThat(testFormPaie.getMoisFin()).isEqualTo(DEFAULT_MOIS_FIN);
        assertThat(testFormPaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFormPaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFormPaie.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFormPaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFormPaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFormPaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testFormPaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFormPaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFormPaieWithExistingId() throws Exception {
        // Create the FormPaie with an existing ID
        formPaie.setId(1L);

        int databaseSizeBeforeCreate = formPaieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormPaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaie)))
            .andExpect(status().isBadRequest());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormPaies() throws Exception {
        // Initialize the database
        formPaieRepository.saveAndFlush(formPaie);

        // Get all the formPaieList
        restFormPaieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formPaie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].anneDebut").value(hasItem(DEFAULT_ANNE_DEBUT)))
            .andExpect(jsonPath("$.[*].anneeFin").value(hasItem(DEFAULT_ANNEE_FIN)))
            .andExpect(jsonPath("$.[*].moisDebut").value(hasItem(DEFAULT_MOIS_DEBUT)))
            .andExpect(jsonPath("$.[*].moisFin").value(hasItem(DEFAULT_MOIS_FIN)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getFormPaie() throws Exception {
        // Initialize the database
        formPaieRepository.saveAndFlush(formPaie);

        // Get the formPaie
        restFormPaieMockMvc
            .perform(get(ENTITY_API_URL_ID, formPaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formPaie.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.anneDebut").value(DEFAULT_ANNE_DEBUT))
            .andExpect(jsonPath("$.anneeFin").value(DEFAULT_ANNEE_FIN))
            .andExpect(jsonPath("$.moisDebut").value(DEFAULT_MOIS_DEBUT))
            .andExpect(jsonPath("$.moisFin").value(DEFAULT_MOIS_FIN))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingFormPaie() throws Exception {
        // Get the formPaie
        restFormPaieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormPaie() throws Exception {
        // Initialize the database
        formPaieRepository.saveAndFlush(formPaie);

        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();

        // Update the formPaie
        FormPaie updatedFormPaie = formPaieRepository.findById(formPaie.getId()).get();
        // Disconnect from session so that the updates on updatedFormPaie are not directly saved in db
        em.detach(updatedFormPaie);
        updatedFormPaie
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .anneDebut(UPDATED_ANNE_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .moisFin(UPDATED_MOIS_FIN)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFormPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormPaie))
            )
            .andExpect(status().isOk());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
        FormPaie testFormPaie = formPaieList.get(formPaieList.size() - 1);
        assertThat(testFormPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFormPaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaie.getAnneDebut()).isEqualTo(UPDATED_ANNE_DEBUT);
        assertThat(testFormPaie.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testFormPaie.getMoisDebut()).isEqualTo(UPDATED_MOIS_DEBUT);
        assertThat(testFormPaie.getMoisFin()).isEqualTo(UPDATED_MOIS_FIN);
        assertThat(testFormPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFormPaie() throws Exception {
        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();
        formPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormPaie() throws Exception {
        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();
        formPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormPaie() throws Exception {
        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();
        formPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formPaie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormPaieWithPatch() throws Exception {
        // Initialize the database
        formPaieRepository.saveAndFlush(formPaie);

        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();

        // Update the formPaie using partial update
        FormPaie partialUpdatedFormPaie = new FormPaie();
        partialUpdatedFormPaie.setId(formPaie.getId());

        partialUpdatedFormPaie
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisFin(UPDATED_MOIS_FIN)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFormPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormPaie))
            )
            .andExpect(status().isOk());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
        FormPaie testFormPaie = formPaieList.get(formPaieList.size() - 1);
        assertThat(testFormPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaie.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFormPaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaie.getAnneDebut()).isEqualTo(DEFAULT_ANNE_DEBUT);
        assertThat(testFormPaie.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testFormPaie.getMoisDebut()).isEqualTo(DEFAULT_MOIS_DEBUT);
        assertThat(testFormPaie.getMoisFin()).isEqualTo(UPDATED_MOIS_FIN);
        assertThat(testFormPaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFormPaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFormPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFormPaieWithPatch() throws Exception {
        // Initialize the database
        formPaieRepository.saveAndFlush(formPaie);

        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();

        // Update the formPaie using partial update
        FormPaie partialUpdatedFormPaie = new FormPaie();
        partialUpdatedFormPaie.setId(formPaie.getId());

        partialUpdatedFormPaie
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .anneDebut(UPDATED_ANNE_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .moisFin(UPDATED_MOIS_FIN)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFormPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormPaie))
            )
            .andExpect(status().isOk());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
        FormPaie testFormPaie = formPaieList.get(formPaieList.size() - 1);
        assertThat(testFormPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormPaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFormPaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFormPaie.getAnneDebut()).isEqualTo(UPDATED_ANNE_DEBUT);
        assertThat(testFormPaie.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testFormPaie.getMoisDebut()).isEqualTo(UPDATED_MOIS_DEBUT);
        assertThat(testFormPaie.getMoisFin()).isEqualTo(UPDATED_MOIS_FIN);
        assertThat(testFormPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFormPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFormPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFormPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFormPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFormPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFormPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFormPaie() throws Exception {
        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();
        formPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormPaie() throws Exception {
        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();
        formPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormPaie() throws Exception {
        int databaseSizeBeforeUpdate = formPaieRepository.findAll().size();
        formPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormPaieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formPaie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormPaie in the database
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormPaie() throws Exception {
        // Initialize the database
        formPaieRepository.saveAndFlush(formPaie);

        int databaseSizeBeforeDelete = formPaieRepository.findAll().size();

        // Delete the formPaie
        restFormPaieMockMvc
            .perform(delete(ENTITY_API_URL_ID, formPaie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormPaie> formPaieList = formPaieRepository.findAll();
        assertThat(formPaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
