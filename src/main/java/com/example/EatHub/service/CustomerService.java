package com.example.EatHub.service;

import com.example.EatHub.Transformer.CustomerTransformer;
import com.example.EatHub.dto.requestDTOs.CustomerRequest;
import com.example.EatHub.dto.responseDTOs.CustomerResponse;
import com.example.EatHub.exceptions.AccountAlreadyExistException;
import com.example.EatHub.exceptions.CustomerNotFoundException;
import com.example.EatHub.model.Cart;
import com.example.EatHub.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EatHub.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        // check for email and mob first
        Optional<Customer> customerOptional =customerRepository.findByMobileNo(customerRequest.getMobileNo());
        if(customerOptional.isPresent()){
            throw new AccountAlreadyExistException("Already have account with this mobile number!");
        }
        Optional<Customer> customerOptional1 =customerRepository.findByEmail(customerRequest.getEmail());
        if(customerOptional1.isPresent()){
            throw new AccountAlreadyExistException("Already have account with this email!");
        }
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

    public CustomerResponse getCustomerWithMostOrders() {
//        List<Customer> customers= customerRepository.findAll();
//        if(customers.isEmpty()){
//            throw new CustomerNotFoundException("No Customers Available in our Database!");
//        }
//        long count=-1;
//        Customer customer=null;
//        for(Customer c: customers){
//            long currCount=c.getOrders().size();
//            if(currCount>count){
//                count=currCount;
//                customer= c;
//            }
//        }
//        return  CustomerTransformer.CustomerToCustomerResponse(customer);

        Optional<Customer> customerOptional= customerRepository.getCustomerWithMostOrders();
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("No Customers Available in our Database!");
        }
        Customer customer=customerOptional.get();
        return  CustomerTransformer.CustomerToCustomerResponse(customer);
    }

    public CustomerResponse getCustomerWithLeastOrders() {

//        List<Customer> customers= customerRepository.findAll();
//        if(customers.isEmpty()){
//            throw new CustomerNotFoundException("No Customers Available in our Database!");
//        }
//        long count=Long.MAX_VALUE;
//        Customer customer=null;
//        for(Customer c: customers){
//            long currCount=c.getOrders().size();
//            if(currCount<count){
//                count=currCount;
//                customer= c;
//            }
//        }
//        return  CustomerTransformer.CustomerToCustomerResponse(customer);

        Optional<Customer> customerOptional= customerRepository.getCustomerWithLeastOrders();
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("No Customers Available in our Database!");
        }
        Customer customer=customerOptional.get();
        return  CustomerTransformer.CustomerToCustomerResponse(customer);
    }
}
