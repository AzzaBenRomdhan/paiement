package com.example.paymentms.Controller;

import java.util.HashMap;
import java.util.Map;

import com.example.paymentms.Model.Payment;
import com.example.paymentms.Repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.example.paymentms.Model.CheckoutPayment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class StripeController {

    @Autowired
    private PaymentRepository paymentRepository;
    // create a Gson object
    @Value("${stripe.key.secret}")
    private String stripeApiKey;

    private static Gson gson = new Gson();

    @PostMapping("/payment")
    /**
     * Payment with Stripe checkout page
     *
     * @throws StripeException
     */
    public String paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) throws StripeException {
        // We initilize stripe object with the api key
        Stripe.apiKey = stripeApiKey;
        // We create a  stripe session parameters
        SessionCreateParams params = SessionCreateParams.builder()
                // We will use the credit card payment method
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(
                        payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                                        .builder().setName(payment.getName()).build())
                                                .build())
                                .build())
                .build();
        // create a stripe session
        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        // We get the sessionId and we putted inside the response data you can get more info from the session object
        responseData.put("id", session.getId());
        // We can return only the sessionId as a String

        Payment newPayment = new Payment();
        newPayment.setAmount(payment.getAmount());
        newPayment.setCurrency(payment.getCurrency());

        paymentRepository.save(newPayment);


        return gson.toJson(responseData);
    }






}