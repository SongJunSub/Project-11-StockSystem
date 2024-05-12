package com.example.stocksystem.facade;

import com.example.stocksystem.repository.LockRepository;
import com.example.stocksystem.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    //부모의 트랜잭션과 별도로 실행이 되어야 하기 떄문에 Propagation 설정 변경
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity){
        try {
            lockRepository.getLock(id.toString());
            stockService.decreaseStock(id, quantity);
        }
        finally {
            lockRepository.releaseLock(id.toString());
        }
    }

}