package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.BatchStatus;
import org.zimnat.repository.BatchStatusRepository;
import org.zimnat.service.BatchStatusService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.BatchStatus}.
 */
@Service
@Transactional
public class BatchStatusServiceImpl implements BatchStatusService {

    private static final Logger log = LoggerFactory.getLogger(BatchStatusServiceImpl.class);

    private final BatchStatusRepository batchStatusRepository;

    public BatchStatusServiceImpl(BatchStatusRepository batchStatusRepository) {
        this.batchStatusRepository = batchStatusRepository;
    }

    @Override
    public BatchStatus save(BatchStatus batchStatus) {
        log.debug("Request to save BatchStatus : {}", batchStatus);
        return batchStatusRepository.save(batchStatus);
    }

    @Override
    public BatchStatus update(BatchStatus batchStatus) {
        log.debug("Request to update BatchStatus : {}", batchStatus);
        return batchStatusRepository.save(batchStatus);
    }

    @Override
    public Optional<BatchStatus> partialUpdate(BatchStatus batchStatus) {
        log.debug("Request to partially update BatchStatus : {}", batchStatus);

        return batchStatusRepository
            .findById(batchStatus.getId())
            .map(existingBatchStatus -> {
                if (batchStatus.getPartnerReference() != null) {
                    existingBatchStatus.setPartnerReference(batchStatus.getPartnerReference());
                }
                if (batchStatus.getTheDate() != null) {
                    existingBatchStatus.setTheDate(batchStatus.getTheDate());
                }
                if (batchStatus.getTheVersion() != null) {
                    existingBatchStatus.setTheVersion(batchStatus.getTheVersion());
                }
                if (batchStatus.getPartnerToken() != null) {
                    existingBatchStatus.setPartnerToken(batchStatus.getPartnerToken());
                }

                return existingBatchStatus;
            })
            .map(batchStatusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchStatus> findAll(Pageable pageable) {
        log.debug("Request to get all BatchStatuses");
        return batchStatusRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BatchStatus> findOne(Long id) {
        log.debug("Request to get BatchStatus : {}", id);
        return batchStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BatchStatus : {}", id);
        batchStatusRepository.deleteById(id);
    }
}
