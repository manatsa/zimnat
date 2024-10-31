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
import org.zimnat.domain.LICQuoteResponse;
import org.zimnat.repository.LICQuoteResponseRepository;
import org.zimnat.service.LICQuoteResponseService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICQuoteResponse}.
 */
@RestController
@RequestMapping("/api/lic-quote-responses")
public class LICQuoteResponseResource {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteResponseResource.class);

    private static final String ENTITY_NAME = "lICQuoteResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICQuoteResponseService lICQuoteResponseService;

    private final LICQuoteResponseRepository lICQuoteResponseRepository;

    public LICQuoteResponseResource(
        LICQuoteResponseService lICQuoteResponseService,
        LICQuoteResponseRepository lICQuoteResponseRepository
    ) {
        this.lICQuoteResponseService = lICQuoteResponseService;
        this.lICQuoteResponseRepository = lICQuoteResponseRepository;
    }

    /**
     * {@code POST  /lic-quote-responses} : Create a new lICQuoteResponse.
     *
     * @param lICQuoteResponse the lICQuoteResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICQuoteResponse, or with status {@code 400 (Bad Request)} if the lICQuoteResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICQuoteResponse> createLICQuoteResponse(@RequestBody LICQuoteResponse lICQuoteResponse)
        throws URISyntaxException {
        log.debug("REST request to save LICQuoteResponse : {}", lICQuoteResponse);
        if (lICQuoteResponse.getId() != null) {
            throw new BadRequestAlertException("A new lICQuoteResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICQuoteResponse = lICQuoteResponseService.save(lICQuoteResponse);
        return ResponseEntity.created(new URI("/api/lic-quote-responses/" + lICQuoteResponse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICQuoteResponse.getId().toString()))
            .body(lICQuoteResponse);
    }

    /**
     * {@code PUT  /lic-quote-responses/:id} : Updates an existing lICQuoteResponse.
     *
     * @param id the id of the lICQuoteResponse to save.
     * @param lICQuoteResponse the lICQuoteResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuoteResponse,
     * or with status {@code 400 (Bad Request)} if the lICQuoteResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICQuoteResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICQuoteResponse> updateLICQuoteResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LICQuoteResponse lICQuoteResponse
    ) throws URISyntaxException {
        log.debug("REST request to update LICQuoteResponse : {}, {}", id, lICQuoteResponse);
        if (lICQuoteResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuoteResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICQuoteResponse = lICQuoteResponseService.update(lICQuoteResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuoteResponse.getId().toString()))
            .body(lICQuoteResponse);
    }

    /**
     * {@code PATCH  /lic-quote-responses/:id} : Partial updates given fields of an existing lICQuoteResponse, field will ignore if it is null
     *
     * @param id the id of the lICQuoteResponse to save.
     * @param lICQuoteResponse the lICQuoteResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuoteResponse,
     * or with status {@code 400 (Bad Request)} if the lICQuoteResponse is not valid,
     * or with status {@code 404 (Not Found)} if the lICQuoteResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICQuoteResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICQuoteResponse> partialUpdateLICQuoteResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LICQuoteResponse lICQuoteResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICQuoteResponse partially : {}, {}", id, lICQuoteResponse);
        if (lICQuoteResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuoteResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICQuoteResponse> result = lICQuoteResponseService.partialUpdate(lICQuoteResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuoteResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-quote-responses} : get all the lICQuoteResponses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICQuoteResponses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICQuoteResponse>> getAllLICQuoteResponses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LICQuoteResponses");
        Page<LICQuoteResponse> page = lICQuoteResponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-quote-responses/:id} : get the "id" lICQuoteResponse.
     *
     * @param id the id of the lICQuoteResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICQuoteResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICQuoteResponse> getLICQuoteResponse(@PathVariable("id") Long id) {
        log.debug("REST request to get LICQuoteResponse : {}", id);
        Optional<LICQuoteResponse> lICQuoteResponse = lICQuoteResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICQuoteResponse);
    }

    /**
     * {@code DELETE  /lic-quote-responses/:id} : delete the "id" lICQuoteResponse.
     *
     * @param id the id of the lICQuoteResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICQuoteResponse(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICQuoteResponse : {}", id);
        lICQuoteResponseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
