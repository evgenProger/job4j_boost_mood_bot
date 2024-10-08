package ru.job4j.bmb;

import ru.job4j.bmb.services.TelegramBotService;
import ru.job4j.content.Content;
import ru.job4j.handler.BotCommandHandler;

public class DIByDirectInjectMain {

    public static void main(String[] args) {
        var handler = new BotCommandHandler();
        var tg = new TelegramBotService(handler);
        tg.content(new Content());
    }
}
