package com.example.EatHub.service;

import com.example.EatHub.Transformer.FoodTransformer;
import com.example.EatHub.dto.requestDTOs.FoodRequest;
import com.example.EatHub.dto.responseDTOs.CartResponse;
import com.example.EatHub.dto.responseDTOs.CartStatusResponse;
import com.example.EatHub.dto.responseDTOs.FoodResponse;
import com.example.EatHub.exceptions.CustomerNotFoundException;
import com.example.EatHub.exceptions.MenuItemNotFoundException;
import com.example.EatHub.model.*;
import com.example.EatHub.repository.CartRepository;
import com.example.EatHub.repository.CustomerRepository;
import com.example.EatHub.repository.FoodRepository;
import com.example.EatHub.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    final CartRepository cartRepository;
    final CustomerRepository customerRepository;
    final FoodRepository foodRepository;
    final MenuRepository menuRepository;

    @Autowired
    CartService(CartRepository cartRepository,
                CustomerRepository customerRepository,
                FoodRepository foodRepository,
                MenuRepository menuRepository){
        this.cartRepository=cartRepository;
        this.customerRepository=customerRepository;
        this.foodRepository=foodRepository;
        this.menuRepository= menuRepository;
    }

    public CartStatusResponse addFoodItemToCart(FoodRequest foodRequest) {

        Optional<Customer> customerOptional = customerRepository.findByMobileNo(foodRequest.getCustomerMobile());
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Customer doesn't exisit");
        }
        Customer customer= customerOptional.get();

        Optional<MenuItem> menuItemOptional = menuRepository.findById(foodRequest.getMenuItemId());
        if(menuItemOptional.isEmpty()){
            throw new MenuItemNotFoundException("Item not available in the restaurant!!!");
        }

        MenuItem menuItem = menuItemOptional.get();
        if(!menuItem.getRestaurant().isOpened() || !menuItem.isAvailable()) {
            throw new MenuItemNotFoundException("Given dish is out of stock for now!!!");
        }

        Cart cart = customer.getCart();

        // having item from same restaurant
        if(cart.getFoodItems().size()!=0){
            Restaurant currRestaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
            Restaurant newRestaurant = menuItem.getRestaurant();

            if(!currRestaurant.equals(newRestaurant)){
                List<FoodItem> foodItems = cart.getFoodItems();
                for(FoodItem foodItem: foodItems) {
                    foodItem.setCart(null);
                    foodItem.setOrder(null);
                    foodItem.setMenuItem(null);
                }
                cart.setCartTotal(0);
                cart.getFoodItems().clear();
                foodRepository.deleteAll(foodItems);
            }
        }

        boolean alreadyExists = false;
        FoodItem savedFoodItem = null;
        if(cart.getFoodItems().size()!=0){
            for(FoodItem foodItem: cart.getFoodItems()){
                if(foodItem.getMenuItem().getId()==menuItem.getId()){
                    savedFoodItem = foodItem;
                    int curr = foodItem.getRequiredQuantity();
                    foodItem.setRequiredQuantity(curr+foodRequest.getRequiredQuantity());
                    alreadyExists = true;
                    break;
                }
            }
        }

        if(!alreadyExists){
            FoodItem foodItem = FoodItem.builder()
                    .menuItem(menuItem)
                    .requiredQuantity(foodRequest.getRequiredQuantity())
                    .totalCost(foodRequest.getRequiredQuantity()*menuItem.getPrice())
                    .build();

            savedFoodItem = foodRepository.save(foodItem);
            cart.getFoodItems().add(savedFoodItem);
            menuItem.getFoodItems().add(savedFoodItem);
            savedFoodItem.setCart(cart);
        }

        double cartTotal = 0;
        for(FoodItem food: cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }

        cart.setCartTotal(cartTotal);
        // save
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for(FoodItem food: cart.getFoodItems()){
            foodResponseList.add(FoodTransformer.FoodToFoodResponse(food));
        }

        return CartStatusResponse.builder()
                .customerName(savedCart.getCustomer().getName())
                .customerMobile(savedCart.getCustomer().getMobileNo())
                .customerAddress(savedCart.getCustomer().getAddress())
                .foodList(foodResponseList)
                .restaurantName(savedMenuItem.getRestaurant().getName())
                .cartTotal(cartTotal)
                .build();

    }
}