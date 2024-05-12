package com.example.stocksystem.facade;

import com.example.stocksystem.repository.RedisLockRepository;
import com.example.stocksystem.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        // While문을 활용하여 Lock 획득을 시도
        // 만약에 Lock 획득에 실패하였다면 Thread Sleep을 활용해서 100m의 텀을 투고 Lock 획득을 재시도한다.
        // 이렇게 해야 Redis에 갈 수 있는 부하를 줄여줄 수 있게 된다.
        while(!redisLockRepository.lock(id)){
            Thread.sleep(100);
        }

        try {
            stockService.decreaseStock(id, quantity);
        }
        finally {
            redisLockRepository.unlock(id);
        }
    }

}