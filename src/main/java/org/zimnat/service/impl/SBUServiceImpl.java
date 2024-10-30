package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.SBU;
import org.zimnat.repository.SBURepository;
import org.zimnat.service.SBUService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.SBU}.
 */
@Service
@Transactional
public class SBUServiceImpl implements SBUService {

    private static final Logger log = LoggerFactory.getLogger(SBUServiceImpl.class);

    private final SBURepository sBURepository;

    public SBUServiceImpl(SBURepository sBURepository) {
        this.sBURepository = sBURepository;
    }

    @Override
    public SBU save(SBU sBU) {
        log.debug("Request to save SBU : {}", sBU);
        return sBURepository.save(sBU);
    }

    @Override
    public SBU update(SBU sBU) {
        log.debug("Request to update SBU : {}", sBU);
        return sBURepository.save(sBU);
    }

    @Override
    public Optional<SBU> partialUpdate(SBU sBU) {
        log.debug("Request to partially update SBU : {}", sBU);

        return sBURepository
            .findById(sBU.getId())
            .map(existingSBU -> {
                if (sBU.getName() != null) {
                    existingSBU.setName(sBU.getName());
                }
                if (sBU.getCode() != null) {
                    existingSBU.setCode(sBU.getCode());
                }
                if (sBU.getAddress() != null) {
                    existingSBU.setAddress(sBU.getAddress());
                }
                if (sBU.getStatus() != null) {
                    existingSBU.setStatus(sBU.getStatus());
                }

                return existingSBU;
            })
            .map(sBURepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SBU> findAll(Pageable pageable) {
        log.debug("Request to get all SBUS");
        return sBURepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SBU> findOne(Long id) {
        log.debug("Request to get SBU : {}", id);
        return sBURepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SBU : {}", id);
        sBURepository.deleteById(id);
    }
}
