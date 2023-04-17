package com.xa.filteringtest2_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.Biodata;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Long> {

    @Query(value = "SELECT * FROM m_biodata mu WHERE mb.id = ?1", nativeQuery = true)
    List<Biodata> findImageById(Long id);  

}