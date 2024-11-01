package org.zimnat.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.domain.Client;
import org.zimnat.repository.ClientRepository;
import org.zimnat.service.ClientService;

/**
 * Service Implementation for managing {@link org.zimnat.domain.Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getTitle() != null) {
                    existingClient.setTitle(client.getTitle());
                }
                if (client.getFirstName() != null) {
                    existingClient.setFirstName(client.getFirstName());
                }
                if (client.getLastName() != null) {
                    existingClient.setLastName(client.getLastName());
                }
                if (client.getIdType() != null) {
                    existingClient.setIdType(client.getIdType());
                }
                if (client.getIdNumber() != null) {
                    existingClient.setIdNumber(client.getIdNumber());
                }
                if (client.getDob() != null) {
                    existingClient.setDob(client.getDob());
                }
                if (client.getPhoneNumber() != null) {
                    existingClient.setPhoneNumber(client.getPhoneNumber());
                }
                if (client.getStatus() != null) {
                    existingClient.setStatus(client.getStatus());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
