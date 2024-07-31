package com.ite.authservice.event.eventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ite.authservice.constants.JobQueue;
import com.ite.authservice.payload.dto.AdminDTO;
import com.ite.authservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;



    @RabbitHandler
    @RabbitListener(queues = JobQueue.CREATE_ADMIN_ACCOUNT_QUEUE)
    public void createAdminAccount(String json){
        try {
            AdminDTO adminDTO = objectMapper.readValue(json, AdminDTO.class);
            accountService.createAdminAccount(adminDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitHandler
    @RabbitListener(queues = JobQueue.UPDATE_ADMIN_ACCOUNT_QUEUE)
    public void updateAdminAccount(String json){
        try {
            AdminDTO adminDTO = objectMapper.readValue(json, AdminDTO.class);
            accountService.updateAdminAccount(adminDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
