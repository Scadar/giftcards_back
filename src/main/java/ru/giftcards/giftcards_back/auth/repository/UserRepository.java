package ru.giftcards.giftcards_back.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.giftcards.giftcards_back.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);

    User findByEmail(String email);
}
