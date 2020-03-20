package com.softserve.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.key.secret}")
    private String stripeSecretKey;


    private String getStripeCustomerId(Map<String, Object> userMap) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        Customer customer = Customer.create(userMap);
        return customer.getId();
    }


    private String getSourceId(String customerId, Map<String, Object> cardMap) throws StripeException {
        Customer customer = Customer.retrieve(customerId);
        cardMap.put("cvc", "123");
        Map<String, Object> tokenParameters = new HashMap<>();
        tokenParameters.put("card", cardMap);
        Token token = Token.create(tokenParameters);
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("source", token.getId());
        return customer.getSources().create(source).getId();

    }

    public Boolean charge(int amount, Map<String, Object> userMap, Map<String, Object> cardMap) throws StripeException {
        String customerId = getStripeCustomerId(userMap);
        String cardId = getSourceId(customerId, cardMap);

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "EUR");
        chargeParams.put("customer", customerId);
        chargeParams.put("card", cardId);
        Charge charge = Charge.create(chargeParams);
        return charge.getPaid();
    }


    public Refund checkingPayment(Map<String, Object> userMap, Map<String, Object> cardMap) throws StripeException {
        String customerId = getStripeCustomerId(userMap);
        String cardId = getSourceId(customerId, cardMap);

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", 100);
        chargeParams.put("currency", "EUR");
        chargeParams.put("customer", customerId);
        chargeParams.put("card", cardId);
        Charge charge = Charge.create(chargeParams);
        Refund refund = new Refund();
        boolean check = charge.getPaid();
        System.out.println("check = " + check);
        if (check) {
            Map<String, Object> refundParameters = new HashMap<>();
            refundParameters.put("charge", charge);
            refund = Refund.create(refundParameters);
            return refund;
        }

        charge.getBillingDetails();
        return refund;
    }

}
