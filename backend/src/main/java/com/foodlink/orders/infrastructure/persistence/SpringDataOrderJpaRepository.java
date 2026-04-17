package com.foodlink.orders.infrastructure.persistence;

import com.foodlink.orders.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderJpaRepository extends JpaRepository<Order, Long> {
}
