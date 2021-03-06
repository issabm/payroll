package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Enfant;
import com.issa.payroll.repository.EnfantRepository;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link EnfantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EnfantResourceIT {

    private static final String DEFAULT_NOM_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_AR = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_EN = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_EN = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_EN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/enfants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnfantMockMvc;

    private Enfant enfant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createEntity(EntityManager em) {
        Enfant enfant = new Enfant()
            .nomAr(DEFAULT_NOM_AR)
            .prenomAr(DEFAULT_PRENOM_AR)
            .nomEn(DEFAULT_NOM_EN)
            .prenomEn(DEFAULT_PRENOM_EN)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return enfant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createUpdatedEntity(EntityManager em) {
        Enfant enfant = new Enfant()
            .nomAr(UPDATED_NOM_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return enfant;
    }

    @BeforeEach
    public void initTest() {
        enfant = createEntity(em);
    }

    @Test
    @Transactional
    void createEnfant() throws Exception {
        int databaseSizeBeforeCreate = enfantRepository.findAll().size();
        // Create the Enfant
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isCreated());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeCreate + 1);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNomAr()).isEqualTo(DEFAULT_NOM_AR);
        assertThat(testEnfant.getPrenomAr()).isEqualTo(DEFAULT_PRENOM_AR);
        assertThat(testEnfant.getNomEn()).isEqualTo(DEFAULT_NOM_EN);
        assertThat(testEnfant.getPrenomEn()).isEqualTo(DEFAULT_PRENOM_EN);
        assertThat(testEnfant.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testEnfant.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEnfant.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEnfant.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEnfant.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEnfant.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createEnfantWithExistingId() throws Exception {
        // Create the Enfant with an existing ID
        enfant.setId(1L);

        int databaseSizeBeforeCreate = enfantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnfants() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        // Get all the enfantList
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enfant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomAr").value(hasItem(DEFAULT_NOM_AR)))
            .andExpect(jsonPath("$.[*].prenomAr").value(hasItem(DEFAULT_PRENOM_AR)))
            .andExpect(jsonPath("$.[*].nomEn").value(hasItem(DEFAULT_NOM_EN)))
            .andExpect(jsonPath("$.[*].prenomEn").value(hasItem(DEFAULT_PRENOM_EN)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        // Get the enfant
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL_ID, enfant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enfant.getId().intValue()))
            .andExpect(jsonPath("$.nomAr").value(DEFAULT_NOM_AR))
            .andExpect(jsonPath("$.prenomAr").value(DEFAULT_PRENOM_AR))
            .andExpect(jsonPath("$.nomEn").value(DEFAULT_NOM_EN))
            .andExpect(jsonPath("$.prenomEn").value(DEFAULT_PRENOM_EN))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEnfant() throws Exception {
        // Get the enfant
        restEnfantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant
        Enfant updatedEnfant = enfantRepository.findById(enfant.getId()).get();
        // Disconnect from session so that the updates on updatedEnfant are not directly saved in db
        em.detach(updatedEnfant);
        updatedEnfant
            .nomAr(UPDATED_NOM_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnfant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testEnfant.getPrenomAr()).isEqualTo(UPDATED_PRENOM_AR);
        assertThat(testEnfant.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testEnfant.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testEnfant.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testEnfant.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEnfant.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEnfant.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEnfant.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEnfant.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enfant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .nomAr(UPDATED_NOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testEnfant.getPrenomAr()).isEqualTo(DEFAULT_PRENOM_AR);
        assertThat(testEnfant.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testEnfant.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testEnfant.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testEnfant.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEnfant.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEnfant.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEnfant.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEnfant.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .nomAr(UPDATED_NOM_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testEnfant.getPrenomAr()).isEqualTo(UPDATED_PRENOM_AR);
        assertThat(testEnfant.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testEnfant.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testEnfant.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testEnfant.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEnfant.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEnfant.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEnfant.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEnfant.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeDelete = enfantRepository.findAll().size();

        // Delete the enfant
        restEnfantMockMvc
            .perform(delete(ENTITY_API_URL_ID, enfant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
