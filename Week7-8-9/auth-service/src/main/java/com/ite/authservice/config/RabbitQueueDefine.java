package com.ite.authservice.config;


import com.ite.authservice.constants.JobQueue;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueDefine {

    @Bean
    public Queue emailApproveAccountQueue() {
        return new Queue(JobQueue.APPROVED_ACCOUNT_NOTICE_QUEUE, true);
    }

    @Bean
    public Queue createAdminAccountQueue(){ return new Queue(JobQueue.CREATE_ADMIN_ACCOUNT_QUEUE,true);}

    @Bean
    public Queue updateAdminAccountQueue(){return new Queue(JobQueue.UPDATE_ADMIN_ACCOUNT_QUEUE,true);}

    @Bean
    public Queue resetPasswordQueue(){return  new Queue(JobQueue.RESET_PASSWORD_NOTICE_QUEUE,true);}

    @Bean
    public Queue updateRoleGroup(){return  new Queue(JobQueue.UPDATE_ROLE,true);}
    @Bean
    public Queue deleteRoleGroup(){return  new Queue(JobQueue.DELETE_ROLE,true);}

    @Bean
    public Queue addRoleGroupQueue(){return new Queue(JobQueue.ADD_ROLE_GROUP_QUEUE,true);}


    //    ===========================================================================================================================
    @Bean
    public DirectExchange emailExchange(){
        return new DirectExchange("email-exchange");
    }

    @Bean
    public DirectExchange adminAccountExchange(){ return  new DirectExchange("adminAccount-exchange");}

    @Bean
    public DirectExchange roleGroupExchange(){ return  new DirectExchange("roleGroup-exchange");}

//    ===========================================================================================================================

    @Bean
    public Binding bindingEmailExchangeToQueue(Queue emailApproveAccountQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailApproveAccountQueue).to(emailExchange).with("approveAccountNotice.routing.key");
    }

    @Bean
    public Binding bindingCreateAdminAccountQueueToAdminAccountExchange(Queue createAdminAccountQueue, DirectExchange adminAccountExchange){
        return BindingBuilder.bind(createAdminAccountQueue).to(adminAccountExchange).with("createAdminAccount.routing.key");
    }

    @Bean
    public Binding bindingUpdateAdminAccountQueueToAdminAccountExchange(Queue updateAdminAccountQueue, DirectExchange adminAccountExchange){
        return BindingBuilder.bind(updateAdminAccountQueue).to(adminAccountExchange).with("updateAdminAccount.routing.key");
    }

    @Bean
    public Binding bindingResetPasswordQueueToEmailExchange(Queue resetPasswordQueue, DirectExchange emailExchange){
        return BindingBuilder.bind(resetPasswordQueue).to(emailExchange).with("resetPassword.routing.key");
    }

    @Bean Binding bindingAddRoleGroupQueueToRoleGroupExchange(Queue addRoleGroupQueue,DirectExchange roleGroupExchange){
        return BindingBuilder.bind(addRoleGroupQueue).to(roleGroupExchange).with("addRoleGroup.routing.key");
    }

    @Bean
    public Binding bindingUpdateRoleGroupQueueToRoleGroupExchange(Queue updateRoleGroup, DirectExchange roleGroupExchange){
        return BindingBuilder.bind(updateRoleGroup).to(roleGroupExchange).with("updaterole.routing.key");
    }
    @Bean
    public Binding bindingDeleteRoleGroupQueueToRoleGroupExchange(Queue deleteRoleGroup, DirectExchange roleGroupExchange){
        return BindingBuilder.bind(deleteRoleGroup).to(roleGroupExchange).with("deleteRole.routing.key");
    }


    @Bean
    public Queue lockAccountNoticeQueue(){
        return new Queue(JobQueue.LOCK_ACCOUNT_NOTICE_QUEUE,true);
    }

    @Bean
    public Binding bindingLockAccountNoticeQueueToEmailExchange(Queue lockAccountNoticeQueue, DirectExchange emailExchange){
        return BindingBuilder.bind(lockAccountNoticeQueue).to(emailExchange).with("lockAccountNotice.routing.key");
    }

}
