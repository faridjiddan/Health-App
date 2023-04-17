package com.xa.filteringtest2_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "SELECT * FROM t_token tt WHERE tt.token = ?1", nativeQuery = true)
    List<Token> findToken(String token);

}
