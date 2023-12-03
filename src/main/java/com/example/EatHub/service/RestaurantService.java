package com.example.EatHub.service;

import com.example.EatHub.Transformer.MenuItemTransformer;
import com.example.EatHub.Transformer.RestaurantTransformer;
import com.example.EatHub.dto.requestDTOs.MenuRequest;
import com.example.EatHub.dto.requestDTOs.RestaurantRequest;
import com.example.EatHub.dto.responseDTOs.RestaurantResponse;
import com.example.EatHub.exceptions.AccountAlreadyExistException;
import com.example.EatHub.exceptions.InvalidEmailOrMobileNoException;
import com.example.EatHub.exceptions.RestaurantNotFoundException;
import com.example.EatHub.model.MenuItem;
import com.example.EatHub.model.Restaurant;
import com.example.EatHub.repository.RestaurantRepository;
import com.example.EatHub.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    final RestaurantRepository restaurantRepository;
    final ValidationUtils validationUtils;

    /**
     * Constructor injection
     * @param restaurantRepository
     */
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository,
                             ValidationUtils validationUtils){
        this.restaurantRepository=restaurantRepository;
        this.validationUtils=validationUtils;
    }


    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        // check mobileNo
        if(restaurantRequest.getContact().length()!=10){
            throw new InvalidEmailOrMobileNoException("Invalid Contact Number!");
        }
        Optional<Restaurant> restaurantOptional= restaurantRepository.findByContactNumber(restaurantRequest.getContact());
        if(restaurantOptional.isPresent()){
            throw new AccountAlreadyExistException("Account already present with this Contact number!");
        }
        // restaurantRequest to restaurant entity
        Restaurant restaurant= RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        // save the entity
        Restaurant savedRestaurant =restaurantRepository.save(restaurant);
        // saved entity to restaurant Response to return
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public String changeOpenedStatus(int id) {

        // check if id is valid or not
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant Not Exist!");
        }
        Restaurant restaurant= restaurantRepository.findById(id).get();
        // change the status
        restaurant.setOpened(!restaurant.isOpened());
        restaurantRepository.save(restaurant);  // save the restaurant

        if(restaurant.isOpened()){
            return "Restaurant is Opened Now!";
        }
        else return "Restaurant is Closed";
    }

    public RestaurantResponse addMenuItemsToRestaurant(MenuRequest menu) {
        // check the resto id is valid or not
        if(!validationUtils.validateRestaurantId(menu.getRestaurantId())){
            throw new RestaurantNotFoundException("Restaurant Not Exist!");
        }
        Restaurant restaurant= restaurantRepository.findById(menu.getRestaurantId()).get();
        //create food entity
        MenuItem menuItem= MenuItemTransformer.MenuRequestToMenuItem(menu);
        // set the restaurant
        menuItem.setRestaurant(restaurant);
        // add food entity to restaurant available menu
        restaurant.getAvailableMenuItems().add(menuItem);

        // save both restaurant and menuItem
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);   // it will save both resto and menuItem as cascaded all

        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);

    }
}
