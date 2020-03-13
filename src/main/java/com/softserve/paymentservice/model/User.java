package com.softserve.paymentservice.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@EntityScan
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "customerIdFromStripe")
    private String customerIdFromStripe;

    @Column(name = "userUUID")
    private UUID userUUID;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Card> cards;


//todo do we need a constructor ?

}
