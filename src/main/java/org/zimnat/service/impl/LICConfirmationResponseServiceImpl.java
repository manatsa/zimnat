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
import org.zimnat.domain.LICConfirmationResponse;
import org.zimnat.repository.LICConfirmationResponseRepository;
import org.zimnat.service.LICConfirmationResponseService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.LICConfirmationResponse}.
 */
@Service
@Transactional
public class LICConfirmationResponseServiceImpl implements LICConfirmationResponseService {

    private static final Logger log = LoggerFactory.getLogger(LICConfirmationResponseServiceImpl.class);

    private final LICConfirmationResponseRepository lICConfirmationResponseRepository;

    public LICConfirmationResponseServiceImpl(LICConfirmationResponseRepository lICConfirmationResponseRepository) {
        this.lICConfirmationResponseRepository = lICConfirmationResponseRepository;
    }

    @Override
    public LICConfirmationResponse save(LICConfirmationResponse lICConfirmationResponse) {
        log.debug("Request to save LICConfirmationResponse : {}", lICConfirmationResponse);
        return lICConfirmationResponseRepository.save(lICConfirmationResponse);
    }

    @Override
    public LICConfirmationResponse update(LICConfirmationResponse lICConfirmationResponse) {
        log.debug("Request to update LICConfirmationResponse : {}", lICConfirmationResponse);
        return lICConfirmationResponseRepository.save(lICConfirmationResponse);
    }

    @Override
    public Optional<LICConfirmationResponse> partialUpdate(LICConfirmationResponse lICConfirmationResponse) {
        log.debug("Request to partially update LICConfirmationResponse : {}", lICConfirmationResponse);

        return lICConfirmationResponseRepository
            .findById(lICConfirmationResponse.getId())
            .map(existingLICConfirmationResponse -> {
                if (lICConfirmationResponse.getResult() != null) {
                    existingLICConfirmationResponse.setResult(lICConfirmationResponse.getResult());
                }
                if (lICConfirmationResponse.getMessage() != null) {
                    existingLICConfirmationResponse.setMessage(lICConfirmationResponse.getMessage());
                }
                if (lICConfirmationResponse.getLicenceID() != null) {
                    existingLICConfirmationResponse.setLicenceID(lICConfirmationResponse.getLicenceID());
                }
                if (lICConfirmationResponse.getReceiptID() != null) {
                    existingLICConfirmationResponse.setReceiptID(lICConfirmationResponse.getReceiptID());
                }
                if (lICConfirmationResponse.getvRN() != null) {
                    existingLICConfirmationResponse.setvRN(lICConfirmationResponse.getvRN());
                }
                if (lICConfirmationResponse.getStatus() != null) {
                    existingLICConfirmationResponse.setStatus(lICConfirmationResponse.getStatus());
                }
                if (lICConfirmationResponse.getLoadedBy() != null) {
                    existingLICConfirmationResponse.setLoadedBy(lICConfirmationResponse.getLoadedBy());
                }
                if (lICConfirmationResponse.getLoadedDate() != null) {
                    existingLICConfirmationResponse.setLoadedDate(lICConfirmationResponse.getLoadedDate());
                }
                if (lICConfirmationResponse.getApprovedBy() != null) {
                    existingLICConfirmationResponse.setApprovedBy(lICConfirmationResponse.getApprovedBy());
                }
                if (lICConfirmationResponse.getApprovedDate() != null) {
                    existingLICConfirmationResponse.setApprovedDate(lICConfirmationResponse.getApprovedDate());
                }
                if (lICConfirmationResponse.getiDNumber() != null) {
                    existingLICConfirmationResponse.setiDNumber(lICConfirmationResponse.getiDNumber());
                }
                if (lICConfirmationResponse.getFirstName() != null) {
                    existingLICConfirmationResponse.setFirstName(lICConfirmationResponse.getFirstName());
                }
                if (lICConfirmationResponse.getLastName() != null) {
                    existingLICConfirmationResponse.setLastName(lICConfirmationResponse.getLastName());
                }
                if (lICConfirmationResponse.getAddress1() != null) {
                    existingLICConfirmationResponse.setAddress1(lICConfirmationResponse.getAddress1());
                }
                if (lICConfirmationResponse.getAddress2() != null) {
                    existingLICConfirmationResponse.setAddress2(lICConfirmationResponse.getAddress2());
                }
                if (lICConfirmationResponse.getSuburbID() != null) {
                    existingLICConfirmationResponse.setSuburbID(lICConfirmationResponse.getSuburbID());
                }
                if (lICConfirmationResponse.getTransactionAmt() != null) {
                    existingLICConfirmationResponse.setTransactionAmt(lICConfirmationResponse.getTransactionAmt());
                }
                if (lICConfirmationResponse.getArrearsAmt() != null) {
                    existingLICConfirmationResponse.setArrearsAmt(lICConfirmationResponse.getArrearsAmt());
                }
                if (lICConfirmationResponse.getPenaltiesAmt() != null) {
                    existingLICConfirmationResponse.setPenaltiesAmt(lICConfirmationResponse.getPenaltiesAmt());
                }
                if (lICConfirmationResponse.getAdministrationAmt() != null) {
                    existingLICConfirmationResponse.setAdministrationAmt(lICConfirmationResponse.getAdministrationAmt());
                }
                if (lICConfirmationResponse.getTotalLicAmt() != null) {
                    existingLICConfirmationResponse.setTotalLicAmt(lICConfirmationResponse.getTotalLicAmt());
                }
                if (lICConfirmationResponse.getRadioTVAmt() != null) {
                    existingLICConfirmationResponse.setRadioTVAmt(lICConfirmationResponse.getRadioTVAmt());
                }
                if (lICConfirmationResponse.getRadioTVArrearsAmt() != null) {
                    existingLICConfirmationResponse.setRadioTVArrearsAmt(lICConfirmationResponse.getRadioTVArrearsAmt());
                }
                if (lICConfirmationResponse.getTotalRadioTVAmt() != null) {
                    existingLICConfirmationResponse.setTotalRadioTVAmt(lICConfirmationResponse.getTotalRadioTVAmt());
                }
                if (lICConfirmationResponse.getTotalAmount() != null) {
                    existingLICConfirmationResponse.setTotalAmount(lICConfirmationResponse.getTotalAmount());
                }
                if (lICConfirmationResponse.getTaxClass() != null) {
                    existingLICConfirmationResponse.setTaxClass(lICConfirmationResponse.getTaxClass());
                }
                if (lICConfirmationResponse.getNettMass() != null) {
                    existingLICConfirmationResponse.setNettMass(lICConfirmationResponse.getNettMass());
                }
                if (lICConfirmationResponse.getLicExpiryDate() != null) {
                    existingLICConfirmationResponse.setLicExpiryDate(lICConfirmationResponse.getLicExpiryDate());
                }
                if (lICConfirmationResponse.getRadioTVExpiryDate() != null) {
                    existingLICConfirmationResponse.setRadioTVExpiryDate(lICConfirmationResponse.getRadioTVExpiryDate());
                }

                return existingLICConfirmationResponse;
            })
            .map(lICConfirmationResponseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LICConfirmationResponse> findAll(Pageable pageable) {
        log.debug("Request to get all LICConfirmationResponses");
        return lICConfirmationResponseRepository.findAll(pageable);
    }

    /**
     *  Get all the lICConfirmationResponses where LICResultResponse is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LICConfirmationResponse> findAllWhereLICResultResponseIsNull() {
        log.debug("Request to get all lICConfirmationResponses where LICResultResponse is null");
        return StreamSupport.stream(lICConfirmationResponseRepository.findAll().spliterator(), false)
            .filter(lICConfirmationResponse -> lICConfirmationResponse.getLICResultResponse() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LICConfirmationResponse> findOne(Long id) {
        log.debug("Request to get LICConfirmationResponse : {}", id);
        return lICConfirmationResponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LICConfirmationResponse : {}", id);
        lICConfirmationResponseRepository.deleteById(id);
    }
}
