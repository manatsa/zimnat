package org.zimnat.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zimnat.domain.BatchStatus;
import org.zimnat.repository.BatchStatusRepository;
import org.zimnat.service.BatchStatusService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.BatchStatus}.
 */
@RestController
@RequestMapping("/api/batch-statuses")
public class BatchStatusResource {

    private static final Logger log = LoggerFactory.getLogger(BatchStatusResource.class);

    private static final String ENTITY_NAME = "batchStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchStatusService batchStatusService;

    private final BatchStatusRepository batchStatusRepository;

    public BatchStatusResource(BatchStatusService batchStatusService, BatchStatusRepository batchStatusRepository) {
        this.batchStatusService = batchStatusService;
        this.batchStatusRepository = batchStatusRepository;
    }

    /**
     * {@code POST  /batch-statuses} : Create a new batchStatus.
     *
     * @param batchStatus the batchStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchStatus, or with status {@code 400 (Bad Request)} if the batchStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BatchStatus> createBatchStatus(@Valid @RequestBody BatchStatus batchStatus) throws URISyntaxException {
        log.debug("REST request to save BatchStatus : {}", batchStatus);
        if (batchStatus.getId() != null) {
            throw new BadRequestAlertException("A new batchStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        batchStatus = batchStatusService.save(batchStatus);
        return ResponseEntity.created(new URI("/api/batch-statuses/" + batchStatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, batchStatus.getId().toString()))
            .body(batchStatus);
    }

    /**
     * {@code PUT  /batch-statuses/:id} : Updates an existing batchStatus.
     *
     * @param id the id of the batchStatus to save.
     * @param batchStatus the batchStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchStatus,
     * or with status {@code 400 (Bad Request)} if the batchStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BatchStatus> updateBatchStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BatchStatus batchStatus
    ) throws URISyntaxException {
        log.debug("REST request to update BatchStatus : {}, {}", id, batchStatus);
        if (batchStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        batchStatus = batchStatusService.update(batchStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batchStatus.getId().toString()))
            .body(batchStatus);
    }

    /**
     * {@code PATCH  /batch-statuses/:id} : Partial updates given fields of an existing batchStatus, field will ignore if it is null
     *
     * @param id the id of the batchStatus to save.
     * @param batchStatus the batchStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchStatus,
     * or with status {@code 400 (Bad Request)} if the batchStatus is not valid,
     * or with status {@code 404 (Not Found)} if the batchStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the batchStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BatchStatus> partialUpdateBatchStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BatchStatus batchStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update BatchStatus partially : {}, {}", id, batchStatus);
        if (batchStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BatchStatus> result = batchStatusService.partialUpdate(batchStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batchStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /batch-statuses} : get all the batchStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchStatuses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BatchStatus>> getAllBatchStatuses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BatchStatuses");
        Page<BatchStatus> page = batchStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /batch-statuses/:id} : get the "id" batchStatus.
     *
     * @param id the id of the batchStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BatchStatus> getBatchStatus(@PathVariable("id") Long id) {
        log.debug("REST request to get BatchStatus : {}", id);
        Optional<BatchStatus> batchStatus = batchStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batchStatus);
    }

    /**
     * {@code DELETE  /batch-statuses/:id} : delete the "id" batchStatus.
     *
     * @param id the id of the batchStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatchStatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete BatchStatus : {}", id);
        batchStatusService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
