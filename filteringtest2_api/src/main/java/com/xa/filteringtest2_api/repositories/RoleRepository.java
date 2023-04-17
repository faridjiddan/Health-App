package com.xa.filteringtest2_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
