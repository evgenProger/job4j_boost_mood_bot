package ru.job4j.handler;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.job4j.content.Content;

@Service
public class BotCommandHandler {

    public void receive(Content content) {
        System.out.println(content);
    }
}
