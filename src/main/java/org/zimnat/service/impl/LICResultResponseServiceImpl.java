package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.LICResultResponse;
import org.zimnat.repository.LICResultResponseRepository;
import org.zimnat.service.LICResultResponseService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICResultResponse}.
 */
@Service
@Transactional
public class LICResultResponseServiceImpl implements LICResultResponseService {

    private static final Logger log = LoggerFactory.getLogger(LICResultResponseServiceImpl.class);

    private final LICResultResponseRepository lICResultResponseRepository;

    public LICResultResponseServiceImpl(LICResultResponseRepository lICResultResponseRepository) {
        this.lICResultResponseRepository = lICResultResponseRepository;
    }

    @Override
    public LICResultResponse save(LICResultResponse lICResultResponse) {
        log.debug("Request to save LICResultResponse : {}", lICResultResponse);
        return lICResultResponseRepository.save(lICResultResponse);
    }

    @Override
    public LICResultResponse update(LICResultResponse lICResultResponse) {
        log.debug("Request to update LICResultResponse : {}", lICResultResponse);
        return lICResultResponseRepository.save(lICResultResponse);
    }

    @Override
    public Optional<LICResultResponse> partialUpdate(LICResultResponse lICResultResponse) {
        log.debug("Request to partially update LICResultResponse : {}", lICResultResponse);

        return lICResultResponseRepository
            .findById(lICResultResponse.getId())
            .map(existingLICResultResponse -> {
                if (lICResultResponse.getPartnerReference() != null) {
                    existingLICResultResponse.setPartnerReference(lICResultResponse.getPartnerReference());
                }
                if (lICResultResponse.getTheDate() != null) {
                    existingLICResultResponse.setTheDate(lICResultResponse.getTheDate());
                }
                if (lICResultResponse.getVersion() != null) {
                    existingLICResultResponse.setVersion(lICResultResponse.getVersion());
                }

                return existingLICResultResponse;
            })
            .map(lICResultResponseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICResultResponse> findAll(Pageable pageable) {
        log.debug("Request to get all LICResultResponses");
        return lICResultResponseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICResultResponse> findOne(Long id) {
        log.debug("Request to get LICResultResponse : {}", id);
        return lICResultResponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICResultResponse : {}", id);
        lICResultResponseRepository.deleteById(id);
    }
}
