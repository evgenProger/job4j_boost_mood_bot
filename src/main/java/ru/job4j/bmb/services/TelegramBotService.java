package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.job4j.content.Content;
import ru.job4j.handler.BotCommandHandler;

@Service
public class TelegramBotService {

    private final BotCommandHandler handler;

    public TelegramBotService(BotCommandHandler handler) {
        this.handler = handler;
    }

    public void content(Content content) {
        handler.receive(content);
    }
}
