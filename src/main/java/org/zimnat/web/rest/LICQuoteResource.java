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
import org.zimnat.domain.LICQuote;
import org.zimnat.repository.LICQuoteRepository;
import org.zimnat.service.LICQuoteService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.LICQuote}.
 */
@RestController
@RequestMapping("/api/lic-quotes")
public class LICQuoteResource {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteResource.class);

    private static final String ENTITY_NAME = "lICQuote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LICQuoteService lICQuoteService;

    private final LICQuoteRepository lICQuoteRepository;

    public LICQuoteResource(LICQuoteService lICQuoteService, LICQuoteRepository lICQuoteRepository) {
        this.lICQuoteService = lICQuoteService;
        this.lICQuoteRepository = lICQuoteRepository;
    }

    /**
     * {@code POST  /lic-quotes} : Create a new lICQuote.
     *
     * @param lICQuote the lICQuote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lICQuote, or with status {@code 400 (Bad Request)} if the lICQuote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LICQuote> createLICQuote(@Valid @RequestBody LICQuote lICQuote) throws URISyntaxException {
        log.debug("REST request to save LICQuote : {}", lICQuote);
        if (lICQuote.getId() != null) {
            throw new BadRequestAlertException("A new lICQuote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lICQuote = lICQuoteService.save(lICQuote);
        return ResponseEntity.created(new URI("/api/lic-quotes/" + lICQuote.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lICQuote.getId().toString()))
            .body(lICQuote);
    }

    /**
     * {@code PUT  /lic-quotes/:id} : Updates an existing lICQuote.
     *
     * @param id the id of the lICQuote to save.
     * @param lICQuote the lICQuote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuote,
     * or with status {@code 400 (Bad Request)} if the lICQuote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lICQuote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LICQuote> updateLICQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LICQuote lICQuote
    ) throws URISyntaxException {
        log.debug("REST request to update LICQuote : {}, {}", id, lICQuote);
        if (lICQuote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lICQuote = lICQuoteService.update(lICQuote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuote.getId().toString()))
            .body(lICQuote);
    }

    /**
     * {@code PATCH  /lic-quotes/:id} : Partial updates given fields of an existing lICQuote, field will ignore if it is null
     *
     * @param id the id of the lICQuote to save.
     * @param lICQuote the lICQuote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lICQuote,
     * or with status {@code 400 (Bad Request)} if the lICQuote is not valid,
     * or with status {@code 404 (Not Found)} if the lICQuote is not found,
     * or with status {@code 500 (Internal Server Error)} if the lICQuote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LICQuote> partialUpdateLICQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LICQuote lICQuote
    ) throws URISyntaxException {
        log.debug("REST request to partial update LICQuote partially : {}, {}", id, lICQuote);
        if (lICQuote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lICQuote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lICQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LICQuote> result = lICQuoteService.partialUpdate(lICQuote);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lICQuote.getId().toString())
        );
    }

    /**
     * {@code GET  /lic-quotes} : get all the lICQuotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lICQuotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LICQuote>> getAllLICQuotes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LICQuotes");
        Page<LICQuote> page = lICQuoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lic-quotes/:id} : get the "id" lICQuote.
     *
     * @param id the id of the lICQuote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lICQuote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LICQuote> getLICQuote(@PathVariable("id") Long id) {
        log.debug("REST request to get LICQuote : {}", id);
        Optional<LICQuote> lICQuote = lICQuoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lICQuote);
    }

    /**
     * {@code DELETE  /lic-quotes/:id} : delete the "id" lICQuote.
     *
     * @param id the id of the lICQuote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLICQuote(@PathVariable("id") Long id) {
        log.debug("REST request to delete LICQuote : {}", id);
        lICQuoteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
