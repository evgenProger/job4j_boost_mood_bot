package ru.job4j.bmb.services;

import org.springframework.stereotype.Service;
import ru.job4j.model.Content;
import ru.job4j.handler.BotCommandHandler;
import ru.job4j.service.PostLog;

@Service
public class TelegramBotService {

    private final BotCommandHandler handler;

    public TelegramBotService(BotCommandHandler handler) {
        this.handler = handler;
    }

    @PostLog
    public void content(Content content) {
        handler.receive(content);
    }
}
