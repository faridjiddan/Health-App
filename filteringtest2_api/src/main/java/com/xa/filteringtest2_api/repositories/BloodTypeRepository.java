package com.xa.filteringtest2_api.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.BloodType;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Long> {
    
    @Query(value = "SELECT * FROM m_blood_group WHERE code ILIKE %?1% and is_delete = false", nativeQuery = true)
    List<Map<String, Object>> getBloodTypeByCode(String code);

    @Query(value = "SELECT mbg.code FROM m_blood_group mbg WHERE mbg.code = ?1 and mbg.is_delete = false", nativeQuery = true)
    List<Map<String, Object>> getValidationCode(String code);
    
    // Masih belum mau muncul

    @Query(value = "select mbg.id, " +
                    "mbg.code, " +
                    "mbg.description, " +
                    "mbg.created_by, " +
                    "mb.fullname " +
                    "from m_blood_group mbg " +
                    "left join m_user mu on mbg.modified_by = mu.id " +
                    "left join m_biodata mb on mu.biodata_id  = mb.id " +
                    "where mbg.is_delete = false ", nativeQuery = true)
    List<Map<String, Object>> getAllBloodData();

    // @Query(value = "SELECT bt from BloodType bt LEFT JOIN bt.modifiedBy mu LEFT JOIN u.biodata b")
    // List<BloodType> getAllBloodData();

}