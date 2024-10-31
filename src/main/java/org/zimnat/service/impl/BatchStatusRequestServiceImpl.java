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
import org.zimnat.domain.BatchStatusRequest;
import org.zimnat.repository.BatchStatusRequestRepository;
import org.zimnat.service.BatchStatusRequestService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.BatchStatusRequest}.
 */
@Service
@Transactional
public class BatchStatusRequestServiceImpl implements BatchStatusRequestService {

    private static final Logger log = LoggerFactory.getLogger(BatchStatusRequestServiceImpl.class);

    private final BatchStatusRequestRepository batchStatusRequestRepository;

    public BatchStatusRequestServiceImpl(BatchStatusRequestRepository batchStatusRequestRepository) {
        this.batchStatusRequestRepository = batchStatusRequestRepository;
    }

    @Override
    public BatchStatusRequest save(BatchStatusRequest batchStatusRequest) {
        log.debug("Request to save BatchStatusRequest : {}", batchStatusRequest);
        return batchStatusRequestRepository.save(batchStatusRequest);
    }

    @Override
    public BatchStatusRequest update(BatchStatusRequest batchStatusRequest) {
        log.debug("Request to update BatchStatusRequest : {}", batchStatusRequest);
        return batchStatusRequestRepository.save(batchStatusRequest);
    }

    @Override
    public Optional<BatchStatusRequest> partialUpdate(BatchStatusRequest batchStatusRequest) {
        log.debug("Request to partially update BatchStatusRequest : {}", batchStatusRequest);

        return batchStatusRequestRepository
            .findById(batchStatusRequest.getId())
            .map(existingBatchStatusRequest -> {
                if (batchStatusRequest.getTheFunction() != null) {
                    existingBatchStatusRequest.setTheFunction(batchStatusRequest.getTheFunction());
                }
                if (batchStatusRequest.getInsuranceIDBatch() != null) {
                    existingBatchStatusRequest.setInsuranceIDBatch(batchStatusRequest.getInsuranceIDBatch());
                }

                return existingBatchStatusRequest;
            })
            .map(batchStatusRequestRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchStatusRequest> findAll(Pageable pageable) {
        log.debug("Request to get all BatchStatusRequests");
        return batchStatusRequestRepository.findAll(pageable);
    }

    /**
     *  Get all the batchStatusRequests where BatchStatus is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BatchStatusRequest> findAllWhereBatchStatusIsNull() {
        log.debug("Request to get all batchStatusRequests where BatchStatus is null");
        return StreamSupport.stream(batchStatusRequestRepository.findAll().spliterator(), false)
            .filter(batchStatusRequest -> batchStatusRequest.getBatchStatus() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BatchStatusRequest> findOne(Long id) {
        log.debug("Request to get BatchStatusRequest : {}", id);
        return batchStatusRequestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BatchStatusRequest : {}", id);
        batchStatusRequestRepository.deleteById(id);
    }
}
