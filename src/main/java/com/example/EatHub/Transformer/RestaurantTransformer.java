package com.example.EatHub.Transformer;

import com.example.EatHub.dto.requestDTOs.RestaurantRequest;
import com.example.EatHub.dto.responseDTOs.MenuResponse;
import com.example.EatHub.dto.responseDTOs.RestaurantResponse;
import com.example.EatHub.model.MenuItem;
import com.example.EatHub.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTransformer {


    public static Restaurant RestaurantRequestToRestaurant(RestaurantRequest restaurantRequest){
        return Restaurant.builder()
                .name(restaurantRequest.getName())
                .contact(restaurantRequest.getContact())
                .restaurantCategory(restaurantRequest.getRestaurantCategory())
                .location(restaurantRequest.getLocation())
                .opened(true)
                .orders(new ArrayList<>())
                .availableMenuItems(new ArrayList<>())
                .build();
    }
    public static RestaurantResponse RestaurantToRestaurantResponse(Restaurant restaurant){
        List<MenuResponse> menu=new ArrayList<>();
        for(MenuItem menuItem: restaurant.getAvailableMenuItems()){
            menu.add(MenuItemTransformer.MenuItemToMenuResponse(menuItem));
        }
        return  RestaurantResponse.builder()
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .contact(restaurant.getContact())
                .opened(restaurant.isOpened())
                .menu(menu)
                .build();
    }


}
