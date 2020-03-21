package com.softserve.paymentservice;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KickScooterPaymentServiceApplication {

    public static void main(String[] args)   {
        SpringApplication.run(KickScooterPaymentServiceApplication.class, args);
    }
}


