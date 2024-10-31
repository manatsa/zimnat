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
import org.zimnat.domain.Responses;
import org.zimnat.repository.ResponsesRepository;
import org.zimnat.service.ResponsesService;
import org.zimnat.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.zimnat.domain.Responses}.
 */
@RestController
@RequestMapping("/api/responses")
public class ResponsesResource {

    private static final Logger log = LoggerFactory.getLogger(ResponsesResource.class);

    private static final String ENTITY_NAME = "responses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsesService responsesService;

    private final ResponsesRepository responsesRepository;

    public ResponsesResource(ResponsesService responsesService, ResponsesRepository responsesRepository) {
        this.responsesService = responsesService;
        this.responsesRepository = responsesRepository;
    }

    /**
     * {@code POST  /responses} : Create a new responses.
     *
     * @param responses the responses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responses, or with status {@code 400 (Bad Request)} if the responses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Responses> createResponses(@RequestBody Responses responses) throws URISyntaxException {
        log.debug("REST request to save Responses : {}", responses);
        if (responses.getId() != null) {
            throw new BadRequestAlertException("A new responses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        responses = responsesService.save(responses);
        return ResponseEntity.created(new URI("/api/responses/" + responses.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, responses.getId().toString()))
            .body(responses);
    }

    /**
     * {@code PUT  /responses/:id} : Updates an existing responses.
     *
     * @param id the id of the responses to save.
     * @param responses the responses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responses,
     * or with status {@code 400 (Bad Request)} if the responses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Responses> updateResponses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Responses responses
    ) throws URISyntaxException {
        log.debug("REST request to update Responses : {}, {}", id, responses);
        if (responses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        responses = responsesService.update(responses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responses.getId().toString()))
            .body(responses);
    }

    /**
     * {@code PATCH  /responses/:id} : Partial updates given fields of an existing responses, field will ignore if it is null
     *
     * @param id the id of the responses to save.
     * @param responses the responses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responses,
     * or with status {@code 400 (Bad Request)} if the responses is not valid,
     * or with status {@code 404 (Not Found)} if the responses is not found,
     * or with status {@code 500 (Internal Server Error)} if the responses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Responses> partialUpdateResponses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Responses responses
    ) throws URISyntaxException {
        log.debug("REST request to partial update Responses partially : {}, {}", id, responses);
        if (responses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Responses> result = responsesService.partialUpdate(responses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responses.getId().toString())
        );
    }

    /**
     * {@code GET  /responses} : get all the responses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Responses>> getAllResponses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("failureresponse-is-null".equals(filter)) {
            log.debug("REST request to get all Responsess where failureResponse is null");
            return new ResponseEntity<>(responsesService.findAllWhereFailureResponseIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Responses");
        Page<Responses> page = responsesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responses/:id} : get the "id" responses.
     *
     * @param id the id of the responses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Responses> getResponses(@PathVariable("id") Long id) {
        log.debug("REST request to get Responses : {}", id);
        Optional<Responses> responses = responsesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responses);
    }

    /**
     * {@code DELETE  /responses/:id} : delete the "id" responses.
     *
     * @param id the id of the responses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponses(@PathVariable("id") Long id) {
        log.debug("REST request to delete Responses : {}", id);
        responsesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
