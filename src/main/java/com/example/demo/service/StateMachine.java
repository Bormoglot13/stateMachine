package com.example.demo.service;

import com.example.demo.model.CashBox;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
//@Scope(value = "prototype")
//@RequiredArgsConstructor
public class StateMachine {

    @Autowired
    private ApplicationContext ctxt;
    private Map<Integer,Double> scoreMap = new ConcurrentHashMap<>();
    @Getter
    private List<CashBox> cashBoxList = new ArrayList<>();

    public StateMachine() {
    }

    public String nextState(String state){
        final String[] result = {null};
        switch (state) {
            case "A":
                while (result[0] == null) {
                    cashBoxList.forEach(cashBox -> scoreMap.put(cashBox.getNumber(), cashBox.getHealth()));
                    String cashBoxNum = scoreMap.entrySet().stream()
                            .min((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                            .get().getKey().toString();

                    result[0] = state + "->" + cashBoxNum;
                    cashBoxList.forEach(cashBox -> {
                        if (cashBox.getNumber() == Integer.parseInt(cashBoxNum)
                                && cashBox.getHealth() != Integer.MAX_VALUE) {
                            cashBox.incrementCurrentSize();
                        } else if(cashBox.getNumber() == Integer.parseInt(cashBoxNum)) {
                            result[0] = null;
                            try {
                                Thread.sleep(1000*60);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                break;
            default:
                cashBoxList.forEach(cashBox -> {
                if (cashBox.getNumber() == Integer.parseInt(state))
                        cashBox.decrementCurrentSize();
                });
                break;
        }
        if (result[0] != null)
            System.out.println(result[0]);
        return result[0];
    }
}
