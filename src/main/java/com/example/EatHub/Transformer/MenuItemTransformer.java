package com.example.EatHub.Transformer;

import com.example.EatHub.dto.responseDTOs.MenuResponse;
import com.example.EatHub.model.MenuItem;

public class MenuItemTransformer {

    public static MenuResponse MenuItemToMenuResponse(MenuItem menuItem){
        return MenuResponse.builder()
                .dishName(menuItem.getDishName())
                .category(menuItem.getCategory())
                .price(menuItem.getPrice())
                .veg(menuItem.isVeg())
                .build();
    }
}
