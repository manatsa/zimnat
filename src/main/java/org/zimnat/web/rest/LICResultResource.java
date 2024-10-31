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
import org.zimnat.domain.LICResult;
import org.zimnat.repository.LICResultRepository;
import org.zimnat.service.LICResultService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICResult}.
 */
@RestController
@RequestMapping("/api/lic-results")
public class LICResultResource {

    private static final Logger log = LoggerFactory.getLogger(LICResultResource.class);

    private static final String ENTITY_NAME = "lICResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICResultService lICResultService;

    private final LICResultRepository lICResultRepository;

    public LICResultResource(LICResultService lICResultService, LICResultRepository lICResultRepository) {
        this.lICResultService = lICResultService;
        this.lICResultRepository = lICResultRepository;
    }

    /**
     * {@code POST  /lic-results} : Create a new lICResult.
     *
     * @param lICResult the lICResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICResult, or with status {@code 400 (Bad Request)} if the lICResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICResult> createLICResult(@Valid @RequestBody LICResult lICResult) throws URISyntaxException {
        log.debug("REST request to save LICResult : {}", lICResult);
        if (lICResult.getId() != null) {
            throw new BadRequestAlertException("A new lICResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICResult = lICResultService.save(lICResult);
        return ResponseEntity.created(new URI("/api/lic-results/" + lICResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICResult.getId().toString()))
            .body(lICResult);
    }

    /**
     * {@code PUT  /lic-results/:id} : Updates an existing lICResult.
     *
     * @param id the id of the lICResult to save.
     * @param lICResult the lICResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICResult,
     * or with status {@code 400 (Bad Request)} if the lICResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICResult> updateLICResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LICResult lICResult
    ) throws URISyntaxException {
        log.debug("REST request to update LICResult : {}, {}", id, lICResult);
        if (lICResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICResult.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICResult = lICResultService.update(lICResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICResult.getId().toString()))
            .body(lICResult);
    }

    /**
     * {@code PATCH  /lic-results/:id} : Partial updates given fields of an existing lICResult, field will ignore if it is null
     *
     * @param id the id of the lICResult to save.
     * @param lICResult the lICResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICResult,
     * or with status {@code 400 (Bad Request)} if the lICResult is not valid,
     * or with status {@code 404 (Not Found)} if the lICResult is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICResult> partialUpdateLICResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LICResult lICResult
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICResult partially : {}, {}", id, lICResult);
        if (lICResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICResult.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICResult> result = lICResultService.partialUpdate(lICResult);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICResult.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-results} : get all the lICResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICResults in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICResult>> getAllLICResults(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LICResults");
        Page<LICResult> page = lICResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-results/:id} : get the "id" lICResult.
     *
     * @param id the id of the lICResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICResult> getLICResult(@PathVariable("id") Long id) {
        log.debug("REST request to get LICResult : {}", id);
        Optional<LICResult> lICResult = lICResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICResult);
    }

    /**
     * {@code DELETE  /lic-results/:id} : delete the "id" lICResult.
     *
     * @param id the id of the lICResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICResult(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICResult : {}", id);
        lICResultService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
