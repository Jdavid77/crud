package com.devsuperiorjoaodavid.crud.repositories;

import com.devsuperiorjoaodavid.crud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

}
