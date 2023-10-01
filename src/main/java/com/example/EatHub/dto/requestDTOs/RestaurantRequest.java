package com.example.EatHub.dto.requestDTOs;

import com.example.EatHub.enums.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantRequest {

    String name;

    RestaurantCategory restaurantCategory;

    String location;

    String contact;

}
