package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.job4j.service.TelegramBotService;
import ru.job4j.content.Content;

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
            var bot = ctx.getBean(TelegramBotService.class);
            bot.content(new Content());
            beanNameAware.displayAllBeanNames();
        };
    }
}