package com.example.EatHub.repository;

import com.example.EatHub.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{

    public Optional<Restaurant> findByContactNumber(String contact);
}
