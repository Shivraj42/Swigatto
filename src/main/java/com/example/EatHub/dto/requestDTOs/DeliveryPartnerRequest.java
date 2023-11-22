package com.example.EatHub.dto.requestDTOs;

import com.example.EatHub.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryPartnerRequest {

    String name;

    String mobileNo;

    Gender gender;
}
