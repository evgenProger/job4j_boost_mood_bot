package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Award;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AwardRepository extends CrudRepository<Award, Long> {
    
    List<Award> findAll();

    Optional<Award> findById(Long clientId);

    Award save(Award award);
}
