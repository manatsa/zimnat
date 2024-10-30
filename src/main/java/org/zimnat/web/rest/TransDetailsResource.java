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
import org.zimnat.domain.TransDetails;
import org.zimnat.repository.TransDetailsRepository;
import org.zimnat.service.TransDetailsService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.TransDetails}.
 */
@RestController
@RequestMapping("/api/trans-details")
public class TransDetailsResource {

    private static final Logger log = LoggerFactory.getLogger(TransDetailsResource.class);

    private static final String ENTITY_NAME = "transDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransDetailsService transDetailsService;

    private final TransDetailsRepository transDetailsRepository;

    public TransDetailsResource(TransDetailsService transDetailsService, TransDetailsRepository transDetailsRepository) {
        this.transDetailsService = transDetailsService;
        this.transDetailsRepository = transDetailsRepository;
    }

    /**
     * {@code POST  /trans-details} : Create a new transDetails.
     *
     * @param transDetails the transDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transDetails, or with status {@code 400 (Bad Request)} if the transDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TransDetails> createTransDetails(@Valid @RequestBody TransDetails transDetails) throws URISyntaxException {
        log.debug("REST request to save TransDetails : {}", transDetails);
        if (transDetails.getId() != null) {
            throw new BadRequestAlertException("A new transDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        transDetails = transDetailsService.save(transDetails);
        return ResponseEntity.created(new URI("/api/trans-details/" + transDetails.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, transDetails.getId().toString()))
            .body(transDetails);
    }

    /**
     * {@code PUT  /trans-details/:id} : Updates an existing transDetails.
     *
     * @param id the id of the transDetails to save.
     * @param transDetails the transDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transDetails,
     * or with status {@code 400 (Bad Request)} if the transDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransDetails> updateTransDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransDetails transDetails
    ) throws URISyntaxException {
        log.debug("REST request to update TransDetails : {}, {}", id, transDetails);
        if (transDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        transDetails = transDetailsService.update(transDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transDetails.getId().toString()))
            .body(transDetails);
    }

    /**
     * {@code PATCH  /trans-details/:id} : Partial updates given fields of an existing transDetails, field will ignore if it is null
     *
     * @param id the id of the transDetails to save.
     * @param transDetails the transDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transDetails,
     * or with status {@code 400 (Bad Request)} if the transDetails is not valid,
     * or with status {@code 404 (Not Found)} if the transDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the transDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransDetails> partialUpdateTransDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransDetails transDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransDetails partially : {}, {}", id, transDetails);
        if (transDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransDetails> result = transDetailsService.partialUpdate(transDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /trans-details} : get all the transDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TransDetails>> getAllTransDetails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TransDetails");
        Page<TransDetails> page = transDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trans-details/:id} : get the "id" transDetails.
     *
     * @param id the id of the transDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransDetails> getTransDetails(@PathVariable("id") Long id) {
        log.debug("REST request to get TransDetails : {}", id);
        Optional<TransDetails> transDetails = transDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transDetails);
    }

    /**
     * {@code DELETE  /trans-details/:id} : delete the "id" transDetails.
     *
     * @param id the id of the transDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransDetails(@PathVariable("id") Long id) {
        log.debug("REST request to delete TransDetails : {}", id);
        transDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
