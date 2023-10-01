package com.example.EatHub.service;

import com.example.EatHub.Transformer.RestaurantTransformer;
import com.example.EatHub.dto.requestDTOs.RestaurantRequest;
import com.example.EatHub.dto.responseDTOs.RestaurantResponse;
import com.example.EatHub.model.Restaurant;
import com.example.EatHub.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class RestaurantService {
    final RestaurantRepository restaurantRepository;

    /**
     * Constructor injection
     * @param restaurantRepository
     */
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository=restaurantRepository;
    }


    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {

        Restaurant restaurant= RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);

        Restaurant savedRestaurant =restaurantRepository.save(restaurant);

        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }
}
