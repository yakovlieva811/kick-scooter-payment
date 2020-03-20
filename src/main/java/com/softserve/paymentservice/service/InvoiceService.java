package com.softserve.paymentservice.service;

import com.softserve.paymentservice.model.Invoice;
import com.softserve.paymentservice.model.User;
import com.softserve.paymentservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(int amount, String currency, User user) {
        Invoice invoice = new Invoice();
        invoice.setAmount(amount);
        invoice.setCurrency(currency);
        invoice.setDate(new Date());
        invoice.setUser(user);
        invoice.setPayed(false);
        invoiceRepository.save(invoice);
        return invoice;
    }

    public Invoice makePayed(User user) {
        Invoice invoice = invoiceRepository.findInvoiceByPayedAndUser(false, user);
        invoice.setPayed(true);
        invoiceRepository.save(invoice);
        return invoice;
    }

    public List<Invoice> findInvoices(User user, Boolean payed) { //todo add check for two unpayed
        return invoiceRepository.findInvoicesByPayedAndUser(payed, user);
    }

    public List<Invoice> getInvoices(User user) {
        return invoiceRepository.findAllByUser(user);
    }

    public Invoice getUnpayed(User user){
        Invoice invoice = invoiceRepository.findInvoiceByPayedAndUser(false,user);
        return invoice;
    }
}
