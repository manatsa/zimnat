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
import org.zimnat.domain.LICQuoteUpdate;
import org.zimnat.repository.LICQuoteUpdateRepository;
import org.zimnat.service.LICQuoteUpdateService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICQuoteUpdate}.
 */
@RestController
@RequestMapping("/api/lic-quote-updates")
public class LICQuoteUpdateResource {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteUpdateResource.class);

    private static final String ENTITY_NAME = "lICQuoteUpdate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICQuoteUpdateService lICQuoteUpdateService;

    private final LICQuoteUpdateRepository lICQuoteUpdateRepository;

    public LICQuoteUpdateResource(LICQuoteUpdateService lICQuoteUpdateService, LICQuoteUpdateRepository lICQuoteUpdateRepository) {
        this.lICQuoteUpdateService = lICQuoteUpdateService;
        this.lICQuoteUpdateRepository = lICQuoteUpdateRepository;
    }

    /**
     * {@code POST  /lic-quote-updates} : Create a new lICQuoteUpdate.
     *
     * @param lICQuoteUpdate the lICQuoteUpdate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICQuoteUpdate, or with status {@code 400 (Bad Request)} if the lICQuoteUpdate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICQuoteUpdate> createLICQuoteUpdate(@Valid @RequestBody LICQuoteUpdate lICQuoteUpdate)
        throws URISyntaxException {
        log.debug("REST request to save LICQuoteUpdate : {}", lICQuoteUpdate);
        if (lICQuoteUpdate.getId() != null) {
            throw new BadRequestAlertException("A new lICQuoteUpdate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICQuoteUpdate = lICQuoteUpdateService.save(lICQuoteUpdate);
        return ResponseEntity.created(new URI("/api/lic-quote-updates/" + lICQuoteUpdate.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICQuoteUpdate.getId().toString()))
            .body(lICQuoteUpdate);
    }

    /**
     * {@code PUT  /lic-quote-updates/:id} : Updates an existing lICQuoteUpdate.
     *
     * @param id the id of the lICQuoteUpdate to save.
     * @param lICQuoteUpdate the lICQuoteUpdate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuoteUpdate,
     * or with status {@code 400 (Bad Request)} if the lICQuoteUpdate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICQuoteUpdate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICQuoteUpdate> updateLICQuoteUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LICQuoteUpdate lICQuoteUpdate
    ) throws URISyntaxException {
        log.debug("REST request to update LICQuoteUpdate : {}, {}", id, lICQuoteUpdate);
        if (lICQuoteUpdate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuoteUpdate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteUpdateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICQuoteUpdate = lICQuoteUpdateService.update(lICQuoteUpdate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuoteUpdate.getId().toString()))
            .body(lICQuoteUpdate);
    }

    /**
     * {@code PATCH  /lic-quote-updates/:id} : Partial updates given fields of an existing lICQuoteUpdate, field will ignore if it is null
     *
     * @param id the id of the lICQuoteUpdate to save.
     * @param lICQuoteUpdate the lICQuoteUpdate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuoteUpdate,
     * or with status {@code 400 (Bad Request)} if the lICQuoteUpdate is not valid,
     * or with status {@code 404 (Not Found)} if the lICQuoteUpdate is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICQuoteUpdate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICQuoteUpdate> partialUpdateLICQuoteUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LICQuoteUpdate lICQuoteUpdate
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICQuoteUpdate partially : {}, {}", id, lICQuoteUpdate);
        if (lICQuoteUpdate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuoteUpdate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteUpdateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICQuoteUpdate> result = lICQuoteUpdateService.partialUpdate(lICQuoteUpdate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuoteUpdate.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-quote-updates} : get all the lICQuoteUpdates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICQuoteUpdates in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICQuoteUpdate>> getAllLICQuoteUpdates(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LICQuoteUpdates");
        Page<LICQuoteUpdate> page = lICQuoteUpdateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-quote-updates/:id} : get the "id" lICQuoteUpdate.
     *
     * @param id the id of the lICQuoteUpdate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICQuoteUpdate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICQuoteUpdate> getLICQuoteUpdate(@PathVariable("id") Long id) {
        log.debug("REST request to get LICQuoteUpdate : {}", id);
        Optional<LICQuoteUpdate> lICQuoteUpdate = lICQuoteUpdateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICQuoteUpdate);
    }

    /**
     * {@code DELETE  /lic-quote-updates/:id} : delete the "id" lICQuoteUpdate.
     *
     * @param id the id of the lICQuoteUpdate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICQuoteUpdate(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICQuoteUpdate : {}", id);
        lICQuoteUpdateService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
