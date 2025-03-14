package com.philipe.demo.application.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philipe.demo.application.dto.TransferDto;
import com.philipe.demo.domains.model.TransferEntity;
import com.philipe.demo.domains.model.UserEntity;
import com.philipe.demo.domains.repository.UserEntityRepository;

@Service
public class TransferMapper {
    
    @Autowired
    private final UserEntityRepository usersRepository;
    
    public TransferMapper(UserEntityRepository users){
        this.usersRepository = users;
    }

    public TransferEntity toEntity(TransferDto dto){
        
        UserEntity payee = this.usersRepository.findById(dto.getPayee()).orElseThrow();
        UserEntity payer = this.usersRepository.findById(dto.getPayer()).orElseThrow();
        
        System.out.println(String.format("Payee id [ %d ]", payee.getId()));
        System.out.println(String.format("Payer id [ %d ]", payer.getId()));

        TransferEntity entity = new TransferEntity();
        entity.setPayee(payee);
        entity.setPayer(payer);
        entity.setValue(dto.getValue());

        return entity;

    }

    public TransferDto toDto(TransferEntity entity){

        return new TransferDto( entity.getValue(),
                                entity.getPayer().getId(),
                                entity.getPayee().getId(),
                                entity.getCreated_at());
    }
}
