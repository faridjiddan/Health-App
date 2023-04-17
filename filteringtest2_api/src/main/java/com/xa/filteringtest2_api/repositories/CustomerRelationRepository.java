package com.xa.filteringtest2_api.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.CustomerRelation;

@Repository
public interface CustomerRelationRepository extends JpaRepository<CustomerRelation, Long> {
    
    @Query(value = "SELECT * FROM m_customer_relation WHERE name ILIKE %?1%", nativeQuery = true)
    List<Map<String, Object>> getRelationByName(String name);
}