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
import org.zimnat.domain.Quotes;
import org.zimnat.repository.QuotesRepository;
import org.zimnat.service.QuotesService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.Quotes}.
 */
@RestController
@RequestMapping("/api/quotes")
public class QuotesResource {

    private static final Logger log = LoggerFactory.getLogger(QuotesResource.class);

    private static final String ENTITY_NAME = "quotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotesService quotesService;

    private final QuotesRepository quotesRepository;

    public QuotesResource(QuotesService quotesService, QuotesRepository quotesRepository) {
        this.quotesService = quotesService;
        this.quotesRepository = quotesRepository;
    }

    /**
     * {@code POST  /quotes} : Create a new quotes.
     *
     * @param quotes the quotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotes, or with status {@code 400 (Bad Request)} if the quotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Quotes> createQuotes(@RequestBody Quotes quotes) throws URISyntaxException {
        log.debug("REST request to save Quotes : {}", quotes);
        if (quotes.getId() != null) {
            throw new BadRequestAlertException("A new quotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        quotes = quotesService.save(quotes);
        return ResponseEntity.created(new URI("/api/quotes/" + quotes.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, quotes.getId().toString()))
            .body(quotes);
    }

    /**
     * {@code PUT  /quotes/:id} : Updates an existing quotes.
     *
     * @param id the id of the quotes to save.
     * @param quotes the quotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotes,
     * or with status {@code 400 (Bad Request)} if the quotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Quotes> updateQuotes(@PathVariable(value = "id", required = false) final Long id, @RequestBody Quotes quotes)
        throws URISyntaxException {
        log.debug("REST request to update Quotes : {}, {}", id, quotes);
        if (quotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        quotes = quotesService.update(quotes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quotes.getId().toString()))
            .body(quotes);
    }

    /**
     * {@code PATCH  /quotes/:id} : Partial updates given fields of an existing quotes, field will ignore if it is null
     *
     * @param id the id of the quotes to save.
     * @param quotes the quotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotes,
     * or with status {@code 400 (Bad Request)} if the quotes is not valid,
     * or with status {@code 404 (Not Found)} if the quotes is not found,
     * or with status {@code 500 (Internal Server Error)} if the quotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Quotes> partialUpdateQuotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Quotes quotes
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quotes partially : {}, {}", id, quotes);
        if (quotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Quotes> result = quotesService.partialUpdate(quotes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quotes.getId().toString())
        );
    }

    /**
     * {@code GET  /quotes} : get all the quotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Quotes>> getAllQuotes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Quotes");
        Page<Quotes> page = quotesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quotes/:id} : get the "id" quotes.
     *
     * @param id the id of the quotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Quotes> getQuotes(@PathVariable("id") Long id) {
        log.debug("REST request to get Quotes : {}", id);
        Optional<Quotes> quotes = quotesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quotes);
    }

    /**
     * {@code DELETE  /quotes/:id} : delete the "id" quotes.
     *
     * @param id the id of the quotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuotes(@PathVariable("id") Long id) {
        log.debug("REST request to delete Quotes : {}", id);
        quotesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
