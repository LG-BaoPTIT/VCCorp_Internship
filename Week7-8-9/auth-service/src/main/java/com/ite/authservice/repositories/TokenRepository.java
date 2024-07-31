package com.ite.authservice.repositories;

import com.ite.authservice.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token,String> {
    boolean existsTokenByAccessToken(String accessToken);
}
