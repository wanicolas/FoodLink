package com.foodlink.orders.infrastructure.persistence;

import com.foodlink.orders.domain.entity.Order;
import com.foodlink.orders.domain.repository.OrderRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderRepository implements OrderRepository {

  private final SpringDataOrderJpaRepository delegate;

  public JpaOrderRepository(SpringDataOrderJpaRepository delegate) {
    this.delegate = delegate;
  }

  @Override
  public Optional<Order> findById(Long orderId) {
    return delegate.findById(orderId);
  }

  @Override
  public Order save(Order order) {
    return delegate.save(order);
  }
}
