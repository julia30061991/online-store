package com.onlinestore.service;

import com.onlinestore.model.User;
import com.onlinestore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final String REGEX_EMAIL = "([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z0-9]+)+)";
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<User> getUserList() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User getUserInfo(int id) {
        return repository.getUserById(id);
    }

    @Override
    public User createUser(String name, String phone, String email) throws InvalidException {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setFullName(name);
        user.setPhoneNumber(phone);
        if (email.matches(REGEX_EMAIL)) {
            user.setEmail(email);
        } else {
            throw new InvalidException("Невалидный e-mail, проверьте корректность параметра");
        }
        repository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        repository.deleteUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(int id, String name, String phone, String email) throws InvalidException {
        User user = repository.getUserById(id);
        //поверка параметра "имя"
        if (name == null || name.isEmpty()) {
            user.setFullName(user.getFullName());
        } else user.setFullName(name);
        //поверка параметра "телефон"
        if (phone == null || phone.isEmpty()) {
            user.setPhoneNumber(user.getPhoneNumber());
        } else user.setPhoneNumber(phone);
        //поверка параметра "электронная почта"
        if (email == null || email.isEmpty()) {
            user.setEmail(user.getEmail());
        } else if (email.matches(REGEX_EMAIL)) {
            user.setEmail(email);
        } else {
            throw new InvalidException("Невалидный e-mail, проверьте корректность параметра");
        }
        repository.save(user);
    }

    public class InvalidException extends Exception {
        public InvalidException(String message) {
            super(message);
        }
    }
}