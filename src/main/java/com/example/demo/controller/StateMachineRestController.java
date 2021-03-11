package com.example.demo.controller;

import com.example.demo.model.CashBox;
import com.example.demo.service.StateMachine;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RestController
@SpringBootConfiguration
@ComponentScan
public class StateMachineRestController {

    private StateMachine stateMachine;
    @Autowired
    private BeanFactory beanFactory;

    
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "Hello";
    }

    @RequestMapping("/openOperDay")
    @ResponseBody
    public Boolean openOperDay(){
        try {
            stateMachine = beanFactory.getBean(StateMachine.class);
            //stateMachine.getCashBoxList().clear();
            IntStream.rangeClosed(1, 4)
                    .forEach(i -> {
                        int score = 0;
                        switch (i) {
                            case 1:
                                score = 10;
                                break;
                            case 2:
                                score = 13;
                                break;
                            case 3:
                                score = 15;
                                break;
                            case 4:
                                score = 17;
                                break;
                            default:
                                break;
                        }
                        stateMachine.getCashBoxList().add(new CashBox(i, new AtomicInteger(0), score));
                        //CashBox cashBox = beanFactory.getBean(CashBox.class,i, 0, score);
                    });
            return Boolean.TRUE;
        } catch (Exception ex) {
            return  Boolean.FALSE;
        }
    }

    @RequestMapping("/closeOperDay")
    @ResponseBody
    public Boolean closeOperDay() {
        try {
            stateMachine.getCashBoxList().clear();
            return Boolean.TRUE;
        } catch(Exception ex) {
            return Boolean.FALSE;
        }
    }

    @RequestMapping( value = "/input", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String input(@RequestParam("state") String state) throws Exception{
        List<String> availableStates = Arrays.asList("A","1","2","3","4");
        if (!availableStates.contains(state) ) {
            throw new Exception("Value \"state\" may be only in list " + availableStates);
        }
        return stateMachine.nextState(state);
    }

    @RequestMapping( value = "/stat", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<CashBox> stat(){
        return stateMachine.getCashBoxList();
    }

}
