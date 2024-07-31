package com.ite.authservice.repositories;

import com.ite.authservice.entities.MenuInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuInfoRepository extends MongoRepository<MenuInfo,String> {
    MenuInfo findMenuInfosByPermissionId(String permissionId);
}
