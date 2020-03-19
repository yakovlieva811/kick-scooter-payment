package com.softserve.paymentservice.repository;

import com.softserve.paymentservice.model.Card;
import com.softserve.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface CardRepository extends JpaRepository<Card, UUID> {

    List<Card> findByUser(User user);

    Optional<Card> findCardByCardNumberAndUser(String cardNumber, User user);

//    ArrayList<Card> findCardsByUserUUID(UUID userUUID);
//
//    Card findCardByUserUUIDAndCardNumber(UUID userUUID, String cardNumber);

}
