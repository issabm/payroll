package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.MatricePaieEmp;
import com.issa.payroll.repository.MatricePaieEmpRepository;
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
 * Integration tests for the {@link MatricePaieEmpResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MatricePaieEmpResourceIT {

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

    private static final String ENTITY_API_URL = "/api/matrice-paie-emps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatricePaieEmpRepository matricePaieEmpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatricePaieEmpMockMvc;

    private MatricePaieEmp matricePaieEmp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatricePaieEmp createEntity(EntityManager em) {
        MatricePaieEmp matricePaieEmp = new MatricePaieEmp()
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return matricePaieEmp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatricePaieEmp createUpdatedEntity(EntityManager em) {
        MatricePaieEmp matricePaieEmp = new MatricePaieEmp()
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return matricePaieEmp;
    }

    @BeforeEach
    public void initTest() {
        matricePaieEmp = createEntity(em);
    }

    @Test
    @Transactional
    void createMatricePaieEmp() throws Exception {
        int databaseSizeBeforeCreate = matricePaieEmpRepository.findAll().size();
        // Create the MatricePaieEmp
        restMatricePaieEmpMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isCreated());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeCreate + 1);
        MatricePaieEmp testMatricePaieEmp = matricePaieEmpList.get(matricePaieEmpList.size() - 1);
        assertThat(testMatricePaieEmp.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testMatricePaieEmp.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testMatricePaieEmp.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMatricePaieEmp.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testMatricePaieEmp.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMatricePaieEmp.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMatricePaieEmp.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createMatricePaieEmpWithExistingId() throws Exception {
        // Create the MatricePaieEmp with an existing ID
        matricePaieEmp.setId(1L);

        int databaseSizeBeforeCreate = matricePaieEmpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatricePaieEmpMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatricePaieEmps() throws Exception {
        // Initialize the database
        matricePaieEmpRepository.saveAndFlush(matricePaieEmp);

        // Get all the matricePaieEmpList
        restMatricePaieEmpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricePaieEmp.getId().intValue())))
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
    void getMatricePaieEmp() throws Exception {
        // Initialize the database
        matricePaieEmpRepository.saveAndFlush(matricePaieEmp);

        // Get the matricePaieEmp
        restMatricePaieEmpMockMvc
            .perform(get(ENTITY_API_URL_ID, matricePaieEmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matricePaieEmp.getId().intValue()))
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
    void getNonExistingMatricePaieEmp() throws Exception {
        // Get the matricePaieEmp
        restMatricePaieEmpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMatricePaieEmp() throws Exception {
        // Initialize the database
        matricePaieEmpRepository.saveAndFlush(matricePaieEmp);

        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();

        // Update the matricePaieEmp
        MatricePaieEmp updatedMatricePaieEmp = matricePaieEmpRepository.findById(matricePaieEmp.getId()).get();
        // Disconnect from session so that the updates on updatedMatricePaieEmp are not directly saved in db
        em.detach(updatedMatricePaieEmp);
        updatedMatricePaieEmp
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restMatricePaieEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMatricePaieEmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMatricePaieEmp))
            )
            .andExpect(status().isOk());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
        MatricePaieEmp testMatricePaieEmp = matricePaieEmpList.get(matricePaieEmpList.size() - 1);
        assertThat(testMatricePaieEmp.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMatricePaieEmp.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMatricePaieEmp.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMatricePaieEmp.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMatricePaieEmp.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMatricePaieEmp.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMatricePaieEmp.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMatricePaieEmp() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();
        matricePaieEmp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatricePaieEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matricePaieEmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatricePaieEmp() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();
        matricePaieEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatricePaieEmp() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();
        matricePaieEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieEmpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matricePaieEmp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatricePaieEmpWithPatch() throws Exception {
        // Initialize the database
        matricePaieEmpRepository.saveAndFlush(matricePaieEmp);

        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();

        // Update the matricePaieEmp using partial update
        MatricePaieEmp partialUpdatedMatricePaieEmp = new MatricePaieEmp();
        partialUpdatedMatricePaieEmp.setId(matricePaieEmp.getId());

        partialUpdatedMatricePaieEmp.op(UPDATED_OP).isDeleted(UPDATED_IS_DELETED);

        restMatricePaieEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatricePaieEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatricePaieEmp))
            )
            .andExpect(status().isOk());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
        MatricePaieEmp testMatricePaieEmp = matricePaieEmpList.get(matricePaieEmpList.size() - 1);
        assertThat(testMatricePaieEmp.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testMatricePaieEmp.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testMatricePaieEmp.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMatricePaieEmp.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMatricePaieEmp.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMatricePaieEmp.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMatricePaieEmp.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMatricePaieEmpWithPatch() throws Exception {
        // Initialize the database
        matricePaieEmpRepository.saveAndFlush(matricePaieEmp);

        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();

        // Update the matricePaieEmp using partial update
        MatricePaieEmp partialUpdatedMatricePaieEmp = new MatricePaieEmp();
        partialUpdatedMatricePaieEmp.setId(matricePaieEmp.getId());

        partialUpdatedMatricePaieEmp
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restMatricePaieEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatricePaieEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatricePaieEmp))
            )
            .andExpect(status().isOk());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
        MatricePaieEmp testMatricePaieEmp = matricePaieEmpList.get(matricePaieEmpList.size() - 1);
        assertThat(testMatricePaieEmp.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMatricePaieEmp.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMatricePaieEmp.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMatricePaieEmp.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMatricePaieEmp.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMatricePaieEmp.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMatricePaieEmp.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMatricePaieEmp() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();
        matricePaieEmp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatricePaieEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matricePaieEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatricePaieEmp() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();
        matricePaieEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatricePaieEmp() throws Exception {
        int databaseSizeBeforeUpdate = matricePaieEmpRepository.findAll().size();
        matricePaieEmp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatricePaieEmpMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(matricePaieEmp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatricePaieEmp in the database
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatricePaieEmp() throws Exception {
        // Initialize the database
        matricePaieEmpRepository.saveAndFlush(matricePaieEmp);

        int databaseSizeBeforeDelete = matricePaieEmpRepository.findAll().size();

        // Delete the matricePaieEmp
        restMatricePaieEmpMockMvc
            .perform(delete(ENTITY_API_URL_ID, matricePaieEmp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatricePaieEmp> matricePaieEmpList = matricePaieEmpRepository.findAll();
        assertThat(matricePaieEmpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
