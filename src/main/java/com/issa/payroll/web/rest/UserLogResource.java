package com.issa.payroll.web.rest;

import com.issa.payroll.domain.UserLog;
import com.issa.payroll.repository.UserLogRepository;
import com.issa.payroll.service.UserLogService;
import com.issa.payroll.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.issa.payroll.domain.UserLog}.
 */
@RestController
@RequestMapping("/api")
public class UserLogResource {

    private final Logger log = LoggerFactory.getLogger(UserLogResource.class);

    private static final String ENTITY_NAME = "userLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserLogService userLogService;

    private final UserLogRepository userLogRepository;

    public UserLogResource(UserLogService userLogService, UserLogRepository userLogRepository) {
        this.userLogService = userLogService;
        this.userLogRepository = userLogRepository;
    }

    /**
     * {@code POST  /user-logs} : Create a new userLog.
     *
     * @param userLog the userLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userLog, or with status {@code 400 (Bad Request)} if the userLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-logs")
    public ResponseEntity<UserLog> createUserLog(@RequestBody UserLog userLog) throws URISyntaxException {
        log.debug("REST request to save UserLog : {}", userLog);
        if (userLog.getId() != null) {
            throw new BadRequestAlertException("A new userLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserLog result = userLogService.save(userLog);
        return ResponseEntity
            .created(new URI("/api/user-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-logs/:id} : Updates an existing userLog.
     *
     * @param id the id of the userLog to save.
     * @param userLog the userLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userLog,
     * or with status {@code 400 (Bad Request)} if the userLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-logs/{id}")
    public ResponseEntity<UserLog> updateUserLog(@PathVariable(value = "id", required = false) final Long id, @RequestBody UserLog userLog)
        throws URISyntaxException {
        log.debug("REST request to update UserLog : {}, {}", id, userLog);
        if (userLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserLog result = userLogService.save(userLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-logs/:id} : Partial updates given fields of an existing userLog, field will ignore if it is null
     *
     * @param id the id of the userLog to save.
     * @param userLog the userLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userLog,
     * or with status {@code 400 (Bad Request)} if the userLog is not valid,
     * or with status {@code 404 (Not Found)} if the userLog is not found,
     * or with status {@code 500 (Internal Server Error)} if the userLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserLog> partialUpdateUserLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserLog userLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserLog partially : {}, {}", id, userLog);
        if (userLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserLog> result = userLogService.partialUpdate(userLog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userLog.getId().toString())
        );
    }

    /**
     * {@code GET  /user-logs} : get all the userLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userLogs in body.
     */
    @GetMapping("/user-logs")
    public ResponseEntity<List<UserLog>> getAllUserLogs(Pageable pageable) {
        log.debug("REST request to get a page of UserLogs");
        Page<UserLog> page = userLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-logs/:id} : get the "id" userLog.
     *
     * @param id the id of the userLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-logs/{id}")
    public ResponseEntity<UserLog> getUserLog(@PathVariable Long id) {
        log.debug("REST request to get UserLog : {}", id);
        Optional<UserLog> userLog = userLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userLog);
    }

    /**
     * {@code DELETE  /user-logs/:id} : delete the "id" userLog.
     *
     * @param id the id of the userLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-logs/{id}")
    public ResponseEntity<Void> deleteUserLog(@PathVariable Long id) {
        log.debug("REST request to delete UserLog : {}", id);
        userLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
