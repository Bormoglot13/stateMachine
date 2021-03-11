package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class CashBox {

    private static final int MAX_SIZE = 20;

    @Getter
    @Setter
    private int number;

    @Getter
    @Setter
    private AtomicInteger currentSize;

    @Getter
    @Setter
    private double score;

    public CashBox(int number, AtomicInteger currentSize, double score) {
        this.number = number;
        this.currentSize = currentSize;
        this.score = score;
    }

    public void decrementCurrentSize(){
        if (currentSize.get() != 0)
            currentSize.decrementAndGet();
    }

    public void incrementCurrentSize(){
        if (currentSize.get() != MAX_SIZE)
            currentSize.incrementAndGet();
    }

    public double getHealth(){
        if (currentSize.get()==MAX_SIZE)
            return Integer.MAX_VALUE;
        return currentSize.get()==0 ? 0 : currentSize.get()/score;
    }
}
