package ru.myforum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.myforum.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
