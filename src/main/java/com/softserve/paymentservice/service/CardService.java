package com.softserve.paymentservice.service;

import com.softserve.paymentservice.exception.CardNotFoundException;
import com.softserve.paymentservice.model.Card;
import com.softserve.paymentservice.model.User;
import com.softserve.paymentservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public List<Card> getCardsByUserId(UUID userId) {
        return cardRepository.findByUserUUID(userId);
    }

    public Card addCardToUser(Card card) {
//        Card card = new CardDtoToModel().convert(cardDto);
//        card.setUser(user);
        cardRepository.save(card);
        return card;
    }

    public List<Card> getUserCards(User user) {
        List<Card> cards = cardRepository.findByUser(user);
        return cards;
    }

    public Map getCardMapByNumberAndUserId(String cardNumber, User user) {
        Card card = cardRepository.findCardByCardNumberAndUser(cardNumber, user).get();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", card.getCardNumber());
        cardMap.put("exp_month", card.getExpMonth());
        cardMap.put("exp_year", card.getExpYear());
        return cardMap;
    }

    public Card blockCard(String cardNumber, User user, Boolean block) throws CardNotFoundException {
        Card card = cardRepository.findCardByCardNumberAndUser(cardNumber, user).orElseThrow(CardNotFoundException::new);
        card.setBlocked(block);
        cardRepository.save(card);
        return card;
    }


}
