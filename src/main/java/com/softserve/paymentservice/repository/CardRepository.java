package com.softserve.paymentservice.repository;

import com.softserve.paymentservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    ArrayList<Card> findCardsByUserUUID(UUID userUUID);

    Card findCardByUserUUIDAndCardNumber(UUID userUUID, String cardNumber);

}
