package com.softserve.paymentservice;

import com.stripe.exception.StripeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KickScooterPaymentServiceApplication {

    public static void main(String[] args) throws StripeException {
        SpringApplication.run(KickScooterPaymentServiceApplication.class, args);
    }
}

