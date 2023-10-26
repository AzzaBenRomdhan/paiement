package com.example.paymentms.Controller;

import com.example.paymentms.Model.Formation;
import com.example.paymentms.Model.ImageForm;
import com.example.paymentms.Repo.FormationRepository;
import com.example.paymentms.Repo.ImageRepository;
import com.example.paymentms.services.IforamtionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:4200")

public class FormationController {
private final IforamtionService iforamtionService;
    private final FormationRepository formationRepository;


    @PostMapping("/addFormation")
    public Formation addFormation(@RequestBody Formation f) {

       return this.iforamtionService.AddFormation(f);
    }


    @GetMapping("/all")
    public List<Formation> getAllformation() {
        return this.iforamtionService.findAll();
}

    @GetMapping("/lastidpost")
    public Long lastidpost(){
        return this.formationRepository.findLastInsertedId();
    }


}
