package com.onlinestore.repository;

import com.onlinestore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {

    @Override
    List<User> findAll();

    User getUserById(int id);

    void deleteUserById(int id);

    @Override
    <S extends User> S save(S entity);
}
