package com.xa.filteringtest2_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xa.filteringtest2_api.models.AppointmentDone;

@Repository
public interface AppointmentDoneRepository extends JpaRepository<AppointmentDone, Long> {

}