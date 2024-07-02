package com.orange.shop.sales;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findByItemName(String title);
    Optional<Sales> findByMemberId(Long id);
    List<Sales> findAllByMemberId(Long id);
}
