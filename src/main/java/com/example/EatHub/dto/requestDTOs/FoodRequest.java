package com.example.EatHub.dto.requestDTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodRequest {

    int requiredQuantity;

    String customerMobile;

    int menuItemId;
}
