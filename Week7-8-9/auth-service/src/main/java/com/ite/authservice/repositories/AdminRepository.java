package com.ite.authservice.repositories;

import com.ite.authservice.entities.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin,String> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Admin findAdminByEmail(String email);
    Admin findAdminByAdminId(String id);

}
