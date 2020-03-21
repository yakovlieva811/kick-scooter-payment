package com.softserve.paymentservice.service;

import com.softserve.paymentservice.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
//
//    private final UserRepository userRepository;
//
//    public User createUser(UUID userId) {
//        User user = new User();
//        user.setId(userId);
//        userRepository.save(user);
//        return user;
//    }
//
//    public User findUser(UUID userId) {
//        User user = userRepository.findUserById(userId).orElseThrow(()-> new RuntimeException());
//        return user;
//    }
//
//
//    public Map getUserMap(UUID userId) {
//        User user = userRepository.findUserById(userId).orElse(createUser(userId));
//        Map<String, Object> userMap = new HashMap<>();
//        userMap.put("name",  user.getId());
//        return userMap;
//    }
//
//    public User addStripeId(UUID userId, String stripeId) {
//        User user = findUser(userId);
//        user.setCustomerIdFromStripe(stripeId);
//        return user;
//    }
//
//    public User addCard(Card card, UUID userId){
//        User user = findUser(userId);
//    }
}
