package com.xa.filteringtest2_api.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT mc.id, " +
                    "mcm.parent_biodata_id, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    // "JOIN m_customer_member mcm ON mb.id = mcm.parent_biodata_id " +
                    "JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "WHERE mcm.parent_biodata_id = ?1 and mb.is_delete is false and mc.is_delete is false and mcm.is_delete is false", nativeQuery = true)
    List<Map<String, Object>> getAllData(Long id);

    @Query(value = "SELECT mc.id, " +
                    "mc.biodata_id, " +
                    "mcm.id as custmember_id, " +
                    "mcm.customer_relation_id, " +
                    "mb.created_on as mbcreated_on, " +
                    "mc.created_on as mccreated_on, " +
                    "mcm.created_on as mcmcreated_on, " +
                    "mb.created_by as mbcreated_by, " +
                    "mc.created_by as mccreated_by, " +
                    "mcm.created_by as mcmcreated_by, " +
                    "mb.modified_on as mbmodified_on, " +
                    "mc.modified_on as mcmodified_on, " +
                    "mcm.modified_on as mcmmodified_on, " +
                    "mb.modified_by as mbmodified_by, " +
                    "mc.modified_by as mcodified_by, " +
                    "mcm.modified_by as mcmmodified_by, " +
                    "mb.deleted_on as mbdeleted_on, " +
                    "mc.deleted_on as mcdeleted_on, " +
                    "mcm.deleted_on as mcmdeleted_on, " +
                    "mb.deleted_by as mbdeleted_by, " +
                    "mc.deleted_by as mcdeleted_by, " +
                    "mcm.deleted_by as mcmdeleted_by, " +
                    "mb.is_delete as mbis_delete, " +
                    "mc.is_delete as mcis_delete, " +
                    "mcm.is_delete as mcmis_delete, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "mc.dob, " +
                    "mc.gender, " +
                    "mc.weight, " +
                    "mc.height, " +
                    "mb.mobile_phone, " +
                    "mcm.parent_biodata_id, " +
                    "mc.blood_group_id, " +
                    "mbg.code, " +
                    "mc.rhesus_type, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "full outer JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "full outer JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    "full outer JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "full outer JOIN m_blood_group mbg ON mc.blood_group_id = mbg.id " +
                    "WHERE mcm.parent_biodata_id = ?1 and mc.id = ?2 and mb.is_delete is false and mc.is_delete is false and mcm.is_delete is false", nativeQuery = true)
    List<Map<String, Object>> getDataById(Long biodataid, Long id);

    @Query(value = "SELECT mc.id, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    "JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "WHERE mcm.parent_biodata_id = ?1 AND mb.fullname ILIKE %?2% AND mb.is_delete is false and mc.is_delete is false and mcm.is_delete is false", nativeQuery = true)
    List<Map<String, Object>> getCustomerByName(Long id, String name);

    @Query(value = "SELECT mc.id, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    "JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "WHERE mcm.parent_biodata_id = ?1 and mb.is_delete is false AND mc.is_delete is false AND mcm.is_delete is false " +
                    "ORDER BY usia", nativeQuery = true)
    List<Map<String, Object>> sortByAgeAsc(Long id);

    @Query(value = "SELECT mc.id, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    "JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "WHERE mcm.parent_biodata_id = ?1 and mb.is_delete is false AND mc.is_delete is false AND mcm.is_delete is false " +
                    "ORDER BY mb.fullname", nativeQuery = true)
    List<Map<String, Object>> sortByNameAsc(Long id);

    @Query(value = "SELECT mc.id, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    "JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "WHERE mcm.parent_biodata_id = ?1 and mb.is_delete is false AND mc.is_delete is false AND mcm.is_delete is false " +
                    "ORDER BY usia DESC", nativeQuery = true)
    List<Map<String, Object>> sortByAgeDesc(Long id);

    @Query(value = "SELECT mc.id, " +
                    "mb.fullname, " +
                    "mcr.name, " +
                    "((extract (year from now())) - (extract (year from mc.dob))) as usia " +
                    "FROM m_biodata mb " + 
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "JOIN m_customer_member mcm ON mc.id = mcm.customer_id " +
                    "JOIN m_customer_relation mcr ON mcm.customer_relation_id = mcr.id " +
                    "WHERE mcm.parent_biodata_id = ?1 and mb.is_delete is false AND mc.is_delete is false AND mcm.is_delete is false " +
                    "ORDER BY mb.fullname DESC", nativeQuery = true)
    List<Map<String, Object>> sortByNameDesc(Long id);

    @Query(value = "SELECT tcc.customer_id, " +
                    "COUNT(tcc.customer_id) AS jumlah_chat " +
                    "FROM t_customer_chat tcc " +
                    "WHERE tcc.customer_id = ?1 " +
                    "GROUP BY tcc.customer_id ", nativeQuery = true)
    List<Map<String, Object>> getChat(Long id);

    @Query(value = "SELECT ta.customer_id, " +
                    "COUNT(ta.customer_id) AS jumlah_janji " +
                    "FROM t_appointment ta " +
                    "WHERE ta.customer_id = ?1 " +
                    "GROUP BY ta.customer_id ", nativeQuery = true)
    List<Map<String, Object>> getAppointment(Long id);


}