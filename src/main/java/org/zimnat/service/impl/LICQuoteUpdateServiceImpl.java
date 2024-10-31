package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.LICQuoteUpdate;
import org.zimnat.repository.LICQuoteUpdateRepository;
import org.zimnat.service.LICQuoteUpdateService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICQuoteUpdate}.
 */
@Service
@Transactional
public class LICQuoteUpdateServiceImpl implements LICQuoteUpdateService {

    private static final Logger log = LoggerFactory.getLogger(LICQuoteUpdateServiceImpl.class);

    private final LICQuoteUpdateRepository lICQuoteUpdateRepository;

    public LICQuoteUpdateServiceImpl(LICQuoteUpdateRepository lICQuoteUpdateRepository) {
        this.lICQuoteUpdateRepository = lICQuoteUpdateRepository;
    }

    @Override
    public LICQuoteUpdate save(LICQuoteUpdate lICQuoteUpdate) {
        log.debug("Request to save LICQuoteUpdate : {}", lICQuoteUpdate);
        return lICQuoteUpdateRepository.save(lICQuoteUpdate);
    }

    @Override
    public LICQuoteUpdate update(LICQuoteUpdate lICQuoteUpdate) {
        log.debug("Request to update LICQuoteUpdate : {}", lICQuoteUpdate);
        return lICQuoteUpdateRepository.save(lICQuoteUpdate);
    }

    @Override
    public Optional<LICQuoteUpdate> partialUpdate(LICQuoteUpdate lICQuoteUpdate) {
        log.debug("Request to partially update LICQuoteUpdate : {}", lICQuoteUpdate);

        return lICQuoteUpdateRepository
            .findById(lICQuoteUpdate.getId())
            .map(existingLICQuoteUpdate -> {
                if (lICQuoteUpdate.getPartnerReference() != null) {
                    existingLICQuoteUpdate.setPartnerReference(lICQuoteUpdate.getPartnerReference());
                }
                if (lICQuoteUpdate.getTheDate() != null) {
                    existingLICQuoteUpdate.setTheDate(lICQuoteUpdate.getTheDate());
                }
                if (lICQuoteUpdate.getTheVersion() != null) {
                    existingLICQuoteUpdate.setTheVersion(lICQuoteUpdate.getTheVersion());
                }
                if (lICQuoteUpdate.getPartnerToken() != null) {
                    existingLICQuoteUpdate.setPartnerToken(lICQuoteUpdate.getPartnerToken());
                }
                if (lICQuoteUpdate.getLicenceID() != null) {
                    existingLICQuoteUpdate.setLicenceID(lICQuoteUpdate.getLicenceID());
                }
                if (lICQuoteUpdate.getStatus() != null) {
                    existingLICQuoteUpdate.setStatus(lICQuoteUpdate.getStatus());
                }
                if (lICQuoteUpdate.getPaymentMethod() != null) {
                    existingLICQuoteUpdate.setPaymentMethod(lICQuoteUpdate.getPaymentMethod());
                }
                if (lICQuoteUpdate.getDeliveryMethod() != null) {
                    existingLICQuoteUpdate.setDeliveryMethod(lICQuoteUpdate.getDeliveryMethod());
                }

                return existingLICQuoteUpdate;
            })
            .map(lICQuoteUpdateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICQuoteUpdate> findAll(Pageable pageable) {
        log.debug("Request to get all LICQuoteUpdates");
        return lICQuoteUpdateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICQuoteUpdate> findOne(Long id) {
        log.debug("Request to get LICQuoteUpdate : {}", id);
        return lICQuoteUpdateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICQuoteUpdate : {}", id);
        lICQuoteUpdateRepository.deleteById(id);
    }
}
