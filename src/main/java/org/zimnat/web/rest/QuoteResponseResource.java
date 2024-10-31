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
import org.zimnat.domain.QuoteResponse;
import org.zimnat.repository.QuoteResponseRepository;
import org.zimnat.service.QuoteResponseService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.QuoteResponse}.
 */
@RestController
@RequestMapping("/api/quote-responses")
public class QuoteResponseResource {

    private static final Logger log = LoggerFactory.getLogger(QuoteResponseResource.class);

    private static final String ENTITY_NAME = "quoteResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuoteResponseService quoteResponseService;

    private final QuoteResponseRepository quoteResponseRepository;

    public QuoteResponseResource(QuoteResponseService quoteResponseService, QuoteResponseRepository quoteResponseRepository) {
        this.quoteResponseService = quoteResponseService;
        this.quoteResponseRepository = quoteResponseRepository;
    }

    /**
     * {@code POST  /quote-responses} : Create a new quoteResponse.
     *
     * @param quoteResponse the quoteResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quoteResponse, or with status {@code 400 (Bad Request)} if the quoteResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QuoteResponse> createQuoteResponse(@RequestBody QuoteResponse quoteResponse) throws URISyntaxException {
        log.debug("REST request to save QuoteResponse : {}", quoteResponse);
        if (quoteResponse.getId() != null) {
            throw new BadRequestAlertException("A new quoteResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        quoteResponse = quoteResponseService.save(quoteResponse);
        return ResponseEntity.created(new URI("/api/quote-responses/" + quoteResponse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, quoteResponse.getId().toString()))
            .body(quoteResponse);
    }

    /**
     * {@code PUT  /quote-responses/:id} : Updates an existing quoteResponse.
     *
     * @param id the id of the quoteResponse to save.
     * @param quoteResponse the quoteResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quoteResponse,
     * or with status {@code 400 (Bad Request)} if the quoteResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quoteResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuoteResponse> updateQuoteResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuoteResponse quoteResponse
    ) throws URISyntaxException {
        log.debug("REST request to update QuoteResponse : {}, {}", id, quoteResponse);
        if (quoteResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quoteResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quoteResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        quoteResponse = quoteResponseService.update(quoteResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quoteResponse.getId().toString()))
            .body(quoteResponse);
    }

    /**
     * {@code PATCH  /quote-responses/:id} : Partial updates given fields of an existing quoteResponse, field will ignore if it is null
     *
     * @param id the id of the quoteResponse to save.
     * @param quoteResponse the quoteResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quoteResponse,
     * or with status {@code 400 (Bad Request)} if the quoteResponse is not valid,
     * or with status {@code 404 (Not Found)} if the quoteResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the quoteResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuoteResponse> partialUpdateQuoteResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuoteResponse quoteResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuoteResponse partially : {}, {}", id, quoteResponse);
        if (quoteResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quoteResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quoteResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuoteResponse> result = quoteResponseService.partialUpdate(quoteResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quoteResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /quote-responses} : get all the quoteResponses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quoteResponses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<QuoteResponse>> getAllQuoteResponses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("licquoteresponse-is-null".equals(filter)) {
            log.debug("REST request to get all QuoteResponses where lICQuoteResponse is null");
            return new ResponseEntity<>(quoteResponseService.findAllWhereLICQuoteResponseIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of QuoteResponses");
        Page<QuoteResponse> page = quoteResponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quote-responses/:id} : get the "id" quoteResponse.
     *
     * @param id the id of the quoteResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quoteResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getQuoteResponse(@PathVariable("id") Long id) {
        log.debug("REST request to get QuoteResponse : {}", id);
        Optional<QuoteResponse> quoteResponse = quoteResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quoteResponse);
    }

    /**
     * {@code DELETE  /quote-responses/:id} : delete the "id" quoteResponse.
     *
     * @param id the id of the quoteResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteResponse(@PathVariable("id") Long id) {
        log.debug("REST request to delete QuoteResponse : {}", id);
        quoteResponseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
