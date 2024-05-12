package com.example.stocksystem.service;

import com.example.stocksystem.domain.Stock;
import com.example.stocksystem.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decreaseWithOptimisticLock(Long id, Long quantity){
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);

        stock.decreaseQuantity(quantity);

        stockRepository.save(stock);
    }

}