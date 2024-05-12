package com.example.stocksystem.facade;

import com.example.stocksystem.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(Long id, Long quantity){
        //Redisson을 이용하여 Lock 객체를 가져온다.
        RLock lock = redissonClient.getLock(id.toString());

        try {
            boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);

            if(!available){
                System.out.println("Lock 획득에 실패하였습니다.");
                return;
            }

            stockService.decreaseStock(id, quantity);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }

}