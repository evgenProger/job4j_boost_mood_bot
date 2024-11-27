package ru.job4j.service;

import org.apache.logging.log4j.message.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.job4j.repository.UserRepository;

public class RemindService {
    private final TgRemoteService tgRemoteService;
    private final UserRepository userRepository;

    public RemindService(TgRemoteService tgRemoteService, UserRepository userRepository) {
        this.tgRemoteService = tgRemoteService;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRateString = "${remind.period}")
    public void ping() {
        for (var user: userRepository.findAll()) {
            var message = new SendMessage();
            message.setChatId(user.getChatId());
            message.setText("Ping");
            tgRemoteService.send(message);
        }
    }
}