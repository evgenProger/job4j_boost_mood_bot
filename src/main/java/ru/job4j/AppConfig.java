package ru.job4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

public class AppConfig {

    @Value("${telegramm.bot.name}")
    private String telegrammBotName;
}
