package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.LICQuote;
import org.zimnat.repository.LICQuoteRepository;
import org.zimnat.service.LICQuoteService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICQuote}.
 */
@Service
@Transactional
public class LICQuoteServiceImpl implements LICQuoteService {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteServiceImpl.class);

    private final LICQuoteRepository lICQuoteRepository;

    public LICQuoteServiceImpl(LICQuoteRepository lICQuoteRepository) {
        this.lICQuoteRepository = lICQuoteRepository;
    }

    @Override
    public LICQuote save(LICQuote lICQuote) {
        log.debug("Request to save LICQuote : {}", lICQuote);
        return lICQuoteRepository.save(lICQuote);
    }

    @Override
    public LICQuote update(LICQuote lICQuote) {
        log.debug("Request to update LICQuote : {}", lICQuote);
        return lICQuoteRepository.save(lICQuote);
    }

    @Override
    public Optional<LICQuote> partialUpdate(LICQuote lICQuote) {
        log.debug("Request to partially update LICQuote : {}", lICQuote);

        return lICQuoteRepository
            .findById(lICQuote.getId())
            .map(existingLICQuote -> {
                if (lICQuote.getPartnerReference() != null) {
                    existingLICQuote.setPartnerReference(lICQuote.getPartnerReference());
                }
                if (lICQuote.getTheDate() != null) {
                    existingLICQuote.setTheDate(lICQuote.getTheDate());
                }
                if (lICQuote.getTheVersion() != null) {
                    existingLICQuote.setTheVersion(lICQuote.getTheVersion());
                }
                if (lICQuote.getPartnerToken() != null) {
                    existingLICQuote.setPartnerToken(lICQuote.getPartnerToken());
                }

                return existingLICQuote;
            })
            .map(lICQuoteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICQuote> findAll(Pageable pageable) {
        log.debug("Request to get all LICQuotes");
        return lICQuoteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICQuote> findOne(Long id) {
        log.debug("Request to get LICQuote : {}", id);
        return lICQuoteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICQuote : {}", id);
        lICQuoteRepository.deleteById(id);
    }
}
