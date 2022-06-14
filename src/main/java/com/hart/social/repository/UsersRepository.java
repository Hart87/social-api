package com.hart.social.repository;

import com.hart.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByEmail(String email);

}