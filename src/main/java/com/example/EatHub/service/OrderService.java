package com.example.EatHub.service;

import com.example.EatHub.Transformer.OrderTransformer;
import com.example.EatHub.dto.responseDTOs.OrderResponse;
import com.example.EatHub.exceptions.CustomerNotFoundException;
import com.example.EatHub.exceptions.EmptyCartException;
import com.example.EatHub.model.*;
import com.example.EatHub.repository.CustomerRepository;
import com.example.EatHub.repository.DeliveryPartnerRepository;
import com.example.EatHub.repository.OrderEntityRepository;
import com.example.EatHub.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {

    final CustomerRepository customerRepository;
    final OrderEntityRepository orderEntityRepository;

    final DeliveryPartnerRepository deliveryPartnerRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderService(CustomerRepository customerRepository,
                        OrderEntityRepository orderEntityRepository,
                        DeliveryPartnerRepository deliveryPartnerRepository,
                        RestaurantRepository restaurantRespository) {
        this.customerRepository = customerRepository;
        this.orderEntityRepository = orderEntityRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.restaurantRepository = restaurantRespository;
    }

    public OrderResponse placeOrder(String customerMobile) {

        // verify the customer
        Optional<Customer> customerOptional = customerRepository.findByMobileNo(customerMobile);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Invalid mobile number!!!");
        }
        Customer customer= customerOptional.get();

        // verify if cart is empty or not
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()==0){
            throw new EmptyCartException("Sorry! Your cart is empty!!!");
        }

        // find a delivery partner to deliver. Randomly
        DeliveryPartner partner = deliveryPartnerRepository.findRandomDeliveryPartner();
        Restaurant restaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();

        // prepare the order entity
        OrderEntity order = OrderTransformer.prepareOrderEntity(cart);

        OrderEntity savedOrder = orderEntityRepository.save(order);

        order.setCustomer(customer);
        order.setDeliveryPartner(partner);
        order.setRestaurant(restaurant);
        order.setFoodItems(cart.getFoodItems());

        customer.getOrders().add(savedOrder);
        partner.getOrders().add(savedOrder);
        restaurant.getOrders().add(savedOrder);

        for(FoodItem foodItem: cart.getFoodItems()){
            foodItem.setCart(null);
            foodItem.setOrder(savedOrder);
        }
        clearCart(cart);

        customerRepository.save(customer);
        deliveryPartnerRepository.save(partner);
        restaurantRepository.save(restaurant);

        // prepare orderResponse
        return OrderTransformer.OrderToOrderResponse(savedOrder);
    }

    private void clearCart(Cart cart) {
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
