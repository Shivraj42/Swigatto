package com.example.EatHub.dto.responseDTOs;

import com.example.EatHub.enums.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantResponse {

    String name;

    String location;

    String contact;

    boolean opened;

    List<MenuResponse> menu;

}
