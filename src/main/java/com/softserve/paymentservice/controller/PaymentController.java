package com.softserve.paymentservice.controller;

import com.softserve.paymentservice.converter.CardDtoToModel;
import com.softserve.paymentservice.dto.CardDto;
import com.softserve.paymentservice.dto.PaymentDto;
import com.softserve.paymentservice.dto.PaymentInfoDto;
import com.softserve.paymentservice.exception.CardNotFoundException;
import com.softserve.paymentservice.model.Card;
import com.softserve.paymentservice.model.Invoice;
import com.softserve.paymentservice.service.AmountCalculator;
import com.softserve.paymentservice.service.CardService;
import com.softserve.paymentservice.service.InvoiceService;
import com.softserve.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/payment")
public class PaymentController {

    //todo update delete:  user card
    //todo   if invoice is unpaid: can't delete card   + check card by refund


    private final AmountCalculator amountCalculator;
    private final CardService cardService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;

    @PostMapping("/card/add")
    ResponseEntity<Card> addCard(@RequestBody CardDto cardDto) {
        Card card = new CardDtoToModel().convert(cardDto);
        cardService.addCardToUser(card);
        return ResponseEntity.created(URI.create("/card/" + card.getUserId())).build();
    }

    @PostMapping("/card/verification")
    ResponseEntity<Card> ckeckCard(@RequestBody PaymentDto paymentDto) throws StripeException, CardNotFoundException {

        boolean workingStatus = paymentService.checkingPayment(cardService.getUserCardByNumber(paymentDto.getCardNumber(), paymentDto.getUserId()), paymentDto.getCvc());

        Card card = cardService.setWorkingStatus(paymentDto.getCardNumber(), paymentDto.getUserId(), workingStatus);
//        return ResponseEntity.created(URI.create("/card/" + card.isWorking())).build();
        return ResponseEntity.ok(card);
    }


    @PostMapping("/card/all")
    ResponseEntity<List<Card>> cards(@RequestParam(name = "userId") UUID userId) { //todo is ot works?
        List<Card> cards = cardService.getCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }


    @PostMapping("/invoice/new")
    ResponseEntity<Invoice> getInvoice(@RequestBody PaymentInfoDto paymentInfoDto) {
        Invoice invoice = invoiceService.createInvoice(amountCalculator.calculateAmount(paymentInfoDto), paymentInfoDto.getCurrency(), paymentInfoDto.getUserid());
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/invoice/payment")
    ResponseEntity<Invoice> payInvoice(@RequestBody PaymentDto paymentDto) throws StripeException {

        Invoice invoice = invoiceService.getUnpayed(paymentDto.getUserId());
        Boolean successful = paymentService.pay(invoice.getAmount(), cardService.getUserCardByNumber(paymentDto.getCardNumber(), paymentDto.getUserId()), paymentDto.getCvc());
        if (successful) {
            invoiceService.makePayed(paymentDto.getUserId());
            return ResponseEntity.ok(invoice);
        } else {
            return (ResponseEntity<Invoice>) ResponseEntity.status(402); //todo ?
        }

    }

    @GetMapping("/invoice/all")
    ResponseEntity<List<Invoice>> getAllInvoices(@RequestParam(name = "userId") UUID userId) {
        List<Invoice> unpaid = invoiceService.findInvoices(userId);
        return ResponseEntity.ok(unpaid);
    }


//    @PostMapping("/payment")
//    ResponseEntity<Invoice> payInvoice(@RequestBody PaymentRequest paymentRequest) {
////        Invoice invoice1 = invoiceService.createInvoice(amountCalculator.calculateAmount(paymentRequest), paymentRequest.getCurrency(), userService.findUser(paymentRequest.getUserid()));
//        userService.findUser(paymentRequest.getUserid());
//        User user = userService.findUser(paymentRequest.getUserid());
//        List<Invoice> invoices = invoiceService.findInvoices(user, false);
//
//
//        invoices.size();
//
////        paymentService.charge();
//
//        return ResponseEntity.ok(invoice);
//    }
}