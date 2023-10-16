package com.esprit.bizmatchback.controllers;

import com.esprit.bizmatchback.entities.Payment;
import com.esprit.bizmatchback.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value= "/payment/payment")
@CrossOrigin(origins = "http://localhost:4200/")
public class PaymentController {
    @Autowired
    PaymentRepository paymentRepository;

    public PaymentController (PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @PostMapping("/create")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment){
        return ResponseEntity.ok(paymentRepository.save(payment));
    }
    @GetMapping("/payments")
    public ResponseEntity getAllPayments(){
        return  ResponseEntity.ok(this.paymentRepository.findAll());
    }
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    @PutMapping("/update/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment payment1 = paymentRepository.findById(id).orElse(null);
        if (payment1 != null) {
            payment1.setAmount(payment.getAmount());
            payment1.setDate(payment.getDate());
            payment1.setPaymentStatus(payment.getPaymentStatus());

            paymentRepository.save(payment1);
        }
    }
    @DeleteMapping("/delete/{idTache}")
    public void deletePaymentById(@PathVariable Long id) {
        paymentRepository.deleteById(id);
    }

}
