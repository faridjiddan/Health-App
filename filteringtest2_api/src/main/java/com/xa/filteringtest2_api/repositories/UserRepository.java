package com.xa.filteringtest2_api.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query(value = "SELECT mb.fullname, "+ 
                    "mb.id as biodata_id, " +
                    "mc.id as customer_id, " +
                    "mc.dob, " +
                    "mb.mobile_phone, " +
                    "mu.email, " +
                    "mu.password, " +
                    "mc.gender, " +
                    "mc.weight, " +
                    "mc.height, " +
                    "mc.rhesus_type, " +
                    "mb.image_path, " +
                    "mu.last_login " +
                    "FROM m_user mu " + 
                    "JOIN m_biodata mb ON mu.biodata_id = mb.id " +
                    "JOIN m_customer mc ON mb.id = mc.biodata_id ", nativeQuery = true)
    List<Map<String, Object>> getAllData();

    @Query(value = "SELECT mb.fullname, "+ 
                    "mb.id as biodata_id, " +
                    "mc.id as customer_id, " +
                    "mc.dob, " +
                    "mb.mobile_phone, " +
                    "mu.email, " +
                    "mu.password, " +
                    "mc.gender, " +
                    "mc.weight, " +
                    "mc.height, " +
                    "mc.rhesus_type, " +
                    "mb.image_path, " +
                    "mu.last_login, " +
                    // "mu.biodata_id, " +
                    "mu.created_by, " +
                    "mu.created_on, " +
                    "mu.is_delete, " +
                    "mu.role_id, " +
                    "mu.is_locked, " +
                    "mu.login_attempt " +
                    "FROM m_user mu " + 
                    "JOIN m_biodata mb ON mu.biodata_id = mb.id " +
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "WHERE mu.id = ?1", nativeQuery = true)
    List<Map<String, Object>> getDataById(Long id);


    @Query(value = "SELECT * "+ 
                    "FROM m_user mu " + 
                    "WHERE mu.last_login IS not null " +
                    "ORDER BY mu.last_login DESC LIMIT 1", nativeQuery = true)
    List<Map<String, Object>> getLastLogin();

    @Query(value = "SELECT mb.id as biodata_id, " + 
                    "mc.id as customer_id, " +
                    "mu.id as user_id, " +
                    "mu.last_login " +
                    "FROM m_user mu " + 
                    "JOIN m_biodata mb ON mu.biodata_id = mb.id " +
                    "JOIN m_customer mc ON mb.id = mc.biodata_id " +
                    "WHERE mu.last_login IS not null " +
                    "ORDER BY mu.last_login DESC LIMIT 1", nativeQuery = true)
    List<Map<String, Object>> getUserCustLastLogin();

    @Query("SELECT CASE WHEN COUNT(email) > 0 THEN true ELSE false END FROM User u WHERE u.email = ?1")
    Boolean emailExists(String email);

    @Query(value = "SELECT * FROM m_user mu WHERE mu.email = ?1", nativeQuery = true)
    List<User> findUserByEmail(String email);

    User findByEmail(String email);

    @Query(value = "SELECT * FROM m_user WHERE email = ?1 AND is_locked = false", nativeQuery = true)
    List<User> login(String email, String password);

    @Query(value = "SELECT * FROM m_user mu", nativeQuery = true)
    List<Map<String, Object>> getAllEmail();

    @Query(value = "select mu.id, " +
                    "(extract (year from mu.created_on)) as tahun_join, " +
                    "mb.id as biodata_id, " +
                    "mc.id as customer_id " +
                    "from m_user mu " +
                    "join m_biodata mb on mu.biodata_id = mb.id " +
                    "join m_customer mc on mb.id = mc.biodata_id "  +
                    "WHERE mu.id = ?1", nativeQuery = true)
    List<Map<String, Object>> getYear(Long id);

    // @Query(value = "SELECT * FROM m_user mu", nativeQuery = true)
    // List<Map<String, Object>> getResetPasssword(String oldpass, String newpass, String ket);

}
