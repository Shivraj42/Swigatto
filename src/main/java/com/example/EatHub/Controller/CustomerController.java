package com.example.EatHub.Controller;

import com.example.EatHub.dto.requestDTOs.CustomerRequest;
import com.example.EatHub.dto.responseDTOs.CustomerResponse;
import com.example.EatHub.exceptions.AccountAlreadyExistException;
import com.example.EatHub.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EatHub.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        try {
            CustomerResponse customerResponse= customerService.addCustomer(customerRequest);
            return new ResponseEntity(customerResponse, HttpStatus.CREATED);
        }
        catch (AccountAlreadyExistException e){
            String response =e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    //get customer by mobileNo
    @GetMapping("/get-by-mob-no")
    public ResponseEntity getCustomerByMobileNumber(@RequestParam String mob){
        try {
            CustomerResponse customerResponse = customerService.getCustomerByMobileNumber(mob);
            return new ResponseEntity(customerResponse, HttpStatus.FOUND);
        }
        catch (CustomerNotFoundException e){
            String response =e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    // get the customer with most number of orders

    @GetMapping("/get-customer-with-most-orders")
    public ResponseEntity getCustomerWithMostOrders(){
        try {
            CustomerResponse customerResponse = customerService.getCustomerWithMostOrders();
            return new ResponseEntity(customerResponse, HttpStatus.FOUND);
        }
        catch(CustomerNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    // get the female customer with least number of orders
    @GetMapping("/get-customer-with-least-orders")
    public ResponseEntity getCustomerWithLeastOrders(){
        try {
            CustomerResponse customerResponse = customerService.getCustomerWithLeastOrders();
            return new ResponseEntity(customerResponse, HttpStatus.FOUND);
        }
        catch(CustomerNotFoundException e){
            String response= e.getMessage();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }


}
