package ru.kata.spring.rest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kata.spring.rest.configuration.MyConfig;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        String resultCode = communication.sequence();
        System.out.println("Итоговый код: " + resultCode);
    }
}