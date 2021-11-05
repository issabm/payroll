package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.SoldeAbsenceContrat;
import com.issa.payroll.repository.SoldeAbsenceContratRepository;
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
 * Integration tests for the {@link SoldeAbsenceContratResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SoldeAbsenceContratResourceIT {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_FULL_PAY_RIGHT = 1;
    private static final Integer UPDATED_FULL_PAY_RIGHT = 2;

    private static final Integer DEFAULT_HALF_PAY_RIGHT = 1;
    private static final Integer UPDATED_HALF_PAY_RIGHT = 2;

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

    private static final String ENTITY_API_URL = "/api/solde-absence-contrats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoldeAbsenceContratRepository soldeAbsenceContratRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoldeAbsenceContratMockMvc;

    private SoldeAbsenceContrat soldeAbsenceContrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldeAbsenceContrat createEntity(EntityManager em) {
        SoldeAbsenceContrat soldeAbsenceContrat = new SoldeAbsenceContrat()
            .annee(DEFAULT_ANNEE)
            .fullPayRight(DEFAULT_FULL_PAY_RIGHT)
            .halfPayRight(DEFAULT_HALF_PAY_RIGHT)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return soldeAbsenceContrat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldeAbsenceContrat createUpdatedEntity(EntityManager em) {
        SoldeAbsenceContrat soldeAbsenceContrat = new SoldeAbsenceContrat()
            .annee(UPDATED_ANNEE)
            .fullPayRight(UPDATED_FULL_PAY_RIGHT)
            .halfPayRight(UPDATED_HALF_PAY_RIGHT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return soldeAbsenceContrat;
    }

    @BeforeEach
    public void initTest() {
        soldeAbsenceContrat = createEntity(em);
    }

    @Test
    @Transactional
    void createSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeCreate = soldeAbsenceContratRepository.findAll().size();
        // Create the SoldeAbsenceContrat
        restSoldeAbsenceContratMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isCreated());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeCreate + 1);
        SoldeAbsenceContrat testSoldeAbsenceContrat = soldeAbsenceContratList.get(soldeAbsenceContratList.size() - 1);
        assertThat(testSoldeAbsenceContrat.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldeAbsenceContrat.getFullPayRight()).isEqualTo(DEFAULT_FULL_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getHalfPayRight()).isEqualTo(DEFAULT_HALF_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSoldeAbsenceContrat.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSoldeAbsenceContrat.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSoldeAbsenceContrat.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSoldeAbsenceContrat.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSoldeAbsenceContrat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSoldeAbsenceContrat.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSoldeAbsenceContratWithExistingId() throws Exception {
        // Create the SoldeAbsenceContrat with an existing ID
        soldeAbsenceContrat.setId(1L);

        int databaseSizeBeforeCreate = soldeAbsenceContratRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoldeAbsenceContratMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoldeAbsenceContrats() throws Exception {
        // Initialize the database
        soldeAbsenceContratRepository.saveAndFlush(soldeAbsenceContrat);

        // Get all the soldeAbsenceContratList
        restSoldeAbsenceContratMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soldeAbsenceContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].fullPayRight").value(hasItem(DEFAULT_FULL_PAY_RIGHT)))
            .andExpect(jsonPath("$.[*].halfPayRight").value(hasItem(DEFAULT_HALF_PAY_RIGHT)))
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
    void getSoldeAbsenceContrat() throws Exception {
        // Initialize the database
        soldeAbsenceContratRepository.saveAndFlush(soldeAbsenceContrat);

        // Get the soldeAbsenceContrat
        restSoldeAbsenceContratMockMvc
            .perform(get(ENTITY_API_URL_ID, soldeAbsenceContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soldeAbsenceContrat.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.fullPayRight").value(DEFAULT_FULL_PAY_RIGHT))
            .andExpect(jsonPath("$.halfPayRight").value(DEFAULT_HALF_PAY_RIGHT))
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
    void getNonExistingSoldeAbsenceContrat() throws Exception {
        // Get the soldeAbsenceContrat
        restSoldeAbsenceContratMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSoldeAbsenceContrat() throws Exception {
        // Initialize the database
        soldeAbsenceContratRepository.saveAndFlush(soldeAbsenceContrat);

        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();

        // Update the soldeAbsenceContrat
        SoldeAbsenceContrat updatedSoldeAbsenceContrat = soldeAbsenceContratRepository.findById(soldeAbsenceContrat.getId()).get();
        // Disconnect from session so that the updates on updatedSoldeAbsenceContrat are not directly saved in db
        em.detach(updatedSoldeAbsenceContrat);
        updatedSoldeAbsenceContrat
            .annee(UPDATED_ANNEE)
            .fullPayRight(UPDATED_FULL_PAY_RIGHT)
            .halfPayRight(UPDATED_HALF_PAY_RIGHT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSoldeAbsenceContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoldeAbsenceContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSoldeAbsenceContrat))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsenceContrat testSoldeAbsenceContrat = soldeAbsenceContratList.get(soldeAbsenceContratList.size() - 1);
        assertThat(testSoldeAbsenceContrat.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsenceContrat.getFullPayRight()).isEqualTo(UPDATED_FULL_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getHalfPayRight()).isEqualTo(UPDATED_HALF_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsenceContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsenceContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsenceContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsenceContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSoldeAbsenceContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSoldeAbsenceContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();
        soldeAbsenceContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldeAbsenceContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soldeAbsenceContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();
        soldeAbsenceContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();
        soldeAbsenceContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceContratMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoldeAbsenceContratWithPatch() throws Exception {
        // Initialize the database
        soldeAbsenceContratRepository.saveAndFlush(soldeAbsenceContrat);

        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();

        // Update the soldeAbsenceContrat using partial update
        SoldeAbsenceContrat partialUpdatedSoldeAbsenceContrat = new SoldeAbsenceContrat();
        partialUpdatedSoldeAbsenceContrat.setId(soldeAbsenceContrat.getId());

        partialUpdatedSoldeAbsenceContrat
            .annee(UPDATED_ANNEE)
            .fullPayRight(UPDATED_FULL_PAY_RIGHT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restSoldeAbsenceContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldeAbsenceContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldeAbsenceContrat))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsenceContrat testSoldeAbsenceContrat = soldeAbsenceContratList.get(soldeAbsenceContratList.size() - 1);
        assertThat(testSoldeAbsenceContrat.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsenceContrat.getFullPayRight()).isEqualTo(UPDATED_FULL_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getHalfPayRight()).isEqualTo(DEFAULT_HALF_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsenceContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsenceContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsenceContrat.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSoldeAbsenceContrat.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSoldeAbsenceContrat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSoldeAbsenceContrat.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSoldeAbsenceContratWithPatch() throws Exception {
        // Initialize the database
        soldeAbsenceContratRepository.saveAndFlush(soldeAbsenceContrat);

        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();

        // Update the soldeAbsenceContrat using partial update
        SoldeAbsenceContrat partialUpdatedSoldeAbsenceContrat = new SoldeAbsenceContrat();
        partialUpdatedSoldeAbsenceContrat.setId(soldeAbsenceContrat.getId());

        partialUpdatedSoldeAbsenceContrat
            .annee(UPDATED_ANNEE)
            .fullPayRight(UPDATED_FULL_PAY_RIGHT)
            .halfPayRight(UPDATED_HALF_PAY_RIGHT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSoldeAbsenceContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldeAbsenceContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldeAbsenceContrat))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsenceContrat testSoldeAbsenceContrat = soldeAbsenceContratList.get(soldeAbsenceContratList.size() - 1);
        assertThat(testSoldeAbsenceContrat.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsenceContrat.getFullPayRight()).isEqualTo(UPDATED_FULL_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getHalfPayRight()).isEqualTo(UPDATED_HALF_PAY_RIGHT);
        assertThat(testSoldeAbsenceContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsenceContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsenceContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsenceContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsenceContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSoldeAbsenceContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSoldeAbsenceContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();
        soldeAbsenceContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldeAbsenceContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soldeAbsenceContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();
        soldeAbsenceContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoldeAbsenceContrat() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceContratRepository.findAll().size();
        soldeAbsenceContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceContratMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsenceContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldeAbsenceContrat in the database
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoldeAbsenceContrat() throws Exception {
        // Initialize the database
        soldeAbsenceContratRepository.saveAndFlush(soldeAbsenceContrat);

        int databaseSizeBeforeDelete = soldeAbsenceContratRepository.findAll().size();

        // Delete the soldeAbsenceContrat
        restSoldeAbsenceContratMockMvc
            .perform(delete(ENTITY_API_URL_ID, soldeAbsenceContrat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoldeAbsenceContrat> soldeAbsenceContratList = soldeAbsenceContratRepository.findAll();
        assertThat(soldeAbsenceContratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
