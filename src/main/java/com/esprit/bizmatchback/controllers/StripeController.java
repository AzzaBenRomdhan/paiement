package com.esprit.bizmatchback.controllers;

import com.esprit.bizmatchback.entities.Payment;
import com.esprit.bizmatchback.repositories.PaymentRepository;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/payment")
public class StripeController {
    @Autowired
    private PaymentRepository paymentRepository;

    private String stripeApiKey;
    // create a Gson object
    private static Gson gson = new Gson();

    @PostMapping("/stripe")
    public Session createPaymentSession(@RequestBody Payment payment) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(payment.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(payment.getCurrency())
                                                .setUnitAmount(payment.getAmount())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(payment.getName())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );

        // Set the customer email
        SessionCreateParams params = paramsBuilder.build();
        // create a stripe session
        Session session = Session.create(params);
        String clientEmail = session.getCustomerEmail();
        Map<String, String> responseData = new HashMap<>();
        // We get the sessionId and we put it inside the response data. You can get more info from the session object.
        // responseData.put("id", session.getId());

        Payment newPayment = new Payment();
        newPayment.setAmount(payment.getAmount());
        newPayment.setCurrency(payment.getCurrency());
        paymentRepository.save(newPayment);

        return session;
    }

    private static void init() {
        Stripe.apiKey = "sk_test_51O1aSpLAROZ6Bd7tQO8zkxaD7r9E0qWkcVXoVwcOfSpRno1fPw4Tp1QgCqygpGA4fJFQm5jijnyhLoBmsM1gDA1H00rVpMZTcQ";
    }
}
