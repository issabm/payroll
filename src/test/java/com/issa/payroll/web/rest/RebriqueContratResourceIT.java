package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.RebriqueContrat;
import com.issa.payroll.repository.RebriqueContratRepository;
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
 * Integration tests for the {@link RebriqueContratResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RebriqueContratResourceIT {

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SITUATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SITUATION = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/rebrique-contrats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RebriqueContratRepository rebriqueContratRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRebriqueContratMockMvc;

    private RebriqueContrat rebriqueContrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RebriqueContrat createEntity(EntityManager em) {
        RebriqueContrat rebriqueContrat = new RebriqueContrat()
            .util(DEFAULT_UTIL)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return rebriqueContrat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RebriqueContrat createUpdatedEntity(EntityManager em) {
        RebriqueContrat rebriqueContrat = new RebriqueContrat()
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return rebriqueContrat;
    }

    @BeforeEach
    public void initTest() {
        rebriqueContrat = createEntity(em);
    }

    @Test
    @Transactional
    void createRebriqueContrat() throws Exception {
        int databaseSizeBeforeCreate = rebriqueContratRepository.findAll().size();
        // Create the RebriqueContrat
        restRebriqueContratMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isCreated());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeCreate + 1);
        RebriqueContrat testRebriqueContrat = rebriqueContratList.get(rebriqueContratList.size() - 1);
        assertThat(testRebriqueContrat.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testRebriqueContrat.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testRebriqueContrat.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testRebriqueContrat.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRebriqueContrat.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRebriqueContrat.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testRebriqueContrat.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testRebriqueContrat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRebriqueContrat.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createRebriqueContratWithExistingId() throws Exception {
        // Create the RebriqueContrat with an existing ID
        rebriqueContrat.setId(1L);

        int databaseSizeBeforeCreate = rebriqueContratRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRebriqueContratMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRebriqueContrats() throws Exception {
        // Initialize the database
        rebriqueContratRepository.saveAndFlush(rebriqueContrat);

        // Get all the rebriqueContratList
        restRebriqueContratMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rebriqueContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateSituation").value(hasItem(DEFAULT_DATE_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getRebriqueContrat() throws Exception {
        // Initialize the database
        rebriqueContratRepository.saveAndFlush(rebriqueContrat);

        // Get the rebriqueContrat
        restRebriqueContratMockMvc
            .perform(get(ENTITY_API_URL_ID, rebriqueContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rebriqueContrat.getId().intValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateSituation").value(DEFAULT_DATE_SITUATION.toString()))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingRebriqueContrat() throws Exception {
        // Get the rebriqueContrat
        restRebriqueContratMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRebriqueContrat() throws Exception {
        // Initialize the database
        rebriqueContratRepository.saveAndFlush(rebriqueContrat);

        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();

        // Update the rebriqueContrat
        RebriqueContrat updatedRebriqueContrat = rebriqueContratRepository.findById(rebriqueContrat.getId()).get();
        // Disconnect from session so that the updates on updatedRebriqueContrat are not directly saved in db
        em.detach(updatedRebriqueContrat);
        updatedRebriqueContrat
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restRebriqueContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRebriqueContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRebriqueContrat))
            )
            .andExpect(status().isOk());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
        RebriqueContrat testRebriqueContrat = rebriqueContratList.get(rebriqueContratList.size() - 1);
        assertThat(testRebriqueContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testRebriqueContrat.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testRebriqueContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testRebriqueContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRebriqueContrat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRebriqueContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testRebriqueContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRebriqueContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRebriqueContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRebriqueContrat() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();
        rebriqueContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRebriqueContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rebriqueContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRebriqueContrat() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();
        rebriqueContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRebriqueContrat() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();
        rebriqueContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueContratMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRebriqueContratWithPatch() throws Exception {
        // Initialize the database
        rebriqueContratRepository.saveAndFlush(rebriqueContrat);

        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();

        // Update the rebriqueContrat using partial update
        RebriqueContrat partialUpdatedRebriqueContrat = new RebriqueContrat();
        partialUpdatedRebriqueContrat.setId(rebriqueContrat.getId());

        partialUpdatedRebriqueContrat
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restRebriqueContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRebriqueContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRebriqueContrat))
            )
            .andExpect(status().isOk());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
        RebriqueContrat testRebriqueContrat = rebriqueContratList.get(rebriqueContratList.size() - 1);
        assertThat(testRebriqueContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testRebriqueContrat.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testRebriqueContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testRebriqueContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRebriqueContrat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRebriqueContrat.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testRebriqueContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRebriqueContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRebriqueContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRebriqueContratWithPatch() throws Exception {
        // Initialize the database
        rebriqueContratRepository.saveAndFlush(rebriqueContrat);

        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();

        // Update the rebriqueContrat using partial update
        RebriqueContrat partialUpdatedRebriqueContrat = new RebriqueContrat();
        partialUpdatedRebriqueContrat.setId(rebriqueContrat.getId());

        partialUpdatedRebriqueContrat
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restRebriqueContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRebriqueContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRebriqueContrat))
            )
            .andExpect(status().isOk());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
        RebriqueContrat testRebriqueContrat = rebriqueContratList.get(rebriqueContratList.size() - 1);
        assertThat(testRebriqueContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testRebriqueContrat.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testRebriqueContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testRebriqueContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRebriqueContrat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRebriqueContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testRebriqueContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRebriqueContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRebriqueContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRebriqueContrat() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();
        rebriqueContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRebriqueContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rebriqueContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRebriqueContrat() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();
        rebriqueContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRebriqueContrat() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueContratRepository.findAll().size();
        rebriqueContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueContratMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebriqueContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RebriqueContrat in the database
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRebriqueContrat() throws Exception {
        // Initialize the database
        rebriqueContratRepository.saveAndFlush(rebriqueContrat);

        int databaseSizeBeforeDelete = rebriqueContratRepository.findAll().size();

        // Delete the rebriqueContrat
        restRebriqueContratMockMvc
            .perform(delete(ENTITY_API_URL_ID, rebriqueContrat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RebriqueContrat> rebriqueContratList = rebriqueContratRepository.findAll();
        assertThat(rebriqueContratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
