package com.example.stocksystem.repository;

import com.example.stocksystem.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {



}