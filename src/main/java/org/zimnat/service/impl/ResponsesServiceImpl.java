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
import org.zimnat.domain.Responses;
import org.zimnat.repository.ResponsesRepository;
import org.zimnat.service.ResponsesService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.Responses}.
 */
@Service
@Transactional
public class ResponsesServiceImpl implements ResponsesService {

    private static final Logger log = LoggerFactory.getLogger(ResponsesServiceImpl.class);

    private final ResponsesRepository responsesRepository;

    public ResponsesServiceImpl(ResponsesRepository responsesRepository) {
        this.responsesRepository = responsesRepository;
    }

    @Override
    public Responses save(Responses responses) {
        log.debug("Request to save Responses : {}", responses);
        return responsesRepository.save(responses);
    }

    @Override
    public Responses update(Responses responses) {
        log.debug("Request to update Responses : {}", responses);
        return responsesRepository.save(responses);
    }

    @Override
    public Optional<Responses> partialUpdate(Responses responses) {
        log.debug("Request to partially update Responses : {}", responses);

        return responsesRepository
            .findById(responses.getId())
            .map(existingResponses -> {
                if (responses.getResult() != null) {
                    existingResponses.setResult(responses.getResult());
                }
                if (responses.getMessage() != null) {
                    existingResponses.setMessage(responses.getMessage());
                }

                return existingResponses;
            })
            .map(responsesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Responses> findAll(Pageable pageable) {
        log.debug("Request to get all Responses");
        return responsesRepository.findAll(pageable);
    }

    /**
     *  Get all the responses where FailureResponse is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Responses> findAllWhereFailureResponseIsNull() {
        log.debug("Request to get all responses where FailureResponse is null");
        return StreamSupport.stream(responsesRepository.findAll().spliterator(), false)
            .filter(responses -> responses.getFailureResponse() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Responses> findOne(Long id) {
        log.debug("Request to get Responses : {}", id);
        return responsesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responses : {}", id);
        responsesRepository.deleteById(id);
    }
}
