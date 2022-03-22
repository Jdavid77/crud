package com.devsuperiorjoaodavid.crud.services;


import com.devsuperiorjoaodavid.crud.dto.ClientDTO;
import com.devsuperiorjoaodavid.crud.entities.Client;
import com.devsuperiorjoaodavid.crud.repositories.ClientRepository;
import com.devsuperiorjoaodavid.crud.services.exceptions.DatabaseException;
import com.devsuperiorjoaodavid.crud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> clients = repository.findAll(pageRequest);
        Page<ClientDTO> dtoPage = clients.map(ClientDTO::new);
        return dtoPage;
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Optional<Client> obj = repository.findById(id);
        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("ID Not Found!"));
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto){
        Client client = new Client();
        client.setName(dto.getName());
        client.setBirthDate(dto.getBirthDate());
        client.setCpf(dto.getCpf());
        client.setChildren(dto.getChildren());
        client.setIncome(dto.getIncome());
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id , ClientDTO dto){
        try {
            Client entity = repository.getById(id);
            entity.setName(dto.getName());
            entity.setBirthDate(dto.getBirthDate());
            entity.setCpf(dto.getCpf());
            entity.setIncome(dto.getIncome());
            entity.setChildren(dto.getChildren());
            entity = repository.save(entity);
            return new ClientDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("ID Not Found: " + id);

        }
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("ID Not Found: " + id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity Violation!");
        }
    }









}
