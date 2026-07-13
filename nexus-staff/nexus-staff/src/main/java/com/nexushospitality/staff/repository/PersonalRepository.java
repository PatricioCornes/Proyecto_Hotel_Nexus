package com.nexushospitality.staff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexushospitality.staff.model.Personal;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {

    Optional<Personal> findByRun(String run);    

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Personal> findFirstByCargoIgnoreCaseAndDisponibleTrueOrderByIdAsc(String cargo);
}
