package com.example.stocksystem.facade;

import com.example.stocksystem.domain.Stock;
import com.example.stocksystem.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NamedLockStockFacadeTest {

    @Autowired
    private NamedLockStockFacade namedLockStockFacade;

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
    public void decreaseMultipleQuantityWithPessimisticLock() throws InterruptedException {
        //동시에 100개의 요청을 보내야 하므로 멀티 스레드를 사용
        int threadCount = 100;

        //ExecutorService : 병렬 작업을 간단하게 할 수 있게 도와주는 Java의 API
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        //모든 요청이 끝날 때까지 기다려야 하므로 CountDownLatch를 사용
        //CountDownLatch : 다른 Thread에서 수행하는 작업을 기다리도록 도와주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i=0; i<threadCount; i++){
            executorService.submit(() -> {
                try {
                    namedLockStockFacade.decrease(1L, 1L);
                }
                finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        //100 - (1 * 100) = 0
        assertEquals(0, stock.getQuantity());
    }

}