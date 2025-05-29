package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.*;
import ru.job4j.repository.AchievementRepository;
import ru.job4j.repository.MoodLogRepository;
import ru.job4j.repository.MoodRepository;
import ru.job4j.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MoodService {
    private final MoodRepository moodRepository;
    private final MoodLogRepository moodLogRepository;
    private final RecommendationEngine recommendationEngine;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public MoodService(MoodRepository moodRepository, MoodLogRepository moodLogRepository, RecommendationEngine recommendationEngine,
                       UserRepository userRepository, AchievementRepository achievementRepository) {
        this.moodRepository = moodRepository;
        this.moodLogRepository = moodLogRepository;
        this.recommendationEngine = recommendationEngine;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
    }

    public Content chooseMood(User user, Long moodId) {
        Optional<Mood> moodOptional = moodRepository.findById(moodId);
        if (moodOptional.isEmpty()) {
            Content content = new Content(user.getChatId());
            content.setText("Настроение не найдено.");
            return content;
        }
        Mood mood = moodOptional.get();
        MoodLog log = new MoodLog();
        log.setMood(mood);
        moodLogRepository.save(log);
        return recommendationEngine.recommendFor(user.getChatId(), moodId);
    }

    public Optional<Content> weekMoodLogCommand(long chatId, Long clientId) {
        var content = new Content(chatId);
        var user = userRepository.findByClientId(clientId);
        if (user == null) {
            content.setText("Пользователь не найден.");
            return Optional.of(content);
        }
        long oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS).getEpochSecond();
        List<MoodLog> recentLogs = moodLogRepository.findRecentLogs(user.getId(), oneWeekAgo);
        String message = formatMoodLogs(recentLogs, "Ваши записи настроения за неделю");
        content.setText(message);
        return Optional.of(content);
    }

    public Optional<Content> monthMoodLogCommand(long chatId, Long clientId) {
        var content = new Content(chatId);
        var user = userRepository.findByClientId(clientId);
        if (user == null) {
            content.setText("Пользователь не найден.");
            return Optional.of(content);
        }
        long oneWeekAgo = Instant.now().minus(1, ChronoUnit.MONTHS).getEpochSecond();
        List<MoodLog> recentLogs = moodLogRepository.findRecentLogs(user.getId(), oneWeekAgo);
        String message = formatMoodLogs(recentLogs, "Ваши записи настроения за месяц");
        content.setText(message);
        return Optional.of(content);
    }

    public Optional<Content> awards(long chatId, Long clientId) {
        var content = new Content(chatId);
        var user = userRepository.findByClientId(clientId);
        if (user == null) {
            content.setText("Пользователь не найден.");
            return Optional.of(content);
        }
        List<Achievement> achievements = achievementRepository.findByUser(user);
        List<Award> awards = achievements.stream()
                .map(Achievement::getAward)
                .toList();
        if (awards.isEmpty()) {
            content.setText("У вас пока нет наград.");
        } else {
            StringBuilder sb = new StringBuilder("🎖 Ваши награды:\n");
            for (Award award : awards) {
                sb.append("• ").append(award.getTitle())
                        .append(" — ").append(award.getDescription()).append("\n");
            }
            content.setText(sb.toString());
        }
        return Optional.of(content);
    }

    private String formatMoodLogs(List<MoodLog> logs, String title) {
        if (logs.isEmpty()) {
            return title + ":\nNo mood logs found.";
        }
        var sb = new StringBuilder(title + ":\n");
        logs.forEach(log -> {
            String formattedDate = formatter.format(Instant.ofEpochSecond(log.getCreatedAt()));
            sb.append(formattedDate).append(": ").append(log.getMood().getText()).append("\n");
        });
        return sb.toString();
    }
}