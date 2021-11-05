package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.AssieteInfo;
import com.issa.payroll.repository.AssieteInfoRepository;
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
 * Integration tests for the {@link AssieteInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssieteInfoResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final Double DEFAULT_VAL = 1D;
    private static final Double UPDATED_VAL = 2D;

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

    private static final String ENTITY_API_URL = "/api/assiete-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssieteInfoRepository assieteInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssieteInfoMockMvc;

    private AssieteInfo assieteInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssieteInfo createEntity(EntityManager em) {
        AssieteInfo assieteInfo = new AssieteInfo()
            .priorite(DEFAULT_PRIORITE)
            .val(DEFAULT_VAL)
            .util(DEFAULT_UTIL)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return assieteInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssieteInfo createUpdatedEntity(EntityManager em) {
        AssieteInfo assieteInfo = new AssieteInfo()
            .priorite(UPDATED_PRIORITE)
            .val(UPDATED_VAL)
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return assieteInfo;
    }

    @BeforeEach
    public void initTest() {
        assieteInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createAssieteInfo() throws Exception {
        int databaseSizeBeforeCreate = assieteInfoRepository.findAll().size();
        // Create the AssieteInfo
        restAssieteInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assieteInfo)))
            .andExpect(status().isCreated());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AssieteInfo testAssieteInfo = assieteInfoList.get(assieteInfoList.size() - 1);
        assertThat(testAssieteInfo.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testAssieteInfo.getVal()).isEqualTo(DEFAULT_VAL);
        assertThat(testAssieteInfo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testAssieteInfo.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testAssieteInfo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testAssieteInfo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAssieteInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAssieteInfo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testAssieteInfo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAssieteInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAssieteInfo.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createAssieteInfoWithExistingId() throws Exception {
        // Create the AssieteInfo with an existing ID
        assieteInfo.setId(1L);

        int databaseSizeBeforeCreate = assieteInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssieteInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assieteInfo)))
            .andExpect(status().isBadRequest());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssieteInfos() throws Exception {
        // Initialize the database
        assieteInfoRepository.saveAndFlush(assieteInfo);

        // Get all the assieteInfoList
        restAssieteInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assieteInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].val").value(hasItem(DEFAULT_VAL.doubleValue())))
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
    void getAssieteInfo() throws Exception {
        // Initialize the database
        assieteInfoRepository.saveAndFlush(assieteInfo);

        // Get the assieteInfo
        restAssieteInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, assieteInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assieteInfo.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.val").value(DEFAULT_VAL.doubleValue()))
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
    void getNonExistingAssieteInfo() throws Exception {
        // Get the assieteInfo
        restAssieteInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssieteInfo() throws Exception {
        // Initialize the database
        assieteInfoRepository.saveAndFlush(assieteInfo);

        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();

        // Update the assieteInfo
        AssieteInfo updatedAssieteInfo = assieteInfoRepository.findById(assieteInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAssieteInfo are not directly saved in db
        em.detach(updatedAssieteInfo);
        updatedAssieteInfo
            .priorite(UPDATED_PRIORITE)
            .val(UPDATED_VAL)
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restAssieteInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssieteInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAssieteInfo))
            )
            .andExpect(status().isOk());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
        AssieteInfo testAssieteInfo = assieteInfoList.get(assieteInfoList.size() - 1);
        assertThat(testAssieteInfo.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testAssieteInfo.getVal()).isEqualTo(UPDATED_VAL);
        assertThat(testAssieteInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAssieteInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testAssieteInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAssieteInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAssieteInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssieteInfo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAssieteInfo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAssieteInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAssieteInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAssieteInfo() throws Exception {
        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();
        assieteInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssieteInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assieteInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assieteInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssieteInfo() throws Exception {
        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();
        assieteInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assieteInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssieteInfo() throws Exception {
        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();
        assieteInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assieteInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssieteInfoWithPatch() throws Exception {
        // Initialize the database
        assieteInfoRepository.saveAndFlush(assieteInfo);

        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();

        // Update the assieteInfo using partial update
        AssieteInfo partialUpdatedAssieteInfo = new AssieteInfo();
        partialUpdatedAssieteInfo.setId(assieteInfo.getId());

        partialUpdatedAssieteInfo
            .val(UPDATED_VAL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restAssieteInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssieteInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssieteInfo))
            )
            .andExpect(status().isOk());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
        AssieteInfo testAssieteInfo = assieteInfoList.get(assieteInfoList.size() - 1);
        assertThat(testAssieteInfo.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testAssieteInfo.getVal()).isEqualTo(UPDATED_VAL);
        assertThat(testAssieteInfo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testAssieteInfo.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testAssieteInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAssieteInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAssieteInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssieteInfo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testAssieteInfo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAssieteInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAssieteInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAssieteInfoWithPatch() throws Exception {
        // Initialize the database
        assieteInfoRepository.saveAndFlush(assieteInfo);

        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();

        // Update the assieteInfo using partial update
        AssieteInfo partialUpdatedAssieteInfo = new AssieteInfo();
        partialUpdatedAssieteInfo.setId(assieteInfo.getId());

        partialUpdatedAssieteInfo
            .priorite(UPDATED_PRIORITE)
            .val(UPDATED_VAL)
            .util(UPDATED_UTIL)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restAssieteInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssieteInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssieteInfo))
            )
            .andExpect(status().isOk());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
        AssieteInfo testAssieteInfo = assieteInfoList.get(assieteInfoList.size() - 1);
        assertThat(testAssieteInfo.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testAssieteInfo.getVal()).isEqualTo(UPDATED_VAL);
        assertThat(testAssieteInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAssieteInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testAssieteInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAssieteInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAssieteInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssieteInfo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAssieteInfo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAssieteInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAssieteInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAssieteInfo() throws Exception {
        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();
        assieteInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssieteInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assieteInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assieteInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssieteInfo() throws Exception {
        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();
        assieteInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assieteInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssieteInfo() throws Exception {
        int databaseSizeBeforeUpdate = assieteInfoRepository.findAll().size();
        assieteInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssieteInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assieteInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssieteInfo in the database
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssieteInfo() throws Exception {
        // Initialize the database
        assieteInfoRepository.saveAndFlush(assieteInfo);

        int databaseSizeBeforeDelete = assieteInfoRepository.findAll().size();

        // Delete the assieteInfo
        restAssieteInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, assieteInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssieteInfo> assieteInfoList = assieteInfoRepository.findAll();
        assertThat(assieteInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
