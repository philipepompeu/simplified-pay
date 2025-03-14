package com.philipe.demo.domains.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philipe.demo.domains.model.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long>{
    
    public Optional<UserEntity> findByLegalIdentifierOrEmail(String legalIdentifier, String email);
}
