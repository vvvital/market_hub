package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.error.exception.UserExistException;
import com.teamchallenge.markethub.error.exception.UserNotFoundException;
import com.teamchallenge.markethub.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(User user);
    void remove(Integer id) throws UserNotFoundException;
    User findByEmail(String email);
    boolean existByEmailAndPhone(String email, String phone) throws UserExistException;
    User findById(Integer id) throws UserNotFoundException;
    List<User> findAll();
}
