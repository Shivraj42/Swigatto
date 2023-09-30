package com.example.EatHub.dto.responseDTOs;

import com.example.EatHub.enums.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuResponse {

    String dishName;

    double price;

    FoodCategory category;

    boolean veg;
}
