package com.example.EatHub.service;

import com.example.EatHub.Transformer.DeliveryPartnerTransformer;
import com.example.EatHub.dto.requestDTOs.DeliveryPartnerRequest;
import com.example.EatHub.exceptions.AccountAlreadyExistException;
import com.example.EatHub.exceptions.InvalidEmailOrMobileNoException;
import com.example.EatHub.model.DeliveryPartner;
import com.example.EatHub.repository.DeliveryPartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryPartnerService {
    final DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository){
        this.deliveryPartnerRepository=deliveryPartnerRepository;
    }

    public String addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        //check for mobile no
        if(deliveryPartnerRequest.getMobileNo().length()!=10){
            throw new InvalidEmailOrMobileNoException("Invalid Contact Number!");
        }
        Optional<DeliveryPartner> deliveryPartnerOptional= deliveryPartnerRepository.findByMobileNo(deliveryPartnerRequest.getMobileNo());
        if(deliveryPartnerOptional.isPresent()){
            throw new AccountAlreadyExistException("Account already Exist with that mobile number!");
        }
        // create entity from request
        DeliveryPartner deliveryPartner= DeliveryPartnerTransformer.DeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);
        // save entity
        deliveryPartnerRepository.save(deliveryPartner);

        return "You have been successfully regsitered!!!";
    }
}
