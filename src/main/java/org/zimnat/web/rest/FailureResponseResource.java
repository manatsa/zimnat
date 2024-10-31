package org.zimnat.web.rest;

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
import org.zimnat.domain.FailureResponse;
import org.zimnat.repository.FailureResponseRepository;
import org.zimnat.service.FailureResponseService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.FailureResponse}.
 */
@RestController
@RequestMapping("/api/failure-responses")
public class FailureResponseResource {

    private static final Logger log = LoggerFactory.getLogger(FailureResponseResource.class);

    private static final String ENTITY_NAME = "failureResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FailureResponseService failureResponseService;

    private final FailureResponseRepository failureResponseRepository;

    public FailureResponseResource(FailureResponseService failureResponseService, FailureResponseRepository failureResponseRepository) {
        this.failureResponseService = failureResponseService;
        this.failureResponseRepository = failureResponseRepository;
    }

    /**
     * {@code POST  /failure-responses} : Create a new failureResponse.
     *
     * @param failureResponse the failureResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new failureResponse, or with status {@code 400 (Bad Request)} if the failureResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FailureResponse> createFailureResponse(@RequestBody FailureResponse failureResponse) throws URISyntaxException {
        log.debug("REST request to save FailureResponse : {}", failureResponse);
        if (failureResponse.getId() != null) {
            throw new BadRequestAlertException("A new failureResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        failureResponse = failureResponseService.save(failureResponse);
        return ResponseEntity.created(new URI("/api/failure-responses/" + failureResponse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, failureResponse.getId().toString()))
            .body(failureResponse);
    }

    /**
     * {@code PUT  /failure-responses/:id} : Updates an existing failureResponse.
     *
     * @param id the id of the failureResponse to save.
     * @param failureResponse the failureResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated failureResponse,
     * or with status {@code 400 (Bad Request)} if the failureResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the failureResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FailureResponse> updateFailureResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FailureResponse failureResponse
    ) throws URISyntaxException {
        log.debug("REST request to update FailureResponse : {}, {}", id, failureResponse);
        if (failureResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, failureResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!failureResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        failureResponse = failureResponseService.update(failureResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, failureResponse.getId().toString()))
            .body(failureResponse);
    }

    /**
     * {@code PATCH  /failure-responses/:id} : Partial updates given fields of an existing failureResponse, field will ignore if it is null
     *
     * @param id the id of the failureResponse to save.
     * @param failureResponse the failureResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated failureResponse,
     * or with status {@code 400 (Bad Request)} if the failureResponse is not valid,
     * or with status {@code 404 (Not Found)} if the failureResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the failureResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FailureResponse> partialUpdateFailureResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FailureResponse failureResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update FailureResponse partially : {}, {}", id, failureResponse);
        if (failureResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, failureResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!failureResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FailureResponse> result = failureResponseService.partialUpdate(failureResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, failureResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /failure-responses} : get all the failureResponses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of failureResponses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FailureResponse>> getAllFailureResponses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FailureResponses");
        Page<FailureResponse> page = failureResponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /failure-responses/:id} : get the "id" failureResponse.
     *
     * @param id the id of the failureResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the failureResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FailureResponse> getFailureResponse(@PathVariable("id") Long id) {
        log.debug("REST request to get FailureResponse : {}", id);
        Optional<FailureResponse> failureResponse = failureResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(failureResponse);
    }

    /**
     * {@code DELETE  /failure-responses/:id} : delete the "id" failureResponse.
     *
     * @param id the id of the failureResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFailureResponse(@PathVariable("id") Long id) {
        log.debug("REST request to delete FailureResponse : {}", id);
        failureResponseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
