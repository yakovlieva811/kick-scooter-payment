package com.softserve.paymentservice.controller;

import com.softserve.paymentservice.converter.CardDtoToModel;
import com.softserve.paymentservice.dto.CardDto;
import com.softserve.paymentservice.dto.PaymentDto;
import com.softserve.paymentservice.dto.PaymentInfoDto;
import com.softserve.paymentservice.dto.UserDto;
import com.softserve.paymentservice.model.Card;
import com.softserve.paymentservice.model.Invoice;
import com.softserve.paymentservice.model.User;
import com.softserve.paymentservice.service.*;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/payment")
public class PaymentController {

    //todo update delete:  user card
    //todo   if invoice is unpaid: can't delete card   + check card by refund


    private final AmountCalculator amountCalculator;
    private final CardService cardService;
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;

    @PostMapping("/card/add")
    ResponseEntity<Card> addCard(@RequestBody CardDto cardDto) {
        Card card = new CardDtoToModel().convert(cardDto);
        User user = userService.createUser(cardDto.getUserUUID());
        user.getCards().add(card);

        card.setUser(user);
        cardService.addCardToUser(card);
        return ResponseEntity.created(URI.create("/card/" + card.getUser().getId())).build();
    }


    @PostMapping("/card/all")
    ResponseEntity<Set<Card>> cards(@RequestBody UserDto userDto) {
        User user = userService.findUser(userDto.getUserUUID());
        System.out.println(user.getId());
//        List<Card> cards = cardService.getCardsByUserId(user.getId());
        user.getCards();
        return ResponseEntity.ok(user.getCards());
    }


    @PostMapping("/invoice")
    ResponseEntity<Invoice> getInvoice(@RequestBody PaymentInfoDto paymentInfoDto) {
        Invoice invoice = invoiceService.createInvoice(amountCalculator.calculateAmount(paymentInfoDto), paymentInfoDto.getCurrency(), userService.findUser(paymentInfoDto.getUserid()));
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/invoice/pay")
    ResponseEntity<Invoice> payInvoice(@RequestBody PaymentDto paymentDto) throws StripeException {
        User user = userService.findUser(paymentDto.getUserId());
        Invoice invoice = invoiceService.getUnpayed(user);
        Boolean successful = paymentService.charge(invoice.getAmount(), userService.getUserMap(paymentDto.getUserId()), cardService.getCardMapByNumberAndUserId(paymentDto.getCardNumber(), user));
        if (successful) {
            invoiceService.makePayed(user);
            return ResponseEntity.ok(invoice);
        } else {
            return (ResponseEntity<Invoice>) ResponseEntity.status(402); //todo ?
        }

    }

    @GetMapping("/invoice/unpaid")
    ResponseEntity<List<Invoice>> getUnpaidInvoice(@RequestBody UserDto userDto) {
        User user = userService.findUser(userDto.getUserUUID());
        List<Invoice> unpaid = invoiceService.findInvoices(user, false);
        return ResponseEntity.ok(unpaid);
    }

    @PostMapping("/card/check")
    ResponseEntity<Refund> checkcard(@RequestBody PaymentDto paymentDto) throws StripeException {
        User user = userService.findUser(paymentDto.getUserId());
        Refund refund = paymentService.checkingPayment(userService.getUserMap(paymentDto.getUserId()), cardService.getCardMapByNumberAndUserId(paymentDto.getCardNumber(), userService.findUser(paymentDto.getUserId())));
        //todo sent the answer to card service and block/not card
        return ResponseEntity.ok(refund);
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

    @GetMapping("/invoice/all")
    ResponseEntity<List<Invoice>> invoices(@RequestBody UserDto userDto) {
        List<Invoice> invoices = invoiceService.getInvoices(userService.findUser(userDto.getUserUUID()));
        return ResponseEntity.ok(invoices);
    }


    @GetMapping("/customHeader")
    ResponseEntity<String> customHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        return new ResponseEntity<>(
                "Custom header set", headers, HttpStatus.OK);
    }


}