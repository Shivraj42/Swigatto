package com.example.EatHub.Controller;

import com.example.EatHub.dto.requestDTOs.MenuRequest;
import com.example.EatHub.dto.requestDTOs.RestaurantRequest;
import com.example.EatHub.dto.responseDTOs.RestaurantResponse;
import com.example.EatHub.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    final RestaurantService restaurantService;

    /**
     * Constructor injection
     * @param restaurantService
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService=restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){

        RestaurantResponse restaurantResponse= restaurantService.addRestaurant(restaurantRequest);
        return  new ResponseEntity(restaurantResponse, HttpStatus.CREATED);
    }

    // update status
    @PutMapping("/update/status")
    public ResponseEntity changeOpenedStatus(@RequestParam int id){
        String response = restaurantService.changeOpenedStatus(id);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    // add menu
    @PutMapping("/add/menu")
    public ResponseEntity addMenuItemsToRestaurant(@RequestBody MenuRequest menu){
        RestaurantResponse restaurantResponse= restaurantService.addMenuItemsToRestaurant(menu);
        return new ResponseEntity(restaurantResponse, HttpStatus.CREATED);
    }

    // get menu of a restaurant

    // give all the restauratns who have served more than 'x' number of orders

    // give the restaurants which have maximum number of items in their menu and which are opened also
}
