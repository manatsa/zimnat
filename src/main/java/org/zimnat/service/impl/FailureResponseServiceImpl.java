package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.FailureResponse;
import org.zimnat.repository.FailureResponseRepository;
import org.zimnat.service.FailureResponseService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.FailureResponse}.
 */
@Service
@Transactional
public class FailureResponseServiceImpl implements FailureResponseService {

    private static final Logger log = LoggerFactory.getLogger(FailureResponseServiceImpl.class);

    private final FailureResponseRepository failureResponseRepository;

    public FailureResponseServiceImpl(FailureResponseRepository failureResponseRepository) {
        this.failureResponseRepository = failureResponseRepository;
    }

    @Override
    public FailureResponse save(FailureResponse failureResponse) {
        log.debug("Request to save FailureResponse : {}", failureResponse);
        return failureResponseRepository.save(failureResponse);
    }

    @Override
    public FailureResponse update(FailureResponse failureResponse) {
        log.debug("Request to update FailureResponse : {}", failureResponse);
        return failureResponseRepository.save(failureResponse);
    }

    @Override
    public Optional<FailureResponse> partialUpdate(FailureResponse failureResponse) {
        log.debug("Request to partially update FailureResponse : {}", failureResponse);

        return failureResponseRepository
            .findById(failureResponse.getId())
            .map(existingFailureResponse -> {
                if (failureResponse.getPartnerReference() != null) {
                    existingFailureResponse.setPartnerReference(failureResponse.getPartnerReference());
                }
                if (failureResponse.getDate() != null) {
                    existingFailureResponse.setDate(failureResponse.getDate());
                }
                if (failureResponse.getVersion() != null) {
                    existingFailureResponse.setVersion(failureResponse.getVersion());
                }

                return existingFailureResponse;
            })
            .map(failureResponseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FailureResponse> findAll(Pageable pageable) {
        log.debug("Request to get all FailureResponses");
        return failureResponseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FailureResponse> findOne(Long id) {
        log.debug("Request to get FailureResponse : {}", id);
        return failureResponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FailureResponse : {}", id);
        failureResponseRepository.deleteById(id);
    }
}
