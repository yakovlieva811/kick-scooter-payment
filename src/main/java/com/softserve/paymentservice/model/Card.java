package com.softserve.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.UUID;

@Data
@EntityScan
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardID;

    private UUID userUUID;
    private String cardNumber;
    private String expMonth;
    private String expYear;
    private boolean blocked;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="userId", nullable = false)
    private User user;

//    public Card(UUID userUUID, String cardNumber, String expMonth, String expYear, boolean blocked) {
//        this.userUUID = userUUID;
//        this.cardNumber = cardNumber;
//        this.expMonth = expMonth;
//        this.expYear = expYear;
//        this.blocked =blocked;
//    }
}
