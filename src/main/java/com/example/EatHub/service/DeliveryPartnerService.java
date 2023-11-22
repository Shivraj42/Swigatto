package com.example.EatHub.service;

import com.example.EatHub.Transformer.DeliveryPartnerTransformer;
import com.example.EatHub.dto.requestDTOs.DeliveryPartnerRequest;
import com.example.EatHub.model.DeliveryPartner;
import com.example.EatHub.repository.DeliveryPartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerService {
    final DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository){
        this.deliveryPartnerRepository=deliveryPartnerRepository;
    }

    public String addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        // create entity from request
        DeliveryPartner deliveryPartner= DeliveryPartnerTransformer.DeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);
        // save entity
        deliveryPartnerRepository.save(deliveryPartner);

        return "You have been successfully regsitered!!!";
    }
}
