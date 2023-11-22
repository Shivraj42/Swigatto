package com.example.EatHub.Transformer;

import com.example.EatHub.dto.requestDTOs.DeliveryPartnerRequest;
import com.example.EatHub.model.DeliveryPartner;

public class DeliveryPartnerTransformer {
    public static DeliveryPartner DeliveryPartnerRequestToDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest){
        return  DeliveryPartner.builder()
                .name(deliveryPartnerRequest.getName())
                .mobileNo(deliveryPartnerRequest.getMobileNo())
                .gender(deliveryPartnerRequest.getGender())
                .build();
    }
}
