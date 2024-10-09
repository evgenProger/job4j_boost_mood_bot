package ru.job4j;

import org.springframework.beans.factory.annotation.Value;

public class AppConfig {

    @Value("${telegramm.bot.name}")
    private String telegrammBotName;
}
