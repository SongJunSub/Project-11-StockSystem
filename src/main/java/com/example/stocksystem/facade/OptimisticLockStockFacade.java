package com.example.stocksystem.facade;

import com.example.stocksystem.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        //업데이트를 실패했을 때 재시도
        while(true) {
            try {
                optimisticLockStockService.decreaseWithOptimisticLock(id, quantity);

                break;
            }
            catch(Exception e) {
                Thread.sleep(50);
            }
        }
    }

}