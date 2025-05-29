package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.model.MoodLog;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoodLogRepository extends CrudRepository<MoodLog, Long> {

    List<MoodLog> findAll();

    Optional<MoodLog> findById(Long moodLogId);

    MoodLog save(MoodLog moodLog);

    @Query("select m from MoodLog m where m.user =:user_id and m.createdAt >= :after")
    List<MoodLog> findRecentLogs(@Param("user_id") long userId, @Param("after") long createAt);
}
