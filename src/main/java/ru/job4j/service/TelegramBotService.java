package ru.job4j.service;

import ru.job4j.content.Content;
import ru.job4j.handler.BotCommandHandler;

public class TelegramBotService {
    private final BotCommandHandler handler;

    public TelegramBotService(BotCommandHandler handler) {
        this.handler = handler;
    }

    public void content(Content content) {
        //handler.receive(content);
    }
}
