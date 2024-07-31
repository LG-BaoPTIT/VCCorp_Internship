package com.ite.authservice.repositories;

import com.ite.authservice.entities.RoleGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleGroupRepository extends MongoRepository<RoleGroup,String> {
    boolean existsByRoleGroupId(String roleGroupId);
    boolean existsByName(String name);
    RoleGroup findRoleGroupByRoleGroupId(String roleGroupId);

}
