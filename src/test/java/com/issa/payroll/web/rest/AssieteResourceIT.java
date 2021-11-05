package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Assiete;
import com.issa.payroll.repository.AssieteRepository;
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
 * Integration tests for the {@link AssieteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssieteResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB = "AAAAAAAAAA";
    private static final String UPDATED_LIB = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SITUATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SITUATION = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/assietes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssieteRepository assieteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssieteMockMvc;

    private Assiete assiete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assiete createEntity(EntityManager em) {
        Assiete assiete = new Assiete()
            .priorite(DEFAULT_PRIORITE)
            .code(DEFAULT_CODE)
            .lib(DEFAULT_LIB)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .util(DEFAULT_UTIL)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return assiete;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assiete createUpdatedEntity(EntityManager em) {
        Assiete assiete = new Assiete()
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return assiete;
    }

    @BeforeEach
    public void initTest() {
        assiete = createEntity(em);
    }

    @Test
    @Transactional
    void createAssiete() throws Exception {
        int databaseSizeBeforeCreate = assieteRepository.findAll().size();
        // Create the Assiete
        restAssieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assiete)))
            .andExpect(status().isCreated());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeCreate + 1);
        Assiete testAssiete = assieteList.get(assieteList.size() - 1);
        assertThat(testAssiete.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testAssiete.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssiete.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testAssiete.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testAssiete.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testAssiete.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAssiete.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAssiete.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testAssiete.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testAssiete.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAssiete.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAssiete.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createAssieteWithExistingId() throws Exception {
        // Create the Assiete with an existing ID
        assiete.setId(1L);

        int databaseSizeBeforeCreate = assieteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assiete)))
            .andExpect(status().isBadRequest());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssietes() throws Exception {
        // Initialize the database
        assieteRepository.saveAndFlush(assiete);

        // Get all the assieteList
        restAssieteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assiete.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].lib").value(hasItem(DEFAULT_LIB)))
            .andExpect(jsonPath("$.[*].dateSituation").value(hasItem(DEFAULT_DATE_SITUATION.toString())))
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
    void getAssiete() throws Exception {
        // Initialize the database
        assieteRepository.saveAndFlush(assiete);

        // Get the assiete
        restAssieteMockMvc
            .perform(get(ENTITY_API_URL_ID, assiete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assiete.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.lib").value(DEFAULT_LIB))
            .andExpect(jsonPath("$.dateSituation").value(DEFAULT_DATE_SITUATION.toString()))
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
    void getNonExistingAssiete() throws Exception {
        // Get the assiete
        restAssieteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssiete() throws Exception {
        // Initialize the database
        assieteRepository.saveAndFlush(assiete);

        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();

        // Update the assiete
        Assiete updatedAssiete = assieteRepository.findById(assiete.getId()).get();
        // Disconnect from session so that the updates on updatedAssiete are not directly saved in db
        em.detach(updatedAssiete);
        updatedAssiete
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restAssieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssiete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAssiete))
            )
            .andExpect(status().isOk());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
        Assiete testAssiete = assieteList.get(assieteList.size() - 1);
        assertThat(testAssiete.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testAssiete.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssiete.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testAssiete.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testAssiete.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAssiete.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAssiete.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssiete.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAssiete.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAssiete.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAssiete.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAssiete.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAssiete() throws Exception {
        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();
        assiete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assiete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assiete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssiete() throws Exception {
        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();
        assiete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assiete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssiete() throws Exception {
        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();
        assiete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assiete)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssieteWithPatch() throws Exception {
        // Initialize the database
        assieteRepository.saveAndFlush(assiete);

        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();

        // Update the assiete using partial update
        Assiete partialUpdatedAssiete = new Assiete();
        partialUpdatedAssiete.setId(assiete.getId());

        partialUpdatedAssiete.createdBy(UPDATED_CREATED_BY).util(UPDATED_UTIL).op(UPDATED_OP).isDeleted(UPDATED_IS_DELETED);

        restAssieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssiete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssiete))
            )
            .andExpect(status().isOk());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
        Assiete testAssiete = assieteList.get(assieteList.size() - 1);
        assertThat(testAssiete.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testAssiete.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssiete.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testAssiete.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testAssiete.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testAssiete.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAssiete.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssiete.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAssiete.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAssiete.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAssiete.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAssiete.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAssieteWithPatch() throws Exception {
        // Initialize the database
        assieteRepository.saveAndFlush(assiete);

        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();

        // Update the assiete using partial update
        Assiete partialUpdatedAssiete = new Assiete();
        partialUpdatedAssiete.setId(assiete.getId());

        partialUpdatedAssiete
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restAssieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssiete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssiete))
            )
            .andExpect(status().isOk());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
        Assiete testAssiete = assieteList.get(assieteList.size() - 1);
        assertThat(testAssiete.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testAssiete.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssiete.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testAssiete.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testAssiete.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAssiete.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAssiete.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssiete.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAssiete.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAssiete.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAssiete.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAssiete.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAssiete() throws Exception {
        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();
        assiete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assiete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assiete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssiete() throws Exception {
        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();
        assiete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assiete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssiete() throws Exception {
        int databaseSizeBeforeUpdate = assieteRepository.findAll().size();
        assiete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assiete)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assiete in the database
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssiete() throws Exception {
        // Initialize the database
        assieteRepository.saveAndFlush(assiete);

        int databaseSizeBeforeDelete = assieteRepository.findAll().size();

        // Delete the assiete
        restAssieteMockMvc
            .perform(delete(ENTITY_API_URL_ID, assiete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assiete> assieteList = assieteRepository.findAll();
        assertThat(assieteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
