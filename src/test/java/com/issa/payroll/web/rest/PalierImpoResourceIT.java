package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.PalierImpo;
import com.issa.payroll.repository.PalierImpoRepository;
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
 * Integration tests for the {@link PalierImpoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PalierImpoResourceIT {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Double DEFAULT_PAYROLL_MIN = 1D;
    private static final Double UPDATED_PAYROLL_MIN = 2D;

    private static final Double DEFAULT_PAYROLL_MAX = 1D;
    private static final Double UPDATED_PAYROLL_MAX = 2D;

    private static final Double DEFAULT_IMPO_VALUE = 1D;
    private static final Double UPDATED_IMPO_VALUE = 2D;

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIF = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String ENTITY_API_URL = "/api/palier-impos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PalierImpoRepository palierImpoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPalierImpoMockMvc;

    private PalierImpo palierImpo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PalierImpo createEntity(EntityManager em) {
        PalierImpo palierImpo = new PalierImpo()
            .annee(DEFAULT_ANNEE)
            .payrollMin(DEFAULT_PAYROLL_MIN)
            .payrollMax(DEFAULT_PAYROLL_MAX)
            .impoValue(DEFAULT_IMPO_VALUE)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .dateModif(DEFAULT_DATE_MODIF)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return palierImpo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PalierImpo createUpdatedEntity(EntityManager em) {
        PalierImpo palierImpo = new PalierImpo()
            .annee(UPDATED_ANNEE)
            .payrollMin(UPDATED_PAYROLL_MIN)
            .payrollMax(UPDATED_PAYROLL_MAX)
            .impoValue(UPDATED_IMPO_VALUE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return palierImpo;
    }

    @BeforeEach
    public void initTest() {
        palierImpo = createEntity(em);
    }

    @Test
    @Transactional
    void createPalierImpo() throws Exception {
        int databaseSizeBeforeCreate = palierImpoRepository.findAll().size();
        // Create the PalierImpo
        restPalierImpoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierImpo)))
            .andExpect(status().isCreated());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeCreate + 1);
        PalierImpo testPalierImpo = palierImpoList.get(palierImpoList.size() - 1);
        assertThat(testPalierImpo.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testPalierImpo.getPayrollMin()).isEqualTo(DEFAULT_PAYROLL_MIN);
        assertThat(testPalierImpo.getPayrollMax()).isEqualTo(DEFAULT_PAYROLL_MAX);
        assertThat(testPalierImpo.getImpoValue()).isEqualTo(DEFAULT_IMPO_VALUE);
        assertThat(testPalierImpo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPalierImpo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPalierImpo.getDateModif()).isEqualTo(DEFAULT_DATE_MODIF);
        assertThat(testPalierImpo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPalierImpo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPalierImpo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createPalierImpoWithExistingId() throws Exception {
        // Create the PalierImpo with an existing ID
        palierImpo.setId(1L);

        int databaseSizeBeforeCreate = palierImpoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPalierImpoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierImpo)))
            .andExpect(status().isBadRequest());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPalierImpos() throws Exception {
        // Initialize the database
        palierImpoRepository.saveAndFlush(palierImpo);

        // Get all the palierImpoList
        restPalierImpoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palierImpo.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].payrollMin").value(hasItem(DEFAULT_PAYROLL_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].payrollMax").value(hasItem(DEFAULT_PAYROLL_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].impoValue").value(hasItem(DEFAULT_IMPO_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].dateModif").value(hasItem(sameInstant(DEFAULT_DATE_MODIF))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getPalierImpo() throws Exception {
        // Initialize the database
        palierImpoRepository.saveAndFlush(palierImpo);

        // Get the palierImpo
        restPalierImpoMockMvc
            .perform(get(ENTITY_API_URL_ID, palierImpo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(palierImpo.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.payrollMin").value(DEFAULT_PAYROLL_MIN.doubleValue()))
            .andExpect(jsonPath("$.payrollMax").value(DEFAULT_PAYROLL_MAX.doubleValue()))
            .andExpect(jsonPath("$.impoValue").value(DEFAULT_IMPO_VALUE.doubleValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.dateModif").value(sameInstant(DEFAULT_DATE_MODIF)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPalierImpo() throws Exception {
        // Get the palierImpo
        restPalierImpoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPalierImpo() throws Exception {
        // Initialize the database
        palierImpoRepository.saveAndFlush(palierImpo);

        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();

        // Update the palierImpo
        PalierImpo updatedPalierImpo = palierImpoRepository.findById(palierImpo.getId()).get();
        // Disconnect from session so that the updates on updatedPalierImpo are not directly saved in db
        em.detach(updatedPalierImpo);
        updatedPalierImpo
            .annee(UPDATED_ANNEE)
            .payrollMin(UPDATED_PAYROLL_MIN)
            .payrollMax(UPDATED_PAYROLL_MAX)
            .impoValue(UPDATED_IMPO_VALUE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierImpoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPalierImpo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPalierImpo))
            )
            .andExpect(status().isOk());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
        PalierImpo testPalierImpo = palierImpoList.get(palierImpoList.size() - 1);
        assertThat(testPalierImpo.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierImpo.getPayrollMin()).isEqualTo(UPDATED_PAYROLL_MIN);
        assertThat(testPalierImpo.getPayrollMax()).isEqualTo(UPDATED_PAYROLL_MAX);
        assertThat(testPalierImpo.getImpoValue()).isEqualTo(UPDATED_IMPO_VALUE);
        assertThat(testPalierImpo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierImpo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPalierImpo.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierImpo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierImpo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPalierImpo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingPalierImpo() throws Exception {
        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();
        palierImpo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalierImpoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, palierImpo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(palierImpo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPalierImpo() throws Exception {
        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();
        palierImpo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierImpoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(palierImpo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPalierImpo() throws Exception {
        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();
        palierImpo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierImpoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierImpo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePalierImpoWithPatch() throws Exception {
        // Initialize the database
        palierImpoRepository.saveAndFlush(palierImpo);

        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();

        // Update the palierImpo using partial update
        PalierImpo partialUpdatedPalierImpo = new PalierImpo();
        partialUpdatedPalierImpo.setId(palierImpo.getId());

        partialUpdatedPalierImpo
            .annee(UPDATED_ANNEE)
            .payrollMin(UPDATED_PAYROLL_MIN)
            .impoValue(UPDATED_IMPO_VALUE)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierImpoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPalierImpo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPalierImpo))
            )
            .andExpect(status().isOk());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
        PalierImpo testPalierImpo = palierImpoList.get(palierImpoList.size() - 1);
        assertThat(testPalierImpo.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierImpo.getPayrollMin()).isEqualTo(UPDATED_PAYROLL_MIN);
        assertThat(testPalierImpo.getPayrollMax()).isEqualTo(DEFAULT_PAYROLL_MAX);
        assertThat(testPalierImpo.getImpoValue()).isEqualTo(UPDATED_IMPO_VALUE);
        assertThat(testPalierImpo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPalierImpo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPalierImpo.getDateModif()).isEqualTo(DEFAULT_DATE_MODIF);
        assertThat(testPalierImpo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPalierImpo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPalierImpo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdatePalierImpoWithPatch() throws Exception {
        // Initialize the database
        palierImpoRepository.saveAndFlush(palierImpo);

        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();

        // Update the palierImpo using partial update
        PalierImpo partialUpdatedPalierImpo = new PalierImpo();
        partialUpdatedPalierImpo.setId(palierImpo.getId());

        partialUpdatedPalierImpo
            .annee(UPDATED_ANNEE)
            .payrollMin(UPDATED_PAYROLL_MIN)
            .payrollMax(UPDATED_PAYROLL_MAX)
            .impoValue(UPDATED_IMPO_VALUE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierImpoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPalierImpo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPalierImpo))
            )
            .andExpect(status().isOk());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
        PalierImpo testPalierImpo = palierImpoList.get(palierImpoList.size() - 1);
        assertThat(testPalierImpo.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierImpo.getPayrollMin()).isEqualTo(UPDATED_PAYROLL_MIN);
        assertThat(testPalierImpo.getPayrollMax()).isEqualTo(UPDATED_PAYROLL_MAX);
        assertThat(testPalierImpo.getImpoValue()).isEqualTo(UPDATED_IMPO_VALUE);
        assertThat(testPalierImpo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierImpo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPalierImpo.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierImpo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierImpo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPalierImpo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingPalierImpo() throws Exception {
        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();
        palierImpo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalierImpoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, palierImpo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierImpo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPalierImpo() throws Exception {
        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();
        palierImpo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierImpoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierImpo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPalierImpo() throws Exception {
        int databaseSizeBeforeUpdate = palierImpoRepository.findAll().size();
        palierImpo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierImpoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(palierImpo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PalierImpo in the database
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePalierImpo() throws Exception {
        // Initialize the database
        palierImpoRepository.saveAndFlush(palierImpo);

        int databaseSizeBeforeDelete = palierImpoRepository.findAll().size();

        // Delete the palierImpo
        restPalierImpoMockMvc
            .perform(delete(ENTITY_API_URL_ID, palierImpo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PalierImpo> palierImpoList = palierImpoRepository.findAll();
        assertThat(palierImpoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
