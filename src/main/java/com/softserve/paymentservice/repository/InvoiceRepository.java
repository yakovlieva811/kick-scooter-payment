package com.softserve.paymentservice.repository;

import com.softserve.paymentservice.model.Invoice;
import com.softserve.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> findAllByUser(User user);

    Optional<Invoice> findInvoiceByDateAndUser(Date date, User user);

    List<Invoice> findInvoicesByPayedAndUser(Boolean payed, User user);

    Invoice findInvoiceByPayedAndUser(Boolean payed, User user);


}
