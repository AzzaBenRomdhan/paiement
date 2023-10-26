package com.example.paymentms.services;

import com.example.paymentms.Model.Formation;

import java.util.List;

public interface IforamtionService {
    Formation AddFormation(Formation formation);
    List<Formation> findAll();


}
