package com.philipe.demo.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.philipe.demo.application.dto.TransferDto;
import com.philipe.demo.application.mapper.TransferMapper;
import com.philipe.demo.domains.model.TransferEntity;
import com.philipe.demo.domains.repository.TransferRepository;
import com.philipe.demo.infra.external.ExternalAuthorizationService;
import com.philipe.demo.presentation.exception.RequestValidationException;

@Service
public class TransferService {
    

    @Autowired
    private final TransferMapper mapper;
    
    @Autowired
    private final UserService userService;
    
    @Autowired
    private final TransferRepository repository;

    private final ExternalAuthorizationService authorizationService;
    
    public TransferService(TransferMapper mapper, UserService userService, TransferRepository repository, ExternalAuthorizationService authorizationService){
        this.mapper = mapper;
        this.userService = userService;
        this.repository = repository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public TransferDto transferMoney(TransferDto dto) throws Exception{

        if (!this.authorizationService.authorize(dto)) {
            throw new RequestValidationException("External service did not authorize the transaction");
        }

        TransferEntity transfer = mapper.toEntity(dto);

        userService.withdrawFromUser(transfer.getValue(), transfer.getPayer());        
        userService.depositToUser(transfer.getValue(), transfer.getPayee());
        
        return mapper.toDto(repository.saveAndFlush(transfer));              


    }
}
