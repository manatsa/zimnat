package org.zimnat.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.LICQuoteRequest;
import org.zimnat.repository.LICQuoteRequestRepository;
import org.zimnat.service.LICQuoteRequestService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICQuoteRequest}.
 */
@Service
@Transactional
public class LICQuoteRequestServiceImpl implements LICQuoteRequestService {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteRequestServiceImpl.class);

    private final LICQuoteRequestRepository lICQuoteRequestRepository;

    public LICQuoteRequestServiceImpl(LICQuoteRequestRepository lICQuoteRequestRepository) {
        this.lICQuoteRequestRepository = lICQuoteRequestRepository;
    }

    @Override
    public LICQuoteRequest save(LICQuoteRequest lICQuoteRequest) {
        log.debug("Request to save LICQuoteRequest : {}", lICQuoteRequest);
        return lICQuoteRequestRepository.save(lICQuoteRequest);
    }

    @Override
    public LICQuoteRequest update(LICQuoteRequest lICQuoteRequest) {
        log.debug("Request to update LICQuoteRequest : {}", lICQuoteRequest);
        return lICQuoteRequestRepository.save(lICQuoteRequest);
    }

    @Override
    public Optional<LICQuoteRequest> partialUpdate(LICQuoteRequest lICQuoteRequest) {
        log.debug("Request to partially update LICQuoteRequest : {}", lICQuoteRequest);

        return lICQuoteRequestRepository
            .findById(lICQuoteRequest.getId())
            .map(existingLICQuoteRequest -> {
                if (lICQuoteRequest.getTheFunction() != null) {
                    existingLICQuoteRequest.setTheFunction(lICQuoteRequest.getTheFunction());
                }

                return existingLICQuoteRequest;
            })
            .map(lICQuoteRequestRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICQuoteRequest> findAll(Pageable pageable) {
        log.debug("Request to get all LICQuoteRequests");
        return lICQuoteRequestRepository.findAll(pageable);
    }

    /**
     *  Get all the lICQuoteRequests where LICQuote is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LICQuoteRequest> findAllWhereLICQuoteIsNull() {
        log.debug("Request to get all lICQuoteRequests where LICQuote is null");
        return StreamSupport.stream(lICQuoteRequestRepository.findAll().spliterator(), false)
            .filter(lICQuoteRequest -> lICQuoteRequest.getLICQuote() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICQuoteRequest> findOne(Long id) {
        log.debug("Request to get LICQuoteRequest : {}", id);
        return lICQuoteRequestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICQuoteRequest : {}", id);
        lICQuoteRequestRepository.deleteById(id);
    }
}
