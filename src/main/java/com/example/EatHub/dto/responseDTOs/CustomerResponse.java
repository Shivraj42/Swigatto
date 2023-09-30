package com.example.EatHub.dto.responseDTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    String name;

    String mobileNo;

    String address;

    CartResponse cart;

}
