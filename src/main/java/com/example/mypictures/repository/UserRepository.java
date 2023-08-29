package com.example.mypictures.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.mypictures.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByPasswordHash(String passwordHash);

    User findByEmail(String email);

    User findByPhoneNumber(String PhoneNumber);

    User findByUserId(long id);
}
