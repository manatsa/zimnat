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
import org.zimnat.domain.QuoteResponse;
import org.zimnat.repository.QuoteResponseRepository;
import org.zimnat.service.QuoteResponseService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.QuoteResponse}.
 */
@Service
@Transactional
public class QuoteResponseServiceImpl implements QuoteResponseService {

    private static final Logger log = LoggerFactory.getLogger(QuoteResponseServiceImpl.class);

    private final QuoteResponseRepository quoteResponseRepository;

    public QuoteResponseServiceImpl(QuoteResponseRepository quoteResponseRepository) {
        this.quoteResponseRepository = quoteResponseRepository;
    }

    @Override
    public QuoteResponse save(QuoteResponse quoteResponse) {
        log.debug("Request to save QuoteResponse : {}", quoteResponse);
        return quoteResponseRepository.save(quoteResponse);
    }

    @Override
    public QuoteResponse update(QuoteResponse quoteResponse) {
        log.debug("Request to update QuoteResponse : {}", quoteResponse);
        return quoteResponseRepository.save(quoteResponse);
    }

    @Override
    public Optional<QuoteResponse> partialUpdate(QuoteResponse quoteResponse) {
        log.debug("Request to partially update QuoteResponse : {}", quoteResponse);

        return quoteResponseRepository
            .findById(quoteResponse.getId())
            .map(existingQuoteResponse -> {
                if (quoteResponse.getResult() != null) {
                    existingQuoteResponse.setResult(quoteResponse.getResult());
                }
                if (quoteResponse.getMessage() != null) {
                    existingQuoteResponse.setMessage(quoteResponse.getMessage());
                }

                return existingQuoteResponse;
            })
            .map(quoteResponseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuoteResponse> findAll(Pageable pageable) {
        log.debug("Request to get all QuoteResponses");
        return quoteResponseRepository.findAll(pageable);
    }

    /**
     *  Get all the quoteResponses where LICQuoteResponse is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QuoteResponse> findAllWhereLICQuoteResponseIsNull() {
        log.debug("Request to get all quoteResponses where LICQuoteResponse is null");
        return StreamSupport.stream(quoteResponseRepository.findAll().spliterator(), false)
            .filter(quoteResponse -> quoteResponse.getLICQuoteResponse() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteResponse> findOne(Long id) {
        log.debug("Request to get QuoteResponse : {}", id);
        return quoteResponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuoteResponse : {}", id);
        quoteResponseRepository.deleteById(id);
    }
}
