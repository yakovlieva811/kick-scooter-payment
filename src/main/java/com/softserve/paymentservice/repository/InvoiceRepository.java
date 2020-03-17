package com.softserve.paymentservice.repository;

import com.softserve.paymentservice.model.Invoice;
import com.softserve.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Component
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    ArrayList<Invoice> findAllByUser(User user);

    Invoice findInvoiceByDateAndUser(Date date, User user);

    ArrayList<Invoice> findInvoicesByPayedAndUser(Boolean payed, User user);
}
