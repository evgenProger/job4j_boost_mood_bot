package ru.job4j;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.job4j.service.TgRemoteService;

@SpringBootApplication
public class Main {

    @Autowired
    BeanNameAware beanNameAware;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initTelegramApi(ApplicationContext ctx) {
        return args -> {
            var bot = ctx.getBean(TgRemoteService.class);
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(bot);
                System.out.println("Бот успешно зарегестрирован");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public CommandLineRunner checkEnv(ApplicationContext ctx) {
        return args -> {
            System.out.println(ctx.getEnvironment().getProperty("telegram.bot.name"));
        };
    }
}