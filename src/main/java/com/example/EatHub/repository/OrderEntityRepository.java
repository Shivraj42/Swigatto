package com.example.EatHub.repository;

import com.example.EatHub.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {
}
