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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zimnat.domain.BatchStatusRequest;
import org.zimnat.repository.BatchStatusRequestRepository;
import org.zimnat.service.BatchStatusRequestService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.BatchStatusRequest}.
 */
@RestController
@RequestMapping("/api/batch-status-requests")
public class BatchStatusRequestResource {

    private static final Logger log = LoggerFactory.getLogger(BatchStatusRequestResource.class);

    private static final String ENTITY_NAME = "batchStatusRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchStatusRequestService batchStatusRequestService;

    private final BatchStatusRequestRepository batchStatusRequestRepository;

    public BatchStatusRequestResource(
        BatchStatusRequestService batchStatusRequestService,
        BatchStatusRequestRepository batchStatusRequestRepository
    ) {
        this.batchStatusRequestService = batchStatusRequestService;
        this.batchStatusRequestRepository = batchStatusRequestRepository;
    }

    /**
     * {@code POST  /batch-status-requests} : Create a new batchStatusRequest.
     *
     * @param batchStatusRequest the batchStatusRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchStatusRequest, or with status {@code 400 (Bad Request)} if the batchStatusRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BatchStatusRequest> createBatchStatusRequest(@Valid @RequestBody BatchStatusRequest batchStatusRequest)
        throws URISyntaxException {
        log.debug("REST request to save BatchStatusRequest : {}", batchStatusRequest);
        if (batchStatusRequest.getId() != null) {
            throw new BadRequestAlertException("A new batchStatusRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        batchStatusRequest = batchStatusRequestService.save(batchStatusRequest);
        return ResponseEntity.created(new URI("/api/batch-status-requests/" + batchStatusRequest.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, batchStatusRequest.getId().toString()))
            .body(batchStatusRequest);
    }

    /**
     * {@code PUT  /batch-status-requests/:id} : Updates an existing batchStatusRequest.
     *
     * @param id the id of the batchStatusRequest to save.
     * @param batchStatusRequest the batchStatusRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchStatusRequest,
     * or with status {@code 400 (Bad Request)} if the batchStatusRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchStatusRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BatchStatusRequest> updateBatchStatusRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BatchStatusRequest batchStatusRequest
    ) throws URISyntaxException {
        log.debug("REST request to update BatchStatusRequest : {}, {}", id, batchStatusRequest);
        if (batchStatusRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchStatusRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchStatusRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        batchStatusRequest = batchStatusRequestService.update(batchStatusRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batchStatusRequest.getId().toString()))
            .body(batchStatusRequest);
    }

    /**
     * {@code PATCH  /batch-status-requests/:id} : Partial updates given fields of an existing batchStatusRequest, field will ignore if it is null
     *
     * @param id the id of the batchStatusRequest to save.
     * @param batchStatusRequest the batchStatusRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchStatusRequest,
     * or with status {@code 400 (Bad Request)} if the batchStatusRequest is not valid,
     * or with status {@code 404 (Not Found)} if the batchStatusRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the batchStatusRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BatchStatusRequest> partialUpdateBatchStatusRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BatchStatusRequest batchStatusRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update BatchStatusRequest partially : {}, {}", id, batchStatusRequest);
        if (batchStatusRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchStatusRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchStatusRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BatchStatusRequest> result = batchStatusRequestService.partialUpdate(batchStatusRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batchStatusRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /batch-status-requests} : get all the batchStatusRequests.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchStatusRequests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BatchStatusRequest>> getAllBatchStatusRequests(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("batchstatus-is-null".equals(filter)) {
            log.debug("REST request to get all BatchStatusRequests where batchStatus is null");
            return new ResponseEntity<>(batchStatusRequestService.findAllWhereBatchStatusIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of BatchStatusRequests");
        Page<BatchStatusRequest> page = batchStatusRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /batch-status-requests/:id} : get the "id" batchStatusRequest.
     *
     * @param id the id of the batchStatusRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchStatusRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BatchStatusRequest> getBatchStatusRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get BatchStatusRequest : {}", id);
        Optional<BatchStatusRequest> batchStatusRequest = batchStatusRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batchStatusRequest);
    }

    /**
     * {@code DELETE  /batch-status-requests/:id} : delete the "id" batchStatusRequest.
     *
     * @param id the id of the batchStatusRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatchStatusRequest(@PathVariable("id") Long id) {
        log.debug("REST request to delete BatchStatusRequest : {}", id);
        batchStatusRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
