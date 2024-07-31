package com.ite.authservice.repositories;

import com.ite.authservice.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    User findUserByEmail(String email);
    User findUserByUserId(String id);
}
