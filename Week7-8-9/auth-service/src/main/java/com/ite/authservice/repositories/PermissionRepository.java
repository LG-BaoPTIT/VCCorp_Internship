package com.ite.authservice.repositories;

import com.ite.authservice.entities.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends MongoRepository<Permission,String> {
    Permission findPermissionByPermissionId(String permissionId);
}
