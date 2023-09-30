package com.example.EatHub.Controller;

import com.example.EatHub.dto.requestDTOs.CustomerRequest;
import com.example.EatHub.dto.responseDTOs.CustomerResponse;
import com.example.EatHub.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EatHub.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse= customerService.addCustomer(customerRequest);
        return new ResponseEntity(customerResponse, HttpStatus.CREATED);
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

}
