package com.example.EatHub.repository;

import com.example.EatHub.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Optional<Customer> findByMobileNo(String mob);

    @Query(value = "select c from Customer c order by size(c.orders) desc limit 1")
    Optional<Customer> getCustomerWithMostOrders();

    @Query(value = "select c from Customer c order by size(c.orders) limit 1")
    Optional<Customer> getCustomerWithLeastOrders();
}
