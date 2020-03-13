package com.softserve.paymentservice.repository;

import com.softserve.paymentservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    ArrayList<Invoice> findAllByUserUUID(UUID userUUID);

    Invoice findInvoiceByDateAndUserUUID(Date date, UUID userUUID);

    ArrayList<Invoice> findInvoicesByPayed(Boolean payed);
}
