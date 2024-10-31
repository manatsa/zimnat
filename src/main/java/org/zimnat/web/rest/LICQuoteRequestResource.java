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
import org.zimnat.domain.LICQuoteRequest;
import org.zimnat.repository.LICQuoteRequestRepository;
import org.zimnat.service.LICQuoteRequestService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICQuoteRequest}.
 */
@RestController
@RequestMapping("/api/lic-quote-requests")
public class LICQuoteRequestResource {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteRequestResource.class);

    private static final String ENTITY_NAME = "lICQuoteRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICQuoteRequestService lICQuoteRequestService;

    private final LICQuoteRequestRepository lICQuoteRequestRepository;

    public LICQuoteRequestResource(LICQuoteRequestService lICQuoteRequestService, LICQuoteRequestRepository lICQuoteRequestRepository) {
        this.lICQuoteRequestService = lICQuoteRequestService;
        this.lICQuoteRequestRepository = lICQuoteRequestRepository;
    }

    /**
     * {@code POST  /lic-quote-requests} : Create a new lICQuoteRequest.
     *
     * @param lICQuoteRequest the lICQuoteRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICQuoteRequest, or with status {@code 400 (Bad Request)} if the lICQuoteRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICQuoteRequest> createLICQuoteRequest(@Valid @RequestBody LICQuoteRequest lICQuoteRequest)
        throws URISyntaxException {
        log.debug("REST request to save LICQuoteRequest : {}", lICQuoteRequest);
        if (lICQuoteRequest.getId() != null) {
            throw new BadRequestAlertException("A new lICQuoteRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICQuoteRequest = lICQuoteRequestService.save(lICQuoteRequest);
        return ResponseEntity.created(new URI("/api/lic-quote-requests/" + lICQuoteRequest.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICQuoteRequest.getId().toString()))
            .body(lICQuoteRequest);
    }

    /**
     * {@code PUT  /lic-quote-requests/:id} : Updates an existing lICQuoteRequest.
     *
     * @param id the id of the lICQuoteRequest to save.
     * @param lICQuoteRequest the lICQuoteRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuoteRequest,
     * or with status {@code 400 (Bad Request)} if the lICQuoteRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICQuoteRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICQuoteRequest> updateLICQuoteRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LICQuoteRequest lICQuoteRequest
    ) throws URISyntaxException {
        log.debug("REST request to update LICQuoteRequest : {}, {}", id, lICQuoteRequest);
        if (lICQuoteRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuoteRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICQuoteRequest = lICQuoteRequestService.update(lICQuoteRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuoteRequest.getId().toString()))
            .body(lICQuoteRequest);
    }

    /**
     * {@code PATCH  /lic-quote-requests/:id} : Partial updates given fields of an existing lICQuoteRequest, field will ignore if it is null
     *
     * @param id the id of the lICQuoteRequest to save.
     * @param lICQuoteRequest the lICQuoteRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuoteRequest,
     * or with status {@code 400 (Bad Request)} if the lICQuoteRequest is not valid,
     * or with status {@code 404 (Not Found)} if the lICQuoteRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICQuoteRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICQuoteRequest> partialUpdateLICQuoteRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LICQuoteRequest lICQuoteRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICQuoteRequest partially : {}, {}", id, lICQuoteRequest);
        if (lICQuoteRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuoteRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICQuoteRequest> result = lICQuoteRequestService.partialUpdate(lICQuoteRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuoteRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-quote-requests} : get all the lICQuoteRequests.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICQuoteRequests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICQuoteRequest>> getAllLICQuoteRequests(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("licquote-is-null".equals(filter)) {
            log.debug("REST request to get all LICQuoteRequests where lICQuote is null");
            return new ResponseEntity<>(lICQuoteRequestService.findAllWhereLICQuoteIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of LICQuoteRequests");
        Page<LICQuoteRequest> page = lICQuoteRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-quote-requests/:id} : get the "id" lICQuoteRequest.
     *
     * @param id the id of the lICQuoteRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICQuoteRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICQuoteRequest> getLICQuoteRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get LICQuoteRequest : {}", id);
        Optional<LICQuoteRequest> lICQuoteRequest = lICQuoteRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICQuoteRequest);
    }

    /**
     * {@code DELETE  /lic-quote-requests/:id} : delete the "id" lICQuoteRequest.
     *
     * @param id the id of the lICQuoteRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICQuoteRequest(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICQuoteRequest : {}", id);
        lICQuoteRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
