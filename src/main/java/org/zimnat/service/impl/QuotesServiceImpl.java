package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.Quotes;
import org.zimnat.repository.QuotesRepository;
import org.zimnat.service.QuotesService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.Quotes}.
 */
@Service
@Transactional
public class QuotesServiceImpl implements QuotesService {

    private static final Logger log = LoggerFactory.getLogger(QuotesServiceImpl.class);

    private final QuotesRepository quotesRepository;

    public QuotesServiceImpl(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    @Override
    public Quotes save(Quotes quotes) {
        log.debug("Request to save Quotes : {}", quotes);
        return quotesRepository.save(quotes);
    }

    @Override
    public Quotes update(Quotes quotes) {
        log.debug("Request to update Quotes : {}", quotes);
        return quotesRepository.save(quotes);
    }

    @Override
    public Optional<Quotes> partialUpdate(Quotes quotes) {
        log.debug("Request to partially update Quotes : {}", quotes);

        return quotesRepository
            .findById(quotes.getId())
            .map(existingQuotes -> {
                if (quotes.getvRN() != null) {
                    existingQuotes.setvRN(quotes.getvRN());
                }
                if (quotes.getLicenceID() != null) {
                    existingQuotes.setLicenceID(quotes.getLicenceID());
                }
                if (quotes.getResult() != null) {
                    existingQuotes.setResult(quotes.getResult());
                }
                if (quotes.getMessage() != null) {
                    existingQuotes.setMessage(quotes.getMessage());
                }
                if (quotes.getiDNumber() != null) {
                    existingQuotes.setiDNumber(quotes.getiDNumber());
                }
                if (quotes.getClientIDType() != null) {
                    existingQuotes.setClientIDType(quotes.getClientIDType());
                }
                if (quotes.getFirstName() != null) {
                    existingQuotes.setFirstName(quotes.getFirstName());
                }
                if (quotes.getLastName() != null) {
                    existingQuotes.setLastName(quotes.getLastName());
                }
                if (quotes.getAddress1() != null) {
                    existingQuotes.setAddress1(quotes.getAddress1());
                }
                if (quotes.getAddress2() != null) {
                    existingQuotes.setAddress2(quotes.getAddress2());
                }
                if (quotes.getSuburbID() != null) {
                    existingQuotes.setSuburbID(quotes.getSuburbID());
                }
                if (quotes.getLicFrequency() != null) {
                    existingQuotes.setLicFrequency(quotes.getLicFrequency());
                }
                if (quotes.getRadioTVUsage() != null) {
                    existingQuotes.setRadioTVUsage(quotes.getRadioTVUsage());
                }
                if (quotes.getRadioTVFrequency() != null) {
                    existingQuotes.setRadioTVFrequency(quotes.getRadioTVFrequency());
                }
                if (quotes.getTaxClass() != null) {
                    existingQuotes.setTaxClass(quotes.getTaxClass());
                }
                if (quotes.getNettMass() != null) {
                    existingQuotes.setNettMass(quotes.getNettMass());
                }
                if (quotes.getLicExpiryDate() != null) {
                    existingQuotes.setLicExpiryDate(quotes.getLicExpiryDate());
                }
                if (quotes.getTransactionAmt() != null) {
                    existingQuotes.setTransactionAmt(quotes.getTransactionAmt());
                }
                if (quotes.getArrearsAmt() != null) {
                    existingQuotes.setArrearsAmt(quotes.getArrearsAmt());
                }
                if (quotes.getPenaltiesAmt() != null) {
                    existingQuotes.setPenaltiesAmt(quotes.getPenaltiesAmt());
                }
                if (quotes.getAdministrationAmt() != null) {
                    existingQuotes.setAdministrationAmt(quotes.getAdministrationAmt());
                }
                if (quotes.getTotalLicAmt() != null) {
                    existingQuotes.setTotalLicAmt(quotes.getTotalLicAmt());
                }
                if (quotes.getRadioTVAmt() != null) {
                    existingQuotes.setRadioTVAmt(quotes.getRadioTVAmt());
                }
                if (quotes.getRadioTVArrearsAmt() != null) {
                    existingQuotes.setRadioTVArrearsAmt(quotes.getRadioTVArrearsAmt());
                }
                if (quotes.getTotalRadioTVAmt() != null) {
                    existingQuotes.setTotalRadioTVAmt(quotes.getTotalRadioTVAmt());
                }
                if (quotes.getTotalAmount() != null) {
                    existingQuotes.setTotalAmount(quotes.getTotalAmount());
                }

                return existingQuotes;
            })
            .map(quotesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Quotes> findAll(Pageable pageable) {
        log.debug("Request to get all Quotes");
        return quotesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Quotes> findOne(Long id) {
        log.debug("Request to get Quotes : {}", id);
        return quotesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quotes : {}", id);
        quotesRepository.deleteById(id);
    }
}
