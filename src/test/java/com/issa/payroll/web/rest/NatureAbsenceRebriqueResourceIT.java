package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NatureAbsenceRebrique;
import com.issa.payroll.repository.NatureAbsenceRebriqueRepository;
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
 * Integration tests for the {@link NatureAbsenceRebriqueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NatureAbsenceRebriqueResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE_TAKEN = 1D;
    private static final Double UPDATED_VALUE_TAKEN = 2D;

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

    private static final String ENTITY_API_URL = "/api/nature-absence-rebriques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureAbsenceRebriqueRepository natureAbsenceRebriqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureAbsenceRebriqueMockMvc;

    private NatureAbsenceRebrique natureAbsenceRebrique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAbsenceRebrique createEntity(EntityManager em) {
        NatureAbsenceRebrique natureAbsenceRebrique = new NatureAbsenceRebrique()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .valueTaken(DEFAULT_VALUE_TAKEN)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return natureAbsenceRebrique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAbsenceRebrique createUpdatedEntity(EntityManager em) {
        NatureAbsenceRebrique natureAbsenceRebrique = new NatureAbsenceRebrique()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .valueTaken(UPDATED_VALUE_TAKEN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return natureAbsenceRebrique;
    }

    @BeforeEach
    public void initTest() {
        natureAbsenceRebrique = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeCreate = natureAbsenceRebriqueRepository.findAll().size();
        // Create the NatureAbsenceRebrique
        restNatureAbsenceRebriqueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isCreated());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeCreate + 1);
        NatureAbsenceRebrique testNatureAbsenceRebrique = natureAbsenceRebriqueList.get(natureAbsenceRebriqueList.size() - 1);
        assertThat(testNatureAbsenceRebrique.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureAbsenceRebrique.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureAbsenceRebrique.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureAbsenceRebrique.getValueTaken()).isEqualTo(DEFAULT_VALUE_TAKEN);
        assertThat(testNatureAbsenceRebrique.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureAbsenceRebrique.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureAbsenceRebrique.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureAbsenceRebrique.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureAbsenceRebrique.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureAbsenceRebrique.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureAbsenceRebrique.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNatureAbsenceRebriqueWithExistingId() throws Exception {
        // Create the NatureAbsenceRebrique with an existing ID
        natureAbsenceRebrique.setId(1L);

        int databaseSizeBeforeCreate = natureAbsenceRebriqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureAbsenceRebriqueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureAbsenceRebriques() throws Exception {
        // Initialize the database
        natureAbsenceRebriqueRepository.saveAndFlush(natureAbsenceRebrique);

        // Get all the natureAbsenceRebriqueList
        restNatureAbsenceRebriqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureAbsenceRebrique.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].valueTaken").value(hasItem(DEFAULT_VALUE_TAKEN.doubleValue())))
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
    void getNatureAbsenceRebrique() throws Exception {
        // Initialize the database
        natureAbsenceRebriqueRepository.saveAndFlush(natureAbsenceRebrique);

        // Get the natureAbsenceRebrique
        restNatureAbsenceRebriqueMockMvc
            .perform(get(ENTITY_API_URL_ID, natureAbsenceRebrique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureAbsenceRebrique.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.valueTaken").value(DEFAULT_VALUE_TAKEN.doubleValue()))
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
    void getNonExistingNatureAbsenceRebrique() throws Exception {
        // Get the natureAbsenceRebrique
        restNatureAbsenceRebriqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureAbsenceRebrique() throws Exception {
        // Initialize the database
        natureAbsenceRebriqueRepository.saveAndFlush(natureAbsenceRebrique);

        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();

        // Update the natureAbsenceRebrique
        NatureAbsenceRebrique updatedNatureAbsenceRebrique = natureAbsenceRebriqueRepository.findById(natureAbsenceRebrique.getId()).get();
        // Disconnect from session so that the updates on updatedNatureAbsenceRebrique are not directly saved in db
        em.detach(updatedNatureAbsenceRebrique);
        updatedNatureAbsenceRebrique
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .valueTaken(UPDATED_VALUE_TAKEN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureAbsenceRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureAbsenceRebrique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureAbsenceRebrique))
            )
            .andExpect(status().isOk());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
        NatureAbsenceRebrique testNatureAbsenceRebrique = natureAbsenceRebriqueList.get(natureAbsenceRebriqueList.size() - 1);
        assertThat(testNatureAbsenceRebrique.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAbsenceRebrique.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAbsenceRebrique.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAbsenceRebrique.getValueTaken()).isEqualTo(UPDATED_VALUE_TAKEN);
        assertThat(testNatureAbsenceRebrique.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAbsenceRebrique.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAbsenceRebrique.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAbsenceRebrique.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAbsenceRebrique.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAbsenceRebrique.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAbsenceRebrique.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();
        natureAbsenceRebrique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAbsenceRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureAbsenceRebrique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();
        natureAbsenceRebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();
        natureAbsenceRebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureAbsenceRebriqueWithPatch() throws Exception {
        // Initialize the database
        natureAbsenceRebriqueRepository.saveAndFlush(natureAbsenceRebrique);

        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();

        // Update the natureAbsenceRebrique using partial update
        NatureAbsenceRebrique partialUpdatedNatureAbsenceRebrique = new NatureAbsenceRebrique();
        partialUpdatedNatureAbsenceRebrique.setId(natureAbsenceRebrique.getId());

        partialUpdatedNatureAbsenceRebrique.libAr(UPDATED_LIB_AR).op(UPDATED_OP).createdDate(UPDATED_CREATED_DATE);

        restNatureAbsenceRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAbsenceRebrique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAbsenceRebrique))
            )
            .andExpect(status().isOk());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
        NatureAbsenceRebrique testNatureAbsenceRebrique = natureAbsenceRebriqueList.get(natureAbsenceRebriqueList.size() - 1);
        assertThat(testNatureAbsenceRebrique.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureAbsenceRebrique.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAbsenceRebrique.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureAbsenceRebrique.getValueTaken()).isEqualTo(DEFAULT_VALUE_TAKEN);
        assertThat(testNatureAbsenceRebrique.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureAbsenceRebrique.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureAbsenceRebrique.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureAbsenceRebrique.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAbsenceRebrique.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureAbsenceRebrique.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAbsenceRebrique.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNatureAbsenceRebriqueWithPatch() throws Exception {
        // Initialize the database
        natureAbsenceRebriqueRepository.saveAndFlush(natureAbsenceRebrique);

        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();

        // Update the natureAbsenceRebrique using partial update
        NatureAbsenceRebrique partialUpdatedNatureAbsenceRebrique = new NatureAbsenceRebrique();
        partialUpdatedNatureAbsenceRebrique.setId(natureAbsenceRebrique.getId());

        partialUpdatedNatureAbsenceRebrique
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .valueTaken(UPDATED_VALUE_TAKEN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureAbsenceRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAbsenceRebrique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAbsenceRebrique))
            )
            .andExpect(status().isOk());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
        NatureAbsenceRebrique testNatureAbsenceRebrique = natureAbsenceRebriqueList.get(natureAbsenceRebriqueList.size() - 1);
        assertThat(testNatureAbsenceRebrique.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAbsenceRebrique.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAbsenceRebrique.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAbsenceRebrique.getValueTaken()).isEqualTo(UPDATED_VALUE_TAKEN);
        assertThat(testNatureAbsenceRebrique.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAbsenceRebrique.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAbsenceRebrique.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAbsenceRebrique.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAbsenceRebrique.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAbsenceRebrique.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAbsenceRebrique.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();
        natureAbsenceRebrique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAbsenceRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureAbsenceRebrique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();
        natureAbsenceRebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureAbsenceRebrique() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRebriqueRepository.findAll().size();
        natureAbsenceRebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsenceRebrique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAbsenceRebrique in the database
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureAbsenceRebrique() throws Exception {
        // Initialize the database
        natureAbsenceRebriqueRepository.saveAndFlush(natureAbsenceRebrique);

        int databaseSizeBeforeDelete = natureAbsenceRebriqueRepository.findAll().size();

        // Delete the natureAbsenceRebrique
        restNatureAbsenceRebriqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureAbsenceRebrique.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureAbsenceRebrique> natureAbsenceRebriqueList = natureAbsenceRebriqueRepository.findAll();
        assertThat(natureAbsenceRebriqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
