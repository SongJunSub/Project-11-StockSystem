package com.example.stocksystem.service;

import com.example.stocksystem.domain.Stock;
import com.example.stocksystem.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    //테스트 전 데이터 삽입
    @BeforeEach
    public void before(){
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    //테스트 후 데이터 삭제
    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    public void decreaseQuantity(){
        stockService.decreaseStock(1L, 1L);

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(99, stock.getQuantity());
    }

}