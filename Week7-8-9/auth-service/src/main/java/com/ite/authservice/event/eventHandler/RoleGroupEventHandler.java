package com.ite.authservice.event.eventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ite.authservice.config.SystemLogger;
import com.ite.authservice.constants.JobQueue;
import com.ite.authservice.constants.LogStepConstant;
import com.ite.authservice.payload.dto.RoleGroupDTO;
import com.ite.authservice.service.RoleGroupService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleGroupEventHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleGroupService roleGroupService;

    @Autowired
    private SystemLogger logger;



    @RabbitHandler
    @RabbitListener(queues = JobQueue.ADD_ROLE_GROUP_QUEUE)
    public void createRoleGroup(String data){
        try {
            logger.log(Thread.currentThread().getName(), "Handler event create role group", LogStepConstant.BEGIN_PROCESS,data);
            RoleGroupDTO roleGroupDTO = objectMapper.readValue(data, RoleGroupDTO.class);
            roleGroupService.create(roleGroupDTO);
            logger.log(Thread.currentThread().getName(), "Handler event create role group", LogStepConstant.END_PROCESS,data);
        } catch (JsonProcessingException e) {
            logger.log(Thread.currentThread().getName(), "Handler event create role group", LogStepConstant.END_PROCESS,"ERROR: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @RabbitHandler
    @RabbitListener(queues = JobQueue.UPDATE_ROLE)
    public void updateAdminAccount(String json){

        RoleGroupDTO roleGroupDTO = null;
        try {
            roleGroupDTO = objectMapper.readValue(json, RoleGroupDTO.class);
            roleGroupService.updateRoleGroup(roleGroupDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    @RabbitHandler
    @RabbitListener(queues = JobQueue.DELETE_ROLE)
    public void deleteAdminAccount(String roleGroupId){
        roleGroupService.deleteRoleGroup(roleGroupId);
    }
}
