package com.example.paymentms.services;

import com.example.paymentms.Model.Formation;
import com.example.paymentms.Repo.FormationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormationImp implements IforamtionService{
    private final FormationRepository formationRepository;
    @Override
    public Formation AddFormation(Formation formation) {
        return this.formationRepository.save(formation);
    }

    @Override
    public List<Formation> findAll() {
        return this.formationRepository.findAll();
    }
}
