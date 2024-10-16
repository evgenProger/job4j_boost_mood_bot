package ru.job4j.service;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* ru.job4j.service.*.*(..))")
    public void logDeforeMethod() {
        LOGGER.info("Method in TelegramBotService is about to be executed");
    }

    @After("@annotation(PostLog)")
    public void logAfter() {
        LOGGER.info("Method with @PostLog has been executed");
    }
}
