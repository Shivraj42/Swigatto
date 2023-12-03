package com.example.EatHub.Controller;

import com.example.EatHub.dto.requestDTOs.DeliveryPartnerRequest;
import com.example.EatHub.exceptions.AccountAlreadyExistException;
import com.example.EatHub.exceptions.InvalidEmailOrMobileNoException;
import com.example.EatHub.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/partner")
public class DeliveryPartnerController {
    final DeliveryPartnerService deliveryPartnerService;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService){
        this.deliveryPartnerService=deliveryPartnerService;
    }

    // add
    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){
        try{
            String message= deliveryPartnerService.addDeliveryPartner(deliveryPartnerRequest);
            return new ResponseEntity(message, HttpStatus.CREATED);
        }
        catch (AccountAlreadyExistException e){
            String message= e.getMessage();
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        catch (InvalidEmailOrMobileNoException e){
            String message= e.getMessage();
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    // give delivery partner with highest number of deliveries

    // send an email to all the partners who have done less than 10 deliveries.
}
