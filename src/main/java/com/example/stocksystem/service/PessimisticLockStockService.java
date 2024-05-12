package com.example.stocksystem.service;

import com.example.stocksystem.domain.Stock;
import com.example.stocksystem.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decreaseWithPessimisticLock(Long id, Long quantity){
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);

        stock.decreaseQuantity(quantity);

        stockRepository.save(stock);
    }

}