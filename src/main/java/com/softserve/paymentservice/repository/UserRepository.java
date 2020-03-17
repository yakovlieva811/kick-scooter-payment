package com.softserve.paymentservice.repository;

import com.softserve.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserById(UUID userUUID);
    User findByCustomerIdFromStripe(String customerIdFromStripe);

}
