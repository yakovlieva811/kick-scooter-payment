package com.softserve.paymentservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cardNumber;
    private String expMonth;
    private String expYear;
    private boolean working;
    private UUID userId;

}
