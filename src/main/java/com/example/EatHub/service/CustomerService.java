package com.example.EatHub.service;

import com.example.EatHub.Transformer.CustomerTransformer;
import com.example.EatHub.dto.requestDTOs.CustomerRequest;
import com.example.EatHub.dto.responseDTOs.CustomerResponse;
import com.example.EatHub.exceptions.CustomerNotFoundException;
import com.example.EatHub.model.Cart;
import com.example.EatHub.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EatHub.repository.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer= CustomerTransformer.CustomerRequestToCustomer(customerRequest);
        Cart cart= Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();
        customer.setCart(cart);
        Customer savedCustomer= customerRepository.save(customer);
        return CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerByMobileNumber(String mob) {
        Optional<Customer> customerOptional= customerRepository.findByMobileNo(mob);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Incorrect Mobile Number!");
        }
        Customer customer= customerOptional.get();
        return  CustomerTransformer.CustomerToCustomerResponse(customer);
    }
}
