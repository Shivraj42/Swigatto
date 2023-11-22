package com.example.EatHub.utils;

import com.example.EatHub.model.Restaurant;
import com.example.EatHub.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationUtils {
    final RestaurantRepository restaurantRepository;

    @Autowired
    public ValidationUtils(RestaurantRepository restaurantRepository){
        this.restaurantRepository=restaurantRepository;
    }

    public boolean validateRestaurantId(int id) {
        Optional<Restaurant> restaurantOptional= restaurantRepository.findById(id);
        return restaurantOptional.isPresent();
    }

}
