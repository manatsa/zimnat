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
import org.zimnat.domain.TPILICQuote;
import org.zimnat.repository.TPILICQuoteRepository;
import org.zimnat.service.TPILICQuoteService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.TPILICQuote}.
 */
@RestController
@RequestMapping("/api/tpilic-quotes")
public class TPILICQuoteResource {

    private static final Logger log = LoggerFactory.getLogger(TPILICQuoteResource.class);

    private static final String ENTITY_NAME = "tPILICQuote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TPILICQuoteService tPILICQuoteService;

    private final TPILICQuoteRepository tPILICQuoteRepository;

    public TPILICQuoteResource(TPILICQuoteService tPILICQuoteService, TPILICQuoteRepository tPILICQuoteRepository) {
        this.tPILICQuoteService = tPILICQuoteService;
        this.tPILICQuoteRepository = tPILICQuoteRepository;
    }

    /**
     * {@code POST  /tpilic-quotes} : Create a new tPILICQuote.
     *
     * @param tPILICQuote the tPILICQuote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tPILICQuote, or with status {@code 400 (Bad Request)} if the tPILICQuote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TPILICQuote> createTPILICQuote(@Valid @RequestBody TPILICQuote tPILICQuote) throws URISyntaxException {
        log.debug("REST request to save TPILICQuote : {}", tPILICQuote);
        if (tPILICQuote.getId() != null) {
            throw new BadRequestAlertException("A new tPILICQuote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tPILICQuote = tPILICQuoteService.save(tPILICQuote);
        return ResponseEntity.created(new URI("/api/tpilic-quotes/" + tPILICQuote.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tPILICQuote.getId().toString()))
            .body(tPILICQuote);
    }

    /**
     * {@code PUT  /tpilic-quotes/:id} : Updates an existing tPILICQuote.
     *
     * @param id the id of the tPILICQuote to save.
     * @param tPILICQuote the tPILICQuote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPILICQuote,
     * or with status {@code 400 (Bad Request)} if the tPILICQuote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tPILICQuote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TPILICQuote> updateTPILICQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TPILICQuote tPILICQuote
    ) throws URISyntaxException {
        log.debug("REST request to update TPILICQuote : {}, {}", id, tPILICQuote);
        if (tPILICQuote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tPILICQuote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tPILICQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tPILICQuote = tPILICQuoteService.update(tPILICQuote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tPILICQuote.getId().toString()))
            .body(tPILICQuote);
    }

    /**
     * {@code PATCH  /tpilic-quotes/:id} : Partial updates given fields of an existing tPILICQuote, field will ignore if it is null
     *
     * @param id the id of the tPILICQuote to save.
     * @param tPILICQuote the tPILICQuote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tPILICQuote,
     * or with status {@code 400 (Bad Request)} if the tPILICQuote is not valid,
     * or with status {@code 404 (Not Found)} if the tPILICQuote is not found,
     * or with status {@code 500 (Internal Server Error)} if the tPILICQuote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TPILICQuote> partialUpdateTPILICQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TPILICQuote tPILICQuote
    ) throws URISyntaxException {
        log.debug("REST request to partial update TPILICQuote partially : {}, {}", id, tPILICQuote);
        if (tPILICQuote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tPILICQuote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tPILICQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TPILICQuote> result = tPILICQuoteService.partialUpdate(tPILICQuote);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tPILICQuote.getId().toString())
        );
    }

    /**
     * {@code GET  /tpilic-quotes} : get all the tPILICQuotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tPILICQuotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TPILICQuote>> getAllTPILICQuotes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TPILICQuotes");
        Page<TPILICQuote> page = tPILICQuoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tpilic-quotes/:id} : get the "id" tPILICQuote.
     *
     * @param id the id of the tPILICQuote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tPILICQuote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TPILICQuote> getTPILICQuote(@PathVariable("id") Long id) {
        log.debug("REST request to get TPILICQuote : {}", id);
        Optional<TPILICQuote> tPILICQuote = tPILICQuoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPILICQuote);
    }

    /**
     * {@code DELETE  /tpilic-quotes/:id} : delete the "id" tPILICQuote.
     *
     * @param id the id of the tPILICQuote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTPILICQuote(@PathVariable("id") Long id) {
        log.debug("REST request to delete TPILICQuote : {}", id);
        tPILICQuoteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
