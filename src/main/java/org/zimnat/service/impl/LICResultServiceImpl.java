package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.LICResult;
import org.zimnat.repository.LICResultRepository;
import org.zimnat.service.LICResultService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICResult}.
 */
@Service
@Transactional
public class LICResultServiceImpl implements LICResultService {

    private static final Logger log = LoggerFactory.getLogger(LICResultServiceImpl.class);

    private final LICResultRepository lICResultRepository;

    public LICResultServiceImpl(LICResultRepository lICResultRepository) {
        this.lICResultRepository = lICResultRepository;
    }

    @Override
    public LICResult save(LICResult lICResult) {
        log.debug("Request to save LICResult : {}", lICResult);
        return lICResultRepository.save(lICResult);
    }

    @Override
    public LICResult update(LICResult lICResult) {
        log.debug("Request to update LICResult : {}", lICResult);
        return lICResultRepository.save(lICResult);
    }

    @Override
    public Optional<LICResult> partialUpdate(LICResult lICResult) {
        log.debug("Request to partially update LICResult : {}", lICResult);

        return lICResultRepository
            .findById(lICResult.getId())
            .map(existingLICResult -> {
                if (lICResult.getPartnerReference() != null) {
                    existingLICResult.setPartnerReference(lICResult.getPartnerReference());
                }
                if (lICResult.getTheDate() != null) {
                    existingLICResult.setTheDate(lICResult.getTheDate());
                }
                if (lICResult.getTheVersion() != null) {
                    existingLICResult.setTheVersion(lICResult.getTheVersion());
                }
                if (lICResult.getPartnerToken() != null) {
                    existingLICResult.setPartnerToken(lICResult.getPartnerToken());
                }
                if (lICResult.getLicenceID() != null) {
                    existingLICResult.setLicenceID(lICResult.getLicenceID());
                }
                if (lICResult.getFunction() != null) {
                    existingLICResult.setFunction(lICResult.getFunction());
                }

                return existingLICResult;
            })
            .map(lICResultRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICResult> findAll(Pageable pageable) {
        log.debug("Request to get all LICResults");
        return lICResultRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICResult> findOne(Long id) {
        log.debug("Request to get LICResult : {}", id);
        return lICResultRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICResult : {}", id);
        lICResultRepository.deleteById(id);
    }
}
