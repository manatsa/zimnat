package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.TransDetails;
import org.zimnat.repository.TransDetailsRepository;
import org.zimnat.service.TransDetailsService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.TransDetails}.
 */
@Service
@Transactional
public class TransDetailsServiceImpl implements TransDetailsService {

    private static final Logger log = LoggerFactory.getLogger(TransDetailsServiceImpl.class);

    private final TransDetailsRepository transDetailsRepository;

    public TransDetailsServiceImpl(TransDetailsRepository transDetailsRepository) {
        this.transDetailsRepository = transDetailsRepository;
    }

    @Override
    public TransDetails save(TransDetails transDetails) {
        log.debug("Request to save TransDetails : {}", transDetails);
        return transDetailsRepository.save(transDetails);
    }

    @Override
    public TransDetails update(TransDetails transDetails) {
        log.debug("Request to update TransDetails : {}", transDetails);
        return transDetailsRepository.save(transDetails);
    }

    @Override
    public Optional<TransDetails> partialUpdate(TransDetails transDetails) {
        log.debug("Request to partially update TransDetails : {}", transDetails);

        return transDetailsRepository
            .findById(transDetails.getId())
            .map(existingTransDetails -> {
                if (transDetails.getPolicyRef() != null) {
                    existingTransDetails.setPolicyRef(transDetails.getPolicyRef());
                }
                if (transDetails.getCoverStartDate() != null) {
                    existingTransDetails.setCoverStartDate(transDetails.getCoverStartDate());
                }
                if (transDetails.getCoverEndDate() != null) {
                    existingTransDetails.setCoverEndDate(transDetails.getCoverEndDate());
                }
                if (transDetails.getPremium() != null) {
                    existingTransDetails.setPremium(transDetails.getPremium());
                }
                if (transDetails.getPayType() != null) {
                    existingTransDetails.setPayType(transDetails.getPayType());
                }
                if (transDetails.getBusType() != null) {
                    existingTransDetails.setBusType(transDetails.getBusType());
                }
                if (transDetails.getExtStatus() != null) {
                    existingTransDetails.setExtStatus(transDetails.getExtStatus());
                }
                if (transDetails.getTransStatus() != null) {
                    existingTransDetails.setTransStatus(transDetails.getTransStatus());
                }
                if (transDetails.getSyncStatus() != null) {
                    existingTransDetails.setSyncStatus(transDetails.getSyncStatus());
                }
                if (transDetails.getCurrency() != null) {
                    existingTransDetails.setCurrency(transDetails.getCurrency());
                }

                return existingTransDetails;
            })
            .map(transDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransDetails> findAll(Pageable pageable) {
        log.debug("Request to get all TransDetails");
        return transDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransDetails> findOne(Long id) {
        log.debug("Request to get TransDetails : {}", id);
        return transDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransDetails : {}", id);
        transDetailsRepository.deleteById(id);
    }
}
