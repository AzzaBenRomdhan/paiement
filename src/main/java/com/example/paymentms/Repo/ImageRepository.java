package com.example.paymentms.Repo;

import com.example.paymentms.Model.Formation;
import com.example.paymentms.Model.ImageForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageForm, Integer> {
    Optional<ImageForm> findByName (String name);
    Optional<ImageForm>findByFormation_IdFormation(Integer idFormation);
}
