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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zimnat.domain.LICConfirmationResponse;
import org.zimnat.repository.LICConfirmationResponseRepository;
import org.zimnat.service.LICConfirmationResponseService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICConfirmationResponse}.
 */
@RestController
@RequestMapping("/api/lic-confirmation-responses")
public class LICConfirmationResponseResource {

    private static final Logger log = LoggerFactory.getLogger(LICConfirmationResponseResource.class);

    private static final String ENTITY_NAME = "lICConfirmationResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICConfirmationResponseService lICConfirmationResponseService;

    private final LICConfirmationResponseRepository lICConfirmationResponseRepository;

    public LICConfirmationResponseResource(
        LICConfirmationResponseService lICConfirmationResponseService,
        LICConfirmationResponseRepository lICConfirmationResponseRepository
    ) {
        this.lICConfirmationResponseService = lICConfirmationResponseService;
        this.lICConfirmationResponseRepository = lICConfirmationResponseRepository;
    }

    /**
     * {@code POST  /lic-confirmation-responses} : Create a new lICConfirmationResponse.
     *
     * @param lICConfirmationResponse the lICConfirmationResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICConfirmationResponse, or with status {@code 400 (Bad Request)} if the lICConfirmationResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICConfirmationResponse> createLICConfirmationResponse(
        @RequestBody LICConfirmationResponse lICConfirmationResponse
    ) throws URISyntaxException {
        log.debug("REST request to save LICConfirmationResponse : {}", lICConfirmationResponse);
        if (lICConfirmationResponse.getId() != null) {
            throw new BadRequestAlertException("A new lICConfirmationResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICConfirmationResponse = lICConfirmationResponseService.save(lICConfirmationResponse);
        return ResponseEntity.created(new URI("/api/lic-confirmation-responses/" + lICConfirmationResponse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICConfirmationResponse.getId().toString()))
            .body(lICConfirmationResponse);
    }

    /**
     * {@code PUT  /lic-confirmation-responses/:id} : Updates an existing lICConfirmationResponse.
     *
     * @param id the id of the lICConfirmationResponse to save.
     * @param lICConfirmationResponse the lICConfirmationResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICConfirmationResponse,
     * or with status {@code 400 (Bad Request)} if the lICConfirmationResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICConfirmationResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICConfirmationResponse> updateLICConfirmationResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LICConfirmationResponse lICConfirmationResponse
    ) throws URISyntaxException {
        log.debug("REST request to update LICConfirmationResponse : {}, {}", id, lICConfirmationResponse);
        if (lICConfirmationResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICConfirmationResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICConfirmationResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICConfirmationResponse = lICConfirmationResponseService.update(lICConfirmationResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICConfirmationResponse.getId().toString()))
            .body(lICConfirmationResponse);
    }

    /**
     * {@code PATCH  /lic-confirmation-responses/:id} : Partial updates given fields of an existing lICConfirmationResponse, field will ignore if it is null
     *
     * @param id the id of the lICConfirmationResponse to save.
     * @param lICConfirmationResponse the lICConfirmationResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICConfirmationResponse,
     * or with status {@code 400 (Bad Request)} if the lICConfirmationResponse is not valid,
     * or with status {@code 404 (Not Found)} if the lICConfirmationResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICConfirmationResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICConfirmationResponse> partialUpdateLICConfirmationResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LICConfirmationResponse lICConfirmationResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICConfirmationResponse partially : {}, {}", id, lICConfirmationResponse);
        if (lICConfirmationResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICConfirmationResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICConfirmationResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICConfirmationResponse> result = lICConfirmationResponseService.partialUpdate(lICConfirmationResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICConfirmationResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-confirmation-responses} : get all the lICConfirmationResponses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICConfirmationResponses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICConfirmationResponse>> getAllLICConfirmationResponses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("licresultresponse-is-null".equals(filter)) {
            log.debug("REST request to get all LICConfirmationResponses where lICResultResponse is null");
            return new ResponseEntity<>(lICConfirmationResponseService.findAllWhereLICResultResponseIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of LICConfirmationResponses");
        Page<LICConfirmationResponse> page = lICConfirmationResponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-confirmation-responses/:id} : get the "id" lICConfirmationResponse.
     *
     * @param id the id of the lICConfirmationResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICConfirmationResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICConfirmationResponse> getLICConfirmationResponse(@PathVariable("id") Long id) {
        log.debug("REST request to get LICConfirmationResponse : {}", id);
        Optional<LICConfirmationResponse> lICConfirmationResponse = lICConfirmationResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICConfirmationResponse);
    }

    /**
     * {@code DELETE  /lic-confirmation-responses/:id} : delete the "id" lICConfirmationResponse.
     *
     * @param id the id of the lICConfirmationResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICConfirmationResponse(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICConfirmationResponse : {}", id);
        lICConfirmationResponseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
