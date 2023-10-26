package com.example.paymentms.Repo;

import com.example.paymentms.Model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
    @Query("SELECT MAX(f.idFormation) FROM Formation f")
    Long findLastInsertedId();

}
