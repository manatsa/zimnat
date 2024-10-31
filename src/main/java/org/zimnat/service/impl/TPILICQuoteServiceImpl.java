package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.TPILICQuote;
import org.zimnat.repository.TPILICQuoteRepository;
import org.zimnat.service.TPILICQuoteService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.TPILICQuote}.
 */
@Service
@Transactional
public class TPILICQuoteServiceImpl implements TPILICQuoteService {

    private static final Logger log = LoggerFactory.getLogger(TPILICQuoteServiceImpl.class);

    private final TPILICQuoteRepository tPILICQuoteRepository;

    public TPILICQuoteServiceImpl(TPILICQuoteRepository tPILICQuoteRepository) {
        this.tPILICQuoteRepository = tPILICQuoteRepository;
    }

    @Override
    public TPILICQuote save(TPILICQuote tPILICQuote) {
        log.debug("Request to save TPILICQuote : {}", tPILICQuote);
        return tPILICQuoteRepository.save(tPILICQuote);
    }

    @Override
    public TPILICQuote update(TPILICQuote tPILICQuote) {
        log.debug("Request to update TPILICQuote : {}", tPILICQuote);
        return tPILICQuoteRepository.save(tPILICQuote);
    }

    @Override
    public Optional<TPILICQuote> partialUpdate(TPILICQuote tPILICQuote) {
        log.debug("Request to partially update TPILICQuote : {}", tPILICQuote);

        return tPILICQuoteRepository
            .findById(tPILICQuote.getId())
            .map(existingTPILICQuote -> {
                if (tPILICQuote.getPartnerReference() != null) {
                    existingTPILICQuote.setPartnerReference(tPILICQuote.getPartnerReference());
                }
                if (tPILICQuote.getTheDate() != null) {
                    existingTPILICQuote.setTheDate(tPILICQuote.getTheDate());
                }
                if (tPILICQuote.getTheVersion() != null) {
                    existingTPILICQuote.setTheVersion(tPILICQuote.getTheVersion());
                }
                if (tPILICQuote.getPartnerToken() != null) {
                    existingTPILICQuote.setPartnerToken(tPILICQuote.getPartnerToken());
                }
                if (tPILICQuote.getFunction() != null) {
                    existingTPILICQuote.setFunction(tPILICQuote.getFunction());
                }

                return existingTPILICQuote;
            })
            .map(tPILICQuoteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TPILICQuote> findAll(Pageable pageable) {
        log.debug("Request to get all TPILICQuotes");
        return tPILICQuoteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TPILICQuote> findOne(Long id) {
        log.debug("Request to get TPILICQuote : {}", id);
        return tPILICQuoteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPILICQuote : {}", id);
        tPILICQuoteRepository.deleteById(id);
    }
}
