package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Achievement;
import ru.job4j.model.MoodLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    List<Achievement> findAll();

    Optional<Achievement> findById(Long achievementId);

    Achievement save(Achievement achievement);
}
