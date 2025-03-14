package com.philipe.demo.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philipe.demo.domains.model.TransferEntity;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long>{
    
}
