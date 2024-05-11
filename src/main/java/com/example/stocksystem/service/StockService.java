package com.example.stocksystem.service;

import com.example.stocksystem.domain.Stock;
import com.example.stocksystem.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private StockRepository stockRepository;

    @Transactional
    public void decreaseStock(Long id, Long quantity) {
        //Stock 조회
        Stock stock = stockRepository.findById(id).orElseThrow();

        //재고 감소
        stock.decreaseQuantity(quantity);

        //갱신된 값 저장
        stockRepository.saveAndFlush(stock);
    }

}