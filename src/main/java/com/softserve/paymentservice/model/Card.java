package com.softserve.paymentservice.model;

import lombok.*;

import javax.persistence.*;


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
    private boolean blocked;

//    @ManyToOne()
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

}
