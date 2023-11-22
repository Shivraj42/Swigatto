package com.example.EatHub.Transformer;

import com.example.EatHub.dto.requestDTOs.MenuRequest;
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
    public static MenuItem MenuRequestToMenuItem(MenuRequest menuRequest){
        return MenuItem.builder()
                .dishName(menuRequest.getDishName())
                .price(menuRequest.getPrice())
                .category(menuRequest.getCategory())
                .veg(menuRequest.isVeg())
                .available(menuRequest.isAvailable())
                .build();
    }
}
