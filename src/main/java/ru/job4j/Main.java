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
import ru.job4j.model.Award;
import ru.job4j.model.Mood;
import ru.job4j.model.MoodContent;
import ru.job4j.repository.AwardRepository;
import ru.job4j.repository.MoodContentRepository;
import ru.job4j.repository.MoodRepository;
import ru.job4j.service.TgRemoteService;

import java.util.ArrayList;
import java.util.List;

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
    CommandLineRunner loadDatabase(MoodRepository moodRepository,
                                   MoodContentRepository moodContentRepository,
                                   AwardRepository awardRepository) {
        return args -> {
            moodContentRepository.deleteAll();
            moodRepository.deleteAll();
            awardRepository.deleteAll();
            if (!moodRepository.findAll().isEmpty()) {
                return;
            }
            List<Mood> moods = createMoods();
            moodRepository.saveAll(moods);
            List<MoodContent> moodContents = createMoodContents(moods);
            moodContentRepository.saveAll(moodContents);
            moodContentRepository.saveAll(moodContents);
            List<Award> awards = createAwards();
            awardRepository.saveAll(awards);
        };
    }

    private List<Mood> createMoods() {
        List<String[]> moodsData = List.of(
                new String[]{"Воодушевленное настроение 🌟", "Великолепно! Вы чувствуете себя на высоте. Продолжайте в том же духе."},
                new String[]{"Успокоение и гармония 🧘‍♂️", "Потрясающе! Вы в состоянии внутреннего мира и гармонии."},
                new String[]{"В состоянии комфорта ☺️", "Отлично! Вы чувствуете себя уютно и спокойно."},
                new String[]{"Легкое волнение 🎈", "Замечательно! Немного волнения добавляет жизни краски."},
                new String[]{"Сосредоточенное настроение 🎯", "Хорошо! Ваш фокус на высоте, используйте это время эффективно."},
                new String[]{"Тревожное настроение 😟", "Не волнуйтесь, всё пройдет. Попробуйте расслабиться и найти источник вашего беспокойства."},
                new String[]{"Разочарованное настроение 😞", "Бывает. Не позволяйте разочарованию сбить вас с толку, всё наладится."},
                new String[]{"Усталое настроение 😴", "Похоже, вам нужен отдых. Позаботьтесь о себе и отдохните."},
                new String[]{"Вдохновенное настроение 💡", "Потрясающе! Вы полны идей и энергии для их реализации."},
                new String[]{"Раздраженное настроение 😠", "Попробуйте успокоиться и найти причину раздражения, чтобы исправить ситуацию."}
        );
        List<Mood> moods = moodsData.stream()
                .map(data -> new Mood(data[0], false))
                .toList();
        if (!moods.isEmpty()) {
            moods.get(0).setGood(true);
        }
        return moods;
    }

    private List<MoodContent> createMoodContents(List<Mood> moods) {
        List<String> texts = List.of(
                "Великолепно! Вы чувствуете себя на высоте. Продолжайте в том же духе.",
                "Потрясающе! Вы в состоянии внутреннего мира и гармонии.",
                "Отлично! Вы чувствуете себя уютно и спокойно.",
                "Замечательно! Немного волнения добавляет жизни краски.",
                "Хорошо! Ваш фокус на высоте, используйте это время эффективно.",
                "Не волнуйтесь, всё пройдет. Попробуйте расслабиться и найти источник вашего беспокойства.",
                "Бывает. Не позволяйте разочарованию сбить вас с толку, всё наладится.",
                "Похоже, вам нужен отдых. Позаботьтесь о себе и отдохните.",
                "Потрясающе! Вы полны идей и энергии для их реализации.",
                "Попробуйте успокоиться и найти причину раздражения, чтобы исправить ситуацию."
        );
        List<MoodContent> list = new ArrayList<>();
        for (int i = 0; i < moods.size(); i++) {
            list.add(new MoodContent(moods.get(i), texts.get(i)));
        }
        return list;
    }

    private List<Award> createAwards() {
        return List.of(
                new Award("Смайлик дня", "За 1 день хорошего настроения. Награда: Веселый смайлик или стикер, отправленный пользователю в качестве поощрения.", 1),
                new Award("Настроение недели", "За 7 последовательных дней хорошего или отличного настроения. Награда: Специальный значок или иконка, отображаемая в профиле пользователя в течение недели.", 7),
                new Award("Бонусные очки", "За каждые 3 дня хорошего настроения. Награда: Очки, которые можно обменять на виртуальные предметы или функции внутри приложения.", 3),
                new Award("Персонализированные рекомендации", "После 5 дней хорошего настроения. Награда: Подборка контента или активности на основе интересов пользователя.", 5),
                new Award("Достижение 'Солнечный луч'", "За 10 дней непрерывного хорошего настроения. Награда: Разблокировка новой темы оформления или фона в приложении.", 10),
                new Award("Виртуальный подарок", "После 15 дней хорошего настроения. Награда: Возможность отправить или получить виртуальный подарок внутри приложения.", 15),
                new Award("Титул 'Лучезарный'", "За 20 дней хорошего или отличного настроения. Награда: Специальный титул, отображаемый рядом с именем пользователя.", 20),
                new Award("Доступ к премиум-функциям", "После 30 дней хорошего настроения. Награда: Временный доступ к премиум-функциям или эксклюзивному контенту.", 30),
                new Award("Участие в розыгрыше призов", "За каждую неделю хорошего настроения. Награда: Шанс выиграть призы в ежемесячных розыгрышах.", 7),
                new Award("Эксклюзивный контент", "После 25 дней хорошего настроения. Награда: Доступ к эксклюзивным статьям, видео или мероприятиям.", 25),
                new Award("Награда 'Настроение месяца'", "За поддержание хорошего или отличного настроения в течение целого месяца. Награда: Специальный значок, признание в сообществе или дополнительные привилегии.", 30),
                new Award("Физический подарок", "После 60 дней хорошего настроения. Награда: Возможность получить небольшой физический подарок, например, открытку или фирменный сувенир.", 60),
                new Award("Коучинговая сессия", "После 45 дней хорошего настроения. Награда: Бесплатная сессия с коучем или консультантом для дальнейшего улучшения благополучия.", 45),
                new Award("Разблокировка мини-игр", "После 14 дней хорошего настроения. Награда: Доступ к развлекательным мини-играм внутри приложения.", 14),
                new Award("Персональное поздравление", "За значимые достижения (например, 50 дней хорошего настроения). Награда: Персонализированное сообщение от команды приложения или вдохновляющая цитата.", 50)
        );
    }
}