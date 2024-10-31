package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.LICQuoteResponse;
import org.zimnat.repository.LICQuoteResponseRepository;
import org.zimnat.service.LICQuoteResponseService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICQuoteResponse}.
 */
@Service
@Transactional
public class LICQuoteResponseServiceImpl implements LICQuoteResponseService {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteResponseServiceImpl.class);

    private final LICQuoteResponseRepository lICQuoteResponseRepository;

    public LICQuoteResponseServiceImpl(LICQuoteResponseRepository lICQuoteResponseRepository) {
        this.lICQuoteResponseRepository = lICQuoteResponseRepository;
    }

    @Override
    public LICQuoteResponse save(LICQuoteResponse lICQuoteResponse) {
        log.debug("Request to save LICQuoteResponse : {}", lICQuoteResponse);
        return lICQuoteResponseRepository.save(lICQuoteResponse);
    }

    @Override
    public LICQuoteResponse update(LICQuoteResponse lICQuoteResponse) {
        log.debug("Request to update LICQuoteResponse : {}", lICQuoteResponse);
        return lICQuoteResponseRepository.save(lICQuoteResponse);
    }

    @Override
    public Optional<LICQuoteResponse> partialUpdate(LICQuoteResponse lICQuoteResponse) {
        log.debug("Request to partially update LICQuoteResponse : {}", lICQuoteResponse);

        return lICQuoteResponseRepository
            .findById(lICQuoteResponse.getId())
            .map(existingLICQuoteResponse -> {
                if (lICQuoteResponse.getPartnerReference() != null) {
                    existingLICQuoteResponse.setPartnerReference(lICQuoteResponse.getPartnerReference());
                }
                if (lICQuoteResponse.getTheDate() != null) {
                    existingLICQuoteResponse.setTheDate(lICQuoteResponse.getTheDate());
                }
                if (lICQuoteResponse.getTheVersion() != null) {
                    existingLICQuoteResponse.setTheVersion(lICQuoteResponse.getTheVersion());
                }

                return existingLICQuoteResponse;
            })
            .map(lICQuoteResponseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICQuoteResponse> findAll(Pageable pageable) {
        log.debug("Request to get all LICQuoteResponses");
        return lICQuoteResponseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICQuoteResponse> findOne(Long id) {
        log.debug("Request to get LICQuoteResponse : {}", id);
        return lICQuoteResponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICQuoteResponse : {}", id);
        lICQuoteResponseRepository.deleteById(id);
    }
}
