package com.esprit.bizmatchback.repositories;

import com.esprit.bizmatchback.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
