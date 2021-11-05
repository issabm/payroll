package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.MatricePaie;
import com.issa.payroll.repository.MatricePaieRepository;
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
 * Integration tests for the {@link MatricePaieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MatricePaieResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DISPLAY = false;
    private static final Boolean UPDATED_IS_DISPLAY = true;

    private static final Integer DEFAULT_ANNEE_DEBUT = 1;
    private static final Integer UPDATED_ANNEE_DEBUT = 2;

    private static final Integer DEFAULT_MOIS_DEBUT = 1;
    private static final Integer UPDATED_MOIS_DEBUT = 2;

    private static final Integer DEFAULT_ANNEE_FIN = 1;
    private static final Integer UPDATED_ANNEE_FIN = 2;

    private static final Integer DEFAULT_MOIS_FIN = 1;
    private static final Integer UPDATED_MOIS_FIN = 2;

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/matrice-paies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatricePaieRepository matricePaieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatricePaieMockMvc;

    private MatricePaie matricePaie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatricePaie createEntity(EntityManager em) {
        MatricePaie matricePaie = new MatricePaie()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .isDisplay(DEFAULT_IS_DISPLAY)
            .anneeDebut(DEFAULT_ANNEE_DEBUT)
            .moisDebut(DEFAULT_MOIS_DEBUT)
            .anneeFin(DEFAULT_ANNEE_FIN)
            .moisFin(DEFAULT_MOIS_FIN)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return matricePaie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatricePaie createUpdatedEntity(EntityManager em) {
        MatricePaie matricePaie = new MatricePaie()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .isDisplay(UPDATED_IS_DISPLAY)
            .anneeDebut(UPDATED_ANNEE_DEBUT)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisFin(UPDATED_MOIS_FIN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return matricePaie;
    }

    @BeforeEach
    public void initTest() {
        matricePaie = createEntity(em);
    }

    @Test
    @Transactional
    void createMatricePaie() throws Exception {
        int databaseSizeBeforeCreate = matricePaieRepository.findAll().size();
        // Create the MatricePaie
        restMatricePaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matricePaie)))
            .andExpect(status().isCreated());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeCreate + 1);
        MatricePaie testMatricePaie = matricePaieList.get(matricePaieList.size() - 1);
        assertThat(testMatricePaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMatricePaie.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testMatricePaie.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testMatricePaie.getIsDisplay()).isEqualTo(DEFAULT_IS_DISPLAY);
        assertThat(testMatricePaie.getAnneeDebut()).isEqualTo(DEFAULT_ANNEE_DEBUT);
        assertThat(testMatricePaie.getMoisDebut()).isEqualTo(DEFAULT_MOIS_DEBUT);
        assertThat(testMatricePaie.getAnneeFin()).isEqualTo(DEFAULT_ANNEE_FIN);
        assertThat(testMatricePaie.getMoisFin()).isEqualTo(DEFAULT_MOIS_FIN);
        assertThat(testMatricePaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testMatricePaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testMatricePaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMatricePaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testMatricePaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMatricePaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMatricePaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createMatricePaieWithExistingId() throws Exception {
        // Create the MatricePaie with an existing ID
        matricePaie.setId(1L);

        int databaseSizeBeforeCreate = matricePaieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatricePaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matricePaie)))
            .andExpect(status().isBadRequest());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatricePaies() throws Exception {
        // Initialize the database
        matricePaieRepository.saveAndFlush(matricePaie);

        // Get all the matricePaieList
        restMatricePaieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricePaie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].isDisplay").value(hasItem(DEFAULT_IS_DISPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].anneeDebut").value(hasItem(DEFAULT_ANNEE_DEBUT)))
            .andExpect(jsonPath("$.[*].moisDebut").value(hasItem(DEFAULT_MOIS_DEBUT)))
            .andExpect(jsonPath("$.[*].anneeFin").value(hasItem(DEFAULT_ANNEE_FIN)))
            .andExpect(jsonPath("$.[*].moisFin").value(hasItem(DEFAULT_MOIS_FIN)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getMatricePaie() throws Exception {
        // Initialize the database
        matricePaieRepository.saveAndFlush(matricePaie);

        // Get the matricePaie
        restMatricePaieMockMvc
            .perform(get(ENTITY_API_URL_ID, matricePaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matricePaie.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.isDisplay").value(DEFAULT_IS_DISPLAY.booleanValue()))
            .andExpect(jsonPath("$.anneeDebut").value(DEFAULT_ANNEE_DEBUT))
            .andExpect(jsonPath("$.moisDebut").value(DEFAULT_MOIS_DEBUT))
            .andExpect(jsonPath("$.anneeFin").value(DEFAULT_ANNEE_FIN))
            .andExpect(jsonPath("$.moisFin").value(DEFAULT_MOIS_FIN))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingMatricePaie() throws Exception {
        // Get the matricePaie
        restMatricePaieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMatricePaie() throws Exception {
        // Initialize the database
        matricePaieRepository.saveAndFlush(matricePaie);

        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();

        // Update the matricePaie
        MatricePaie updatedMatricePaie = matricePaieRepository.findById(matricePaie.getId()).get();
        // Disconnect from session so that the updates on updatedMatricePaie are not directly saved in db
        em.detach(updatedMatricePaie);
        updatedMatricePaie
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .isDisplay(UPDATED_IS_DISPLAY)
            .anneeDebut(UPDATED_ANNEE_DEBUT)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisFin(UPDATED_MOIS_FIN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restMatricePaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMatricePaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMatricePaie))
            )
            .andExpect(status().isOk());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
        MatricePaie testMatricePaie = matricePaieList.get(matricePaieList.size() - 1);
        assertThat(testMatricePaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMatricePaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testMatricePaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testMatricePaie.getIsDisplay()).isEqualTo(UPDATED_IS_DISPLAY);
        assertThat(testMatricePaie.getAnneeDebut()).isEqualTo(UPDATED_ANNEE_DEBUT);
        assertThat(testMatricePaie.getMoisDebut()).isEqualTo(UPDATED_MOIS_DEBUT);
        assertThat(testMatricePaie.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testMatricePaie.getMoisFin()).isEqualTo(UPDATED_MOIS_FIN);
        assertThat(testMatricePaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMatricePaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMatricePaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMatricePaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMatricePaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMatricePaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMatricePaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMatricePaie() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();
        matricePaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatricePaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matricePaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matricePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatricePaie() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();
        matricePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matricePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatricePaie() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();
        matricePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matricePaie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatricePaieWithPatch() throws Exception {
        // Initialize the database
        matricePaieRepository.saveAndFlush(matricePaie);

        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();

        // Update the matricePaie using partial update
        MatricePaie partialUpdatedMatricePaie = new MatricePaie();
        partialUpdatedMatricePaie.setId(matricePaie.getId());

        partialUpdatedMatricePaie
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .anneeDebut(UPDATED_ANNEE_DEBUT)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .dateop(UPDATED_DATEOP)
            .op(UPDATED_OP)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restMatricePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatricePaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatricePaie))
            )
            .andExpect(status().isOk());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
        MatricePaie testMatricePaie = matricePaieList.get(matricePaieList.size() - 1);
        assertThat(testMatricePaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMatricePaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testMatricePaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testMatricePaie.getIsDisplay()).isEqualTo(DEFAULT_IS_DISPLAY);
        assertThat(testMatricePaie.getAnneeDebut()).isEqualTo(UPDATED_ANNEE_DEBUT);
        assertThat(testMatricePaie.getMoisDebut()).isEqualTo(UPDATED_MOIS_DEBUT);
        assertThat(testMatricePaie.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testMatricePaie.getMoisFin()).isEqualTo(DEFAULT_MOIS_FIN);
        assertThat(testMatricePaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testMatricePaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMatricePaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMatricePaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMatricePaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMatricePaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMatricePaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMatricePaieWithPatch() throws Exception {
        // Initialize the database
        matricePaieRepository.saveAndFlush(matricePaie);

        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();

        // Update the matricePaie using partial update
        MatricePaie partialUpdatedMatricePaie = new MatricePaie();
        partialUpdatedMatricePaie.setId(matricePaie.getId());

        partialUpdatedMatricePaie
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .isDisplay(UPDATED_IS_DISPLAY)
            .anneeDebut(UPDATED_ANNEE_DEBUT)
            .moisDebut(UPDATED_MOIS_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .moisFin(UPDATED_MOIS_FIN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restMatricePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatricePaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatricePaie))
            )
            .andExpect(status().isOk());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
        MatricePaie testMatricePaie = matricePaieList.get(matricePaieList.size() - 1);
        assertThat(testMatricePaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMatricePaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testMatricePaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testMatricePaie.getIsDisplay()).isEqualTo(UPDATED_IS_DISPLAY);
        assertThat(testMatricePaie.getAnneeDebut()).isEqualTo(UPDATED_ANNEE_DEBUT);
        assertThat(testMatricePaie.getMoisDebut()).isEqualTo(UPDATED_MOIS_DEBUT);
        assertThat(testMatricePaie.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testMatricePaie.getMoisFin()).isEqualTo(UPDATED_MOIS_FIN);
        assertThat(testMatricePaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMatricePaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMatricePaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMatricePaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMatricePaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMatricePaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMatricePaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMatricePaie() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();
        matricePaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatricePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matricePaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matricePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatricePaie() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();
        matricePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matricePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatricePaie() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieRepository.findAll().size();
        matricePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(matricePaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatricePaie in the database
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatricePaie() throws Exception {
        // Initialize the database
        matricePaieRepository.saveAndFlush(matricePaie);

        int databaseSizeBeforeDelete = matricePaieRepository.findAll().size();

        // Delete the matricePaie
        restMatricePaieMockMvc
            .perform(delete(ENTITY_API_URL_ID, matricePaie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatricePaie> matricePaieList = matricePaieRepository.findAll();
        assertThat(matricePaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
