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
import org.zimnat.domain.LICResultResponse;
import org.zimnat.repository.LICResultResponseRepository;
import org.zimnat.service.LICResultResponseService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICResultResponse}.
 */
@RestController
@RequestMapping("/api/lic-result-responses")
public class LICResultResponseResource {

    private static final Logger log = LoggerFactory.getLogger(LICResultResponseResource.class);

    private static final String ENTITY_NAME = "lICResultResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICResultResponseService lICResultResponseService;

    private final LICResultResponseRepository lICResultResponseRepository;

    public LICResultResponseResource(
        LICResultResponseService lICResultResponseService,
        LICResultResponseRepository lICResultResponseRepository
    ) {
        this.lICResultResponseService = lICResultResponseService;
        this.lICResultResponseRepository = lICResultResponseRepository;
    }

    /**
     * {@code POST  /lic-result-responses} : Create a new lICResultResponse.
     *
     * @param lICResultResponse the lICResultResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICResultResponse, or with status {@code 400 (Bad Request)} if the lICResultResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICResultResponse> createLICResultResponse(@RequestBody LICResultResponse lICResultResponse)
        throws URISyntaxException {
        log.debug("REST request to save LICResultResponse : {}", lICResultResponse);
        if (lICResultResponse.getId() != null) {
            throw new BadRequestAlertException("A new lICResultResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICResultResponse = lICResultResponseService.save(lICResultResponse);
        return ResponseEntity.created(new URI("/api/lic-result-responses/" + lICResultResponse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICResultResponse.getId().toString()))
            .body(lICResultResponse);
    }

    /**
     * {@code PUT  /lic-result-responses/:id} : Updates an existing lICResultResponse.
     *
     * @param id the id of the lICResultResponse to save.
     * @param lICResultResponse the lICResultResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICResultResponse,
     * or with status {@code 400 (Bad Request)} if the lICResultResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICResultResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICResultResponse> updateLICResultResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LICResultResponse lICResultResponse
    ) throws URISyntaxException {
        log.debug("REST request to update LICResultResponse : {}, {}", id, lICResultResponse);
        if (lICResultResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICResultResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICResultResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICResultResponse = lICResultResponseService.update(lICResultResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICResultResponse.getId().toString()))
            .body(lICResultResponse);
    }

    /**
     * {@code PATCH  /lic-result-responses/:id} : Partial updates given fields of an existing lICResultResponse, field will ignore if it is null
     *
     * @param id the id of the lICResultResponse to save.
     * @param lICResultResponse the lICResultResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICResultResponse,
     * or with status {@code 400 (Bad Request)} if the lICResultResponse is not valid,
     * or with status {@code 404 (Not Found)} if the lICResultResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICResultResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICResultResponse> partialUpdateLICResultResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LICResultResponse lICResultResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICResultResponse partially : {}, {}", id, lICResultResponse);
        if (lICResultResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICResultResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICResultResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICResultResponse> result = lICResultResponseService.partialUpdate(lICResultResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICResultResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-result-responses} : get all the lICResultResponses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICResultResponses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICResultResponse>> getAllLICResultResponses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LICResultResponses");
        Page<LICResultResponse> page = lICResultResponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-result-responses/:id} : get the "id" lICResultResponse.
     *
     * @param id the id of the lICResultResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICResultResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICResultResponse> getLICResultResponse(@PathVariable("id") Long id) {
        log.debug("REST request to get LICResultResponse : {}", id);
        Optional<LICResultResponse> lICResultResponse = lICResultResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICResultResponse);
    }

    /**
     * {@code DELETE  /lic-result-responses/:id} : delete the "id" lICResultResponse.
     *
     * @param id the id of the lICResultResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICResultResponse(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICResultResponse : {}", id);
        lICResultResponseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
